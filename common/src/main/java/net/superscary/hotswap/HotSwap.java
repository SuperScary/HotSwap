package net.superscary.hotswap;

import net.minecraft.network.chat.Component;
import net.superscary.hotswap.config.Config;
import net.superscary.hotswap.config.JsonConfig;
import net.superscary.hotswap.platform.Services;

import java.nio.file.Path;
import java.nio.file.Paths;

public class HotSwap {

    public static final boolean IS_ALPHA = true;

    public static final JsonConfig<Config> CONFIG;

    public static void init() {
        if (Services.PLATFORM.isModLoaded("hotswap")) {
            Constants.LOG.info("Initializing HotSwap...");
        }
    }

    public static Component getEnabled (boolean bool) {
        return Component.translatable("chat.hotswap.toggleEnable." + bool);
    }

    static {
        Path configPath = Paths.get("config", Constants.MOD_ID + ".json5");
        CONFIG = new JsonConfig.Builder<Config>().file(configPath).type(Config.class).defaultConfig(new Config()).headerComment("This is the main configuration file for HotSwap.").build();
        CONFIG.save();
    }
}