package me.tomster09090.staffutil.commands.rsp;

import me.tomster09090.staffutil.StaffUtil;
import me.tomster09090.staffutil.util.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class resourcePackCommand implements CommandExecutor {

    StaffUtil main;

    public resourcePackCommand(StaffUtil main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            try {
                player.setResourcePack(main.getConfig().getString("resource-pack-link"));
            } catch (NullPointerException ex) {
                System.out.println("An Error has occurred!");
                player.sendMessage("Failed to load resource pack. Please make sure you have set it correctly in the config.yml.");
            }
            player.sendMessage(CC.GREEN + "Resource pack sucessfully reloaded.");
        }
        return false;
    }
}
