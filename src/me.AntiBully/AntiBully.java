package me.AntiBully;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class AntiBully extends JavaPlugin implements Listener 
{   
    @Override
    public void onEnable()
    {
     getLogger().info("AntiBully has started up! :D");
    }
    
    @Override
    public void onDisable()
    {
     getLogger().info("AntiBully has been disabled! D:");
    }
     
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) 
    {
            final Player p = event.getPlayer();
            String message = event.getMessage().trim();   
            ConfigManager cm = ConfigManager.getConfig(p);
            
            if (!cm.exists())
            {
            FileConfiguration f = cm.getConfig();
            
            f.addDefault("blocked_words", "ADD THEM HERE");
            }
            
            if (!cm.getConfig().getString("staff").equals(p.getName()) && message.contains(cm.getConfig().getString("blocked_words"))) 
            {
              p.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + cm.getConfig().getString("server_name") + ChatColor.DARK_GRAY + "] " + " AntiBully has blocked your message as you have said a blocked word!.");
              event.setCancelled(true);
            }
    }
}
