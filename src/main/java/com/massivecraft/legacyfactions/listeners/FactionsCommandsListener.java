package com.massivecraft.legacyfactions.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import com.massivecraft.legacyfactions.FactionsPluginBase;
import com.massivecraft.legacyfactions.entity.FPlayerColl;

public class FactionsCommandsListener implements Listener {

    private FactionsPluginBase p;

    public FactionsCommandsListener(FactionsPluginBase p) {
        this.p = p;
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        if (FactionsPlayerListener.preventCommand(event.getMessage(), event.getPlayer())) {
            if (p.logPlayerCommands()) {
                Bukkit.getLogger().info("[PLAYER_COMMAND] " + event.getPlayer().getName() + ": " + event.getMessage());
            }
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (p.handleCommand(event.getPlayer(), event.getMessage(), false, true)) {
            if (p.logPlayerCommands()) {
                Bukkit.getLogger().info("[PLAYER_COMMAND] " + event.getPlayer().getName() + ": " + event.getMessage());
            }
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerPreLogin(PlayerLoginEvent event) {
        FPlayerColl.getInstance().getByPlayer(event.getPlayer());
    }
}
