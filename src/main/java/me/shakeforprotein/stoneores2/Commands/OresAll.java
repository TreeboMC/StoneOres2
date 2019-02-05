package me.shakeforprotein.stoneores2.Commands;

import me.shakeforprotein.stoneores2.Methods.GetAllGeneratorGroups;
import me.shakeforprotein.stoneores2.StoneOres2;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OresAll implements CommandExecutor {

    private StoneOres2 plugin;
    private GetAllGeneratorGroups getAllGeneratorGroups;

    public OresAll(StoneOres2 main) {
        this.plugin = main;
        this.getAllGeneratorGroups = new GetAllGeneratorGroups(main);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("oresAll")) {
            Player p = (Player) sender;
                    getAllGeneratorGroups.getAllGeneratorGroups(p);
                }
        return true;
    }
}
