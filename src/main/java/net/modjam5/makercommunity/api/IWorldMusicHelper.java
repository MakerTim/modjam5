package net.modjam5.makercommunity.api;

import net.minecraft.util.math.BlockPos;

/**
 * @author Tim Biesenbeek
 */
public interface IWorldMusicHelper {

	NumberPartPlaying getNumberStep(long tick, BlockPos position);

	void log(BlockPos playLocation, NumberPart numberPart);

	class NumberPartPlaying extends NumberPart {
		public final int wait;

		public NumberPartPlaying(int key, int part, int wait) {
			super(key, part);
			this.wait = wait;
		}

        public NumberPartPlaying(NumberPart value, int wait) {
			super(value.number, value.part);
			this.wait = wait;
		}
	}

	class NumberPart {
		public final int number;
		public final int part;

		public NumberPart(int number, int part) {
			this.number = number;
			this.part = part;
		}
	}
}
