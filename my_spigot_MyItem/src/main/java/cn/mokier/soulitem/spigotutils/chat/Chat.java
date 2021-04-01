package cn.mokier.soulitem.spigotutils.chat;

import cn.mokier.spigotutils.utils.Utils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Chat extends Lang {

    private static Logger logger;

    public static void init(JavaPlugin javaPlugin, List<ChatTypes> chatTypes) {
        if(chatTypes == null) {
            chatTypes = new ArrayList<>();
            chatTypes.add(ChatTypes.MESSAGE);
            chatTypes.add(ChatTypes.BROADCAST);
        }
        Lang.init(javaPlugin, chatTypes);
        logger = javaPlugin.getLogger();
    }

    public static void sendLangMessage(CommandSender sender, String node, boolean isFilter, String... replaces) {
        try {
            List<String> msgList = getMessage(node);

            for(String msg : msgList) {
                ChatTypes type = toChatTypes(msg);
                String name = type.getName();

                msg = msg.replace(name, "");
                msg = isFilter ? Utils.filterVar(msg, replaces) : msg;
                switch (type) {
                    case MESSAGE:
                        sender.sendMessage(msg);
                        break;
                    case BROADCAST:
                        Bukkit.broadcastMessage(msg);
                        break;
                    case ACTIONBAR:
                        sendActionBar(sender, msg, node);
                        break;
                    case TITLE:
                        sendTitle(sender, msg, node);
                        break;
                    default:
                        break;
                }
            }
        } catch (Exception e) {
            sendLogger(Level.WARNING, "§c[魂式体系] 文件 lang.yml 的 "+node+" 项设置异常，请检查并重新设置", false);
        }
    }

    public static void sendMessage(CommandSender sender, String msg, boolean isFilter, String... replaces) {
        msg = isFilter ? Utils.filterVar(msg) : msg;
        sender.sendMessage(msg);
    }

    public static void sendLogger(Level level, String msg, boolean isFilter, String... replaces) {
        msg = isFilter ? Utils.filterVar(msg, replaces) : msg;
        logger.log(level, msg);
    }

    public static void sendTitle(CommandSender sender, String msg, String node) {
        try {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                String title = msg.split(",")[0];
                String subtitle = msg.split(",")[1];
                int fadeIn = Integer.parseInt(msg.split(",")[2]);
                int stay = Integer.parseInt(msg.split(",")[3]);
                int fadeOut = Integer.parseInt(msg.split(",")[4]);
                player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
            }else{
                sendLogger(Level.WARNING, "§c[魂式体系] 文件 lang.yml 的 "+node+" 项设置异常，发送的对象不支持Title信息", false);
            }
        }catch (ArrayIndexOutOfBoundsException e) {
            sendLogger(Level.WARNING, "§c[魂式体系] 文件 lang.yml 的 "+node+" 项设设置异常，Title信息请依据 &4主信息,子信息,淡入tick,持续tick,淡出tick &c进行设置", false);
        }
    }

    public static void sendActionBar(CommandSender sender, String msg, String node) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(msg));
        }else{
            sendLogger(Level.WARNING, "§c[魂式体系] 文件 lang.yml 的 "+node+" 项设置异常，发送的对象不支持actionBar信息", false);
        }
    }

}
