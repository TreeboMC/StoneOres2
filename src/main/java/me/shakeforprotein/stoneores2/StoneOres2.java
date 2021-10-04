package me.shakeforprotein.stoneores2;

import me.shakeforprotein.stoneores2.Commands.*;
import me.shakeforprotein.stoneores2.Listeners.BlockFromToEvent;
import me.shakeforprotein.stoneores2.Methods.LoadYamls;
import me.shakeforprotein.stoneores2.Methods.MkDir;
import me.shakeforprotein.treeboroots.TreeboRoots;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public final class StoneOres2 extends JavaPlugin {


    private TreeboRoots roots;
    private MkDir mkDir;
    private LoadYamls loadYamls;
    private Ores ores;
    private OresAll oresAll;
    private OresReload oresReload;
    private OresVersion oresVersion;
    private Value value;
    public File langConfig = null;
    public final YamlConfiguration lang = new YamlConfiguration();
    public final BlockFace[] sides = new BlockFace[]{BlockFace.SELF, BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST};
    public String Language = getConfig().getString("language");
    public String mp = Language + ".messages.";
    public static String badge;


    @Override
    public void onEnable() {
        if (Bukkit.getPluginManager().getPlugin("TreeboRoots") != null && Bukkit.getPluginManager().getPlugin("TreeboRoots").isEnabled()) {
            roots = (TreeboRoots) Bukkit.getPluginManager().getPlugin("TreeboRoots");
            roots.updateHandler.registerPlugin(this, "TreeboMC", "StoneOres2", Material.DIAMOND_ORE);
        } else {
            Bukkit.getScheduler().runTaskLater(this, () -> {
                if (Bukkit.getPluginManager().getPlugin("TreeboRoots") != null && Bukkit.getPluginManager().getPlugin("TreeboRoots").isEnabled()) {
                    roots = (TreeboRoots) Bukkit.getPluginManager().getPlugin("TreeboRoots");
                    roots.updateHandler.registerPlugin(this, "TreeboMC", "StoneOres2", Material.DIAMOND_ORE);
                }
            }, 100L);
        }

        Logger logger = this.getLogger();
        badge = this.getConfig().getString("badge") == null ? ChatColor.translateAlternateColorCodes('&', "&3&l[&2SStoneOres2&3&l]&r") : ChatColor.translateAlternateColorCodes('&', getConfig().getString("badge"));

        logger.info(" Version " + this.getDescription().getVersion() + " is starting");
        logger.info(" Registering Listeners");
        this.mkDir = new MkDir(this);
        this.loadYamls = new LoadYamls(this);
        this.ores = new Ores(this);
        this.oresAll = new OresAll(this);
        this.oresReload = new OresReload(this);
        this.oresVersion = new OresVersion(this);
        this.value = new Value(this);
        getServer().getPluginManager().registerEvents(new BlockFromToEvent(this), this);
        this.getCommand("ores").setExecutor(ores);
        this.getCommand("oresAll").setExecutor(oresAll);
        this.getCommand("oresReload").setExecutor(oresReload);
        this.getCommand("oresVersion").setExecutor(oresVersion);
        this.getCommand("value").setExecutor(value);

        getConfig().options().copyDefaults(true);
        getConfig().set("version", this.getDescription().getVersion());
        if (getConfig().get("yamlCreatedIn") == null) {
            getConfig().set("yamlCreatedIn", this.getDescription().getVersion());
        }
        //Setup Metrics
        if (getConfig().get("bstatsIntegration") != null) {
            if (getConfig().getBoolean("externalFeatures.bstatsIntegration")) {
                logger.info(this.getName() + " has enabled bStats metric collection");
                Metrics metrics = new Metrics(this);
            } else {
                logger.info("Bstats integration disabled in config.");
            }
        } else {
            logger.info("Bstats integration disabled due to missing value in config.");
        }
        saveConfig();
        langConfig = new File(getDataFolder(), "lang.yml");
        lang.options().copyDefaults(true);
        mkDir.mkdir(langConfig);
        loadYamls.loadYamls();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    public YamlConfiguration getLang() {
        return lang;
    }
}
