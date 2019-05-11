package tk.xmon.helpop.utils;

import org.bukkit.ChatColor;

public class ChatUtil {
    public static String fixColor(String text) {
        return ChatColor.translateAlternateColorCodes('&', text.replace(">>", "»").replace("<<", "«").replace("*", "¢").replace("{O}", "\u2022").replace("{NEWLINE}", "\n"));
    }

    public static boolean canUse(final long saveTime, final long time) {
        return System.currentTimeMillis() - saveTime >= time;
    }
}
