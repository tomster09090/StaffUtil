package me.tomster09090.staffutil.commands.chatfunctions;

import me.tomster09090.staffutil.StaffUtil;
import me.tomster09090.staffutil.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.swing.*;

public class clearChat implements CommandExecutor {

    StaffUtil main;

    public clearChat(StaffUtil main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            if (player.hasPermission("staff.cc")) {
                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (!players.hasPermission("staff.cc.bypass")){
                        for (int i = 0; i < main.getConfig().getInt("number-of-lines-to-clear"); i++){
                            players.sendMessage("");
                        }
                        players.sendMessage(CC.RED + "Chat was cleared by " + player.getName());
                    }else{
                        players.sendMessage(CC.GREEN + "Chat was cleared by " + player.getName() + ". However you have bypass.");
                    }
                }
            }
        }
        return false;
    }
}
