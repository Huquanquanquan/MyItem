package cn.mokier.soulitem.spigotutils.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemStackUtils {

    public static boolean isEmpty(ItemStack itemStack) {
        return itemStack==null || itemStack.getType() == Material.AIR;
    }

    public static boolean hasDisplayName(ItemStack itemStack) {
        if(!isEmpty(itemStack)) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            return itemMeta.hasDisplayName();
        }
        return false;
    }

    public static boolean hasLore(ItemStack itemStack) {
        if(!isEmpty(itemStack)) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            return itemMeta.hasLore();
        }
        return false;
    }

    public static boolean equalsItemStack(ItemStack itemStack, ItemStack byItemStack) {
        return !isEmpty(itemStack) && !isEmpty(byItemStack) && itemStack.equals(byItemStack);
    }

    public static boolean equalsItemStackByDisplayName(ItemStack itemStack, ItemStack byItemStack) {
        if(!hasDisplayName(itemStack) || !hasDisplayName(byItemStack)) {
            return false;
        }
        return itemStack.getItemMeta().getDisplayName().equals(byItemStack.getItemMeta().getDisplayName());
    }


    public static ItemStack toItemStackByConfiguration(ConfigurationSection configurationSection) throws Exception {
        try {
            Material itemType = Material.valueOf(configurationSection.getString("itemType"));
            short unsafeDamage = (short) configurationSection.getInt("unsafeDamage");
            boolean enchantments = configurationSection.getBoolean("enchantments");
            String displayName = ChatColor.translateAlternateColorCodes('&', configurationSection.getString("displayName"));
            List<String> lore = Utils.filterVar(configurationSection.getStringList("lore"), false);

            ItemStack itemStack = new ItemStack(itemType, 1, unsafeDamage);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(displayName);
            itemMeta.setLore(lore);
            if(enchantments) {
                itemMeta.addEnchant(Enchantment.DURABILITY, 1, false);
                //隐藏附魔
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            itemStack.setItemMeta(itemMeta);
            return itemStack;
        } catch (Exception e) {
            throw new Exception();
        }
    }

}
