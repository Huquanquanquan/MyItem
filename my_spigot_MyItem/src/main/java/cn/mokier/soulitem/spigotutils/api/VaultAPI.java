package cn.mokier.soulitem.spigotutils.api;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.UUID;

public class VaultAPI {

    private static Economy economy;

    public static boolean init() {
        RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }

    /**
     * 扣除金币
     * @param uuid 玩家UUID
     * @param amount 数量
     * @return true 金币足够，扣除成功
     */
    public static boolean withdraw(UUID uuid, int amount) {
        OfflinePlayer player = Bukkit.getPlayer(uuid);
        return !economy.has(player, amount) || !economy.withdrawPlayer(player, amount).transactionSuccess();
    }


    /**
     * 给与金币
     * @param uuid 玩家UUID
     * @param amount 数量
     * @return true 给与成功
     */
    public static boolean give(UUID uuid, int amount) {
        OfflinePlayer player = Bukkit.getPlayer(uuid);
        return economy.depositPlayer(player, amount).transactionSuccess();
    }

    /**
     * 获得已有金币
     * @param uuid 玩家UUID
     * @return
     */
    public static double getAmount(UUID uuid) {
        OfflinePlayer player = Bukkit.getPlayer(uuid);
        return economy.getBalance(player);
    }

    public static Economy getEconomy() {
        return economy;
    }

}
