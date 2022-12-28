package me.tomster09090.staffutil;

import me.tomster09090.staffutil.commands.chatfunctions.chatfilter.asyncChatCooldownListener;
import me.tomster09090.staffutil.commands.chatfunctions.chatfilter.asyncPlayerChatEvent;
import me.tomster09090.staffutil.commands.chatfunctions.chatfilter.chatFilterCommand;
import me.tomster09090.staffutil.commands.chatfunctions.clearChat;
import me.tomster09090.staffutil.commands.chatfunctions.managementChatCommand;
import me.tomster09090.staffutil.commands.nvCommand;
import me.tomster09090.staffutil.commands.reloadCommand;
import me.tomster09090.staffutil.commands.rsp.onPackLoad;
import me.tomster09090.staffutil.commands.chatfunctions.staffChatCommand;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class StaffUtil extends JavaPlugin {
    // public File file = new File(getDataFolder(), "/logs/log.txt");
   // public File dir = new File(getDataFolder(), "logs");
    public File customConfigFile;
    public FileConfiguration customConfig;

    public FileConfiguration getCustomConfig() {
        return this.customConfig;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        //createCustomConfig();
        getConfig().options().copyDefaults();
        saveDefaultConfig();
       // logger();
        getCommand("clearchat").setExecutor(new clearChat(this));
       //getCommand("rsp").setExecutor(new resourcePackCommand(this));
        getCommand("nightvision").setExecutor(new nvCommand());
        Bukkit.getPluginManager().registerEvents(new onPackLoad(this), this);
        Bukkit.getPluginManager().registerEvents(new asyncChatCooldownListener(this), this);
        getCommand("cf").setExecutor(new chatFilterCommand(this));
        (getCommand("sc")).setExecutor(new staffChatCommand(this));
        getCommand("sreload").setExecutor(new reloadCommand(this));
        getCommand("mc").setExecutor(new managementChatCommand(this));
        Bukkit.getPluginManager().registerEvents((Listener) new asyncPlayerChatEvent(this), this);
        Bukkit.getServer().getLogger().info("[SeaCraft STAFFUTILS] SUCCESSFULLY STARTED");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
     //   this.file = null;
     //   this.dir = null;
        Bukkit.getServer().getLogger().info("[SeaCraft STAFFUTILS] SUCCESSFULLY STOPPED");
    }


    public void fileConfig() {

    }

 /*   private void createCustomConfig() {
        customConfigFile = new File(getDataFolder(), "blocked-keywords.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            saveResource("blocked-keywords.yml", false);
        }

        customConfig = new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
  */

/*    public void logger() {
        if (getCustomConfig().getBoolean("write-to-log")) {
            if (!this.dir.exists())
                this.dir.mkdirs();
            if (!this.file.exists())
                try {
                    this.file.createNewFile();
                } catch (IOException iOException) {
                }
        }
    }
 */
}