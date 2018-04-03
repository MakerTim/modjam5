package net.modjam5.makercommunity.common.block;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.modjam5.makercommunity.common.tile.TileEntityPortal;

/**
 * @author Tim Biesenbeek
 */
public class PortalBlock extends Block implements ITileEntityProvider {

	private static final AxisAlignedBB NO_BLOCK_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);

	public PortalBlock(Material blockMaterialIn, MapColor blockMapColorIn) {
		super(blockMaterialIn, blockMapColorIn);
		setBlockUnbreakable();
	}

	public PortalBlock(Material materialIn) {
		this(materialIn, MapColor.AIR);
	}

	public PortalBlock() {
		this(Material.BARRIER);
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
		return Block.FULL_BLOCK_AABB.offset(pos);
	}

	@Nullable
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return NO_BLOCK_AABB;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return NO_BLOCK_AABB;
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityPortal();
	}
}
