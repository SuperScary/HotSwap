package net.superscary.hotswap.api.weapon;

import net.minecraft.world.item.ItemStack;

public record Weapon(ItemStack stack, float attackDamage, int index, int itemDamage) {
}