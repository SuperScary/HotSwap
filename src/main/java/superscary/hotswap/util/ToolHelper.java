package superscary.hotswap.util;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import superscary.hotswap.Config;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

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
        return Math.max(getDamageFor(stack, player), 2.f);
    }

    private static float getDamageFor (ItemStack stack, Player player) {
        String itemDamageStr = stack.getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE).toString().replaceFirst(".*?amount=([0-9]+\\.[0-9]+).*", "$1");
        float itemDamage = 0.0f;
        if (itemDamageStr.matches("[0-9]+\\.[0-9]+")) {
            itemDamage = Float.parseFloat(itemDamageStr);
        }
        float playerBaseDamage = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
        return playerBaseDamage + itemDamage;
    }

    private static boolean isViableWeapon (ItemStack stack) {
        return stack.is(getSwordItemTag()) || (stack.is(getAxeItemTag()) == Config.allowAxe) || (stack.is(ItemTags.WEAPON_ENCHANTABLE) && stack.getItem() instanceof AxeItem == Config.allowAxe);
    }

    private static boolean isViable (ItemStack stack, BlockState state) {
        if (state.is(getPickaxeBlockTag())) {
            return stack.is(getPickaxeItemTag());
        } else if (state.is(getAxeBlockTag())) {
            return stack.is(getAxeItemTag());
        } else if (state.is(getShovelBlockTag())) {
            return stack.is(getShovelItemTag());
        } else if (state.is(getHoeBlockTag())) {
            return stack.is(getHoeItemTag());
        } else if (state.is(getSwordBlockTag()) || state.getBlock() == Blocks.COBWEB) {
            return stack.is(getSwordItemTag());
        }
        return false;
    }

    public static double attackSpeed (ItemStack stack, Player player) {
        return player.getAttributes().getValue(Attributes.ATTACK_SPEED);
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

    public record Tool(ItemStack stack, float destroySpeed, int index, int itemDamage) {
    }

    public record Weapon(ItemStack stack, float attackDamage, int index, int itemDamage) {
    }

}
