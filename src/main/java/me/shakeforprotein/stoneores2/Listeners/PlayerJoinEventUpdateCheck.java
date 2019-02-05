package me.shakeforprotein.stoneores2.Listeners;

import me.shakeforprotein.stoneores2.StoneOres2;
import me.shakeforprotein.stoneores2.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinEventUpdateCheck implements Listener {

private StoneOres2 plugin;
private UpdateChecker uc;

    public PlayerJoinEventUpdateCheck(StoneOres2 main){
        this.plugin = main;
        this.uc = new UpdateChecker(main);
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e) {
        if (e.getPlayer().hasPermission(uc.requiredPermission)) {
            if ((plugin.getConfig().getString(e.getPlayer().getName()) == null) || ((plugin.getConfig().getString(e.getPlayer().getName()) != null) && (plugin.getConfig().getString(e.getPlayer().getName()).equalsIgnoreCase("false")))) {
                uc.getCheckDownloadURL(e.getPlayer());
                plugin.getConfig().set(e.getPlayer().getName(), "true");
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    public void run() {
                        plugin.getConfig().set(e.getPlayer().getName(), "false");
                    }
                }, 60L);
            } else {
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    public void run() {
                        try {
                            plugin.getConfig().set(e.getPlayer().getName(), null);
                        } catch (NullPointerException e) {
                        }
                    }
                }, 80L);
            }
        }
    }
}
