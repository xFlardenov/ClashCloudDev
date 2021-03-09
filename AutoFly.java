package ua.flardenov.autofly;

import java.io.File;

import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public class AutoFly extends JavaPlugin {
	
    public void onEnable() {
        this.saveDefaultConfig();
        new File(this.getDataFolder(), "config.yml");
        final String permission = this.getConfig().getString("permission");
        final String message = this.getConfig().getString("message");
        final boolean byDefault = this.getConfig().getBoolean("byDefault");
        this.getServer().getPluginManager().registerEvents((Listener)new Listeners(permission, message, byDefault), (org.bukkit.plugin.Plugin)this);
    }

		
		
		
	}


