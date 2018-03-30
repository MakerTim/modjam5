package net.modjam5.makercommunity.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.modjam5.makercommunity.BaseMod;
import net.modjam5.makercommunity.common.Instrument;
import net.modjam5.makercommunity.worldmusic.MusicWorldHelper;

/**
 * @author Tim Biesenbeek
 */
public class SoundUtil {

	private static final Map<String, SoundEvent> sounds = new HashMap<>();

	public static SoundEvent register(String name) {
		ResourceLocation resourceLocation = new ResourceLocation(BaseMod.MODID, name);

		SoundEvent soundEvent = new SoundEvent(resourceLocation);
		soundEvent.setRegistryName(name);
		ForgeRegistries.SOUND_EVENTS.register(soundEvent);
		sounds.put(name, soundEvent);

		return soundEvent;
	}

	public static SoundEvent find(Instrument instrument) {
		Optional<SoundEvent> optionalSoundEvent = Optional.empty();
		while (!optionalSoundEvent.isPresent()) {
			int key = new Random().nextInt(MusicWorldHelper.NUMBERS) + 1;
			optionalSoundEvent = find(instrument, key);
		}
		return optionalSoundEvent.get();
	}

	public static Optional<SoundEvent> find(Instrument instrument, int key) {
		return Optional.ofNullable(sounds.get("insturment." + key + "." + instrument.toInstrumentName()));
	}

	public static Optional<SoundEvent> find(String name) {
		return Optional.ofNullable(sounds.get(name));
	}
}
