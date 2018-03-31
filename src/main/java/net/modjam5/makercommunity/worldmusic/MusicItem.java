package net.modjam5.makercommunity.worldmusic;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
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
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
		world.playSound(player, pos, SoundUtil.find(instrument), SoundCategory.PLAYERS, 1f, 1f);
		
		return EnumActionResult.SUCCESS;
	}
}
