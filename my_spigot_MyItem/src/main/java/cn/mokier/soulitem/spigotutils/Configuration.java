package cn.mokier.soulitem.spigotutils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Configuration {

    private static File file;
    private static FileConfiguration configuration;

    protected static void init(JavaPlugin javaPlugin, String name) {
        file = new File(javaPlugin.getDataFolder(), name+".yml");
        if (!file.exists()) {
            javaPlugin.saveResource(name+".yml", false);
        }
        configuration = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration getConfiguration() {
        return configuration;
    }

}
