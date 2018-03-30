package net.modjam5.makercommunity.common;

import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author Tim Biesenbeek
 */
public class Registry {

	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
	}

	public void init(FMLInitializationEvent event) {

	}

	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Item> event) {
		ItemRegistry.register();
		event.getRegistry().registerAll(ItemRegistry.items);
	}

	public void startWorld(FMLServerStartingEvent event) {
	}
}
