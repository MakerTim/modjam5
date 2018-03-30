package net.modjam5.makercommunity.util;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.modjam5.makercommunity.BaseMod;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

	public static Optional<SoundEvent> find(String name) {
		return Optional.ofNullable(sounds.get(name));
	}
}
