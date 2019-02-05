package me.shakeforprotein.stoneores2.Methods;

import me.shakeforprotein.stoneores2.StoneOres2;
import org.bukkit.World;

public class GetBlockList {

    private StoneOres2 plugin;

    public GetBlockList(StoneOres2 main){
        this.plugin = main;
    }


    public String[] getBlockList(World world, String genGroup) {
        return plugin.getConfig().getConfigurationSection("world." + world.getName() + ".blocktypes." + genGroup).getKeys(false).toString().substring(1, plugin.getConfig().getConfigurationSection("world." + world.getName() + ".blocktypes." + genGroup).getKeys(false).toString().length() - 1).replaceAll("\\s+", "").split(",");
    }
}
