package net.modjam5.makercommunity.util;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.modjam5.makercommunity.BaseMod;
import net.modjam5.makercommunity.api.ISoundUtil;
import net.modjam5.makercommunity.api.Instrument;
import net.modjam5.makercommunity.api.NumberRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

/**
 * @author Tim Biesenbeek
 */
public class SoundUtil implements ISoundUtil {

	private final Map<String, SoundEvent> sounds = new HashMap<>();

	public SoundEvent register(String name) {
		ResourceLocation resourceLocation = new ResourceLocation(BaseMod.MODID, name);
		SoundEvent soundEvent = new SoundEvent(resourceLocation);
		soundEvent.setRegistryName(name);
		ForgeRegistries.SOUND_EVENTS.register(soundEvent);
		return soundEvent;
	}

	public void register(Instrument instrument, int number) {
		for (int part = 0; part < NumberRegistry.getTotalParts(number); part++) {
			String name = ISoundUtil.toResourceName(instrument, number, part);
			sounds.put(name, register(name));
		}
	}

	public SoundEvent find(Instrument instrument, int part) {
		int key = new Random().nextInt(NumberRegistry.getRegisteredNumbersCount()) + 1;
		Optional<SoundEvent> optionalSoundEvent = find(instrument, key, part);
		return optionalSoundEvent.orElse(SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.villager.no")));
	}

	public Optional<SoundEvent> find(Instrument instrument, int key, int part) {
		return Optional.ofNullable(sounds.get(ISoundUtil.toResourceName(instrument, key, part)));
	}

	public Optional<SoundEvent> find(String name) {
		return Optional.ofNullable(sounds.get(name));
	}

}
