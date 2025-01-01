package superscary.hotswap.api.tool;

import net.minecraft.world.item.PickaxeItem;
import superscary.hotswap.api.ISwappableItem;

public interface IPickaxe<T extends PickaxeItem> extends ISwappableItem<T> {

    T getPickaxe ();

}
