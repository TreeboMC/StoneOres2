package me.shakeforprotein.stoneores2.Methods;

import me.shakeforprotein.stoneores2.StoneOres2;

import java.io.File;

public class MkDir {

    private StoneOres2 plugin;

    public MkDir(StoneOres2 main){
        this.plugin = main;
    }


    public void mkdir(File L) {
        if (!L.exists()) {
            plugin.saveResource("lang.yml", false);
        }
    }
}
