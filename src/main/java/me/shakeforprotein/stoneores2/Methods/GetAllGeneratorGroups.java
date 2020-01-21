package me.shakeforprotein.stoneores2.Methods;

import me.shakeforprotein.stoneores2.StoneOres2;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Set;

public class GetAllGeneratorGroups {

    private StoneOres2 plugin;
    private GetGeneratorGroup getGeneratorGroup;

    public GetAllGeneratorGroups(StoneOres2 main){
        this.plugin = main;
        this.getGeneratorGroup = new GetGeneratorGroup(main);
    }


    public void getAllGeneratorGroups(Player player) {
        String worldStr = player.getWorld().getName();
        ConsoleCommandSender console = Bukkit.getConsoleSender();
        Set<String> tierKeysConf = plugin.getConfig().getConfigurationSection("world." + worldStr + ".tiers").getKeys(false);
        String[] tierKeys = Arrays.copyOf(tierKeysConf.toArray(), tierKeysConf.size(), String[].class);

        String generatorGroup = "default";
        for (String item : tierKeys) {
            Integer tierPoints = plugin.getConfig().getInt("world." + worldStr + ".tiers." + item);
            generatorGroup = getGeneratorGroup.getGeneratorGroup(player.getWorld(), tierPoints + 1);
            Integer blockWeight = 0;
            Double totalWeight = 0.0;

            Set<String> blockKeysConf = plugin.getConfig().getConfigurationSection("world." + worldStr + ".blocktypes." + generatorGroup).getKeys(false);

            for (String block : blockKeysConf) {
                totalWeight = totalWeight + plugin.getConfig().getInt("world." + worldStr + ".blocktypes." + generatorGroup + "." + block);
            }

            String command = "tellraw " + player.getName() + " {\"text\":\"Generator " + generatorGroup + " - " + tierPoints + " levels (Hover me)\",\"color\":\"aqua\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"";
            for (String block : blockKeysConf) {
                blockWeight = plugin.getConfig().getInt("world." + worldStr + ".blocktypes." + generatorGroup + "." + block);
               command += "" + block + " - " + Math.floor((blockWeight / totalWeight) * 100) + "%\\n";
            }
            command += "\"}}";
            Bukkit.dispatchCommand(console, command);
            
        }
    }
}
