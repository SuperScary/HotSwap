package net.superscary.hotswap;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.superscary.hotswap.client.events.ClientEvent;
import net.superscary.hotswap.keybinding.Keybindings;

public class HotSwapFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        HotSwap.init();

        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) {
            Constants.LOG.warn("[{}] {} is not supported on Server configurations.", Constants.MOD_NAME, Constants.MOD_NAME);
        } else {
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
