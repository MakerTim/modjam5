package net.modjam5.makercommunity.common.world.structure;

import static net.minecraft.world.gen.structure.MapGenVillage.VILLAGE_SPAWN_BIOMES;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Biomes;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureStart;
import net.modjam5.makercommunity.common.ItemRegistry;
import net.modjam5.makercommunity.common.Registry;
import net.modjam5.makercommunity.util.MapStructureHelper;

/**
 * @author Tim Biesenbeek
 */
public class VillageStructure extends MapGenStructure implements Structure {

	private static final StructureBoundingBox boundingBox = new StructureBoundingBox(new int[]{0, 0, 0, 11, 6, 11});
	private static final List<Biome> ALLOWED_BIOMES = Collections.singletonList(Biomes.BEACH);

	private int distance = 32;

	@Override
	protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
		return canSpawnStructureAtCoords2(chunkX, chunkZ);
	}
	@Override
	public void updateWorld(World world) {
		this.world = world;
	}

	@Override
	public StructureBoundingBox getBoundingBox() {
		return boundingBox;
	}

	@Override
	public String getStructureName() {
		return "VillageIsland";
	}

	@Nullable
	@Override
	public BlockPos getNearestStructurePos(World worldIn, BlockPos pos, boolean findUnexplored) {
		this.world = worldIn;
		BiomeProvider biomeprovider = worldIn.getBiomeProvider();
		return biomeprovider.isFixedBiome() && biomeprovider.getFixedBiome() != Biomes.BEACH ? null
				: findNearestStructurePosBySpacing(worldIn, this, pos, 80, 20, 10387319, true, 100, findUnexplored);
	}

	@Override
	public boolean canSpawnStructureAtCoords2(int chunkX, int chunkZ) {
		int i = chunkX;
		int j = chunkZ;

		if (chunkX < 0) {
			chunkX -= this.distance - 1;
		}

		if (chunkZ < 0) {
			chunkZ -= this.distance - 1;
		}

		int k = chunkX / this.distance;
		int l = chunkZ / this.distance;
		Random random = this.world.setRandomSeed(k, l, 10387312);
		k = k * this.distance;
		l = l * this.distance;
		k = k + random.nextInt(this.distance - 8);
		l = l + random.nextInt(this.distance - 8);

		if (i == k && j == l) {
			boolean flag = this.world.getBiomeProvider().areBiomesViable(i * 16 + 8, j * 16 + 8, 0,
				VILLAGE_SPAWN_BIOMES);

			if (flag) {
				return random.nextBoolean();
			}
		}

		return false;
	}

	@Override
	protected StructureStart getStructureStart(int chunkX, int chunkZ) {
		return new Start(this, chunkX, chunkZ);
	}

	public void generate(World world, Random random, int xPos, int zPos) {
		int i = xPos + random.nextInt(15);
		int k = zPos + random.nextInt(15);

		int chunkX = i >> 4;
		int chunkZ = k >> 4;
		int height = world.getChunkFromChunkCoords(chunkX, chunkZ).getHeight(new BlockPos(i & 0xF, 0, k & 0xF)) + 35;

		int j = height - 1;
		Entity zombie = EntityList.newEntity(EntityZombie.class, world);
		if (zombie instanceof EntityZombie) {
			EntityZombie zomb = (EntityZombie) zombie;
			zomb.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(ItemRegistry.recorders[0]));
			zomb.setDropChance(EntityEquipmentSlot.HEAD, 100);
			zomb.setLocationAndAngles(i + 7.5, j + 11.0, k + 12.821350744582702, world.rand.nextFloat() * 360F, 0);
			zomb.setCustomNameTag("XJ9");
			zomb.enablePersistence();
			zomb.setAlwaysRenderNameTag(false);

			world.spawnEntity(zomb);
		}
		BlockPos framePos = new BlockPos(i + 12.5, j + 17.5, k + 8);
		EntityItemFrame entityitemframe = new EntityItemFrame(world, framePos, EnumFacing.SOUTH);
		entityitemframe.setDisplayedItem(
			MapStructureHelper.buildMapFor(Registry.structureRegister.byClass(BeachStructure.class), world, framePos));
		world.spawnEntity(entityitemframe);

		VillageStructure1.gen(world, i, j, k);
		VillageStructure2.gen(world, i, j, k);
	}
}