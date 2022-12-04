package me.tomster09090.staffutil.commands.rsp;

import me.tomster09090.staffutil.StaffUtil;
import me.tomster09090.staffutil.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;

public class onPackLoad implements Listener {

    StaffUtil main;

    public onPackLoad(StaffUtil main) {
        this.main = main;
    }

    @EventHandler
    public void onPack(PlayerResourcePackStatusEvent e) {
        Player player = e.getPlayer();
    if (main.getConfig().getBoolean("force-resource-pack")) {
    if (e.getStatus().equals(PlayerResourcePackStatusEvent.Status.DECLINED)) {
        player.kickPlayer(CC.RED + "You have to enable the resource pack.");
    } else if (e.getStatus().equals(PlayerResourcePackStatusEvent.Status.FAILED_DOWNLOAD)) {
        player.kickPlayer(CC.RED + "The resource pack failed to download - please try again or contact a staff member.");
    }
}
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        if (main.getConfig().getBoolean("force-resource-pack")) {
            try {
                e.getPlayer().setResourcePack(main.getConfig().getString("resource-pack-link"));
            }catch (NullPointerException ex){
                e.getPlayer().sendMessage(CC.RED + "An error has occurred.(Staff) Please defer to the terminal for more information.");
                e.getPlayer().sendMessage(CC.RED + "If issue cannot be resolved, please contact the development team.");
                System.out.println("An Error has occourred. Please defer to console for more infomation.");
                ex.printStackTrace();
            }
        }else {
            if (main.getConfig().getBoolean("console-notify-on-fail")) {
                //Bukkit.getServer().getLogger().severe("[STAFFUTILS] RESOURCE PACK IS DISABLED FULLY IN CONFIG - THEREFORE THE PLUGIN WILL NOT ATTEMPT TO USE IT");
                //Commented out on fail logger - as due requests.
            }
        }
    }
}
