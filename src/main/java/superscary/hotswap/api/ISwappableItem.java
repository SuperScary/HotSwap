package superscary.hotswap.api;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface ISwappableItem<T extends Item> {

    T getItem ();

    default boolean isDamageable () {
        return getItem().isDamageable(new ItemStack(getItem()));
    }

    default int getDamage () {
        return getItem().getDamage(new ItemStack(getItem()));
    }

}
