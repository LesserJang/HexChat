package com.jih10157.hexchat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class HexChat extends JavaPlugin {

    private final Pattern hex = Pattern.compile("#[0-9A-Fa-f]{6}");
    private final Pattern rgb = Pattern.compile(
        "#(25[0-5]|2[0-4][0-9]|1[0-9]?[0-9]?|[1-9][0-9]?|[0-9]), ?(25[0-5]|2[0-4][0-9]|1[0-9]?[0-9]?|[1-9][0-9]?|[0-9]), ?(25[0-5]|2[0-4][0-9]|1[0-9]?[0-9]?|[1-9][0-9]?|[0-9])");

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onMessage(AsyncPlayerChatEvent event) {
                String msg = event.getMessage();
                Matcher hexMatcher = hex.matcher(msg);
                while (hexMatcher.find()) {
                    msg = msg
                        .replace(hexMatcher.group(), ChatColor.of(hexMatcher.group()).toString());
                }
                Matcher rgbMatcher = rgb.matcher(msg);
                while (rgbMatcher.find()) {
                    msg = msg.replace(rgbMatcher.group(), ChatColor
                        .of(rgbToHexReturnStr(Integer.parseInt(rgbMatcher.group(1)),
                            Integer.parseInt(rgbMatcher.group(2)),
                            Integer.parseInt(rgbMatcher.group(3)))).toString());
                }
                event.setMessage(msg);
            }
        }, this);
    }

    public static String rgbToHexReturnStr(int r, int g, int b) {
        return String.format("#%02x%02x%02x", r, g, b);
    }
}
