package cn.mokier.soulitem.spigotutils.api;

import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class PlayerPointsAPI {

    private final static String PLUGIN_API = "PlayerPoints";
    private static PlayerPoints playerPoints;

    public static boolean init() {
        final Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin(PLUGIN_API);
        playerPoints = PlayerPoints.class.cast(plugin);
        return playerPoints != null;
    }

    /**
     * 回收点券
     * @param uuid 玩家UUID
     * @param amount 数量
     * @return true 点券足够，回收成功
     */
    public static boolean withdraw(UUID uuid, int amount) {
        return !(playerPoints.getAPI().look(uuid)<amount || !playerPoints.getAPI().take(uuid, amount));
    }

    /**
     * 给与点券
     * @param uuid 玩家UUID
     * @param amount 数量
     * @return true 给与成功
     */
    public static boolean give(UUID uuid, int amount) {
        return playerPoints.getAPI().give(uuid, amount);
    }

    /**
     * 获得已有点券
     * @param uuid 玩家UUID
     * @return
     */
    public static double getAmount(UUID uuid) {
        return playerPoints.getAPI().look(uuid);
    }

    public static PlayerPoints getPlayerPoints() {
        return playerPoints;
    }

}
