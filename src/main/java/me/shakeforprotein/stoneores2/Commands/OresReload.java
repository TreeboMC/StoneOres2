package me.shakeforprotein.stoneores2.Commands;

import me.shakeforprotein.stoneores2.StoneOres2;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;

import java.io.IOException;

public class OresReload implements CommandExecutor {

    private StoneOres2 plugin;

    public OresReload(StoneOres2 main) {
        this.plugin = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("oresReload")) {
            Player p = (Player) sender;
            if (p.hasPermission("stoneores.reload")) {
                try {
                    plugin.lang.load(plugin.langConfig);
                } catch (InvalidConfigurationException | IOException e) {
                }
                plugin.reloadConfig();
                p.sendMessage(plugin.getLang().getString(plugin.mp + "configReloaded").replace('&', '§'));
            } else {
                p.sendMessage(plugin.getLang().getString(plugin.mp + "noPermission").replace('&', '§'));
            }
        }
        return true;
    }
}
