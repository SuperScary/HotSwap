package hotswap;

import hotswap.client.Keybindings;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@Mod(HotSwap.MODID)
public class HotSwap {
    public static final String MODID = "hotswap";

    public HotSwap (IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModHandler {
        @SubscribeEvent
        public static void registerKeys (RegisterKeyMappingsEvent event) {
            event.register(Keybindings.INSTANCE.preventSwitch);
        }
    }

}
