package net.modjam5.makercommunity.worldmusic;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.modjam5.makercommunity.BaseMod;
import net.modjam5.makercommunity.common.Instrument;
import net.modjam5.makercommunity.util.SoundUtil;

/**
 * @author Tim Biesenbeek
 */
public class MusicItem extends Item {

	private Instrument instrument;

	public MusicItem(String name, Instrument instrument) {
		this.instrument = instrument;
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
		MusicWorldHelper.NumberPartPlaying numberPart = MusicWorldHelper.getNumberStep(world, pos);
		if (numberPart.wait > 5) {
			player.getCooldownTracker().setCooldown(this, numberPart.wait - 1);
			return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));
		}
		int part = MusicWorldHelper.nextPart(numberPart.key, numberPart.part);
		MusicWorldHelper.logMap(world, pos, new MusicWorldHelper.NumberPart(numberPart.key, part));
		world.playSound(player, pos, SoundUtil.find(instrument, part), SoundCategory.PLAYERS, 0.5f, 1f);
		player.getCooldownTracker().setCooldown(this, 5 * 20);

		return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));
	}
}
