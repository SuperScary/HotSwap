package net.superscary.hotswap.events;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.superscary.hotswap.HotSwap;
import net.superscary.hotswap.api.tool.ToolHelper;
import net.superscary.hotswap.config.ModConfig;
import net.superscary.hotswap.keybinding.Keybindings;

import static net.superscary.hotswap.HotSwap.IS_ALPHA;

/**
 * This class handles the events for HotSwap.
 * It is used to swap the item in the player's hand when breaking blocks or attacking entities.
 */
public class HotSwapEvents {

	private static final ModConfig CONFIG = HotSwap.CONFIG;

	private static boolean enabled = true;
	private static int currentSelected = -1;
	private static int newSelected = -1;
	private static boolean modified = false;
	private static boolean heldKey = false;
	private static boolean allow = true;
	private static Block oldBlock = null;

	public static void onBeginBreak (Player player, BlockState old, BlockState targetBlock) {
		if (!allow || !enabled || !CONFIG.ACTIONS.MINE.ENABLED.get()) return;
		if ((player.isCreative() == CONFIG.BASIC.ALLOW_IN_CREATIVE.get()) || CONFIG.BASIC.ALLOW_IN_SURVIVAL.get() || CONFIG.BASIC.ALLOW_IN_ADVENTURE.get()) {
			oldBlock = old.getBlock();
			if (!modified || oldBlock == targetBlock.getBlock() || oldBlock != null) {
				modified = true;
				if (oldBlock != targetBlock.getBlock() && !heldKey)
					currentSelected = player.getInventory().getSelectedSlot();
				newSelected = ToolHelper.getBestToolFor(targetBlock, player);
				player.getInventory().setSelectedSlot(newSelected);
			}
		}
	}

	public static void attackEntity (Player player, Entity target) {
		if (!allow || !CONFIG.BASIC.ALLOW_FOR_ATTACKING.get() || !CONFIG.ACTIONS.ATTACK.ENABLED.get() || !enabled) return;
		currentSelected = player.getInventory().getSelectedSlot();
		newSelected = ToolHelper.getBestWeaponFor(player, target, CONFIG.ACTIONS.ATTACK.ALLOW_AXES_FOR_ATTACKING.get());
		player.getInventory().setSelectedSlot(newSelected);
	}

	public static void finishBlockBreak (int button, int action) {
		if (!allow || !enabled) return;
		if (Minecraft.getInstance().screen != null || Minecraft.getInstance().player == null) return;
		Player player = Minecraft.getInstance().player;

		var options = Minecraft.getInstance().options;
		var mouse = options.keyAttack.matchesMouse(button);

		if (mouse && action == InputConstants.PRESS) {
			heldKey = true;
			currentSelected = player.getInventory().getSelectedSlot();
		}

		if (CONFIG.BASIC.KEEP_LAST.get()) return;
		if (mouse && action == InputConstants.RELEASE) {
			player.getInventory().setSelectedSlot(currentSelected);
			modified = false;
			heldKey = false;
		}
	}

	public static void toggleOnOff (int button, int action) {
		if (!allow) return;
		if (Minecraft.getInstance().screen != null || Minecraft.getInstance().player == null) return;

		Player player = Minecraft.getInstance().player;
		if (Keybindings.INSTANCE.toggle.matches(button, button) && action == InputConstants.PRESS) {
			enabled = !enabled;
			player.displayClientMessage(Component.translatable("chat.hotswap.toggleEnable", getEnabled(enabled)), true); //.sendSystemMessage();
		}
	}

	/**
	 * Ticking method
	 */
	public static void holdOff () {
		allow = !Keybindings.INSTANCE.preventSwitch.isDown();
	}

	public static void playerJoin (Entity entity) {
		if (!(entity instanceof Player player)) return;
		currentSelected = player.getInventory().getSelectedSlot();

		if (IS_ALPHA && !CONFIG.DEBUG.IGNORE_ALPHA_MESSAGE.get()) {
			player.displayClientMessage(Component.literal("§l§4WARNING: §rHotSwap is in alpha. Bugs should be expected."), false);
		}
	}

	public static Component getEnabled (boolean bool) {
		return Component.translatable("chat.hotswap.toggleEnable." + bool);
	}

}
