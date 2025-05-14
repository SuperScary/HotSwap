package net.superscary.hotswap;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.superscary.hotswap.client.events.ClientEvent;
import net.superscary.hotswap.keybinding.Keybindings;

public class HotSwapFabric implements ModInitializer {

    public static Config CONFIG;
    
    @Override
    public void onInitialize() {
        
        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.
        Constants.LOG.info("Hello Fabric world!");
        HotSwap.init();

        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) {
            Constants.LOG.warn("[{}] {} is not supported on Server configurations.", Constants.MOD_NAME, Constants.MOD_NAME);
        } else {
            AutoConfig.register(Config.class, JanksonConfigSerializer::new);
            CONFIG = AutoConfig.getConfigHolder(Config.class).getConfig();
            registerKeyBindings();
            ClientEvent.initCallbacks();
        }
    }

    private void registerKeyBindings () {
        var keybindings = Keybindings.INSTANCE;
        KeyBindingHelper.registerKeyBinding(keybindings.preventSwitch);
        KeyBindingHelper.registerKeyBinding(keybindings.toggle);
    }
}
