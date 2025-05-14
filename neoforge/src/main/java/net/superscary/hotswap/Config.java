package net.superscary.hotswap;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
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

	private static final ModConfigSpec.BooleanValue ALLOW_FOR_ATTACKING = BUILDER
			.comment("Allow auto swapping for attacking.")
			.define("allowForAttacking", true);

	private static final ModConfigSpec.BooleanValue ALLOW_AXES_FOR_ATTACKING = BUILDER
			.comment("Allow swapping to an axe when looking for a viable attacking item.")
			.define("allowAxe", true);

	private static final ModConfigSpec.BooleanValue KEEP_LAST = BUILDER
			.comment("Stays on selected tool.")
			.define("keepLast", false);

	static final ModConfigSpec SPEC = BUILDER.build();

	public static boolean allowInCreative;
	public static boolean allowInAdventure;
	public static boolean allowInSurvival;
	public static boolean allowForAttacking;
	public static boolean allowAxe;
	public static boolean keepLast;

	@SubscribeEvent
	static void onLoad (final ModConfigEvent event) {
		allowInCreative = ALLOW_IN_CREATIVE.get();
		allowInAdventure = ALLOW_IN_ADVENTURE.get();
		allowInSurvival = ALLOW_IN_SURVIVAL.get();
		allowForAttacking = ALLOW_FOR_ATTACKING.get();
		allowAxe = ALLOW_AXES_FOR_ATTACKING.get();
		keepLast = KEEP_LAST.get();
	}
}
