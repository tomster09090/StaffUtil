package me.tomster09090.staffutil;

import me.tomster09090.staffutil.commands.chatfunctions.chatfilter.asyncChatCooldownListener;
import me.tomster09090.staffutil.commands.chatfunctions.chatfilter.asyncPlayerChatEvent;
import me.tomster09090.staffutil.commands.chatfunctions.chatfilter.chatFilterCommand;
import me.tomster09090.staffutil.commands.chatfunctions.clearChat;
import me.tomster09090.staffutil.commands.chatfunctions.managementChatCommand;
import me.tomster09090.staffutil.commands.chatfunctions.reportPlayer;
import me.tomster09090.staffutil.commands.reloadCommand;
import me.tomster09090.staffutil.commands.rsp.onPackLoad;
import me.tomster09090.staffutil.commands.rsp.resourcePackCommand;
import me.tomster09090.staffutil.commands.chatfunctions.staffChatCommand;
import me.tomster09090.staffutil.listeners.DiscordListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public final class StaffUtil extends JavaPlugin {

    public File file = new File(getDataFolder(), "/logs/log.txt");
    public File dir = new File(getDataFolder(), "logs");
    public File customConfigFile;
    public FileConfiguration customConfig;

    public JDABuilder builder = JDABuilder.createDefault(this.getConfig().getString("bot-token"));
    private JDA jda;

    public FileConfiguration getCustomConfig() {
        return this.customConfig;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        startBot();
        createCustomConfig();
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        logger();
        getCommand("clearchat").setExecutor(new clearChat(this));
        getCommand("rsp").setExecutor(new resourcePackCommand(this));
        Bukkit.getPluginManager().registerEvents(new onPackLoad(this), this);
        Bukkit.getPluginManager().registerEvents(new DiscordListener(this), this);
        Bukkit.getPluginManager().registerEvents(new asyncChatCooldownListener(this), this);
        getCommand("cf").setExecutor(new chatFilterCommand(this));
        Objects.requireNonNull(getCommand("sc")).setExecutor(new staffChatCommand(this));
        getCommand("sreload").setExecutor(new reloadCommand(this));
        getCommand("report").setExecutor(new reportPlayer() );
        getCommand("mc").setExecutor(new managementChatCommand(this));
        Bukkit.getPluginManager().registerEvents((Listener)new asyncPlayerChatEvent(this), this);
        Bukkit.getServer().getLogger().severe("[EXPLORERVILLE STAFFUTILS] SUCCESSFULLY STARTED");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.file = null;
        this.dir = null;
        if (jda != null) jda.shutdownNow();
        Bukkit.getServer().getLogger().severe("[EXPLORERVILLE STAFFUTILS] SUCCESSFULLY STOPPED");
    }


    public void fileConfig() {

            }
    private void createCustomConfig() {
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
    public void logger() {
        if (getCustomConfig().getBoolean("write-to-log")) {
            if (!this.dir.exists())
                this.dir.mkdirs();
            if (!this.file.exists())
                try {
                    this.file.createNewFile();
                } catch (IOException iOException) {}
        }
    }
    public void startBot(){

        //GRABS BOT TOKEN FROM CONFIG.YML
        JDABuilder builder = JDABuilder.createDefault(this.getConfig().getString("bot-token"));
        builder.setActivity(Activity.playing(getConfig().getString("bot-activity")));
        builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
        builder.addEventListeners(new DiscordListener(this));

        try {
            jda = builder.build();
        }catch (LoginException e){
            e.printStackTrace();
        }
    }

    public JDA getJda() { return jda; }
}
