package superscary.hotswap.util;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.NotNull;

public final class WeaponItem {

    private final Item item;
    private final Player player;

    public WeaponItem(@NotNull ItemStack stack, Player player) {
        this(stack.getItem(), player);
    }

    public WeaponItem(Item item, Player player) {
        Preconditions.checkArgument(item instanceof SwordItem || item instanceof AxeItem);
        this.item = item;
        this.player = player;
    }

    public float getDamageValue() {
        return getDamageFor(new ItemStack(item, 1), player);
    }

    public float getSpeedValue() {
        return getSpeedFor(new ItemStack(item, 1), player);
    }

    private float getDamageFor(@NotNull ItemStack stack, Player player) {
        if (stack.getItem() instanceof TieredItem tieredItem) {
            return tieredItem.getTier().getAttackDamageBonus();
        }

        return 0;
    }

    private float getSpeedFor(ItemStack stack, Player player) {
        if (stack.getItem() instanceof TieredItem tieredItem) {
            return tieredItem.getTier().getSpeed();
        }

        return 0;
    }

    public Item getItem() {
        return item;
    }

    public Player getPlayer() {
        return player;
    }

}
