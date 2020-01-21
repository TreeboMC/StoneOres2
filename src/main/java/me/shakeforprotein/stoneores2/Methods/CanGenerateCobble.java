package me.shakeforprotein.stoneores2.Methods;

import me.shakeforprotein.stoneores2.StoneOres2;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.meta.BlockStateMeta;

public class CanGenerateCobble {

    private StoneOres2 plugin;

    public CanGenerateCobble(StoneOres2 main){
        this.plugin = main;
    }

    public boolean canGenerateCobble(Block block, Block toBlock, BlockFace face) {
        Material mat = toBlock.getType();

        for (BlockFace side : plugin.sides) {
            if(side.equals(BlockFace.DOWN) || side.equals(BlockFace.UP)){
                if(block.getType().equals(Material.LAVA)){
                    if(toBlock.getType().equals(Material.WATER)){
                        return true;
                    }
                }
                else if(block.getType().equals(Material.WATER)){
                    if(toBlock.getType().equals(Material.LAVA)){
                        return true;
                    }
                }
            }
            else {
                if(block.getType().equals(Material.LAVA)){
                    if(toBlock.getRelative(side).getType().equals(Material.WATER)){
                        if(toBlock.getType().equals(Material.AIR)) {
                            return true;
                        }
                    }
                }
                else
                if(block.getType().equals(Material.WATER)){
                    if(toBlock.getRelative(side).getType().equals(Material.LAVA)){
                        if(toBlock.getType().equals(Material.AIR)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
