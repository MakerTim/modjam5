package net.modjam5.makercommunity.common.world.structure;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.modjam5.makercommunity.common.ItemRegistry;
import net.modjam5.makercommunity.util.MapStructureHelper;

/**
 * @author Tim Biesenbeek
 */
public abstract class AbstractEndDeepStructure extends AbstractDeepStructure {

	protected static final int[] distance = new int[]{15, 10, 8, 5, 1, 0};

	@Override
	protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
		return canSpawnStructureAtCoords2(chunkX, chunkZ);
	}
	@Override
	public abstract String getStructureName();

	protected abstract int chunksDistance();

	@Override
	public boolean canSpawnStructureAtCoords2(int chunkX, int chunkZ) {
		chunkZ -= distance[chunksDistance()];
		return EndStructure.canSpawnStructureAtCoords(world, chunkX, chunkZ);
	}

	@Override
	protected ItemStack holdingItem() {
		return new ItemStack(ItemRegistry.recorders[7 + chunksDistance()]);
	}

	@Override
	protected IBlockState mainBlock() {
		if (chunksDistance() == 0) {
			return super.mainBlock();
		}
		return Blocks.PUMPKIN.getDefaultState();
	}

	@Override
	protected ItemStack getMap(World world, BlockPos pos) {
		int zOff = (distance[chunksDistance()] - distance[chunksDistance() + 1]) * 16;
		pos = pos.offset(EnumFacing.NORTH, zOff);
		return MapStructureHelper.buildMapFor(world, pos, getClass().getSimpleName(), getColor());
	}
}