package net.modjam5.makercommunity.util;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * @author Tim Biesenbeek
 */
public class DelayedTask implements Runnable {

	private long delay;
	private Runnable function;

	public DelayedTask(Runnable function) {
		this(20, function);
	}

	public DelayedTask(long delay, Runnable function) {
		this.delay = delay;
		this.function = function;
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onTick(TickEvent.WorldTickEvent event) {
		if (event.phase == TickEvent.Phase.START) {
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