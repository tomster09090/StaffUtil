package me.tomster09090.staffutil.commands.chatfunctions.chatfilter;

import me.tomster09090.staffutil.StaffUtil;
import me.tomster09090.staffutil.util.CC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

public class asyncChatCooldownListener implements Listener {

    StaffUtil main;

    public asyncChatCooldownListener(StaffUtil main) {
        this.main = main;
    }

    HashMap<UUID, Long> cooldown = new HashMap<>();


    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        if (!player.hasPermission("staff.cooldown.bypass")) {
            if (cooldown.containsKey(player.getUniqueId())) {
                if (System.currentTimeMillis() < cooldown.get(player.getUniqueId())) {
                    //Cooldown still active
                    e.setCancelled(true);

                    long whenItEnds = cooldown.get(player.getUniqueId());
                    long now2 = System.currentTimeMillis();
                    long difference = whenItEnds - now2;

                    int differenceInSeconds = (int) (difference / 1000);

                    String message = CC.translate(main.getConfig().getString("cooldown-message"));
                    String message1 = CC.replaceall(message, "%player%", player.getName());
                    String finalMessage = CC.replaceall(message1, "%cooldown%", String.valueOf(differenceInSeconds));
                    player.sendMessage(finalMessage);
                    return;
                }
            }

            long now = System.currentTimeMillis();
            long cooldownTime = main.getConfig().getInt("time-in-seconds") * 1000L;
            long nowPlusCooldown = now + cooldownTime;

            cooldown.put(player.getUniqueId(), nowPlusCooldown);
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){
        cooldown.remove(e.getPlayer().getUniqueId());
    }
}
