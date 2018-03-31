package net.modjam5.makercommunity.common.world;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.modjam5.makercommunity.common.world.structure.BeachStructure;
import net.modjam5.makercommunity.common.world.structure.Structure;

/**
 * @author Tim Biesenbeek
 */
@SuppressWarnings("unchecked")
public class StructureRegister implements IWorldGenerator {

	private static final Class<? extends Structure>[] classes = new Class[]{BeachStructure.class};
	private Structure[] structures = new Structure[classes.length];

	public StructureRegister() {
		for (int i = 0; i < classes.length; i++) {
			try {
				structures[i] = classes[i].newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		int x = chunkX * 16;
		int z = chunkZ * 16;
		for (Structure structure : structures) {
			structure.updateWorld(world);
			if (structure.canSpawnStructureAtCoords(chunkX, chunkZ)) {
				structure.generate(world, random, x, z);
			}
		}
	}
}
