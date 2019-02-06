package me.shakeforprotein.stoneores2.Commands;

import me.shakeforprotein.stoneores2.Methods.GetAllGeneratorGroups;
import me.shakeforprotein.stoneores2.Methods.GetBlockList;
import me.shakeforprotein.stoneores2.Methods.GetGeneratorGroup;
import me.shakeforprotein.stoneores2.Methods.GetIslandLevel;
import me.shakeforprotein.stoneores2.StoneOres2;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.bentobox.bentobox.BentoBox;

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
            World world = p.getWorld();
            String worldName = world.getName();
            if (args.length == 0) {
                String generatorGroup;

                if (plugin.getConfig().getString("world." + p.getWorld().getName() + ".checkIslandLevel").equalsIgnoreCase("true")) {
                    UUID thisIslandOwner = api.getIslands().getIslandAt(p.getLocation()).get().getOwner();
                    p.sendMessage(getIslandLevel.getIslandLevel(thisIslandOwner, p.getWorld()) + "");
                    generatorGroup = getGeneratorGroup.getGeneratorGroup(p.getWorld(), getIslandLevel.getIslandLevel(thisIslandOwner, p.getWorld()));
                } else {
                    generatorGroup = getGeneratorGroup.getGeneratorGroup(p.getWorld(), 1);
                }

                String[] blocksList = getBlockList.getBlockList(p.getWorld(), generatorGroup);


                if (plugin.getConfig().getConfigurationSection("world." + worldName) != null) {

                    int percentCalc = 0, arrayId = 0, i = 0;
                    String[] blocktypes = new String[blocksList.length];
                    String[] cases = new String[50000];


                    for (String item : blocksList) {
                        percentCalc += plugin.getConfig().getInt("world." + worldName + ".blocktypes." + generatorGroup + "." + item);
                        blocktypes[arrayId] = item;
                        arrayId++;

                        while (i < percentCalc) {
                            cases[i] = item;
                            i++;
                        }
                    }


                    String hasPermission = null;
                    int percent = 0;


                    if (generatorGroup != null) {
                        p.sendMessage(plugin.getLang().getString(plugin.mp + "hasTier").replace("{permission}", generatorGroup).replace('&', '§'));
                        p.sendMessage(plugin.getLang().getString(plugin.mp + "rates").replace('&', '§'));

                        for (String item : getBlockList.getBlockList(p.getWorld(), generatorGroup)) {
                            percent = plugin.getConfig().getInt("world." + worldName + ".blocktypes." + generatorGroup + "." + item);
                            double percentDouble = ((double) percent);
                            p.sendMessage("§3" + item + ": §f" + Math.rint((percentDouble / percentCalc) * 100) + "%");
                        }
                    } else {
                        p.sendMessage(ChatColor.RED + "Error determining generator group");

                    }
                }
            }
        }
        return true;
    }
}