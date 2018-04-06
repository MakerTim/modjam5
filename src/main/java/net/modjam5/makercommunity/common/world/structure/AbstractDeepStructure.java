package net.modjam5.makercommunity.common.world.structure;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureStart;
import net.modjam5.makercommunity.common.entity.EntityTriggerArmorStand;

/**
 * @author Tim Biesenbeek
 */
public abstract class AbstractDeepStructure extends MapGenStructure implements Structure {

	protected static final StructureBoundingBox boundingBox = new StructureBoundingBox(new int[]{0, 0, 0, 1, 2, 1});
	protected static final List<Biome> ALLOWED_BIOMES = Collections.singletonList(Biomes.DEEP_OCEAN);

	@Override
	public void updateWorld(World world) {
		this.world = world;
	}

	@Override
	public StructureBoundingBox getBoundingBox(){
		return boundingBox;
	}

	@Override
	public abstract String getStructureName();

	@Nullable
	@Override
	public BlockPos getNearestStructurePos(World worldIn, BlockPos pos, boolean findUnexplored) {
		updateWorld(worldIn);
		return findNearestStructurePosBySpacing(worldIn, this, pos, 80, 20, 10387319, true, 100, findUnexplored);
	}

	@Override
	public abstract boolean canSpawnStructureAtCoords2(int chunkX, int chunkZ);

	protected abstract ItemStack holdingItem();

	protected abstract Class<? extends Entity> summonMob();

	protected abstract ItemStack getMap(World world, BlockPos pos);
	
	protected IBlockState mainBlock(){
		return Blocks.LIT_PUMPKIN.getDefaultState();
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
		Chunk chunk = world.getChunkFromChunkCoords(chunkX, chunkZ);
		int y;
		for (y = chunk.getHeight(new BlockPos(i & 0xF, 0, k & 0xF)) - 1; y > 10; y--) {
			Block block = chunk.getBlockState(i, y, k).getBlock();
			if (block == Blocks.WATER || block == Blocks.FLOWING_WATER) {
				continue;
			}
			break;
		}

		int j = y + 1;

		BlockPos framePos = new BlockPos(i + 1, j, k);
		EntityItemFrame entityitemframe = new EntityItemFrame(world, framePos, EnumFacing.EAST);
		entityitemframe.setDisplayedItem(getMap(world, framePos));
		world.spawnEntity(entityitemframe);

		EntityTriggerArmorStand armorStand = new EntityTriggerArmorStand(world, i + 0.5, j + 1, k + 0.5);
		armorStand.setSummonOnTrigger(summonMob());
		armorStand.setTickDelay(10 * 20);
		armorStand.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, holdingItem());
		world.spawnEntity(armorStand);

		world.setBlockState(new BlockPos(i, j, k), mainBlock(), 3);
		world.setBlockState(new BlockPos(i, j + 1, k), Blocks.AIR.getStateFromMeta(0), 10);
		world.setBlockState(new BlockPos(i, j + 2, k), Blocks.AIR.getStateFromMeta(0), 10);
	}
}