package cn.mokier.soulitem.spigotutils.contain;

import cn.mokier.soulitem.spigotutils.chat.Chat;
import cn.mokier.soulitem.spigotutils.utils.ItemStackUtils;
import cn.mokier.soulitem.spigotutils.utils.Utils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

@Getter
public class Contain {

    /**
     * 容器
     */
    private Inventory inventory;
    /**
     * 节点集合
     */
    private Map<String, ContainNode> nodeMap;
    /**
     * 取消点击节点事件
     */
    public boolean CANCELLED_CLICk_NODE;
    /**
     * 关闭界面返还非节点物品
     */
    public boolean CLOSE_RETURN_ITEM;

    protected void reload(ConfigurationSection configurationSection) {
        try {
            //录入容器
            String title = ChatColor.translateAlternateColorCodes('&', configurationSection.getString("title"));
            int size = configurationSection.getInt("size");
            inventory = Bukkit.createInventory(null, size, ChatColor.translateAlternateColorCodes('&', title));

            //录入节点
            initNode(configurationSection.getConfigurationSection("nodes"));
            //录入设置
            CANCELLED_CLICk_NODE = configurationSection.getBoolean("cancelled_click_node", true);
            CLOSE_RETURN_ITEM = configurationSection.getBoolean("close_return_item", true);

            if(title==null || size<1 || inventory==null) {
                throw new Exception();
            }
        } catch (Exception e) {
            Chat.sendLogger(Level.WARNING, "§c[魂式体系] 文件 inventory.yml 的 "+configurationSection.getCurrentPath()+" 项设置异常，请检查并重新设置", false);
        }
    }

    public Inventory copyInventory(boolean isFilter, String... replaces) {
        Inventory inventory = Bukkit.createInventory(null, this.inventory.getSize(), this.inventory.getTitle());

        if(isFilter) {
            for(int i=0; i<this.inventory.getSize(); i++) {
                ItemStack itemStack = this.inventory.getItem(i);
                if(ItemStackUtils.isEmpty(itemStack)) {
                    continue;
                }
                itemStack = itemStack.clone();
                ItemMeta itemMeta = itemStack.getItemMeta();
                if(itemMeta.hasLore()) {
                    itemMeta.setLore(Utils.filterVar(itemMeta.getLore(), true, replaces));
                }
                itemStack.setItemMeta(itemMeta);
                inventory.setItem(i, itemStack);
            }
        }else {
            inventory.setContents(this.inventory.getContents());
        }
        return inventory;
    }

    /**
     * 通过这个物品获得节点
     * @param itemStack
     * @return
     */
    public ContainNode toNode(ItemStack itemStack) {
        for(ContainNode containNode : nodeMap.values()) {
            if(ItemStackUtils.equalsItemStack(containNode.getItemStack(), itemStack)) {
                return containNode;
            }
        }

        return null;
    }

    /**
     * 是否是这个容器
     * @param inventory  容器
     * @return
     */
    public boolean isContain(Inventory inventory) {
        return this.inventory.getTitle().equals(inventory.getTitle());
    }

    /**
     * 初始化节点集合
     * @param configurationSection 节点路径
     */
    private void initNode(ConfigurationSection configurationSection) {
        try {
            nodeMap = new HashMap<>();
            for(String key : configurationSection.getKeys(false)) {
                ContainNode containNode = new ContainNode(configurationSection.getConfigurationSection(key));
                ItemStack nodeItemStack = containNode.getItemStack();

                for(int slot : containNode.getSlot()) {
                    inventory.setItem(slot, nodeItemStack);
                }
                nodeMap.put(key, containNode);
            }
        } catch (Exception e) {
            Chat.sendLogger(Level.WARNING, "§c[魂式体系] 文件 inventory.yml 的 "+configurationSection.getCurrentPath()+" 项设置异常，请检查并重新设置", false);
        }
    }

}
