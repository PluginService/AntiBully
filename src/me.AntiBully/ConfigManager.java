package me.AntiBully;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager {
	
	private final UUID u;
	private FileConfiguration fc;
	private File file;
	private final JavaPlugin plugin = JavaPlugin.getProvidingPlugin(this.getClass());
	private static List<ConfigManager> configs = new ArrayList<>();

	private ConfigManager(Player p) {
		this.u = p.getUniqueId();

		configs.add(this);
	}
	
	private ConfigManager(UUID u) {
		this.u = u;

		configs.add(this);	
	}

	/**
	 * Gets the owner of the config
	 * 
	 * @return The player as type bukkit.entity.Player
	 */
	public Player getOwner() {
		if (u == null)
			try {
				throw new Exception();
			} catch (Exception e) {
				getInstance().getLogger().warning("ERR... Player is Null!");
				e.printStackTrace();
			}
		return Bukkit.getPlayer(u);
	}
	
	/**
	 * Gets the owner of the config as UUID
	 * 
	 * @return
	 * 	java.util.UUID
	 */
	public UUID getOwnerUUID() {
		if (u == null)
			try {
				throw new Exception();
			} catch (Exception e) {
				getInstance().getLogger().warning("ERR... Player is Null!");
				e.printStackTrace();
			}
		return u;
	}
	
	/**
	 * Returns an instanceof the JavaPlugin. AKA the Main class.
	 * @return
	 * 	The class that extends JavaPlugin
	 */
	public JavaPlugin getInstance() {
		if (plugin == null)
			try {
				throw new Exception();
			} catch (Exception e) {
				e.printStackTrace();
			}
		return plugin;
	}

	/**
	 * Get a config from type 'ConfigManager'. If it doesn't exist it will
	 * create a new ConfigManager. NOTE: Player 'p' must be exactly the
	 * ConfigManager's name. Creates thread safe. So there is only one
	 * instanceof this class ever.
	 * 
	 * @param p
	 *            The Player of the config found by getOwner()
	 * @return Config for given player.
	 */
	public static ConfigManager getConfig(Player p) {
		for (ConfigManager c : configs) {
	           if (c.getOwnerUUID().equals(p.getUniqueId())) {
	               return c;
	            }
	    }
		return new ConfigManager(p);
	}
	
	/**
	 * Get a config from type 'ConfigManager'. If it doesn't exist it will
	 * create a new ConfigManager. NOTE: UUID 'u' must be exactly the
	 * ConfigManager's name. Creates thread safe. So there is only one
	 * instanceof this class ever.
	 * 
	 * @param u
	 * 	The UUID of the player who is the owner of the config
	 * 
	 * @return
	 * 	Config for given UUID
	 */
	public static ConfigManager getConfig(UUID u) {
		for (ConfigManager c : configs) {
	           if (c.getOwnerUUID().equals(u)) {
	               return c;
	            }
	    }
		return new ConfigManager(u);
	}

	/**
	 * Deletes the file
	 * 
	 * @return True if the config was successfully deleted. If anything went
	 *         wrong it returns false
	 */
	public boolean delete() {
		return getFile().delete();
	}

	/**
	 * Checks to make sure the config is null or not. This is only a check and
	 * it wont create the config.
	 * 
	 * @return True if it exists & False if it doesn't
	 */
	public boolean exists() {
		if (fc == null || file == null) {
			File temp = new File(getDataFolder(), getOwnerUUID() + ".yml");
			if (!temp.exists()) {
				return false;
			} else {
				file = temp;
			}
		}
		return true;
	}
	
	

	/**
	 * Gets the plugin's folder. If none exists it will create it.
	 * 
	 * @return The folder as type java.io.File
	 */
	public File getDataFolder() {
		File dir = new File(ConfigManager.class.getProtectionDomain().getCodeSource().getLocation().getPath().replaceAll("%20", " "));
		File d = new File(dir.getParentFile().getPath(), getInstance().getName());
		if (!d.exists()) {
			d.mkdirs();
		}
		return d;
	}

	/**
	 * Gets the File for the owner. If none exists it will create it.
	 * 
	 * @return The File as type java.io.File
	 */
	public File getFile() {
		if (file == null) {
			file = new File(getDataFolder(), getOwnerUUID() + ".yml");
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return file;
	}

	/**
	 * Gets the config for the owner. If none exists it will create it.
	 * 
	 * @return The config as type
	 *         org.bukkit.configuration.file.FileConfiguration
	 */
	public FileConfiguration getConfig() {
		if (fc == null) {
			fc = YamlConfiguration.loadConfiguration(getFile());
		}
		return fc;
	}

	/**
	 * Reloads or "Gets" the file and config
	 */
	public void reload() {
		if (file == null) {
			file = new File(getDataFolder(), getOwner().getUniqueId().toString() + ".yml");
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			fc = YamlConfiguration.loadConfiguration(file);
		}
	}

	/**
	 * Deletes then creates the config
	 */
	public void resetConfig() {
		delete();
		getConfig();
	}

	/**
	 * Saves the config
	 */
	public void saveConfig() {
		try {
			getConfig().save(getFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
