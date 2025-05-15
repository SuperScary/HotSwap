package net.superscary.hotswap.mixin;

import net.minecraft.client.MouseHandler;
import net.superscary.hotswap.api.InputEventType;
import net.superscary.hotswap.callbacks.InputPreCallback;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Injects before MouseHandler#onPress(...)
 */
@Mixin(MouseHandler.class)
public class MouseMixin {
	@Inject(
			method = "onPress",
			at = @At("HEAD"),
			cancellable = true
	)
	private void beforeMouseButton (long window, int button, int action, int mods, CallbackInfo ci) {
		if (InputPreCallback.EVENT.invoker().onInput(InputEventType.MOUSE, button, action, mods)) {
			ci.cancel();
		}
	}
}
