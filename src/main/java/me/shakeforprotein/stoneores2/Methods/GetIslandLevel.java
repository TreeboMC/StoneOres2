package me.shakeforprotein.stoneores2.Methods;

import org.bukkit.World;
import world.bentobox.bentobox.api.addons.request.AddonRequestBuilder;

import java.util.UUID;

public class GetIslandLevel {

    public long getIslandLevel(UUID uuid, World world){
        long islandLevel = (long) new AddonRequestBuilder().addon("Level").label("get-level").addMetaData("uuid", uuid).addMetaData("world", world).request();
        return islandLevel;
    }
}
