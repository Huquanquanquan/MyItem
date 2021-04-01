package cn.mokier.soulitem.spigotutils.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mokier
 */
public class Utils {

    public static String filterVar(String str, String... values) {
        if(values != null) {
            if(values.length % 2 == 0) {
                for(int i = 0;i < values.length;) {
                    str = str.replace(values[i], values[i + 1]);
                    i += 2;
                    if(i >= values.length) {
                        break;
                    }
                }
            }
        }
        return str;
    }

    public static List<String> filterVar(List<String> strs, boolean isFilter, String... values) {
        List<String> list = new ArrayList<>();
        for(String str : strs) {
            str = ChatColor.translateAlternateColorCodes('&', str);
            list.add(isFilter ? filterVar(str, values) : str);
        }
        return list;
    }

    public static Location varyLocation(String str) {
        World world = Bukkit.getWorld(str.split(",")[0]);
        double x = Double.parseDouble(str.split(",")[1]);
        double y = Double.parseDouble(str.split(",")[2]);
        double z = Double.parseDouble(str.split(",")[3]);
        return new Location(world, x, y, z);
    }

    public static String varyLocation(Location location) {
        String world = location.getWorld().getName();
        DecimalFormat df = new DecimalFormat("0.00");
        String x = df.format(location.getX());
        String y = df.format(location.getY());
        String z = df.format(location.getZ());
        return world+","+x+","+y+","+z;
    }

}
