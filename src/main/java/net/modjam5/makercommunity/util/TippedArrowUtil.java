package net.modjam5.makercommunity.util;

import java.util.Collection;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;

/**
 * @author Tim Biesenbeek
 */
public class TippedArrowUtil {

	public static ItemStack tippedArrow(PotionType potion, Collection<PotionEffect> customPotionEffects) {
		ItemStack itemstack = new ItemStack(Items.TIPPED_ARROW);
		PotionUtils.addPotionToItemStack(itemstack, potion);
		PotionUtils.appendEffects(itemstack, customPotionEffects);
		return itemstack;
	}
}
