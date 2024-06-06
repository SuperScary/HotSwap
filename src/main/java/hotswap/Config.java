package hotswap;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = HotSwap.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.BooleanValue ALLOW_IN_CREATIVE = BUILDER
            .comment("Allow swapping while in creative mode.")
            .define("allowInCreative", false);

    private static final ModConfigSpec.BooleanValue ALLOW_IN_ADVENTURE = BUILDER
            .comment("Allow swapping while in adventure mode.")
            .define("allowInAdventure", false);

    private static final ModConfigSpec.BooleanValue ALLOW_IN_SURVIVAL = BUILDER
            .comment("Allow swapping while in survival mode.")
            .define("allowInSurvival", true);

    static final ModConfigSpec SPEC = BUILDER.build();

    public static boolean allowInCreative;
    public static boolean allowInAdventure;
    public static boolean allowInSurvival;

    @SubscribeEvent
    static void onLoad (final ModConfigEvent event) {
        allowInCreative = ALLOW_IN_CREATIVE.get();
        allowInAdventure = ALLOW_IN_ADVENTURE.get();
        allowInSurvival = ALLOW_IN_SURVIVAL.get();
    }
}
