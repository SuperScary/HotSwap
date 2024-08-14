package superscary.hotswap.util;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.jetbrains.annotations.NotNull;

public final class WeaponItem
{

    private final Item item;
    private final Player player;

    public WeaponItem (@NotNull ItemStack stack, Player player) {
        this(stack.getItem(), player);
    }

    public WeaponItem (Item item, Player player) {
        this.item = item;
        this.player = player;
    }

    public float getDamageValue () {
        return getDamageFor(new ItemStack(item, 1), player);
    }

    private float getDamageFor (@NotNull ItemStack stack, Player player) {
        String itemDamageStr = stack.getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE).toString().replaceFirst(".*?amount=([0-9]+\\.[0-9]+).*", "$1");
        item.getDefaultAttributeModifiers(stack).withModifierAdded(Attributes.ATTACK_DAMAGE, AttributeModifier.load(item), EquipmentSlotGroup.ANY).
        float itemDamage = 0.0f;
        if (itemDamageStr.matches("[0-9]+\\.[0-9]+")) {
            itemDamage = Float.parseFloat(itemDamageStr);
        }
        float playerBaseDamage = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
        return playerBaseDamage + itemDamage;
    }

    public Item getItem ()
    {
        return item;
    }

    public Player getPlayer ()
    {
        return player;
    }

}
