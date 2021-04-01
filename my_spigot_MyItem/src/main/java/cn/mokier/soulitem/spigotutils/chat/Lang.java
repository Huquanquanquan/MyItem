package cn.mokier.soulitem.spigotutils.chat;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lang {

    private static File file;
    private static FileConfiguration configuration;
    private static String prefix;
    private static Map<String, List<String>> messageMap;
    private static List<ChatTypes> chatTypes;

    public static void init(JavaPlugin javaPlugin, List<ChatTypes> chatTypes) {
        Lang.chatTypes = chatTypes;

        file = new File(javaPlugin.getDataFolder(), "lang.yml");
        if (!file.exists()) {
            javaPlugin.saveResource("lang.yml", false);
        }

        configuration = YamlConfiguration.loadConfiguration(file);
        prefix = configuration.getString("prefix", "");

        initMessageMap();
    }


    /**
     * 获得信息类型
     */
    public static ChatTypes toChatTypes(String msg) {
        for(ChatTypes chatType : chatTypes) {
            String type = chatType.getName();
            if(msg.startsWith(type)) {
                return chatType;
            }
        }
        return null;
    }

    public static FileConfiguration getConfiguration() {
        return configuration;
    }

    public static List<String> getMessage(String node) {
        return messageMap.get(node);
    }

    public static Map<String, List<String>> getMessageMap() {
        return messageMap;
    }
    public static String getPrefix() {
        return prefix;
    }

    /**
     * 初始化信息集合
     */
    private static void initMessageMap() {
        messageMap = new HashMap<>();
        for(String node : configuration.getKeys(true)) {
            List<String> msgList = getMessageByNode(node);

            if(msgList!=null) {
                messageMap.put(node, msgList);
            }
        }
    }

    /**
     * 通过路径获得信息
     * @param node 路径
     * @return
     */
    private static List<String> getMessageByNode(String node) {
        if(configuration.isConfigurationSection(node) || configuration.getString(node).equalsIgnoreCase("none")) {
            return null;
        }

        List<String> msgList = new ArrayList<>();
        if(configuration.isList(node)) {
            for(String msg : configuration.getStringList(node)) {
                msgList.add(replace(msg));
            }
        }else {
            msgList.add(replace(configuration.getString(node)));
        }

        return msgList;
    }

    /**
     * 替换信息
     * @param msg 信息
     * @return
     */
    private static String replace(String msg) {
        for(ChatTypes chatType : chatTypes) {
            String type = chatType.getName();
            if(msg.startsWith(type)) {
                msg = chatType == ChatTypes.MESSAGE || chatType == ChatTypes.BROADCAST
                        ? type + prefix + msg.replace(type, "")
                        : type + msg.replace(type, "") ;
                return ChatColor.translateAlternateColorCodes('&', msg);
            }
        }

        msg = ChatTypes.MESSAGE.getName() + prefix + msg;
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}