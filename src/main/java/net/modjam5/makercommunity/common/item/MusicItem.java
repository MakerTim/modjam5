package net.modjam5.makercommunity.common.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.modjam5.makercommunity.BaseMod;
import net.modjam5.makercommunity.api.ISoundUtil;
import net.modjam5.makercommunity.api.Instrument;
import net.modjam5.makercommunity.api.NumberRegistry;
import net.modjam5.makercommunity.util.WorldMusicHelper;

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
		WorldMusicHelper.NumberPartPlaying numberPart = NumberRegistry.getNumberStep(world, pos);
		long tickTiming = NumberRegistry.getDuration(numberPart.number);
		if (numberPart.wait > 5 && numberPart.wait != tickTiming) {
			player.getCooldownTracker().setCooldown(this, numberPart.wait - 1);
			return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));
		}
		int part = NumberRegistry.nextPart(numberPart.number, numberPart.part);
		NumberRegistry.log(world, pos, new WorldMusicHelper.NumberPart(numberPart.number, part));
		ISoundUtil.instance.get().find(instrument, numberPart.number, part)
				.ifPresent(sound -> world.playSound(player, pos, sound, SoundCategory.PLAYERS, 3f, 1f));
		player.getCooldownTracker().setCooldown(this, 5 * 20);

		return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));
	}
}
