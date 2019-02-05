package me.shakeforprotein.stoneores2.Listeners;

import me.shakeforprotein.stoneores2.Methods.*;
import me.shakeforprotein.stoneores2.StoneOres2;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import world.bentobox.bentobox.BentoBox;

import java.util.UUID;

public class BlockFromToEvent implements Listener {

    private StoneOres2 plugin;
    private GetIslandLevel getIslandLevel;
    private CanGenerateCobble canGenerateCobble;
    private GetBlockLocation getBlockLocation;
    private GetGeneratorGroup getGeneratorGroup;
    private GetBlockList getBlockList;
    private BentoBox api;


    public BlockFromToEvent(StoneOres2 main){
        this.plugin = main;
        this.canGenerateCobble = new CanGenerateCobble(main);
        this.getBlockLocation = new GetBlockLocation(main);
        this.getIslandLevel = new GetIslandLevel();
        this.getGeneratorGroup = new GetGeneratorGroup(main);
        this.getBlockList = new GetBlockList(main);
        api = BentoBox.getInstance();
    }


    @EventHandler
    public void onBlockFromToEvent(org.bukkit.event.block.BlockFromToEvent e) {
        String generatorGroup = "default";
        String blockToMake = null;
        World world = e.getBlock().getLocation().getWorld();
        String worldName = world.getName();


        if (e.getBlock().getType() == Material.LAVA || (e.getBlock().getType() == Material.WATER)) {
            if (e.getToBlock().getType() == Material.AIR) {
                if (canGenerateCobble.canGenerateCobble(e.getBlock().getType(), e.getToBlock())) {
                    if (getBlockLocation.getBlockLocation(e.getBlock().getType(), e.getToBlock()) != null) {
                        if (plugin.getConfig().getConfigurationSection("world." + worldName) != null) {
                            Location location = getBlockLocation.getBlockLocation(e.getBlock().getType(), e.getToBlock());
                            if(plugin.getConfig().getString("world." + worldName + ".checkIslandLevel").equalsIgnoreCase("true")){
                                UUID ownerUUID = api.getIslands().getIslandAt(e.getBlock().getLocation()).get().getOwner();
                                generatorGroup = getGeneratorGroup.getGeneratorGroup(e.getBlock().getWorld(), getIslandLevel.getIslandLevel(ownerUUID,  e.getBlock().getWorld()));
                            }
                            else{
                                generatorGroup = getGeneratorGroup.getGeneratorGroup(e.getBlock().getWorld(), 1);
                            }
                            String[] blocksList = getBlockList.getBlockList(e.getBlock().getWorld(), generatorGroup);

                            int totalChance = 0, arrayId = 0, i = 0;
                            String[] cases = new String[50000];

                            for (String item : blocksList) {
                                totalChance += plugin.getConfig().getInt("world." + worldName + ".blocktypes." + generatorGroup + "." + item);
                                blocksList[arrayId] = item;
                                arrayId++;

                                while (i < totalChance) {
                                    cases[i] = item;
                                    i++;
                                }
                            }

                            int random = 1 + (int) (Math.random() * totalChance);

                            blockToMake = cases[random];


                            try{e.getBlock().getWorld().getBlockAt(location).setType(Material.getMaterial(blockToMake));}
                            catch(NullPointerException err){}
                            e.setCancelled(true);

                        }
                    }
                }
            }
        }
    }
}
