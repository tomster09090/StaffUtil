package me.tomster09090.staffutil.commands.chatfunctions;

import me.tomster09090.staffutil.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class reportPlayer implements CommandExecutor {



    //REPORT <PLAYERNAME> <REASON>
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        int numOfReports = 0;

        if (sender instanceof Player){
            Player player = (Player) sender;

            if (player.hasPermission("staff.report")){
                if (args.length > 1) {
                Player target = Bukkit.getServer().getPlayer(args[0]);
                if (target == null) player.sendMessage(CC.RED + "You can only report players currently online.");
                if (target != player) {
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        StringBuilder builder = new StringBuilder();
                        for (int i = 1; i < args.length; i++) {
                            builder.append(args[i]).append(" ");
                            builder.toString();
                        } if (builder.toString() != null){
                            if (player.hasPermission("staff.report.read")){
                                player.sendMessage(CC.RED + "Player " + CC.GREEN + player.getName() + CC.RED + " Has reported player "
                                        + CC.GREEN + target.getName() + CC.RED +  " for the reason of " + CC.GREEN + builder);
                            }
                            player.sendMessage(CC.GREEN + "Your report has been placed.");
                        }
                    }
                }else {
                    player.sendMessage(CC.RED + "You cannot report yourself!");
                }
            }else{
                    player.sendMessage(CC.RED + "Incorrect usage! \n Please do /report <player> <reason>!");
                }
            }
       }
        return true;
    }
}
