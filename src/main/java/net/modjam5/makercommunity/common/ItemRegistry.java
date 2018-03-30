package net.modjam5.makercommunity.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.modjam5.makercommunity.BaseMod;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Tim Biesenbeek
 */
public class ItemRegistry {

	public static Item debug;

	public static void register() {
		debug = register("debug", (player) -> {
            System.out.println(player);
			return EnumActionResult.PASS;
		});
	}

	public static Item register(String name) {
		return register(name, null);
	}

	public static Item register(String name, Function<EntityPlayer, EnumActionResult> onClick) {
		Item item = new Item() {
			@Override
			public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
				if (onClick == null) {
					return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
				}
				return onClick.apply(player);
			}
		};
		item.setUnlocalizedName(name);
		item.setRegistryName(new ResourceLocation(BaseMod.MODID, name));
		item.setCreativeTab(CreativeTabs.TRANSPORTATION);
		return new Item();
	}

}
