package hotswap.util;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
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
        }
        else {
            int fastest = fastest(tools, player.getInventory().selected);
            tools.clear();
            return fastest;
        }
    }

    public static int getBestToolFor (Entity entity, Player player) {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (isViableEntity(stack)) return i;
        }
        return player.getInventory().selected;
    }

    public static int fastest (List<Tool> tools, int current) {
        Comparator<Tool> comparator = Comparator.comparing(Tool::destroySpeed).thenComparing(Tool::damage).reversed();
        tools.sort(comparator);
        if (tools.getFirst() != null) {
            int best = tools.getFirst().index;
            tools.clear();
            return best;
        }
        tools.clear();
        return current;
    }

    public static float getDestroySpeed (ItemStack stack, BlockState state) {
        return stack.getDestroySpeed(state);
    }

    public static ItemStack compareDamage (Player player, ItemStack stack1, ItemStack stack2) {
        //if (stack1.getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE). > stack2.getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE))
        return stack1;
    }

    private static boolean isViableEntity (ItemStack stack) {
        return stack.is(getSwordItemTag()) /*|| stack.is(getAxeItemTag())*/;
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
        } else if (state.is(getSwordBlockTag())) {
            return stack.is(getSwordItemTag());
        }
        return false;
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

    public record Tool(ItemStack stack, float destroySpeed, int index, int damage) {

    }

}
