package makercommunity.api;

import java.util.function.BiConsumer;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;

/**
 * @author Tim Biesenbeek
 */
public class PortalBehaviour {

	public static BiConsumer<EntityPlayer, EntityItem> portalFunction = (player, item) -> {
		int id = Item.REGISTRY.getIDForObject(item.getItem().getItem());
		if (id < 0) {
			return;
		}
		id++;
		Item newItem = Item.REGISTRY.getObjectById(id);
		if (newItem == null) {
			return;
		}
		trade(item, new ItemStack(newItem, item.getItem().getCount()), player);
	};
	
	public static void trade(EntityItem get, ItemStack give, EntityPlayer player){
        int count = get.getItem().getCount();
        get.setItem(ItemStack.EMPTY);
        BlockPos pos = get.getPosition();
        ISoundUtil.instance.get().find("portal_use").ifPresent(sound -> get.getEntityWorld().playSound((double) pos.getX(), (double) pos.getY(),
                (double) pos.getZ(), sound, SoundCategory.BLOCKS, 3f, 1, true));
        player.inventory.addItemStackToInventory(give);
        player.setHealth(player.getHealth() - count);
    }
}
