package net.modjam5.makercommunity;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.modjam5.makercommunity.common.Registry;

@Mod(modid = BaseMod.MODID, name = BaseMod.NAME, version = BaseMod.VERSION)
public class BaseMod {
	public static final String MODID = "submineofthieves";
	public static final String NAME = "SubMine of Thieves";
	public static final String VERSION = "0.1";

	public static Logger logger;

	@SidedProxy(clientSide = "net.modjam5.makercommunity.client.ClientRegistry", serverSide = "net.modjam5.makercommunity.server.ServerRegistry")
	private static Registry registry;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		registry.preInit(event);
	}

	@EventHandler
	public void initServer(FMLInitializationEvent event) {
		registry.init(event);
	}

	@EventHandler
	public void postInitServer(FMLPostInitializationEvent event) {
		registry.postInit(event);
	}

	@EventHandler
	public void startWorld(FMLServerStartingEvent event) {
		registry.startWorld(event);
	}

}
