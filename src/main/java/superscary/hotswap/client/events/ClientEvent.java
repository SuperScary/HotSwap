package superscary.hotswap.client.events;

import com.mojang.blaze3d.platform.InputConstants;
import superscary.hotswap.Config;
import superscary.hotswap.HotSwap;
import superscary.hotswap.init.Keybindings;
import superscary.hotswap.util.ToolHelper;
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

@SuppressWarnings("unused")
@EventBusSubscriber(modid = HotSwap.MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
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
                    currentSelected = event.getEntity().getInventory().selected;
                newSelected = ToolHelper.getBestToolFor(event.getTargetBlock(), event.getEntity());
                event.getEntity().getInventory().selected = newSelected;
            }
        }
    }

    @SubscribeEvent
    public static void attackEntity (AttackEntityEvent event) {
        if (!allow || !Config.allowForAttacking || !enabled) return;
        currentSelected = event.getEntity().getInventory().selected;
        newSelected = ToolHelper.getBestWeaponFor(event.getEntity());
        event.getEntity().getInventory().selected = newSelected;
    }

    @SubscribeEvent
    public static void finishBlockBreak (InputEvent.MouseButton.Pre event) {
        if (!allow || !enabled) return;
        if (Minecraft.getInstance().screen != null || Minecraft.getInstance().player == null) return;
        Player player = Minecraft.getInstance().player;

        if (event.getButton() == InputConstants.MOUSE_BUTTON_LEFT && event.getAction() == InputConstants.PRESS) {
            heldKey = true;
            currentSelected = player.getInventory().selected;
        }

        if (event.getButton() == InputConstants.MOUSE_BUTTON_LEFT && event.getAction() == InputConstants.RELEASE) {
            player.getInventory().selected = currentSelected;
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
            player.sendSystemMessage(Component.translatable("chat.hotswap.toggleEnable", enabled));
        }
    }

    @SubscribeEvent
    public static void keyInput (ClientTickEvent.Pre event) {
        allow = !Keybindings.INSTANCE.preventSwitch.isDown();
    }

    @SubscribeEvent
    public static void playerJoin (PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        currentSelected = player.getInventory().selected;
    }

}
