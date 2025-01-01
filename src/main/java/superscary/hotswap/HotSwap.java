package superscary.hotswap;

import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import superscary.hotswap.init.Keybindings;

import static superscary.hotswap.HotSwap.MOD_ID;

@Mod(MOD_ID)
public class HotSwap {

    public static final String MOD_ID = "hotswap";
    public static final String NAME = "HotSwap";

    public static final boolean IS_ALPHA = true;

    static Logger LOG = LoggerFactory.getLogger(HotSwap.class);

    public HotSwap (IEventBus modEventBus, ModContainer modContainer) {
        LOG.info("[{}] began loading...", NAME);
        if (FMLEnvironment.dist == Dist.DEDICATED_SERVER) {
            LOG.warn("[{}] {} is not supported on Server configurations.", NAME, NAME);
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

    public static Component getEnabled (boolean bool) {
        return Component.translatable("chat.hotswap.toggleEnable." + bool);
    }

}
