package net.superscary.hotswap.client.events;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.EntityHitResult;
import net.superscary.hotswap.Config;
import net.superscary.hotswap.HotSwap;
import net.superscary.hotswap.api.tool.ToolHelper;
import net.superscary.hotswap.keybinding.Keybindings;

import static net.superscary.hotswap.HotSwap.IS_ALPHA;

public class ClientEvent {

	private static boolean enabled = true;
	private static int currentSelected = -1;
	private static int newSelected = -1;
	private static boolean modified = false;
	private static boolean heldKey = false;
	private static boolean allow = true;
	private static Block oldBlock = null;
	private static boolean isClicking = false;

	public static void initCallbacks () {
		AttackBlockCallback.EVENT.register(ClientEvent::onBeginBreak);
		AttackEntityCallback.EVENT.register(ClientEvent::onAttackEntity);

		ClientTickEvents.START_CLIENT_TICK.register(ClientEvent::finishBlockBreak);
		ClientTickEvents.START_CLIENT_TICK.register(ClientEvent::toggleOnOff);
		ClientTickEvents.START_CLIENT_TICK.register(ClientEvent::keyInput);
		ServerPlayerEvents.AFTER_RESPAWN.register(ClientEvent::onPlayerJoin);
	}

	private static InteractionResult onBeginBreak (Player player, Level world, InteractionHand hand, BlockPos pos, Direction face) {
		if (!allow || !enabled) return InteractionResult.PASS;

		if ((player.isCreative() == Config.ALLOW_IN_CREATIVE) || Config.ALLOW_IN_SURVIVAL) {
			Block target = world.getBlockState(pos).getBlock();

			if (!modified || oldBlock != target) {
				modified = true;

				if (oldBlock != target && !heldKey) {
					currentSelected = player.getInventory().getSelectedSlot();
				}

				newSelected = ToolHelper.getBestToolFor(world.getBlockState(pos), player);

				player.getInventory().setSelectedSlot(newSelected);
			}
			oldBlock = target;
		}

		// return PASS so vanilla still handles the break
		return InteractionResult.PASS;
	}

	private static InteractionResult onAttackEntity (Player player, Level level, InteractionHand hand, Entity target, EntityHitResult hitResult) {
		if (!allow || !Config.ALLOW_FOR_ATTACKING || !enabled) return InteractionResult.PASS;
		currentSelected = player.getInventory().getSelectedSlot();
		newSelected = ToolHelper.getBestWeaponFor(player, target, Config.ALLOW_AXES_FOR_ATTACKING);
		player.getInventory().setSelectedSlot(newSelected);
		return InteractionResult.PASS;
	}

	public static void finishBlockBreak (Minecraft minecraft) {
		if (!allow || !enabled) return;
		if (Minecraft.getInstance().screen != null || Minecraft.getInstance().player == null) return;
		Player player = Minecraft.getInstance().player;

		if (Minecraft.getInstance().options.keyAttack.isDown()) {
			isClicking = true;
			heldKey = true;
			currentSelected = player.getInventory().getSelectedSlot();
		}

		if (Config.KEEP_LAST) return;
		if (!Minecraft.getInstance().options.keyAttack.isDown() && isClicking) {
			player.getInventory().setSelectedSlot(currentSelected);
			modified = false;
			heldKey = false;
			isClicking = false;
		}
	}

	public static void toggleOnOff (Minecraft minecraft) {
		if (!allow) return;
		if (Minecraft.getInstance().screen != null || Minecraft.getInstance().player == null) return;
		Player player = Minecraft.getInstance().player;
		if (Keybindings.INSTANCE.toggle.isDown()) {
			enabled = !enabled;
			player.displayClientMessage(Component.translatable("chat.hotswap.toggleEnable", HotSwap.getEnabled(enabled)), true);
		}
	}

	public static void keyInput (Minecraft minecraft) {
		allow = !Keybindings.INSTANCE.preventSwitch.isDown();
	}

	public static void onPlayerJoin (ServerPlayer oldPlayer, ServerPlayer newPlayer, boolean alive) {
		currentSelected = newPlayer.getInventory().getSelectedSlot();

		if (IS_ALPHA) {
			newPlayer.displayClientMessage(Component.literal("§l§4WARNING: §rHotSwap is in alpha. Bugs should be expected."), false);
		}
	}

}
