package me.shakeforprotein.stoneores2.Methods;

import me.shakeforprotein.stoneores2.StoneOres2;
import org.bukkit.entity.Player;

public class GetAllGeneratorGroups {

    private StoneOres2 plugin;
    private GetGeneratorGroup getGeneratorGroup;

    public GetAllGeneratorGroups(StoneOres2 main){
        this.plugin = main;
        this.getGeneratorGroup = new GetGeneratorGroup(main);
    }


    public void getAllGeneratorGroups(Player player) {
        String worldStr = player.getWorld().getName();
        String tierKeysConf = plugin.getConfig().getConfigurationSection("world." + worldStr + ".tiers").getKeys(false).toString();
        String[] tierKeys = tierKeysConf.substring(1, tierKeysConf.length() - 1).replaceAll("\\s+", "").split(",");
        String generatorGroup = "default";
        for (String item : tierKeys) {
            Integer tierPoints = plugin.getConfig().getInt("world." + worldStr + ".tiers." + item.trim());
            player.sendMessage(" ");
            player.sendMessage("The generator - " + item + " - requires an island level of " + tierPoints + " and generates ores at the following rates");
            generatorGroup = getGeneratorGroup.getGeneratorGroup(player.getWorld(), tierPoints + 1);
            Integer percent, percentCalc = 0;
            for (String item2 : plugin.getConfig().getConfigurationSection("world." + worldStr + ".blocktypes." + generatorGroup).getKeys(false)) {
                percent = plugin.getConfig().getInt("world." + worldStr + ".blocktypes." + generatorGroup + "." + item2);
                percentCalc += percent;
                double percentDouble = ((double) percent);
                player.sendMessage("§3" + item2 + ": §f" + Math.rint((percentDouble / percentCalc) * 100) + "%");
            }
        }
    }
}
