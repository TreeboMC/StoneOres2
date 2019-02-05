package me.shakeforprotein.stoneores2.Commands;

import me.shakeforprotein.stoneores2.StoneOres2;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.bentobox.bentobox.BentoBox;

public class Value implements CommandExecutor {

private StoneOres2 plugin;
private BentoBox api = BentoBox.getInstance();

    public Value(StoneOres2 main){
        this.plugin = main;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("value")) {
            Integer itemValue = -1;
            Integer itemLimit = -1;
            String blockLimit = "";
            Player p = (Player) sender;
            String itemInHand = p.getInventory().getItemInMainHand().getType().toString();
            try {
                itemValue = api.getAddonsManager().getAddonByName("Level").get().getConfig().getInt("blocks." + itemInHand);
                itemLimit = api.getAddonsManager().getAddonByName("Level").get().getConfig().getInt("limits." + itemInHand);
                if (itemLimit > 0) {
                    blockLimit = " but is worth nothing after " + itemLimit + " blocks of " + itemInHand + " are on your island";
                }
            } catch (NullPointerException e) {
            }
            if (itemValue < 1) {
                itemValue = 0;
            }
            if (sender instanceof Player) {
                p.sendMessage(itemInHand + " has a value of " + itemValue + blockLimit);
            }
        }
        return true;
    }
}
