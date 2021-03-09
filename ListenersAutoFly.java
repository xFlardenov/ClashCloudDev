package ua.flardenov.autofly;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class Listeners implements Listener {
	
    private String permission;
    private String message;
    private boolean byDefault;
    
    Listeners(final String permission, final String message, final boolean byDefault) {
        this.permission = permission;
        this.message = message;
        this.byDefault = byDefault;
    }
    
    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        this.setFly(event.getPlayer());
    }
    
    @EventHandler
    public void onRespawn(final PlayerRespawnEvent event) {
        this.setFly(event.getPlayer());
    }
    
    private void setFly(final Player player) {
        if (player.hasPermission(this.permission)) {
            player.setAllowFlight(true);
            if (this.byDefault) {
                player.setFlying(true);
            }
            if (!this.message.isEmpty()) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.message));
            }
        }
    }
}


