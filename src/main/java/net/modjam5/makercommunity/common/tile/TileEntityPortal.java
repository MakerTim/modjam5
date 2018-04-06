package net.modjam5.makercommunity.common.tile;

import java.util.List;

import makercommunity.api.ISoundUtil;
import makercommunity.api.PortalBehaviour;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;

/**
 * @author Tim Biesenbeek
 */
public class TileEntityPortal extends TileEntity implements ITickable {

	private static final long ambientTiming = 38 * 20;

	@Override
	public boolean hasFastRenderer() {
		return true;
	}

	@SuppressWarnings("ConstantConditions")
	@Override
	public void update() {
		if (world.getTotalWorldTime() % ambientTiming == 0 && world.rand.nextBoolean()) {
			ISoundUtil.instance.get().find("portal_ambient").ifPresent(sound -> world.playSound((double) pos.getX(),
				(double) pos.getY(), (double) pos.getZ(), sound, SoundCategory.BLOCKS, 0.5f, 1, true));
		}
		List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, Block.FULL_BLOCK_AABB.offset(getPos()));
		if (items.isEmpty()) {
			return;
		}
		items.stream() //
				.filter(item -> item.getThrower() != null) //
				.filter(item -> !item.getItem().isEmpty()) //
				.forEach(item -> {
					EntityPlayer player = world.getPlayerEntityByName(item.getThrower());
					if (player == null) {
						return;
					}
					PortalBehaviour.portalFunction.accept(player, item);
				});
	}
}
