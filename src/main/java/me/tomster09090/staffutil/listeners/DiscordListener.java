package me.tomster09090.staffutil.listeners;

import me.tomster09090.staffutil.StaffUtil;
import me.tomster09090.staffutil.util.CC;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import java.util.List;

public class DiscordListener extends ListenerAdapter implements Listener {

    StaffUtil main;

    public DiscordListener(StaffUtil main) {
        this.main = main;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (main.getConfig().getBoolean("discord-chat-to-minecraft")) {
            if (e.getChannel().getName().equals(main.getConfig().getString("text-channel-name"))) {
                if (!e.getMessage().getAuthor().isBot()) {
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        players.sendMessage(CC.RED + e.getMember().getUser().getAsTag() + CC.GRAY + ": " + e.getMessage().getContentRaw());
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerMessage(AsyncPlayerChatEvent e) {
        String message = e.getMessage();
        message = message.replaceAll("@everyone", "`@everyone`").replaceAll("@here", "`@here`");
        if (!e.getPlayer().hasPermission("staff.cf.bypass")) {
            try {
                String[] words = message.split(" ");
                List<String> blockedWords = main.getCustomConfig().getStringList("blocked-keywords");
                for (String word : blockedWords) {
                    if (message.contains(word)) {
                        for (Player players : Bukkit.getOnlinePlayers()) {
                            e.setCancelled(true);
                        }
                    }
                }
            }catch (Exception exception){
                exception.printStackTrace();
            }
        }
        //TextChannel textChannel = main.getJda().getTextChannelsByName(main.getConfig().getString("text-channel-name"), true).get(0);
        //textChannel.sendMessage("**[MINECRAFT] " + e.getPlayer().getName() + ":** " + message).queue();
    }
}