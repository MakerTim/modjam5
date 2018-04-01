package net.modjam5.makercommunity.common.world;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.modjam5.makercommunity.common.world.structure.BeachStructure;
import net.modjam5.makercommunity.common.world.structure.BoatStructure;
import net.modjam5.makercommunity.common.world.structure.Structure;
import net.modjam5.makercommunity.common.world.structure.VillageStructure;

/**
 * @author Tim Biesenbeek
 */
@SuppressWarnings("unchecked")
public class StructureRegister implements IWorldGenerator {

	public static final Class<? extends Structure>[] classes = new Class[]{
			BeachStructure.class, //
			VillageStructure.class, //
			BoatStructure.class //
	};
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

	public Structure byClass(Class<? extends Structure> cls) {
		for (Structure structure : structures) {
			if (structure.getClass() == cls) {
				return structure;
			}
		}
		throw new RuntimeException("DIDN'T REGISTER " + cls);
	}

	public static Optional<Class<? extends Structure>> findClassByName(String name) {
		return Arrays.stream(classes).filter(cls -> cls.getSimpleName().equals(name)).findFirst();
	}

	public Optional<BlockPos> findClosestStructure(Class<? extends Structure> structureClass, World world, BlockPos pos, boolean unvisited) {
		Optional<BlockPos> optionalBlockPos = Optional.empty();
		for (Structure structure : structures) {
			if (structure.getClass() != structureClass) {
				continue;
			}
			if (structure instanceof MapGenStructure) {
				optionalBlockPos = Optional
						.ofNullable(((MapGenStructure) structure).getNearestStructurePos(world, pos, unvisited));
			}

		}
		return optionalBlockPos;
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
