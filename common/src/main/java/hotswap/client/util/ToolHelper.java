package hotswap.client.util;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class ToolHelper {

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
