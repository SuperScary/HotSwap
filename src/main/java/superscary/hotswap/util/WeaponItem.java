package superscary.hotswap.util;

import com.google.common.base.Preconditions;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.NotNull;

public final class WeaponItem {

    private static final float BASE = 2.f;

    private final Item item;
    private final Player player;
    private final DamageSource source;
    private final Entity target;

    public WeaponItem(@NotNull ItemStack stack, Player player, Entity target) {
        this(stack.getItem(), player, player.level().damageSources().playerAttack(player), target);
    }

    public WeaponItem(Item item, Player player, DamageSource source, Entity target) {
        Preconditions.checkArgument(item instanceof SwordItem || item instanceof AxeItem);
        this.item = item;
        this.player = player;
        this.source = source;
        this.target = target;
    }

    public float getDamageValue() {
        return getDamageFor(new ItemStack(getItem(), 1), getTarget());
    }

    public float getSpeedValue() {
        return getSpeedFor(new ItemStack(getItem(), 1), getPlayer());
    }

    private float getDamageFor(@NotNull ItemStack stack, Entity target) {
        return stack.getItem().getAttackDamageBonus(target, BASE, getSource());
    }

    // TODO: Return attack speed for given tool.
    private float getSpeedFor(ItemStack stack, Player player) {
        return 0;
    }

    public Item getItem() {
        return item;
    }

    public Player getPlayer() {
        return player;
    }

    public Entity getTarget() {
        return target;
    }

    public DamageSource getSource() {
        return source;
    }

}
