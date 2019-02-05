package me.shakeforprotein.stoneores2.Commands;

import me.shakeforprotein.stoneores2.StoneOres2;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OresVersion implements CommandExecutor {


    private StoneOres2 plugin;

    public OresVersion(StoneOres2 main) {
        this.plugin = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("oresVersion")) {
            Player p = (Player) sender;
            p.sendMessage("StoneOres - " + plugin.getDescription().getVersion());
        }
        return true;
    }
}
