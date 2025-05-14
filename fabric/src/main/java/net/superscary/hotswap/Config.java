package net.superscary.hotswap;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@me.shedaniel.autoconfig.annotation.Config(name = Constants.MOD_ID)
public class Config implements ConfigData {

	@Comment("Allow swapping while in creative mode.")
	public static boolean ALLOW_IN_CREATIVE = false;

	@Comment("Allow swapping while in adventure mode.")
	public static boolean ALLOW_IN_ADVENTURE = false;

	@Comment("Allow swapping while in survival mode.")
	public static boolean ALLOW_IN_SURVIVAL = true;

	@Comment("Allow auto swapping for attacking.")
	public static boolean ALLOW_FOR_ATTACKING = true;

	@Comment("Allow swapping to an axe when looking for a viable attacking item.")
	public static boolean ALLOW_AXES_FOR_ATTACKING = true;

	@Comment("Stays on selected tool.")
	public static boolean KEEP_LAST = true;

}
