package me.shakeforprotein.stoneores2.Methods;

import me.shakeforprotein.stoneores2.StoneOres2;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class GetBlockLocation {


    private StoneOres2 plugin;

    public GetBlockLocation(StoneOres2 main){
        this.plugin = main;
    }

    public Location getBlockLocation(Material material, Block block) {
        Location location = null;

        for (BlockFace side : plugin.sides) {
            if (block.getRelative(side, 1).getType() == (material == Material.LAVA ? Material.WATER : Material.LAVA)) {
                if (block != null && block.getLocation() != null) {
                    location = block.getLocation();
                } else {
                    break;
                }
            }
        }
        return location;
    }
}
