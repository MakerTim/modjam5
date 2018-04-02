package net.modjam5.makercommunity.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;

/**
 * @author Tim Biesenbeek
 */
public class EnchantingHelper {

	public static ItemStack withEnchant(ItemStack is, Enchantment enchant, int lvl) {
		is.addEnchantment(enchant, lvl);
		return is;
	}

	public static ItemStack withEnchants(ItemStack is, Enchantment[] enchants, int[] levels) {
		if (enchants.length != levels.length) {
			throw new RuntimeException("enchants length not equal to levels length");
		}
		for (int i = 0; i < enchants.length; i++) {
			is = withEnchant(is, enchants[i], levels[i]);
		}
		return is;
	}
}
