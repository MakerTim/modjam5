package net.modjam5.makercommunity.api;

import java.util.Optional;

import net.minecraft.util.SoundEvent;
import net.modjam5.makercommunity.api.hack.NonFinal;

/**
 * @author Tim Biesenbeek
 */
public interface ISoundUtil {
	
	NonFinal<ISoundUtil> instance = new NonFinal<>();
	
	SoundEvent register(String name);

	void register(Instrument instrument, int number);

	SoundEvent find(Instrument instrument, int part);

	Optional<SoundEvent> find(Instrument instrument, int key, int part);

	Optional<SoundEvent> find(String name);

	static String toResourceName(Instrument instrument, int key, int part) {
		return "instrument_" + key + "_" + instrument.toInstrumentName() + "-" + part;
	}
}
