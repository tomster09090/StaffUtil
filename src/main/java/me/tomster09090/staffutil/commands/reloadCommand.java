package me.tomster09090.staffutil.commands;

import me.tomster09090.staffutil.StaffUtil;
import me.tomster09090.staffutil.util.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class reloadCommand implements CommandExecutor {

    StaffUtil main;

    public reloadCommand(StaffUtil main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender.hasPermission("staff.reload")){
            try {
                main.reloadConfig();
                sender.sendMessage(CC.translate("&aConfig successfully reloaded."));
            }catch (Exception ex){
                sender.sendMessage(CC.translate("&cFailed to reload config. Please refer to console for more infomation to pass onto the development team."));
                ex.printStackTrace();
            }
        }else{
            sender.sendMessage("You lack the needed permission node to do this.");
        }
        return false;
    }
}
