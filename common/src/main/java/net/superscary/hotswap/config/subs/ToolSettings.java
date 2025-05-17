package net.superscary.hotswap.config.subs;

import net.superscary.superconfig.annotations.Comment;
import net.superscary.superconfig.annotations.Config;
import net.superscary.superconfig.value.wrappers.BooleanValue;
import net.superscary.superconfig.value.wrappers.ListValue;

import java.util.List;

@Config(name = "tool_settings")
public class ToolSettings {

	@Comment("Enable replacing a broken tool with the next best candidate.")
	public BooleanValue ENABLED = new BooleanValue(true);

	@Comment({"Which tag groups to consider when swapping tools.",
			"Order does not matter."})
	public ListValue<String> TAGS = new ListValue<>(List.of(
			"#minecraft:axes",
			"#minecraft:shovels",
			"#minecraft:pickaxes",
			"#minecraft:swords",
			"#minecraft:hoes",
			"#minecraft:shields"
	));

	@Comment("Any item IDs to blacklist even if their tags match. IE: \"minecraft:diamond_shovel\" will never be swapped to if enabled in this list.")
	public ListValue<String> BLACKLIST = new ListValue<>(List.of());

}
