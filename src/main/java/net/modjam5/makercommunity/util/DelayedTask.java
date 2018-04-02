package net.modjam5.makercommunity.util;

import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * @author Tim Biesenbeek
 */
public class DelayedTask implements Runnable {

	private long delay;
	private Runnable function;
	private World world;

	public DelayedTask(Runnable function) {
		this(function, 20);
	}

	public DelayedTask(Runnable function, long delay) {
		this.delay = delay;
		this.function = function;
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onTick(TickEvent.WorldTickEvent event) {
		if (world == null) {
			world = event.world;
		}
		if (event.phase == TickEvent.Phase.START && event.world == world) {
			run();
		}
	}

	@Override
	public void run() {
		if (--delay < 0) {
			function.run();
			destroy();
		}
	}

	protected void destroy() {
		function = () -> {
		};
		MinecraftForge.EVENT_BUS.unregister(this);
	}
}
