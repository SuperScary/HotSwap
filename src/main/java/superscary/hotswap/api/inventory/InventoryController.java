package superscary.hotswap.api.inventory;

import com.google.common.base.Preconditions;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class InventoryController {

    private final Player player;
    private final Inventory inventory;

    public InventoryController (Player player, Inventory inventory) {
        this.player = player;
        this.inventory = inventory;
    }

    public InventoryController (Player player) {
        this(player, player.getInventory());
    }

    public InventoryController (Inventory inventory) {
        this(inventory.player, inventory);
    }

    public Player getPlayer () {
        Preconditions.checkArgument(this.player != null);
        return this.player;
    }

    public Inventory getInventory () {
        Preconditions.checkArgument(this.inventory != null);
        return this.inventory;
    }

    public int getFirstMatching (int current) {
        Item item = getInventory().getItem(current).getItem();
        for (int i = 0; i < 28; i++) {
            if (i != current) {
                if (getInventory().getItem(i).getItem() == item) {
                    return i;
                }
            }
        }
        return -1;
    }

    public int getFirstMatchingFromSelected () {
        return getFirstMatching(getInventory().selected);
    }

    public void moveStackTo (int from, int to) {
        Preconditions.checkArgument(from != to);

        ItemStack fromStack = getInventory().getItem(from);
        ItemStack toStack = getInventory().getItem(to);
        if (toStack.isEmpty() || toStack.getItem() == fromStack.getItem()) {
            moveAndRemainder(from, to);
        }
    }

    private void moveAndRemainder (int from, int to) {
        ItemStack fromStack = getInventory().getItem(from);
        ItemStack toStack = getInventory().getItem(to);
        if (toStack.getCount() + 1 <= toStack.getMaxStackSize()) {
            int newCount = toStack.getCount() + 1;
            int remainder = Math.max(fromStack.getCount() - 1, 0);
            System.out.println(remainder);
            toStack.setCount(newCount);
            fromStack.setCount(remainder);
        } else {
            toStack.setCount(toStack.getMaxStackSize());
            fromStack.setCount(0);
        }
    }

}
