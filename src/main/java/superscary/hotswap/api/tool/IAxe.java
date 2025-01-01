package superscary.hotswap.api.tool;

import net.minecraft.world.item.AxeItem;
import superscary.hotswap.api.ISwappableItem;

public interface IAxe<T extends AxeItem> extends ISwappableItem<T> {

    T getAxe ();

}
