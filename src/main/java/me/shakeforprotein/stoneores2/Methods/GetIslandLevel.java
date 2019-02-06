package me.shakeforprotein.stoneores2.Methods;

import me.shakeforprotein.stoneores2.StoneOres2;
import org.bukkit.World;
import world.bentobox.bentobox.api.addons.request.AddonRequestBuilder;

import java.util.UUID;

public class GetIslandLevel {

    private StoneOres2 plugin;

    public GetIslandLevel(StoneOres2 main){
        this.plugin = main;
    }
    public long getIslandLevel(UUID uuid, World world){
        long islandLevel = (long) new AddonRequestBuilder()
                .addon("Level")
                .label("island-level")
                .addMetaData("player", uuid)
                .addMetaData("world-name", world.getName())
                .request();

        if (islandLevel < 1){islandLevel = 1;}
        return islandLevel;
    }
}
