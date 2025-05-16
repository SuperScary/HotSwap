package net.superscary.hotswap.config.subs;

import net.superscary.hotswap.config.defs.LogLevel;
import net.superscary.sc.annotations.Comment;
import net.superscary.sc.annotations.Config;
import net.superscary.sc.value.wrappers.BooleanValue;
import net.superscary.sc.value.wrappers.EnumValue;

@Config(name = "Debug")
public class DebugSettings {

	@Comment("Enable or disable debug logging.")
	public BooleanValue DEBUG_LOGGING = new BooleanValue(false);

	@Comment({"Enable or disable logging of HotSwap's actions.", "Possible values: \"OFF\" | \"ERROR\" | \"WARN\" | \"INFO\" | \"DEBUG\""})
	public EnumValue<LogLevel> LOG_LEVEL = new EnumValue<>(LogLevel.class, LogLevel.INFO);

	@Comment("Show the alpha message when the mod is loaded if the version is alpha.")
	public BooleanValue IGNORE_ALPHA_MESSAGE = new BooleanValue(false);
}
