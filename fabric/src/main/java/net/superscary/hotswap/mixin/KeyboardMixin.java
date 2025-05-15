package net.superscary.hotswap.mixin;

import net.minecraft.client.KeyboardHandler;
import net.superscary.hotswap.api.InputEventType;
import net.superscary.hotswap.callbacks.InputPreCallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Injects before Keyboard#onKey(...)
 */
@Mixin(KeyboardHandler.class)
public class KeyboardMixin {
	@Inject(
			method = "keyPress",
			at = @At("HEAD"),
			cancellable = true
	)
	private void beforeKey (long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
		if (InputPreCallback.EVENT.invoker().onInput(InputEventType.KEY, key, action, modifiers)) {
			ci.cancel();
		}
	}
}
