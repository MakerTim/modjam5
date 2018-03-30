package net.modjam5.makercommunity.client;

import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.modjam5.makercommunity.common.Registry;
import net.modjam5.makercommunity.util.SoundUtil;
import net.modjam5.makercommunity.worldmusic.MusicWorldHelper;

import java.util.Optional;

/**
 * @author Tim Biesenbeek
 */
public class ClientRegistry extends Registry {

	private static final String[] instruments = {"drums", "flute", "guitar", "steeldrum", "whistle"};

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		registerSounds();
	}

	private void registerSounds() {
		for (int i = 1; i <= MusicWorldHelper.NUMBERS; i++) {
			for (String instrument : instruments) {
				SoundUtil.register("insturment." + i + "." + instrument);
			}
		}
	}
}
