package net.modjam5.makercommunity.util;

import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.modjam5.makercommunity.common.ItemRegistry;

/**
 * @author Tim Biesenbeek
 */
public class ScubaHelmetHelper {

	public static ItemStack scuba() {
		ItemStack scubaHelmet = new ItemStack(ItemRegistry.scubaHelmet);
		scubaHelmet.addEnchantment(Enchantments.RESPIRATION, 5);
		scubaHelmet.addEnchantment(Enchantments.AQUA_AFFINITY, 5);
		scubaHelmet.addEnchantment(Enchantments.BINDING_CURSE, 0);
		return scubaHelmet;
	}
}
