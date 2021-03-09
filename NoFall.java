package ua.flardenov.nofall;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class NoFall extends JavaPlugin implements Listener {
	
	List<String> worlds;
	
	public NoFall() {
		this.worlds = new ArrayList<String>();
		
	}
	
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)this);
		this.saveConfig();
		for (final String world : this.getConfig().getStringList("worlds")) {
			if (Bukkit.getWorld(world) == null) {
				this.getLogger().info("Неправильно указан мир в config: " + world);	
			}
			else {
				this.worlds.add(world);
			}
		}
	}
	
	@EventHandler
	public void entityDamageEvent(final EntityDamageEvent event) {
		if (event.getEntityType() == EntityType.PLAYER) {
			final Player player = (Player)event.getEntity();
			 if (event.getCause() == EntityDamageEvent.DamageCause.FALL && player.hasPermission("nofall.use") && this.worlds.contains(player.getWorld().getName())) {
				 event.setCancelled(true);
			 }
		}
		
	}

}
