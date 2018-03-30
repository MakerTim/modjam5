package net.modjam5.makercommunity.common;

/**
 * @author Tim Biesenbeek
 */
public enum Instrument {
	DRUMS, FLUTE, GUITAR, STEELDRUM, WHISTLE;

	public String toInstrumentName() {
		return name().toLowerCase();
	}
}
