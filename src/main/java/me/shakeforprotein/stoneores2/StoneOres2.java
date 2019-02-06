package me.shakeforprotein.stoneores2;

import me.shakeforprotein.stoneores2.Commands.*;
import me.shakeforprotein.stoneores2.Listeners.BlockFromToEvent;
import me.shakeforprotein.stoneores2.Listeners.PlayerJoinEventUpdateCheck;
import me.shakeforprotein.stoneores2.Methods.LoadYamls;
import me.shakeforprotein.stoneores2.Methods.MkDir;
import org.bukkit.Bukkit;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import world.bentobox.bentobox.BentoBox;

import java.io.File;

public final class StoneOres2 extends JavaPlugin {

    private UpdateChecker uc;
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


    @Override
    public void onEnable() {
        System.out.println("StoneOres 2 - " + this.getDescription().getVersion() + " is starting");
        System.out.println("StoneOres 2 - Registering Listeners");
        this.uc = new UpdateChecker(this);
        this.mkDir = new MkDir(this);
        this.loadYamls = new LoadYamls(this);
        this.ores = new Ores(this);
        this.oresAll = new OresAll(this);
        this.oresReload = new OresReload(this);
        this.oresVersion = new OresVersion(this);
        this.value = new Value(this);
        getServer().getPluginManager().registerEvents(new BlockFromToEvent(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinEventUpdateCheck(this), this);
        this.getCommand("ores").setExecutor(ores);
        this.getCommand("oresAll").setExecutor(oresAll);
        this.getCommand("oresReload").setExecutor(oresReload);
        this.getCommand("oresVersion").setExecutor(oresVersion);
        this.getCommand("value").setExecutor(value);

        uc.getCheckDownloadURL();
        getConfig().options().copyDefaults(true);
        getConfig().set("version", this.getDescription().getVersion());
        if(getConfig().get("yamlCreatedIn") == null){
            getConfig().set("yamlCreatedIn", this.getDescription().getVersion());
        }
        saveConfig();
        langConfig = new File(getDataFolder(), "lang.yml");
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
