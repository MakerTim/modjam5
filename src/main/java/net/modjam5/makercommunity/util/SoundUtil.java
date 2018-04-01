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
		return soundEvent;
	}

	public static void register(Instrument instrument, int number) {
		for (int part = 0; part < MusicWorldHelper.NUMBER_MAPPING[number - 1]; part++) {
			String name = toResourceName(instrument, number, part);
			sounds.put(name, register(name));
		}
	}

	public static SoundEvent find(Instrument instrument, int part) {
		int key = new Random().nextInt(MusicWorldHelper.NUMBERS) + 1;
		Optional<SoundEvent> optionalSoundEvent = find(instrument, key, part);
		return optionalSoundEvent.orElse(SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.villager.no")));
	}

	public static Optional<SoundEvent> find(Instrument instrument, int key, int part) {
		return Optional.ofNullable(sounds.get(toResourceName(instrument, key, part)));
	}

	public static Optional<SoundEvent> find(String name) {
		return Optional.ofNullable(sounds.get(name));
	}

	private static String toResourceName(Instrument instrument, int key, int part) {
		return "instrument_" + key + "_" + instrument.toInstrumentName() + "-" + part;
	}
}
