package me.shakeforprotein.stoneores2.Methods;

import me.shakeforprotein.stoneores2.StoneOres2;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.IOException;

public class LoadYamls {

    private StoneOres2 plugin;


    public LoadYamls(StoneOres2 main) {
        this.plugin = main;
    }


    public void loadYamls() {
        try {
            plugin.lang.load(plugin.langConfig);
        }
        catch (InvalidConfigurationException | IOException e) {
        }
    }
}
