package net.superscary.hotswap;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.superscary.hotswap.keybinding.Keybindings;

import static net.superscary.hotswap.Constants.MOD_ID;

@Mod(MOD_ID)
public class HotSwapNeoForge {

    public HotSwapNeoForge (IEventBus eventBus, ModContainer modContainer) {

        // This method is invoked by the NeoForge mod loader when it is ready
        // to load your mod. You can access NeoForge and Common code in this
        // project.

        // Use NeoForge to bootstrap the Common mod.
        Constants.LOG.info("Hello NeoForge world!");
        HotSwap.init();

        if (FMLEnvironment.dist == Dist.DEDICATED_SERVER) {
            Constants.LOG.warn("[{}] {} is not supported on Server configurations.", Constants.MOD_NAME, Constants.MOD_NAME);
        } else {
            modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        }

    }

    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModHandler {
        @SubscribeEvent
        public static void registerKeys (RegisterKeyMappingsEvent event) {
            event.register(Keybindings.INSTANCE.preventSwitch);
            event.register(Keybindings.INSTANCE.toggle);
        }
    }

}