package net.modjam5.makercommunity.common;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

/**
 * @author Tim Biesenbeek
 */
public class Registry {

	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
	}

	public void init(FMLInitializationEvent event) {
		ItemRegistry.register();
	}

	public void startWorld(FMLServerStartingEvent event) {
	}
}
