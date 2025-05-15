package net.superscary.hotswap.client.events;

import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
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

	public static void holdOff (ClientTickEvent.Pre event) {
		HotSwapEvents.holdOff();
	}

	public static void playerJoin (PlayerEvent.PlayerLoggedInEvent event) {
		HotSwapEvents.playerJoin(event.getEntity());
	}

}
