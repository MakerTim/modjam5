package net.modjam5.makercommunity.client;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.modjam5.makercommunity.BaseMod;
import net.modjam5.makercommunity.common.Instrument;
import net.modjam5.makercommunity.common.ItemRegistry;
import net.modjam5.makercommunity.common.Registry;
import net.modjam5.makercommunity.util.SoundUtil;
import net.modjam5.makercommunity.worldmusic.MusicWorldHelper;

/**
 * @author Tim Biesenbeek
 */
public class ClientRegistry extends Registry {

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		registerSounds();

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
		for (int i = 1; i <= MusicWorldHelper.NUMBERS; i++) {
			for (Instrument instrument : Instrument.values()) {
				SoundUtil.register(instrument, i);
			}
		}
	}
}
