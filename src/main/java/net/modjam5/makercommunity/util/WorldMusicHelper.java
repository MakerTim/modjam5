package net.modjam5.makercommunity.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.modjam5.makercommunity.api.IWorldMusicHelper;
import net.modjam5.makercommunity.api.NumberRegistry;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author Tim Biesenbeek
 */
public class WorldMusicHelper implements IWorldMusicHelper {

	private final World world;
	private Map<MusicInfo, NumberPart> musicMap = new LinkedHashMap<>();

	public WorldMusicHelper(World world) {
		this.world = world;
	}

	public NumberPartPlaying getNumberStep(long tick, BlockPos position) {
		NumberPartPlaying thePart = null;
		int distanceSq = NumberRegistry.DISTANCE_NUMBERS_LINK;
		Iterator<Map.Entry<MusicInfo, NumberPart>> iterator = musicMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<MusicInfo, NumberPart> mapping = iterator.next();
			MusicInfo info = mapping.getKey();
			long tickTiming = NumberRegistry.getDuration(mapping.getValue().number);
			long ticksAgo = tick - info.tick;
			if (ticksAgo > tickTiming + 3) {
				iterator.remove();
				continue;
			}
			double distanceSqThis = info.pos.distanceSq(position);
			if (distanceSqThis < distanceSq) {
				distanceSq = (int) distanceSqThis;
				thePart = new NumberPartPlaying(mapping.getValue(), (int) (tickTiming - ticksAgo));
			}
		}
		if (thePart == null) {
			int number = new Random().nextInt(NumberRegistry.getRegisteredNumbersCount()) + 1;
			thePart = new NumberPartPlaying(number, -1, 0);
		}
		return thePart;
	}

	@Override
	public void log(BlockPos playLocation, NumberPart numberPart) {
		musicMap.put(new MusicInfo(world.getTotalWorldTime(), playLocation), numberPart);

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
