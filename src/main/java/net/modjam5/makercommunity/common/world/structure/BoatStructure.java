package net.modjam5.makercommunity.common.world.structure;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.monster.EntityIllusionIllager;
import net.minecraft.init.Biomes;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
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
public class BoatStructure extends MapGenStructure implements Structure {

	private static final StructureBoundingBox boundingBox = new StructureBoundingBox(new int[]{0, 0, 0, 7, 10, 17});
	private static final List<Biome> ALLOWED_BIOMES = Collections.singletonList(Biomes.OCEAN);

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
		return "Boat";
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
	public boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
		Random random = this.world.setRandomSeed(2304 * chunkX, 1996 * chunkZ, 905);
		boolean rand = random.nextInt(1000) + 1 <= 2;
		boolean biome = this.world.getBiomeProvider().areBiomesViable(chunkX * 16 + 8, chunkZ * 16 + 8, 1,
			ALLOWED_BIOMES);
		return rand && biome;
	}

	@Override
	protected StructureStart getStructureStart(int chunkX, int chunkZ) {
		return new Start(this, chunkX, chunkZ);
	}

	@SuppressWarnings("deprecation")
	public void generate(World world, Random random, int xPos, int zPos) {
		int i = xPos + random.nextInt(15);
		int k = zPos + random.nextInt(15);

		int chunkX = i >> 4;
		int chunkZ = k >> 4;
		int height = world.getChunkFromChunkCoords(chunkX, chunkZ).getHeight(new BlockPos(i & 0xF, 0, k & 0xF));

		int j = height - getBoundingBox().maxY;

		EntityIllusionIllager illusionIllager = new EntityIllusionIllager(world);
		illusionIllager.setInvisible(true);
		illusionIllager.setLocationAndAngles(i + 4, j + 10.0, k + 15, world.rand.nextFloat() * 360F, 0);
		world.spawnEntity(illusionIllager);
		illusionIllager.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.DIAMOND_HELMET));
		illusionIllager.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.DIAMOND_CHESTPLATE));
		illusionIllager.setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(Items.DIAMOND_LEGGINGS));
		illusionIllager.setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(Items.DIAMOND_BOOTS));
		illusionIllager.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(ItemRegistry.recorders[2]));
		illusionIllager.setDropChance(EntityEquipmentSlot.HEAD, 0f);
		illusionIllager.setDropChance(EntityEquipmentSlot.CHEST, 0f);
		illusionIllager.setDropChance(EntityEquipmentSlot.LEGS, 0f);
		illusionIllager.setDropChance(EntityEquipmentSlot.FEET, 0f);
		illusionIllager.setDropChance(EntityEquipmentSlot.OFFHAND, 100f);
		illusionIllager.setCustomNameTag("NED");
		illusionIllager.enablePersistence();
		illusionIllager.setAlwaysRenderNameTag(false);

		BlockPos framePos = new BlockPos(i + 4.5, j + 7.5, k + 10);
		EntityItemFrame entityitemframe = new EntityItemFrame(world, framePos, EnumFacing.SOUTH);
		// TODO: change type map
		entityitemframe.setDisplayedItem(MapStructureHelper
				.buildMapFor(Registry.structureRegister.byClass(VillageStructure.class), world, framePos));
		world.spawnEntity(entityitemframe);

		EntityArmorStand armorStand = new EntityArmorStand(world, i + 4.5, j + -1, k + 10);
		ItemStack scubaHelmet = new ItemStack(ItemRegistry.scubaHelmet);
		scubaHelmet.addEnchantment(Enchantments.RESPIRATION, 5);
		scubaHelmet.addEnchantment(Enchantments.AQUA_AFFINITY, 5);
		scubaHelmet.addEnchantment(Enchantments.BINDING_CURSE, 0);
		armorStand.setItemStackToSlot(EntityEquipmentSlot.HEAD, scubaHelmet);
		armorStand.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ItemRegistry.recorders[2]));
		world.spawnEntity(armorStand);

		world.setBlockState(new BlockPos(i + 4, j + 0, k + 8), Block.getBlockById(5).getStateFromMeta(2), 3);
		world.setBlockState(new BlockPos(i + 1, j + 1, k + 8), Block.getBlockById(35).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 2, j + 1, k + 8), Block.getBlockById(35).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 3, j + 1, k + 8), Block.getBlockById(35).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 4, j + 1, k + 8), Block.getBlockById(5).getStateFromMeta(2), 3);
		world.setBlockState(new BlockPos(i + 5, j + 1, k + 8), Block.getBlockById(35).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 7, j + 1, k + 8), Block.getBlockById(35).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 3, j + 2, k + 8), Block.getBlockById(35).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 4, j + 2, k + 8), Block.getBlockById(5).getStateFromMeta(2), 3);
		world.setBlockState(new BlockPos(i + 5, j + 2, k + 8), Block.getBlockById(35).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 6, j + 2, k + 8), Block.getBlockById(35).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 1, j + 3, k + 8), Block.getBlockById(35).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 2, j + 3, k + 8), Block.getBlockById(35).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 3, j + 3, k + 8), Block.getBlockById(35).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 4, j + 3, k + 8), Block.getBlockById(5).getStateFromMeta(2), 3);
		world.setBlockState(new BlockPos(i + 5, j + 3, k + 8), Block.getBlockById(35).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 6, j + 3, k + 8), Block.getBlockById(35).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 7, j + 3, k + 8), Block.getBlockById(35).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 0, j + 4, k + 9), Block.getBlockById(35).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 4, j + 4, k + 9), Block.getBlockById(5).getStateFromMeta(2), 3);
		world.setBlockState(new BlockPos(i + 4, j + 5, k + 9), Block.getBlockById(5).getStateFromMeta(2), 3);
		world.setBlockState(new BlockPos(i + 4, j + 6, k + 9), Block.getBlockById(5).getStateFromMeta(2), 3);
		world.setBlockState(new BlockPos(i + 4, j + 7, k + 0), Block.getBlockById(126).getStateFromMeta(8), 3);
		world.setBlockState(new BlockPos(i + 4, j + 7, k + 1), Block.getBlockById(126).getStateFromMeta(8), 3);
		world.setBlockState(new BlockPos(i + 4, j + 7, k + 2), Block.getBlockById(126).getStateFromMeta(8), 3);
		world.setBlockState(new BlockPos(i + 3, j + 7, k + 3), Block.getBlockById(85).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 4, j + 7, k + 3), Block.getBlockById(126).getStateFromMeta(8), 3);
		world.setBlockState(new BlockPos(i + 5, j + 7, k + 3), Block.getBlockById(85).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 3, j + 7, k + 4), Block.getBlockById(85).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 5, j + 7, k + 4), Block.getBlockById(85).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 2, j + 7, k + 5), Block.getBlockById(85).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 6, j + 7, k + 5), Block.getBlockById(85).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 2, j + 7, k + 6), Block.getBlockById(85).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 6, j + 7, k + 6), Block.getBlockById(85).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 4, j + 7, k + 9), Block.getBlockById(5).getStateFromMeta(2), 3);
		world.setBlockState(new BlockPos(i + 2, j + 7, k + 13), Block.getBlockById(85).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 6, j + 7, k + 13), Block.getBlockById(85).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 2, j + 7, k + 14), Block.getBlockById(85).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 6, j + 7, k + 14), Block.getBlockById(85).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 2, j + 7, k + 15), Block.getBlockById(85).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 3, j + 7, k + 15), Block.getBlockById(85).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 5, j + 7, k + 15), Block.getBlockById(85).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 6, j + 7, k + 15), Block.getBlockById(85).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 4, j + 7, k + 16), Block.getBlockById(85).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 3, j + 8, k + 3), Block.getBlockById(53).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 4, j + 8, k + 3), Block.getBlockById(53).getStateFromMeta(2), 3);
		world.setBlockState(new BlockPos(i + 5, j + 8, k + 3), Block.getBlockById(53).getStateFromMeta(1), 3);
		world.setBlockState(new BlockPos(i + 3, j + 8, k + 4), Block.getBlockById(53).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 5, j + 8, k + 4), Block.getBlockById(53).getStateFromMeta(2), 3);
		world.setBlockState(new BlockPos(i + 2, j + 8, k + 5), Block.getBlockById(53).getStateFromMeta(2), 3);
		world.setBlockState(new BlockPos(i + 6, j + 8, k + 5), Block.getBlockById(53).getStateFromMeta(2), 3);
		world.setBlockState(new BlockPos(i + 2, j + 8, k + 6), Block.getBlockById(53).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 6, j + 8, k + 6), Block.getBlockById(53).getStateFromMeta(1), 3);
		world.setBlockState(new BlockPos(i + 2, j + 8, k + 7), Block.getBlockById(53).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 6, j + 8, k + 7), Block.getBlockById(53).getStateFromMeta(1), 3);
		world.setBlockState(new BlockPos(i + 2, j + 8, k + 8), Block.getBlockById(53).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 3, j + 8, k + 8), Block.getBlockById(19).getStateFromMeta(1), 3);
		world.setBlockState(new BlockPos(i + 5, j + 8, k + 8), Block.getBlockById(19).getStateFromMeta(1), 3);
		world.setBlockState(new BlockPos(i + 6, j + 8, k + 8), Block.getBlockById(53).getStateFromMeta(1), 3);
		world.setBlockState(new BlockPos(i + 2, j + 8, k + 9), Block.getBlockById(53).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 3, j + 8, k + 9), Block.getBlockById(19).getStateFromMeta(1), 3);
		world.setBlockState(new BlockPos(i + 4, j + 8, k + 9), Block.getBlockById(5).getStateFromMeta(2), 3);
		world.setBlockState(new BlockPos(i + 5, j + 8, k + 9), Block.getBlockById(19).getStateFromMeta(1), 3);
		world.setBlockState(new BlockPos(i + 6, j + 8, k + 9), Block.getBlockById(53).getStateFromMeta(1), 3);
		world.setBlockState(new BlockPos(i + 2, j + 8, k + 10), Block.getBlockById(53).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 3, j + 8, k + 10), Block.getBlockById(19).getStateFromMeta(1), 3);
		world.setBlockState(new BlockPos(i + 5, j + 8, k + 10), Block.getBlockById(19).getStateFromMeta(1), 3);
		world.setBlockState(new BlockPos(i + 6, j + 8, k + 10), Block.getBlockById(53).getStateFromMeta(1), 3);
		world.setBlockState(new BlockPos(i + 2, j + 8, k + 11), Block.getBlockById(53).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 6, j + 8, k + 11), Block.getBlockById(53).getStateFromMeta(1), 3);
		world.setBlockState(new BlockPos(i + 2, j + 8, k + 12), Block.getBlockById(53).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 6, j + 8, k + 12), Block.getBlockById(53).getStateFromMeta(1), 3);
		world.setBlockState(new BlockPos(i + 2, j + 8, k + 13), Block.getBlockById(53).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 3, j + 8, k + 13), Block.getBlockById(23).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 5, j + 8, k + 13), Block.getBlockById(23).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 6, j + 8, k + 13), Block.getBlockById(53).getStateFromMeta(1), 3);
		world.setBlockState(new BlockPos(i + 2, j + 8, k + 14), Block.getBlockById(53).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 3, j + 8, k + 14), Block.getBlockById(23).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 5, j + 8, k + 14), Block.getBlockById(23).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 6, j + 8, k + 14), Block.getBlockById(53).getStateFromMeta(1), 3);
		world.setBlockState(new BlockPos(i + 2, j + 8, k + 15), Block.getBlockById(53).getStateFromMeta(3), 3);
		world.setBlockState(new BlockPos(i + 3, j + 8, k + 15), Block.getBlockById(5).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 4, j + 8, k + 15), Block.getBlockById(5).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 5, j + 8, k + 15), Block.getBlockById(5).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 6, j + 8, k + 15), Block.getBlockById(53).getStateFromMeta(3), 3);
		world.setBlockState(new BlockPos(i + 4, j + 8, k + 16), Block.getBlockById(85).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 4, j + 9, k + 4), Block.getBlockById(17).getStateFromMeta(8), 3);
		world.setBlockState(new BlockPos(i + 3, j + 9, k + 5), Block.getBlockById(53).getStateFromMeta(2), 3);
		world.setBlockState(new BlockPos(i + 4, j + 9, k + 5), Block.getBlockById(17).getStateFromMeta(8), 3);
		world.setBlockState(new BlockPos(i + 5, j + 9, k + 5), Block.getBlockById(53).getStateFromMeta(2), 3);
		world.setBlockState(new BlockPos(i + 3, j + 9, k + 6), Block.getBlockById(53).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 4, j + 9, k + 6), Block.getBlockById(17).getStateFromMeta(8), 3);
		world.setBlockState(new BlockPos(i + 5, j + 9, k + 6), Block.getBlockById(53).getStateFromMeta(1), 3);
		world.setBlockState(new BlockPos(i + 3, j + 9, k + 7), Block.getBlockById(53).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 4, j + 9, k + 7), Block.getBlockById(17).getStateFromMeta(8), 3);
		world.setBlockState(new BlockPos(i + 5, j + 9, k + 7), Block.getBlockById(53).getStateFromMeta(1), 3);
		world.setBlockState(new BlockPos(i + 3, j + 9, k + 8), Block.getBlockById(53).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 4, j + 9, k + 8), Block.getBlockById(17).getStateFromMeta(8), 3);
		world.setBlockState(new BlockPos(i + 5, j + 9, k + 8), Block.getBlockById(53).getStateFromMeta(1), 3);
		world.setBlockState(new BlockPos(i + 3, j + 9, k + 9), Block.getBlockById(53).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 4, j + 9, k + 9), Block.getBlockById(17).getStateFromMeta(8), 3);
		world.setBlockState(new BlockPos(i + 5, j + 9, k + 9), Block.getBlockById(53).getStateFromMeta(1), 3);
		world.setBlockState(new BlockPos(i + 3, j + 9, k + 10), Block.getBlockById(53).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 4, j + 9, k + 10), Block.getBlockById(17).getStateFromMeta(8), 3);
		world.setBlockState(new BlockPos(i + 5, j + 9, k + 10), Block.getBlockById(53).getStateFromMeta(1), 3);
		world.setBlockState(new BlockPos(i + 3, j + 9, k + 11), Block.getBlockById(53).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 4, j + 9, k + 11), Block.getBlockById(17).getStateFromMeta(8), 3);
		world.setBlockState(new BlockPos(i + 5, j + 9, k + 11), Block.getBlockById(53).getStateFromMeta(1), 3);
		world.setBlockState(new BlockPos(i + 3, j + 9, k + 12), Block.getBlockById(53).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 4, j + 9, k + 12), Block.getBlockById(17).getStateFromMeta(8), 3);
		world.setBlockState(new BlockPos(i + 5, j + 9, k + 12), Block.getBlockById(53).getStateFromMeta(1), 3);
		world.setBlockState(new BlockPos(i + 3, j + 9, k + 13), Block.getBlockById(53).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 4, j + 9, k + 13), Block.getBlockById(17).getStateFromMeta(8), 3);
		world.setBlockState(new BlockPos(i + 5, j + 9, k + 13), Block.getBlockById(53).getStateFromMeta(1), 3);
		world.setBlockState(new BlockPos(i + 3, j + 9, k + 14), Block.getBlockById(53).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 4, j + 9, k + 14), Block.getBlockById(17).getStateFromMeta(8), 3);
		world.setBlockState(new BlockPos(i + 5, j + 9, k + 14), Block.getBlockById(53).getStateFromMeta(1), 3);
		world.setBlockState(new BlockPos(i + 3, j + 9, k + 15), Block.getBlockById(53).getStateFromMeta(3), 3);
		world.setBlockState(new BlockPos(i + 4, j + 9, k + 15), Block.getBlockById(53).getStateFromMeta(3), 3);
		world.setBlockState(new BlockPos(i + 5, j + 9, k + 15), Block.getBlockById(53).getStateFromMeta(3), 3);
		world.setBlockState(new BlockPos(i + 4, j + 9, k + 16), Block.getBlockById(85).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 4, j + 10, k + 16), Block.getBlockById(85).getStateFromMeta(0), 3);
		world.setBlockState(new BlockPos(i + 4, j + 10, k + 17), Block.getBlockById(5).getStateFromMeta(0), 3);
	}
}