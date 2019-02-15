package me.shakeforprotein.stoneores2.Methods;

import me.shakeforprotein.stoneores2.StoneOres2;
import org.bukkit.World;

import java.util.Arrays;
import java.util.Set;


public class GetBlockList {

    private StoneOres2 plugin;

    public GetBlockList(StoneOres2 main){
        this.plugin = main;
    }


    public String[] getBlockList(World world, String genGroup) {

        Set<String> keys = plugin.getConfig().getConfigurationSection("world." + world.getName() + ".blocktypes." + genGroup).getKeys(false);
        String[] keysArray = Arrays.copyOf(keys.toArray(), keys.size(), String[].class);
        return keysArray;
        }
}
