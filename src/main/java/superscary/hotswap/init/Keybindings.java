package superscary.hotswap.init;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;

public final class Keybindings {

    public static final Keybindings INSTANCE = new Keybindings();

    private Keybindings () {
    }

    private static final String CATEGORY = "key.category.hotswap";

    public final KeyMapping preventSwitch = new KeyMapping(
            "key.hotswap.preventSwitch",
            InputConstants.KEY_LALT,
            CATEGORY
    );
    public final KeyMapping toggle = new KeyMapping(
            "key.hotswap.toggle",
            InputConstants.KEY_SEMICOLON,
            CATEGORY
    );

}
