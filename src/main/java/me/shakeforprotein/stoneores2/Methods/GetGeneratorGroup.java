package me.shakeforprotein.stoneores2.Methods;

import me.shakeforprotein.stoneores2.StoneOres2;
import org.bukkit.World;

public class GetGeneratorGroup {

    private StoneOres2 plugin;

    public GetGeneratorGroup(StoneOres2 main){
        this.plugin = main;
    }

    public String getGeneratorGroup(World world, long isLvl) {

        String worldStr = world.getName();
        String tierKeysConf = plugin.getConfig().getConfigurationSection("world." + worldStr + ".tiers").getKeys(false).toString();
        String[] tierKeys = tierKeysConf.substring(1, tierKeysConf.length() - 1).replaceAll("\\s+", "").split(",");
        String islandTier = "default";
        for (String item : tierKeys) {
            Integer tierPoints = plugin.getConfig().getInt("world." + worldStr + ".tiers." + item.trim());
            if (tierPoints < isLvl) {
                islandTier = item.trim();
            }
        }
        return islandTier;
    }
}
