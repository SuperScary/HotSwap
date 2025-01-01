package superscary.hotswap.util;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import superscary.hotswap.Config;
import superscary.hotswap.api.tool.*;
import superscary.hotswap.api.weapon.Weapon;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ToolHelper {

    public static int getBestToolFor (BlockState state, Player player) {
        List<Tool> tools = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (isViable(stack, state)) {
                Tool tool = new Tool(stack, getDestroySpeed(stack, state), i, stack.getDamageValue());
                tools.add(tool);
            }
        }
        if (tools.isEmpty()) {
            return player.getInventory().selected;
        } else {
            int fastest = bestTool(tools, player.getInventory().selected);
            tools.clear();
            return fastest;
        }
    }

    public static int getBestWeaponFor (Player player) {
        ArrayList<Weapon> weapons = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (isViableWeapon(stack)) {
                Weapon weapon = new Weapon(stack, getAttackDamage(stack, player), i, stack.getDamageValue());
                weapons.add(weapon);
            }
        }
        if (weapons.isEmpty()) {
            return player.getInventory().selected;
        } else {
            int best = bestWeapon(weapons, player.getInventory().selected);
            weapons.clear();
            return best;
        }
    }

    public static int bestTool (List<Tool> tools, int current) {
        Comparator<Tool> comparator = Comparator.comparing(Tool::destroySpeed).thenComparing(Tool::itemDamage).reversed();
        tools.sort(comparator);
        if (tools.getFirst() != null) {
            int best = tools.getFirst().index();
            tools.clear();
            return best;
        }
        tools.clear();
        return current;
    }

    public static int bestWeapon (List<Weapon> weapons, int current) {
        Comparator<Weapon> comparator = Comparator.comparing(Weapon::attackDamage).thenComparing(Weapon::itemDamage).reversed();
        weapons.sort(comparator);
        if (weapons.getFirst() != null) {
            int best = weapons.getFirst().index();
            weapons.clear();
            return best;
        }
        weapons.clear();
        return current;
    }

    public static float getDestroySpeed (ItemStack stack, BlockState state) {
        return stack.getDestroySpeed(state);
    }

    public static float getAttackDamage (ItemStack stack, Player player) {
        WeaponItem weaponItem = new WeaponItem(stack, player);
        return Math.max(weaponItem.getDamageValue(), 2.f);
    }

    private static boolean isViableWeapon (ItemStack stack) {
        return stack.is(getSwordItemTag()) || (stack.is(getAxeItemTag()) == Config.allowAxe) || (stack.is(ItemTags.WEAPON_ENCHANTABLE) && stack.getItem() instanceof AxeItem == Config.allowAxe);
    }

    private static boolean isViable (ItemStack stack, BlockState state) {
        if (state.is(getPickaxeBlockTag())) {
            return stack.is(getPickaxeItemTag()) || stack.getItem() instanceof IPickaxe;
        } else if (state.is(getAxeBlockTag())) {
            return stack.is(getAxeItemTag()) || stack.getItem() instanceof IAxe;
        } else if (state.is(getShovelBlockTag())) {
            return stack.is(getShovelItemTag()) || stack.getItem() instanceof IShovel;
        } else if (state.is(getHoeBlockTag())) {
            return stack.is(getHoeItemTag()) || stack.getItem() instanceof IHoe;
        } else if (state.is(getSwordBlockTag()) || state.getBlock() == Blocks.COBWEB) {
            return stack.is(getSwordItemTag()) || stack.getItem() instanceof ISword;
        }
        return false;
    }

    public static double attackSpeed (ItemStack stack, Player player) {
        WeaponItem item = new WeaponItem(stack, player);
        return item.getSpeedValue();
    }

    public static TagKey<Block> getPickaxeBlockTag () {
        return BlockTags.MINEABLE_WITH_PICKAXE;
    }

    public static TagKey<Item> getPickaxeItemTag () {
        return ItemTags.PICKAXES;
    }

    public static TagKey<Block> getAxeBlockTag () {
        return BlockTags.MINEABLE_WITH_AXE;
    }

    public static TagKey<Item> getAxeItemTag () {
        return ItemTags.AXES;
    }

    public static TagKey<Block> getShovelBlockTag () {
        return BlockTags.MINEABLE_WITH_SHOVEL;
    }

    public static TagKey<Item> getShovelItemTag () {
        return ItemTags.SHOVELS;
    }

    public static TagKey<Block> getHoeBlockTag () {
        return BlockTags.MINEABLE_WITH_HOE;
    }

    public static TagKey<Item> getHoeItemTag () {
        return ItemTags.HOES;
    }

    public static TagKey<Block> getSwordBlockTag () {
        return BlockTags.SWORD_EFFICIENT;
    }

    public static TagKey<Item> getSwordItemTag () {
        return ItemTags.SWORDS;
    }

}
