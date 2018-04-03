package net.modjam5.makercommunity.common.world.structure;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureStart;

/**
 * @author Tim Biesenbeek
 */
public interface Structure {
	
	default int getColor(){
		return 7498810;
	}
	
	void generate(World world, Random random, int i2, int k2);

	StructureBoundingBox getBoundingBox();

	boolean canSpawnStructureAtCoords2(int chunkX, int chunkZ);

	default boolean hasSpace(World world, int xPos, int yPos, int zPos) {
		StructureBoundingBox boundingBox = getBoundingBox();
		boolean place = true;
		for (int y = boundingBox.minY; y < boundingBox.maxY; y++) {
			for (int z = boundingBox.minZ; z < boundingBox.maxZ; z++) {
				for (int x = boundingBox.minX; x < boundingBox.maxX; x++) {
					if (world.getBlockState(new BlockPos(xPos + x, yPos + y, zPos + z)).getBlock() != Blocks.AIR) {
						place = false;
					}
				}
			}
		}
		return place;
	}

	void updateWorld(World world);

	class Start extends StructureStart {

		protected Structure structure;

		public Start(Structure structure, int xChunk, int zChunk) {
			super(xChunk, zChunk);
			this.structure = structure;
		}

		@Override
		public StructureBoundingBox getBoundingBox() {
			return structure.getBoundingBox();
		}

		@Override
		public void generateStructure(World worldIn, Random rand, StructureBoundingBox bb) {
			structure.generate(worldIn, rand, getChunkPosX() * 16, getChunkPosX() * 16);
		}
	}
}
