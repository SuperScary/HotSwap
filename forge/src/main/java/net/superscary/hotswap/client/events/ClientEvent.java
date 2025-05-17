package net.superscary.hotswap.client.events;

import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.superscary.hotswap.events.HotSwapEvents;

public class ClientEvent {

	public static void onBeginBreak (PlayerEvent.HarvestCheck event) {
		HotSwapEvents.onBeginBreak(event.getEntity(), event.getTargetBlock(), event.getTargetBlock());
	}

	public static void attackEntity (AttackEntityEvent event) {
		HotSwapEvents.attackEntity(event.getEntity(), event.getTarget());
	}

	public static void finishBlockBreak (InputEvent.MouseButton.Pre event) {
		HotSwapEvents.finishBlockBreak(event.getButton(), event.getAction());
	}

	public static void toggleOnOff (InputEvent.Key event) {
		HotSwapEvents.toggleOnOff(event.getKey(), event.getAction());
	}

	public static void holdOff (TickEvent.ClientTickEvent.Pre event) {
		HotSwapEvents.holdOff();
	}

	public static void playerJoin (PlayerEvent.PlayerLoggedInEvent event) {
		HotSwapEvents.playerJoin(event.getEntity());
	}

}
