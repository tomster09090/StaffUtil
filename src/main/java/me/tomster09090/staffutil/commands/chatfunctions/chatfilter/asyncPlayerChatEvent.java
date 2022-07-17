package me.tomster09090.staffutil.commands.chatfunctions.chatfilter;

import me.tomster09090.staffutil.StaffUtil;
import me.tomster09090.staffutil.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class asyncPlayerChatEvent implements Listener {

    StaffUtil main;

    public asyncPlayerChatEvent(StaffUtil main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();

        if (player.hasPermission("staff.cf.bypass"))
            return;
        String staffMessage = main.getCustomConfig().getString("blocker-staff-message");
        String playerMessage = main.getCustomConfig().getString("blocker-message");
        String whatWasSaid = e.getMessage();
        String removeSpace = whatWasSaid.replace("\\s+", "");
        String message = removeSpace.toLowerCase();
        String[] words = whatWasSaid.split(" ");
        List<String> blockedWords = main.getCustomConfig().getStringList("blocked-keywords");
        for (String word : blockedWords) {
            if (message.contains(word)) {
                if (main.getCustomConfig().getBoolean("write-to-log")) ;
                try {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(this.main.file, true));
                    bw.append("[").append((new SimpleDateFormat("dd-MM-yyyy HH:mm:ss")).format(new Date())).append("] ").append(e.getPlayer().getName()).append(": ").append(e.getMessage()).append("\n");
                    bw.close();
                } catch (Exception ex) {
                }
                for (Player players : Bukkit.getOnlinePlayers()) {
                    e.setCancelled(true);
                    if (players.hasPermission("staff.cf.blocked-word")) {
                        players.sendMessage(CC.translate(staffMessage).replaceAll("%message%", whatWasSaid).replaceAll("%player%", player.getName()));
                    }
                }
                player.sendMessage(CC.translate(playerMessage).replaceAll("%player%", player.getName()));
            }
        }
    }
}
