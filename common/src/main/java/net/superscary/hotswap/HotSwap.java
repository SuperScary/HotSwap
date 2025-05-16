package net.superscary.hotswap;

import net.superscary.hotswap.config.Config;
import net.superscary.hotswap.platform.Services;
import net.superscary.sc.manager.ConfigManager;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HotSwap {

    public static final boolean IS_ALPHA = false;

    public static Config CONFIG;

    public static void init() {
        if (Services.PLATFORM.isModLoaded("hotswap")) {
            Constants.LOG.info("Initializing HotSwap...");
            logAction("HotSwap is running in verbose mode. Did you mean to enable this?");
        }
    }

    public static void logAction (String message, Object... args) {
        if (CONFIG.DEBUG.DEBUG_LOGGING.get()) {
            var logLevel = CONFIG.DEBUG.LOG_LEVEL.get();
            switch (logLevel) {
                case OFF -> {}
                case ERROR -> Constants.LOG.error(message, args);
                case WARN -> Constants.LOG.warn(message, args);
                case INFO -> Constants.LOG.info(message, args);
                case DEBUG -> Constants.LOG.debug(message, args);
            }
        }
    }

    static {
        Path configPath = Paths.get("config", Constants.MOD_ID + ".json5");
        ConfigManager<Config> configManager = new ConfigManager<>(Config.class, configPath);
		try {
			CONFIG = configManager.load();
            configManager.save(CONFIG);
		} catch (IOException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
    }
}