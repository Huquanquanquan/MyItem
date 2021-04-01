package cn.mokier.soulitem.spigotutils.contain;

import cn.mokier.spigotutils.chat.Chat;
import cn.mokier.spigotutils.utils.ItemStackUtils;
import lombok.Data;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.logging.Level;

@Data
public class ContainNode {

    private ContainNodeTypes nodeType;
    private List<Integer> slot;
    private ItemStack itemStack;

    public ContainNode(ConfigurationSection configurationSection) {
        try {
            nodeType = ContainNodeTypes.valueOf(configurationSection.getString("nodeType"));
            slot = configurationSection.getIntegerList("slot");
            itemStack = ItemStackUtils.toItemStackByConfiguration(configurationSection);

            if(nodeType ==null || slot==null || itemStack==null) {
                throw new Exception();
            }
        } catch (Exception e) {
            Chat.sendLogger(Level.WARNING, "§c[魂式体系] 文件 inventory.yml 的 "+configurationSection.getCurrentPath()+" 项设置异常，请检查并重新设置", false);
        }
    }

}
