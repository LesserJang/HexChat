package com.jih10157.hexchat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class HexChat extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onMessage(AsyncPlayerChatEvent event) {
                if (event.getPlayer().hasPermission("hexchat.chat.hex") || event.getPlayer()
                    .hasPermission("hexchat.chat.rgb")) {
                    event.setMessage(applyColor(event.getMessage(),
                        event.getPlayer().hasPermission("hexchat.chat.hex"),
                        event.getPlayer().hasPermission("hexchat.chat.rgb")));
                }
            }

            @EventHandler
            public void onCreateSign(SignChangeEvent event) {
                if (event.getPlayer().hasPermission("hexchat.sign.hex") || event.getPlayer()
                    .hasPermission("hexchat.sign.rgb")) {
                    String line1 = event.getLine(0);
                    if (line1 != null) {
                        event.setLine(0, applyColor(line1,
                            event.getPlayer().hasPermission("hexchat.sign.hex"),
                            event.getPlayer().hasPermission("hexchat.sign.rgb")));
                    }
                    String line2 = event.getLine(1);
                    if (line2 != null) {
                        event.setLine(1, applyColor(line2,
                            event.getPlayer().hasPermission("hexchat.sign.hex"),
                            event.getPlayer().hasPermission("hexchat.sign.rgb")));
                    }
                    String line3 = event.getLine(2);
                    if (line3 != null) {
                        event.setLine(2, applyColor(line3,
                            event.getPlayer().hasPermission("hexchat.sign.hex"),
                            event.getPlayer().hasPermission("hexchat.sign.rgb")));
                    }
                    String line4 = event.getLine(3);
                    if (line4 != null) {
                        event.setLine(3, applyColor(line4,
                            event.getPlayer().hasPermission("hexchat.sign.hex"),
                            event.getPlayer().hasPermission("hexchat.sign.rgb")));
                    }
                }
            }
        }, this);
    }

    private final Pattern hex = Pattern.compile("#[0-9A-Fa-f]{6}");
    private final Pattern rgb = Pattern.compile(
        "#(25[0-5]|2[0-4][0-9]|1[0-9]?[0-9]?|[1-9][0-9]?|[0-9]), ?(25[0-5]|2[0-4][0-9]|1[0-9]?[0-9]?|[1-9][0-9]?|[0-9]), ?(25[0-5]|2[0-4][0-9]|1[0-9]?[0-9]?|[1-9][0-9]?|[0-9])");

    public String applyColor(String str, boolean applyHex, boolean applyRgb) {
        if (applyHex) {
            Matcher hexMatcher = hex.matcher(str);
            while (hexMatcher.find()) {
                str = str.replace(hexMatcher.group(),
                    ChatColor.of(hexMatcher.group()).toString());
            }
        }
        if (applyRgb) {
            Matcher rgbMatcher = rgb.matcher(str);
            while (rgbMatcher.find()) {
                str = str.replace(rgbMatcher.group(), ChatColor
                    .of(rgbToHexReturnStr(Integer.parseInt(rgbMatcher.group(1)),
                        Integer.parseInt(rgbMatcher.group(2)),
                        Integer.parseInt(rgbMatcher.group(3)))).toString());
            }
        }
        return str;
    }

    public static String rgbToHexReturnStr(int r, int g, int b) {
        return String.format("#%02x%02x%02x", r, g, b);
    }
}
