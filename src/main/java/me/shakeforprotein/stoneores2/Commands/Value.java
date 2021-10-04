package me.shakeforprotein.stoneores2.Commands;

import me.shakeforprotein.stoneores2.StoneOres2;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import world.bentobox.bentobox.BentoBox;

import java.io.File;

public class Value implements CommandExecutor {

private StoneOres2 plugin;
private BentoBox api = BentoBox.getInstance();

    private File blockValueFile;
    private FileConfiguration blockYml;

    public Value(StoneOres2 main){
        this.plugin = main;
        if(new File(api.getDataFolder() + File.separator + "addons" + File.separator + "Level", "blockconfig.yml").exists()){
            blockValueFile = new File(api.getDataFolder() + File.separator + "addons" + File.separator + "Level", "blockconfig.yml");
            blockYml = YamlConfiguration.loadConfiguration(blockValueFile);
        }
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(blockValueFile != null) {
            if (cmd.getName().equalsIgnoreCase("value")) {
                Integer itemValue = -1;
                Integer itemLimit = -1;
                String blockLimit = "";
                Player p = (Player) sender;
                String itemInHand = p.getInventory().getItemInMainHand().getType().toString();
                try {
                    itemValue = blockYml.getInt("blocks." + itemInHand);
                    itemLimit = blockYml.getInt("limits." + itemInHand);
                    if (itemLimit > 0) {
                        blockLimit = (plugin.getLang().getString(plugin.mp + "worthNothing")).replace("%ITEMLIMIT%", itemLimit + "").replace("%ITEMINHAND%", itemInHand);
                    }
                } catch (NullPointerException e) {
                }
                if (itemValue < 1) {
                    itemValue = 0;
                }
                if (sender instanceof Player) {
                    p.sendMessage(plugin.badge + " " + itemInHand + plugin.getLang().getString(plugin.mp + "valueOf") + itemValue + blockLimit);
                }
            }
        }
        return true;
    }
}
