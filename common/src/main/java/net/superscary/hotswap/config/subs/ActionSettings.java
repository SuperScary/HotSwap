package net.superscary.hotswap.config.subs;

import net.superscary.superconfig.annotations.Comment;
import net.superscary.superconfig.annotations.Config;

@Config(name = "action_settings")
public class ActionSettings {

	@Comment("Mining Settings")
	public ActionConfig.Mine MINE = new ActionConfig.Mine();

	@Comment("Attacking Settings")
	public ActionConfig.Attack ATTACK = new ActionConfig.Attack();

}
