package net.superscary.hotswap.config;

public class Config {

	@Comment("Allow swapping while in creative mode.")
	@Name("allow_in_creative")
	public boolean ALLOW_IN_CREATIVE = false;

	@Comment("Allow swapping while in adventure mode.")
	@Name("allow_in_adventure")
	public boolean ALLOW_IN_ADVENTURE = false;

	@Comment("Allow swapping while in survival mode.")
	@Name("allow_in_survival")
	public boolean ALLOW_IN_SURVIVAL = true;

	@Comment("Allow auto swapping for attacking.")
	@Name("allow_for_attacking")
	public boolean ALLOW_FOR_ATTACKING = true;

	@Comment("Allow swapping to an axe when looking for a viable attacking entity.")
	@Name("allow_axes_for_attacking")
	public boolean ALLOW_AXES_FOR_ATTACKING = true;

	@Comment("Stay on selected tool.")
	@Name("keep_last")
	public boolean KEEP_LAST = false;

	@Comment("Show the alpha message when the mod is loaded if the version is alpha.")
	@Name("ignore_alpha_message")
	public boolean IGNORE_ALPHA_MESSAGE = false;

}
