package superscary.hotswap.api.tool;

import net.minecraft.world.item.SwordItem;
import superscary.hotswap.api.ISwappableItem;

public interface ISword<T extends SwordItem> extends ISwappableItem<T> {

    T getSword ();

}
