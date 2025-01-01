package superscary.hotswap.api.tool;

import net.minecraft.world.item.HoeItem;
import superscary.hotswap.api.ISwappableItem;

public interface IHoe<T extends HoeItem> extends ISwappableItem<T> {

    T getHoe ();

}
