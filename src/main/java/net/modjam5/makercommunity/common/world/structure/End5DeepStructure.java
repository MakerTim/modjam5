package net.modjam5.makercommunity.common.world.structure;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.modjam5.makercommunity.common.ItemRegistry;
import net.modjam5.makercommunity.common.entity.EntityTriggerArmorStand;

/**
 * @author Tim Biesenbeek
 */
public class End5DeepStructure extends AbstractEndDeepStructure {

	@Override
	public String getStructureName() {
		return "DeepEnd4";
	}

	@Override
	protected int chunksDistance() {
		return 4;
	}

	@Override
	protected Class<? extends Entity> summonMob() {
		return EntityGuardian.class;
	}

	@Override
	@SuppressWarnings("deprecation")
	public void generate(World world, Random random, int xPos, int zPos) {
		int i = xPos + random.nextInt(15);
		int k = zPos + random.nextInt(15);

		int chunkX = i >> 4;
		int chunkZ = k >> 4;
		Chunk chunk = world.getChunkFromChunkCoords(chunkX, chunkZ);
		int height = chunk.getHeight(new BlockPos(i & 0xF, 0, k & 0xF)) - 1;
		int y;
		for (y = height; y > 10; y--) {
			Block block = chunk.getBlockState(i, y, k).getBlock();
			if (block == Blocks.WATER || block == Blocks.FLOWING_WATER) {
				continue;
			}
			break;
		}

		int j = (int) (y + ((height - y) / 2D));

		BlockPos framePos = new BlockPos(i + 1, j, k);
		EntityItemFrame entityitemframe = new EntityItemFrame(world, framePos, EnumFacing.EAST);
		entityitemframe.setDisplayedItem(getMap(world, framePos));
		world.spawnEntity(entityitemframe);

		EntityTriggerArmorStand armorStand = new EntityTriggerArmorStand(world, i + 0.5, j + 1, k + 0.5);
		armorStand.setSummonOnTrigger(summonMob());
		armorStand.setTickDelay(10 * 20);
		armorStand.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ItemRegistry.recorders[11]));
		world.spawnEntity(armorStand);

		world.setBlockState(new BlockPos(i, j, k), Blocks.PUMPKIN.getDefaultState(), 3);
		world.setBlockState(new BlockPos(i, j + 1, k), Blocks.AIR.getStateFromMeta(0), 10);
		world.setBlockState(new BlockPos(i, j + 2, k), Blocks.AIR.getStateFromMeta(0), 10);
	}
}