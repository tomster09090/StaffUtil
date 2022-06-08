package me.tomster09090.staffutil.commands.chatfunctions;
;
import me.tomster09090.staffutil.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class staffChatCommand implements CommandExecutor {

    // /sc <message>

    Plugin plugin;

    public staffChatCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (sender.hasPermission("staff.sc")){
                for (Player players : Bukkit.getOnlinePlayers()){
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < args.length; i++) {
                        builder.append(args[i]).append(" ");
                    }
                    if (players.hasPermission("staff.sc")){
                        String replacedText = CC.replaceall(plugin.getConfig().getString("staff-chat-prefix-format"), "%name%", sender.getName());
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
