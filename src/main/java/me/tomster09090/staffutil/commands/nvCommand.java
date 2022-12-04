package me.tomster09090.staffutil.commands;

import me.tomster09090.staffutil.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class nvCommand implements CommandExecutor {
    List<Player> playersWithNV = new ArrayList<>();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (commandSender.hasPermission("staff.nv")) {
            Player player = (Player) commandSender;
            if (args.length == 0) {
                if (!playersWithNV.contains(player)) {
                    playersWithNV.add(player);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 255, false, false));
                    player.sendMessage(CC.GREEN + "You now have night vision!");
                } else {
                    playersWithNV.remove(player);
                    player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                    player.sendMessage(CC.RED + "You no longer have night vision!");
                }
            }else{
                String targetName = args[0];
                Player target = Bukkit.getServer().getPlayerExact(targetName);
                if (target == null){
                    player.sendMessage(CC.RED + "Player " + targetName + " isn't online.");
                }else if (!playersWithNV.contains(target)){
                    playersWithNV.add(target);
                    player.sendMessage(CC.GREEN + "Forcefully given nightvision to " + targetName + "!");

                    target.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 255, false, false));
                    target.sendMessage(CC.GREEN + "You have forcefully been given night vision!");
                }else{
                    playersWithNV.remove(target);
                    player.sendMessage(CC.RED + "Forcefully removed nightvision from " + targetName + "!");

                    target.removePotionEffect(PotionEffectType.NIGHT_VISION);
                    target.sendMessage(CC.RED + "Your nightvision has been forcefully taken away!");
                }
            }
        }
        return false;
    }
}
