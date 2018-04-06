package net.modjam5.makercommunity.api;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Tim Biesenbeek
 */
public class Instrument {
	private static final Set<Instrument> values = new HashSet<>();
	public static final //
            Instrument DRUMS = new Instrument("DRUMS"), //
			FLUTE = new Instrument("FLUTE"), //
			GUITAR = new Instrument("GUITAR"), //
			STEELDRUM = new Instrument("STEELDRUM"), //
			WHISTLE = new Instrument("WHISTLE");//

	private String name;

	public Instrument(String name) {
		values.add(this);
		this.name = name;
	}

	public String name() {
		return name;
	}

	public String toInstrumentName() {
		return name().toLowerCase();
	}

	public static Instrument[] values() {
		return values.stream().toArray(Instrument[]::new);
	}
}
