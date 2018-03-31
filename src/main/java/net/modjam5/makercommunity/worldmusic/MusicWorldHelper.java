package net.modjam5.makercommunity.worldmusic;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author Tim Biesenbeek
 */
public class MusicWorldHelper {

	public static final int[] NUMBER_MAPPING = {2};
	public static final int NUMBERS = NUMBER_MAPPING.length;
	private static final int distanceLink = (int) Math.pow(100, 2);
	public static final int tickTiming = 5 * 20;
	private static final int _tickTiming = tickTiming + 3;
	private static Map<MusicInfo, NumberPart> musicMap = new LinkedHashMap<>();

	public static int nextPart(int number, int previous) {
		int maxParts = NUMBER_MAPPING[number - 1];
		if (++previous == maxParts) {
			previous = 0;
		}
		return previous;
	}

	public static NumberPartPlaying getNumberStep(World world, BlockPos position) {
		NumberPartPlaying thePart = null;
		int distanceSq = distanceLink;
		Iterator<Map.Entry<MusicInfo, NumberPart>> iterator = musicMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<MusicInfo, NumberPart> mapping = iterator.next();
			MusicInfo info = mapping.getKey();
			long ticksAgo = world.getTotalWorldTime() - info.tick;
			if (ticksAgo > _tickTiming) {
				iterator.remove();
				continue;
			}
			NumberPartPlaying part = new NumberPartPlaying(mapping.getValue(), tickTiming - (int) ticksAgo);
			double distanceSqThis = info.pos.distanceSq(position);
			if (distanceSqThis < distanceSq) {
				distanceSq = (int) distanceSqThis;
				return part; // thePart = part;
			}
		}
		if (thePart == null) {
			thePart = new NumberPartPlaying(new Random().nextInt(MusicWorldHelper.NUMBERS) + 1, -1, 0);
		}
		return thePart;
	}

	public static void logMap(World world, BlockPos playLocation, NumberPart nmbr) {
		musicMap.put(new MusicInfo(world.getTotalWorldTime(), playLocation), nmbr);
	}

	public static class NumberPartPlaying extends NumberPart {
		public final int wait;

		public NumberPartPlaying(int key, int part, int wait) {
			super(key, part);
			this.wait = wait;
		}

		public NumberPartPlaying(NumberPart value, int wait) {
			super(value.key, value.part);
			this.wait = wait;
		}
	}

	public static class NumberPart {
		public final int key;
		public final int part;

		public NumberPart(int key, int part) {
			this.key = key;
			this.part = part;
		}
	}

	private static class MusicInfo {
		private long tick;
		private BlockPos pos;

		private MusicInfo(long tick, BlockPos pos) {
			this.tick = tick;
			this.pos = pos;
		}
	}

}
