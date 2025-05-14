package net.superscary.hotswap.client.events;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.superscary.hotswap.Config;
import net.superscary.hotswap.HotSwap;
import net.superscary.hotswap.api.tool.ToolHelper;
import net.superscary.hotswap.keybinding.Keybindings;

import static net.superscary.hotswap.Constants.MOD_ID;
import static net.superscary.hotswap.HotSwap.IS_ALPHA;

@SuppressWarnings("unused")
@EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ClientEvent {

	private static boolean enabled = true;
	private static int currentSelected = -1;
	private static int newSelected = -1;
	private static boolean modified = false;
	private static boolean heldKey = false;
	private static boolean allow = true;
	private static Block oldBlock = null;

	@SubscribeEvent
	public static void onBeginBreak (PlayerEvent.HarvestCheck event) {
		if (!allow || !enabled) return;
		if ((event.getEntity().isCreative() == Config.allowInCreative) || Config.allowInSurvival) {
			oldBlock = event.getTargetBlock().getBlock();
			if (!modified || oldBlock == event.getTargetBlock().getBlock() || oldBlock != null) {
				modified = true;
				if (oldBlock != event.getTargetBlock().getBlock() && !heldKey)
					currentSelected = event.getEntity().getInventory().getSelectedSlot();
				newSelected = ToolHelper.getBestToolFor(event.getTargetBlock(), event.getEntity());
				event.getEntity().getInventory().setSelectedSlot(newSelected);
			}
		}
	}

	@SubscribeEvent
	public static void attackEntity (AttackEntityEvent event) {
		if (!allow || !Config.allowForAttacking || !enabled) return;
		currentSelected = event.getEntity().getInventory().getSelectedSlot();
		newSelected = ToolHelper.getBestWeaponFor(event.getEntity(), event.getTarget(), Config.allowAxe);
		event.getEntity().getInventory().setSelectedSlot(newSelected);
	}

	@SubscribeEvent
	public static void finishBlockBreak (InputEvent.MouseButton.Pre event) {
		if (!allow || !enabled) return;
		if (Minecraft.getInstance().screen != null || Minecraft.getInstance().player == null) return;
		Player player = Minecraft.getInstance().player;

		if (event.getButton() == InputConstants.MOUSE_BUTTON_LEFT && event.getAction() == InputConstants.PRESS) {
			heldKey = true;
			currentSelected = player.getInventory().getSelectedSlot();
		}

		if (Config.keepLast) return;
		if (event.getButton() == InputConstants.MOUSE_BUTTON_LEFT && event.getAction() == InputConstants.RELEASE) {
			player.getInventory().setSelectedSlot(currentSelected);
			modified = false;
			heldKey = false;
		}
	}

	@SubscribeEvent
	public static void toggleOnOff (InputEvent.Key event) {
		if (!allow) return;
		if (Minecraft.getInstance().screen != null || Minecraft.getInstance().player == null) return;
		Player player = Minecraft.getInstance().player;
		if (Keybindings.INSTANCE.toggle.isDown()) {
			enabled = !enabled;
			player.displayClientMessage(Component.translatable("chat.hotswap.toggleEnable", HotSwap.getEnabled(enabled)), true); //.sendSystemMessage();
		}
	}

	@SubscribeEvent
	public static void keyInput (ClientTickEvent.Pre event) {
		allow = !Keybindings.INSTANCE.preventSwitch.isDown();
	}

	@SubscribeEvent
	public static void playerJoin (PlayerEvent.PlayerLoggedInEvent event) {
		Player player = event.getEntity();
		currentSelected = player.getInventory().getSelectedSlot();

		if (IS_ALPHA) {
			player.displayClientMessage(Component.literal("§l§4WARNING: §rHotSwap is in alpha. Bugs should be expected."), false);
		}
	}

}
