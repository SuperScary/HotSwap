package net.superscary.hotswap;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.superscary.hotswap.client.events.ClientEvent;
import net.superscary.hotswap.keybinding.Keybindings;

import static net.superscary.hotswap.Constants.MOD_ID;

@Mod(Constants.MOD_ID)
public class HotSwapForge {

    public HotSwapForge () {
        HotSwap.init();

        if (FMLEnvironment.dist.isDedicatedServer()) {
            Constants.LOG.warn("[{}] {} is not supported on Server configurations.", Constants.MOD_NAME, Constants.MOD_NAME);
        } else {
            MinecraftForge.EVENT_BUS.addListener(ClientEvent::onBeginBreak);
            MinecraftForge.EVENT_BUS.addListener(ClientEvent::attackEntity);
            MinecraftForge.EVENT_BUS.addListener(ClientEvent::finishBlockBreak);
            MinecraftForge.EVENT_BUS.addListener(ClientEvent::toggleOnOff);
            MinecraftForge.EVENT_BUS.addListener(ClientEvent::holdOff);
            MinecraftForge.EVENT_BUS.addListener(ClientEvent::playerJoin);
        }
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModHandler {
        @SubscribeEvent
        public static void registerKeys (RegisterKeyMappingsEvent event) {
            event.register(Keybindings.INSTANCE.preventSwitch);
            event.register(Keybindings.INSTANCE.toggle);
        }
    }

}