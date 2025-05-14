package net.superscary.hotswap.api;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface ISwappableItem<T extends Item> {

	T getItem ();

	default ItemStack asStack () {
		return new ItemStack(getItem());
	}

	default boolean isDamageable () {
		return asStack().isDamageableItem();
	}

	default int getDamage () {
		return asStack().getDamageValue();
	}

}
