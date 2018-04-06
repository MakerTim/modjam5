package makercommunity.api;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author Tim Biesenbeek
 */
public class NumberRegistry {

	public static final int DISTANCE_NUMBERS_LINK = (int) Math.pow(100, 2);
	private static final List<NumberEntry> NUMBER_MAPPING = new LinkedList<>();
	private static final Map<World, IWorldMusicHelper> MUSIC_HELPER = new HashMap<>();
	public static Function<World, IWorldMusicHelper> helperCreator;

	static {
		registerNumber(2, 5 * 20);
		registerNumber(2, 5 * 20 + 2);
		registerNumber(4, 5 * 20);

		MinecraftForge.EVENT_BUS.register(new WorldHelperCreator());
	}

	public static int registerNumber(int parts, long durationInTicks) {
		NUMBER_MAPPING.add(new NumberEntry(parts, durationInTicks));
		return NUMBER_MAPPING.size() - 1;
	}

	public static int getRegisteredNumbersCount() {
		return NUMBER_MAPPING.size();
	}

	// number index=1
	public static int getTotalParts(int number) {
		return NUMBER_MAPPING.get(number - 1).parts;
	}

	// number index=1
	public static long getDuration(int number) {
		return NUMBER_MAPPING.get(number - 1).duration;
	}

	// number index=1
	public static int nextPart(int number, int previous) {
		int parts = getTotalParts(number);
		if (++previous == parts) {
			previous = 0;
		}
		return previous;
	}

	public static IWorldMusicHelper.NumberPartPlaying getNumberStep(World world, BlockPos position) {
		return MUSIC_HELPER.get(world).getNumberStep(world.getTotalWorldTime(), position);
	}

	public static void log(World world, BlockPos pos, IWorldMusicHelper.NumberPart numberPart) {
		MUSIC_HELPER.get(world).log(pos, numberPart);
	}

	private static class WorldHelperCreator {

		private WorldHelperCreator() {
		}

		@SubscribeEvent
		public void load(WorldEvent.Load event) {
			MUSIC_HELPER.put(event.getWorld(), helperCreator.apply(event.getWorld()));
		}

		@SubscribeEvent
		public void unload(WorldEvent.Unload event) {
			MUSIC_HELPER.remove(event.getWorld());
		}

	}

	private static class NumberEntry {
		final int parts;
		final long duration;

		public NumberEntry(int parts, long duration) {
			this.parts = parts;
			this.duration = duration;
		}
	}
}
