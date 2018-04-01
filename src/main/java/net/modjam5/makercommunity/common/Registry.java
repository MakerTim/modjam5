package net.modjam5.makercommunity.common;

import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.modjam5.makercommunity.common.command.LocateSubMineOfThieves;
import net.modjam5.makercommunity.common.world.StructureRegister;

/**
 * @author Tim Biesenbeek
 */
public class Registry {

	public static StructureRegister structureRegister;

	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
	}

	public void init(FMLInitializationEvent event) {
		GameRegistry.registerWorldGenerator((structureRegister = new StructureRegister()), 5);
	}

	public void postInit(FMLPostInitializationEvent event) {
	}

	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Item> event) {
		ItemRegistry.register();
		event.getRegistry().registerAll(ItemRegistry.items);
		event.getRegistry().registerAll(ItemRegistry.recorders);
	}

	public void startWorld(FMLServerStartingEvent event) {
		event.registerServerCommand(new LocateSubMineOfThieves());
	}
}
