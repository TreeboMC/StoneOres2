package me.shakeforprotein.stoneores2.Commands;

import me.shakeforprotein.stoneores2.Methods.GetAllGeneratorGroups;
import me.shakeforprotein.stoneores2.Methods.GetBlockList;
import me.shakeforprotein.stoneores2.Methods.GetGeneratorGroup;
import me.shakeforprotein.stoneores2.Methods.GetIslandLevel;
import me.shakeforprotein.stoneores2.StoneOres2;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.bentobox.bentobox.BentoBox;

import java.util.Arrays;
import java.util.Set;
import java.util.UUID;

public class Ores implements CommandExecutor {

    private StoneOres2 plugin;
    private GetAllGeneratorGroups getAllGeneratorGroups;
    private GetGeneratorGroup getGeneratorGroup;
    private GetBlockList getBlockList;
    private GetIslandLevel getIslandLevel;
    private BentoBox api = BentoBox.getInstance();

    public Ores(StoneOres2 main) {
        this.plugin = main;
        this.getIslandLevel = new GetIslandLevel(main);
        this.getAllGeneratorGroups = new GetAllGeneratorGroups(main);
        this.getBlockList = new GetBlockList(main);
        this.getGeneratorGroup = new GetGeneratorGroup(main);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("ores")) {
           Player p = (Player) sender;
           World w = p.getWorld();
           Location blockAtFeet = p.getLocation();
           String islandOwner;

           try{
               islandOwner = api.getIslands().getIslandAt(blockAtFeet).get().getOwner().toString();
               long islandLevel = getIslandLevel.getIslandLevel(UUID.fromString(islandOwner), w);
               String generator = getGeneratorGroup.getGeneratorGroup(w, islandLevel);
               Double blockWeight = 0.0;
               Double totalWeight = 0.0;
               Set<String> blockKeysConf = plugin.getConfig().getConfigurationSection("world." + w.getName() + ".blocktypes." + generator).getKeys(false);
               String[] blockKeysArray = Arrays.copyOf(blockKeysConf.toArray(), blockKeysConf.size(), String[].class);

               for (String block : blockKeysArray) {
                   totalWeight = totalWeight + plugin.getConfig().getInt("world." + w.getName() + ".blocktypes." + generator + "." + block);
               }
                p.sendMessage("This island is level " + islandLevel + " which entitles it to use generator group " + generator + "with the following approximate rates:");
               for (String block : blockKeysArray) {
                   blockWeight = new Double(plugin.getConfig().getInt("world." + w.getName() + ".blocktypes." + generator + "." + block));
                   p.sendMessage(ChatColor.AQUA + block + " - " + Math.floor((blockWeight / totalWeight) * 100) + "%");
               }
           }
           catch(Exception err){
               p.sendMessage("An error occured determining island owner. You may not be standing in an island region.");
           }
        }
        return true;
    }
}