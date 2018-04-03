package net.modjam5.makercommunity.common.tile;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.modjam5.makercommunity.util.SoundUtil;

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
			SoundUtil.find("portal_ambient").ifPresent(sound -> world.playSound((double) pos.getX(),
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
					int id = Item.REGISTRY.getIDForObject(item.getItem().getItem());
					if (id < 0) {
						return;
					}
					id++;
					Item newItem = Item.REGISTRY.getObjectById(id);
					if (newItem == null) {
						return;
					}
					int count = item.getItem().getCount();
					item.setItem(ItemStack.EMPTY);
					SoundUtil.find("portal_use").ifPresent(sound -> world.playSound((double) pos.getX(),
						(double) pos.getY(), (double) pos.getZ(), sound, SoundCategory.BLOCKS, 3f, 1, true));
					player.inventory.addItemStackToInventory(new ItemStack(newItem, count));
					player.setHealth(player.getHealth() - count);
				});
	}
}
