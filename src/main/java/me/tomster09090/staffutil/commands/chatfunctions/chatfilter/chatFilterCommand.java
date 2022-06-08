package me.tomster09090.staffutil.commands.chatfunctions.chatfilter;

import me.tomster09090.staffutil.StaffUtil;
import me.tomster09090.staffutil.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.List;

public class chatFilterCommand implements CommandExecutor {

    StaffUtil main;

    public chatFilterCommand(StaffUtil main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            Bukkit.getLogger().info("Only players can execute this command.");
            return false;
        }
        Player p = (Player) sender;

        if (!p.hasPermission("staff.cf.command"))
            return false;
        if (args.length == 0)
            helpMessage(p);
        try {
            switch(args[0]){
                case "help":
                    helpMessage(p);
                    break;
                case "info":
                    infoMessage(p);
                    break;
                case "list":
                    p.sendMessage(CC.RED + "Blocked keywords: " + CC.GRAY + "- ");
                    main.reloadConfig();
                    for (String list : main.getCustomConfig().getStringList("blocked-keywords")){
                        p.sendMessage(CC.GRAY + list.toLowerCase());
                    }
                    break;
                case "add":
                    try {
                        if (args.length > 1) {
                            if (!main.getCustomConfig().getStringList("blocked-keywords").contains(args[1].toLowerCase())) {
                                List<String> blockedWords = main.getCustomConfig().getStringList("blocked-keywords");
                                blockedWords.add(args[1].toLowerCase());
                                main.getCustomConfig().set("blocked-keywords", blockedWords);
                                main.getCustomConfig().save(main.customConfigFile);
                                p.sendMessage(CC.RED + "Added the word " + CC.GRAY + "- " + CC.WHITE + args[1].toLowerCase());
                            } else {
                                p.sendMessage(CC.RED + "The word " + args[1].toLowerCase() + " is already added!");
                            }
                        }else{
                            p.sendMessage(CC.translate("&cPlease specify a word to add."));
                        }
                    }catch (Exception e){
                        p.sendMessage(CC.translate("&cFailed to add word " + CC.GRAY + args[1] + "&c to the blocked keywords list."));
                    }
                    break;
                case "remove":
                    if (args.length == 1) {p.sendMessage(CC.translate("&cPlease specify a word to remove."));}
                    if (main.getCustomConfig().getStringList("blocked-keywords").contains(args[1].toLowerCase())){
                        List<String> blockedWords = main.getCustomConfig().getStringList("blocked-keywords");
                        blockedWords.remove(args[1].toLowerCase());
                        main.getCustomConfig().set("blocked-keywords", blockedWords);
                        p.sendMessage(CC.RED + "Removed the word " + CC.GRAY + "- " + CC.WHITE + args[1].toLowerCase());
                        try {main.getCustomConfig().save(main.customConfigFile);}
                        catch (IOException e) {e.printStackTrace();}
                    }else{
                        p.sendMessage(CC.RED + "The word " + args[1].toLowerCase() + " is already removed!");
                    }
                    break;
                case "reload":
                    try {
                        main.reloadConfig();
                        p.sendMessage(CC.GREEN + "Reloaded custom config.");
                    }catch (Exception e){
                        p.sendMessage(CC.RED + "Failed to reload custom config! Check console for more details");
                        e.printStackTrace();
                    }
                    break;
                default:
                    helpMessage(p);
                    break;
            }
        }catch (Exception e){}
        return false;
    }

    private void helpMessage(Player player){
        player.sendMessage(CC.RED + "/cf help " + CC.GRAY + "- " + CC.WHITE + "Displays this");
        player.sendMessage(CC.RED + "/cf info " + CC.GRAY + "- " + CC.WHITE + "Displays information about the plugin");
        player.sendMessage(CC.RED + "/cf list " + CC.GRAY + "- " + CC.WHITE + "Lists all blocked words");
        player.sendMessage(CC.RED + "/cf add <word> " + CC.GRAY + "- " + CC.WHITE + "adds a blocked word of your choice");
        player.sendMessage(CC.RED + "/cf remove <word> " + CC.GRAY + "- " + CC.WHITE + "removes a blocked word of your choice");
        player.sendMessage(CC.RED + "/cf reload " + CC.GRAY + "- " + CC.WHITE + "reloads the config");
    }
    
    private void infoMessage(Player p){
        p.sendMessage(CC.RED + "Plugin Name " + CC.GRAY + "- " + CC.WHITE + main.getDescription().getName().toString());
        p.sendMessage(CC.RED + "Plugin Authors " + CC.GRAY + "- " + CC.WHITE + main.getDescription().getAuthors().toString());
        p.sendMessage(CC.RED + "Plugin Description " + CC.GRAY + "- " + CC.WHITE + main.getDescription().getDescription().toString());
        p.sendMessage(CC.RED + "Plugin Version " + CC.GRAY + "- " + CC.WHITE + main.getDescription().getVersion().toString());
    }
}
