package ua.flardenov.clashapil;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import ua.flardenov.clashapi.ClashAPI;
import ua.flardenov.clashapi.npc.NPCManager;

public abstract class ClashLoader extends JavaPlugin {
	
	private static Plugin plugin;
	
	public void onLoad() {
		super.onLoad();
		this.preLoad();
		ClashLogger.send(LogEnum.INFO, "\u041f\u043b\u0430\u0433\u0438\u043d &7" + this.getDescription().getName() + "§f \u0437\u0430\u0433\u0440\u0443\u0436\u0435\u043d!");
		this.getDataFolder().mkdirs();
		ClashLoader.plugin = (Plugin)this;
	}
	
	public ClashAPI getAPI() {
		return ClashAPI.getInstance();
		
	}
	
	public NPCManager getManagerNPC() {
		return ClashAPI.getInstance().getNpcManager();
	}
	
	public TagsManager getManagerTags() {
		return ClashAPI.getInstance().getTagsManager();
	}
	
	public ServerBoardManager getManagerServerBoard() {
		return BoardConfig.getServerBoardManager();
	}
	
	public abstract void preLoad();
	
	public static Plugin getPlugin() {
		return ClashLoader.plugin;
	}

	
	

}
