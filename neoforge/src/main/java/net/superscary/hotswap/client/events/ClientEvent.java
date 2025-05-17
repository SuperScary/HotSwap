package net.superscary.hotswap.client.events;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.event.entity.living.ArmorHurtEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerDestroyItemEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.superscary.hotswap.HotSwap;
import net.superscary.hotswap.config.defs.ToolSwap;
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

	public static void itemBreak (PlayerDestroyItemEvent event) {
		if (HotSwap.CONFIG.TOOLS.TOOL_SWAP_BEHAVIOR.get() != ToolSwap.BREAK) return;
		//HotSwapEvents.itemBreak(event.getEntity(), event.getEntity().getInventory(), event.getOriginal(), event.getEntity().getInventory().getSelectedSlot());
	}

	public static void armorBreak (ArmorHurtEvent event) {
		if (!(event.getEntity() instanceof Player player)) return;
		for (EquipmentSlot slot : EquipmentSlot.values()) {
			if (slot.getType() != EquipmentSlot.Type.HUMANOID_ARMOR) continue;
			ItemStack stack    = event.getArmorItemStack(slot);
			if (stack.isEmpty()) continue;                  // no piece there
			float damageToApply = event.getNewDamage(slot); // durability points about to be subtracted

			HotSwapEvents.armorBreak(player, event.getArmorItemStack(slot), damageToApply);
		}
	}

}
