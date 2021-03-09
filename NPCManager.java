package ua.flardenov.clashapi.npc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.bukkit.entity.Entity;
import org.bukkit.entity.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import ua.flardenov.clashapi.ClashAPI;

public class NPCManager {
	
    private static List<NPC> npcs;
    
    public NPCManager() {
        final File file = getDirectory();
        if (file.listFiles() != null) {
            for (final File npcconfig : Objects.requireNonNull(file.listFiles())) {
                new NPCConfig(npcconfig);
            }
        }
        new ClashListener() {
            @EventHandler
            public void onClick(final PlayerInteractEntityEvent event) {
                if (event.getRightClicked().hasMetadata("NPC") || NPCManager.getById(event.getRightClicked().getEntityId()) != null) {
                    final NPCClickEvent clickEvent = new NPCClickEvent(InteractType.RIGHT, NPCManager.getById(event.getRightClicked().getEntityId()), event.getPlayer());
                    clickEvent.call();
                }
            }
            
            @EventHandler
            public void onClick(final EntityDamageByEntityEvent event) {
                if (!(event.getDamager() instanceof Player)) {
                    return;
                }
                final Player player = (Player)event.getDamager();
                final Entity entity = event.getEntity();
                if (entity.hasMetadata("NPC") || NPCManager.getById(entity.getEntityId()) != null) {
                    final NPCClickEvent clickEvent = new NPCClickEvent(InteractType.LEFT, NPCManager.getById(entity.getEntityId()), player);
                    clickEvent.call();
                }
            }
            
            @EventHandler
            public void onNPCClick(final NPCClickEvent event) {
                if (event.getNpc() == null) {
                    return;
                }
                event.getNpc().execute(event.getPlayer());
            }
            
            @EventHandler
            public void onChat(final AsyncPlayerChatEvent event) {
                if (NPCCommand.exist(event.getPlayer().getName().toLowerCase())) {
                    event.setCancelled(true);
                    if (event.getMessage().isEmpty()) {
                        return;
                    }
                    if (event.getMessage().equalsIgnoreCase("cancel") || event.getMessage().equalsIgnoreCase("\u043e\u0442\u043c\u0435\u043d\u0430")) {
                        NPCCommand.remove(event.getPlayer().getName().toLowerCase());
                        return;
                    }
                    final NPC npc = NPCCommand.commandHashMap.get(event.getPlayer().getName().toLowerCase()).keySet().iterator().next();
                    final NPCCommand command = NPCCommand.commandHashMap.get(event.getPlayer().getName().toLowerCase()).values().iterator().next();
                    command.setCommand(event.getMessage());
                    npc.addCommand(command);
                    event.getPlayer().sendMessage("§6ClashAPI §8| §f\u0412\u044b \u0443\u0441\u043f\u0435\u0448\u043d\u043e \u0434\u043e\u0431\u0430\u0432\u0438\u043b\u0438 \u043d\u043e\u0432\u0443\u044e \u043a\u043e\u043c\u0430\u043d\u0434\u0443 NPC §7" + npc.getName() + " §e(" + npc.getEntityID() + ")");
                    event.getPlayer().sendMessage("§8\u041a\u043e\u043c\u0430\u043d\u0434\u0430: §b" + event.getMessage() + " §8\u0422\u0438\u043f: " + command.getNpcCommandType().name());
                    NPCCommand.remove(event.getPlayer().getName().toLowerCase());
                }
            }
            
            @EventHandler
            public void onJoin(final PlayerJoinEvent event) {
                for (final NPC npc : NPCManager.npcs) {
                    npc.show(event.getPlayer());
                }
            }
        };
    }
    
    public static NPCCommand getByID(final NPC npc, final int id) {
        return (npc.getCommands() != null && !npc.getCommands().isEmpty() && id > npc.getCommands().size() - 1) ? npc.getCommands().get(id) : null;
    }
    
    public static File getDirectory() {
        final File file = new File(ClashAPI.getInstance().getDataFolder(), "npc");
        if (!file.exists()) {
            file.mkdir();
        }
        return file;
    }
    
    public static NPC getById(final int id) {
        for (final NPC npc : NPCManager.npcs) {
            if (npc.getEntityID() == id) {
                return npc;
            }
        }
        return null;
    }
    
    public static void saveall() {
        for (final NPC npc : NPCManager.npcs) {
            try {
                npc.save();
            }
            catch (Exception ignore) {
                System.out.println(" ");
                System.out.println("NPC " + npc.getName() + " saving error!");
                System.out.println(" ");
            }
        }
    }
    
    public static void add(final NPC npc) {
        NPCManager.npcs.add(npc);
    }
    
    public static void remove(final NPC npc) {
        NPCManager.npcs.remove(npc);
    }
    
    public static NPC getNpc(final String name) {
        for (final NPC npc : NPCManager.npcs) {
            if (npc.getName().toLowerCase().equalsIgnoreCase(name)) {
                return npc;
            }
        }
        return null;
    }
    
    public static List<NPC> getNpcs() {
        return NPCManager.npcs;
    }
    
    static {
        NPCManager.npcs = new ArrayList<NPC>();
    }
}

