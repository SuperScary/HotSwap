package superscary.hotswap.api.tool;

import net.minecraft.world.item.ShovelItem;
import superscary.hotswap.api.ISwappableItem;

public interface IShovel<T extends ShovelItem> extends ISwappableItem<T> {

    T getShovel ();

}
