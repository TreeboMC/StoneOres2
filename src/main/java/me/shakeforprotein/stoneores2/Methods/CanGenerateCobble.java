package me.shakeforprotein.stoneores2.Methods;

import me.shakeforprotein.stoneores2.StoneOres2;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class CanGenerateCobble {

    private StoneOres2 plugin;

    public CanGenerateCobble(StoneOres2 main){
        this.plugin = main;
    }

    public boolean canGenerateCobble(Material mat, Block block) {
        for (BlockFace side : plugin.sides) {
            if (block.getRelative(side, 1).getType() == (mat == Material.WATER ? Material.LAVA : Material.WATER) || block.getRelative(side, 1).getType() == (mat == Material.WATER ? Material.LAVA : Material.WATER)) {
                return true;
            }
        }
        return true;
    }
}
