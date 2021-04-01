package cn.mokier.soulitem.spigotutils.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerUtils {

    public static void addPlayerItemStack(Player player, ItemStack itemStack) {
        Inventory inventory = player.getInventory();
        if(inventory.firstEmpty()>=0) {
            inventory.addItem(itemStack);
        }else {
            player.getWorld().dropItem(player.getLocation(), itemStack);
        }
    }

    public static boolean consumeItemStack(Player player, String itemDisplayName) {
        Inventory inventory = player.getInventory();

        for(int i=0; i<inventory.getSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);
            if(itemStack!=null) {
                ItemMeta itemMeta = itemStack.getItemMeta();
                if(itemMeta.hasDisplayName() && itemMeta.getDisplayName().equals(itemDisplayName)) {
                    if(itemStack.getAmount()>1) {
                        itemStack.setAmount(itemStack.getAmount()-1);
                        inventory.setItem(i, itemStack);
                    }else {
                        inventory.clear(i);
                    }
                    return true;
                }

            }
        }
        return false;
    }

    public static void consumeHandItemStack(Player player) {
        PlayerInventory inventory = player.getInventory();
        ItemStack itemStack = player.getInventory().getItemInMainHand();

        if(itemStack.getAmount()>1) {
            itemStack.setAmount(itemStack.getAmount()-1);
            inventory.setItemInMainHand(itemStack);
        }else {
            itemStack = new ItemStack(Material.AIR);
            inventory.setItemInMainHand(itemStack);
        }
    }

}
