package net.modjam5.makercommunity.common.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.modjam5.makercommunity.BaseMod;

/**
 * @author Tim Biesenbeek
 */
public class RecordedItem extends Item {

	private SoundEvent musicFile;
	private long tickDuration;

	public RecordedItem(String name, SoundEvent musicFile, long tickDuration) {
		this.musicFile = musicFile;
		this.tickDuration = tickDuration;
		setMaxStackSize(1);
		setUnlocalizedName(BaseMod.MODID + "." + name);
		setRegistryName(new ResourceLocation(BaseMod.MODID, name));
		setCreativeTab(CreativeTabs.MISC);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if (!world.isRemote) {
			return super.onItemRightClick(world, player, hand);
		}
		BlockPos pos = player.getPosition();

		world.playSound(player, pos, musicFile, SoundCategory.PLAYERS, 3f, 1f);
		player.getCooldownTracker().setCooldown(this, (int) tickDuration);

		return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}

}
