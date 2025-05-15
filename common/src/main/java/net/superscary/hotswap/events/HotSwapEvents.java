package net.superscary.hotswap.events;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.superscary.hotswap.HotSwap;
import net.superscary.hotswap.api.tool.ToolHelper;
import net.superscary.hotswap.config.Config;
import net.superscary.hotswap.keybinding.Keybindings;

import static net.superscary.hotswap.HotSwap.IS_ALPHA;

/**
 * This class handles the events for HotSwap.
 * It is used to swap the item in the player's hand when breaking blocks or attacking entities.
 */
public class HotSwapEvents {

	private static final Config CONFIG = HotSwap.CONFIG.get();

	private static boolean enabled = true;
	private static int currentSelected = -1;
	private static int newSelected = -1;
	private static boolean modified = false;
	private static boolean heldKey = false;
	private static boolean allow = true;
	private static Block oldBlock = null;

	public static void onBeginBreak (Player player, BlockState old, BlockState targetBlock) {
		if (!allow || !enabled) return;
		if ((player.isCreative() == CONFIG.ALLOW_IN_CREATIVE) || CONFIG.ALLOW_IN_SURVIVAL || CONFIG.ALLOW_IN_ADVENTURE) {
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
		if (!allow || !CONFIG.ALLOW_FOR_ATTACKING || !enabled) return;
		currentSelected = player.getInventory().getSelectedSlot();
		newSelected = ToolHelper.getBestWeaponFor(player, target, CONFIG.ALLOW_AXES_FOR_ATTACKING);
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

		if (CONFIG.KEEP_LAST) return;
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
			player.displayClientMessage(Component.translatable("chat.hotswap.toggleEnable", HotSwap.getEnabled(enabled)), true); //.sendSystemMessage();
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

		if (IS_ALPHA && !CONFIG.IGNORE_ALPHA_MESSAGE) {
			player.displayClientMessage(Component.literal("§l§4WARNING: §rHotSwap is in alpha. Bugs should be expected."), false);
		}
	}

}
