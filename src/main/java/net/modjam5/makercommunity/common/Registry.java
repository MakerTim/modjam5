package net.modjam5.makercommunity.common;

import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.modjam5.makercommunity.BaseMod;
import net.modjam5.makercommunity.common.command.LocateSubMineOfThieves;
import net.modjam5.makercommunity.common.entity.EntityTriggerArmorStand;
import net.modjam5.makercommunity.common.world.StructureRegister;
import net.modjam5.makercommunity.util.SoundUtil;

/**
 * @author Tim Biesenbeek
 */
public class Registry {

	public static StructureRegister structureRegister;

	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
	}

	public void init(FMLInitializationEvent event) {
		EntityRegistry.registerModEntity(new ResourceLocation(BaseMod.MODID, "triggerarmorstand"),
			EntityTriggerArmorStand.class, "triggerarmorstand", 1, BaseMod.MODID, 64, 1, false);
		GameRegistry.registerWorldGenerator((structureRegister = new StructureRegister()), 5);
		BlockRegistry.registerTileEntity();
		SoundUtil.register("portal_use");
		SoundUtil.register("portal_ambient");
	}

	public void postInit(FMLPostInitializationEvent event) {
	}

	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event) {
		ItemRegistry.register();
		event.getRegistry().registerAll(ItemRegistry.items);
		event.getRegistry().registerAll(ItemRegistry.recorders);
		event.getRegistry()
				.registerAll(Arrays.stream(BlockRegistry.blocks).map(ItemBlock::new)
						.map(item -> (ItemBlock)item.setUnlocalizedName("item." + item.getBlock().getUnlocalizedName()))
						.map(item -> (ItemBlock)item.setRegistryName("item." + item.getBlock().getUnlocalizedName()))
						.map(item -> (ItemBlock)item.setCreativeTab(CreativeTabs.BUILDING_BLOCKS))
						.toArray(ItemBlock[]::new));
	}

	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event) {
		BlockRegistry.register();
		event.getRegistry().registerAll(BlockRegistry.blocks);
	}

	public void startWorld(FMLServerStartingEvent event) {
		event.registerServerCommand(new LocateSubMineOfThieves());
	}
}
