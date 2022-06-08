package me.tomster09090.staffutil.commands.chatfunctions;

import me.tomster09090.staffutil.StaffUtil;
import me.tomster09090.staffutil.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class managementChatCommand implements CommandExecutor {

    StaffUtil plugin;

    public managementChatCommand(StaffUtil plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (sender.hasPermission("staff.mc")){
                for (Player players : Bukkit.getOnlinePlayers()){
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < args.length; i++) {
                        builder.append(args[i]).append(" ");
                    }
                    if (players.hasPermission("staff.mc")){
                        String replacedText = CC.replaceall(plugin.getConfig().getString("management-chat-prefix-format"), "%name%", sender.getName());
                        players.sendMessage(CC.translate(replacedText + " " + builder));
                        Bukkit.getServer().getLogger().info(replacedText + " " + builder);

                    }
                }
            }else{
                sender.sendMessage(CC.translate("&cHey there " + sender.getName() + "!"));
                sender.sendMessage(CC.translate("&cYou cannot perform this command!"));
            }
         return false;
    }
}
