package net.modjam5.makercommunity.client;

import makercommunity.api.ISoundUtil;
import makercommunity.api.Instrument;
import makercommunity.api.NumberRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.modjam5.makercommunity.BaseMod;
import net.modjam5.makercommunity.common.ItemRegistry;
import net.modjam5.makercommunity.common.Registry;
import net.modjam5.makercommunity.common.entity.EntityTriggerArmorStand;

/**
 * @author Tim Biesenbeek
 */
public class ClientRegistry extends Registry {

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		registerSounds();

		RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
		renderManager.entityRenderMap.put(EntityTriggerArmorStand.class,
			renderManager.entityRenderMap.get(EntityArmorStand.class));
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}

	@SubscribeEvent
	public void onModelRegister(ModelRegistryEvent event) {
		for (Item item : ItemRegistry.items) {
			if (item == null || item.getRegistryName() == null) {
				continue;
			}
			ModelLoader.setCustomModelResourceLocation(item, 0,
				new ModelResourceLocation(item.getRegistryName(), null));
		}

		for (Item item : ItemRegistry.recorders) {
			ModelLoader.setCustomModelResourceLocation(item, 0,
				new ModelResourceLocation(new ResourceLocation(BaseMod.MODID, "recorder"), null));
		}
	}

	private void registerSounds() {
		for (int i = 1; i <= NumberRegistry.getRegisteredNumbersCount(); i++) {
			for (Instrument instrument : Instrument.values()) {
				ISoundUtil.instance.get().register(instrument, i);
			}
		}
	}
}
