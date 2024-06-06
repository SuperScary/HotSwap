package hotswap.client.events;

import com.mojang.blaze3d.platform.InputConstants;
import hotswap.Config;
import hotswap.client.Keybindings;
import hotswap.util.ToolHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static hotswap.HotSwap.MODID;

@SuppressWarnings("unused")
@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ClientEvent {

    static Logger LOG = LoggerFactory.getLogger(ClientEvent.class);

    private static int currentSelected = -1;
    private static int newSelected = -1;
    private static boolean modified = false;
    private static boolean heldKey = false;
    private static boolean allow = true;
    private static Block oldBlock = null;

    @SubscribeEvent
    public static void onBeginBreak (PlayerEvent.HarvestCheck event) {
        if (!allow) return;
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
        if (!allow) return;
        currentSelected = event.getEntity().getInventory().selected;
        newSelected = ToolHelper.getBestToolFor(event.getTarget(), event.getEntity());
        event.getEntity().getInventory().selected = newSelected;
    }

    @SubscribeEvent
    public static void finishBlockBreak (InputEvent.MouseButton.Pre event) {
        if (!allow) return;
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
    public static void keyInput (ClientTickEvent.Pre event) {
        allow = !Keybindings.INSTANCE.preventSwitch.isDown();
    }

    @SubscribeEvent
    public static void playerJoin (PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        currentSelected = player.getInventory().selected;
        LOG.info("Setting {} current selected slot to {}({})", player.getDisplayName(), player.getInventory().selected, currentSelected);
    }

}
