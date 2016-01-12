package me.AntiBully;

import java.util.Arrays;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class AntiBully extends JavaPlugin implements Listener 
{
    public static final List<String> LIST_1 = Arrays.asList();

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
     public static final List<String> SWEAR_LIST = Arrays.asList("INSERT WORDS HERE");
     public static final List<String> BULLY_LIST = Arrays.asList("INSERT WORDS HERE");
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) 
    {
            final Player player = event.getPlayer();
            String message = event.getMessage().trim();   
            
            if(message.contains((CharSequence) SWEAR_LIST)) 
            {
              player.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + "Anti" + ChatColor.RED + "Bully" + ChatColor.DARK_GRAY + "] " + " AntiBully has blocked your message as you have swore!");
              event.setCancelled(true);
            }
            
            if(message.contains((CharSequence) BULLY_LIST)) 
            {
              player.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + "Anti" + ChatColor.RED + "Bully" + ChatColor.DARK_GRAY + "] " + " AntiBully has blocked your message as you have said a blocked word!.");
              event.setCancelled(true);
            }
    }
}
