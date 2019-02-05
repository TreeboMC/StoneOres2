package me.shakeforprotein.stoneores2;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class UpdateChecker {


    //These two need to be changed for different plugins
    private StoneOres2 pl;

    public UpdateChecker(StoneOres2 pl) {
        this.pl = pl;
    }

    public String requiredPermission = "stoneores2.updatechecker";

    public Boolean getCheckDownloadURL(Player p) {
        // Code courtesy of Spigot user Ftbastler
        // Adjustments by ShakeforProtein
        if (pl.getConfig().getString("checkUpdates").equalsIgnoreCase("true")) {
            try {
                URL url = new URL(pl.getConfig().getString("apiLink"));
                URLConnection conn = url.openConnection();
                conn.setRequestProperty("User-Agent", "Mozilla/1.0");
                conn.setConnectTimeout(5000);
                conn.connect();
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine = in.readLine();
                String gitVersion = "";
                Boolean getLinkNow = false;

                while (inputLine != null) {

                    JSONParser parser = new JSONParser();
                    JSONObject json = (JSONObject) parser.parse(inputLine);
                    gitVersion = json.get("tag_name").toString();
                    if (!gitVersion.equalsIgnoreCase(pl.getDescription().getVersion())) {
                        p.sendMessage("Latest " + pl.getDescription().getName() + " Version is " + gitVersion);
                        p.sendMessage("Your " + pl.getDescription().getName() + " Version is " + pl.getDescription().getVersion());
                        p.sendMessage(ChatColor.GOLD + "Please update " + pl.getDescription().getName() + " with version at " + pl.getConfig().getString("releasePage"));
                    }

                    break;
                }

                if (!gitVersion.equalsIgnoreCase(pl.getDescription().getVersion())) {
                    Bukkit.getConsoleSender().sendMessage("Latest " + pl.getDescription().getName() + " Version is " + gitVersion);
                    Bukkit.getConsoleSender().sendMessage("Your " + pl.getDescription().getName() + " Version is " + pl.getDescription().getVersion());
                    Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "Please consider updating " + pl.getDescription().getName() + " with version at " + pl.getConfig().getString("releasePage"));
                }
                in.close();

                return true;
            } catch (Exception e) {
                if (p != null) {
                    p.sendMessage(pl.getDescription().getName() + " Failed to check for updates");
                }
                pl.getServer().getLogger().warning("Something went wrong while downloading an update.");
                pl.getServer().getLogger().info("Please check the plugin's release page to see if there are any updates available.");
                pl.getServer().getLogger().info("" + pl.getConfig().getString("releasePage"));
                pl.getServer().getLogger().fine(e.getMessage());
                return false;
            }
        }
        return false;
    }

    public Boolean getCheckDownloadURL() {
        // Code courtesy of Spigot user Ftbastler
        // Adjustments by ShakeforProtein
        if (pl.getConfig().getString("checkUpdates").equalsIgnoreCase("true")) {
            try {
                URL url = new URL(pl.getConfig().getString("apiLink"));
                URLConnection conn = url.openConnection();
                conn.setRequestProperty("User-Agent", "Mozilla/1.0");
                conn.setConnectTimeout(5000);
                conn.connect();
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine = in.readLine();
                String gitVersion = "";
                Boolean getLinkNow = false;

                while (inputLine != null) {

                    JSONParser parser = new JSONParser();
                    JSONObject json = (JSONObject) parser.parse(inputLine);
                    gitVersion = json.get("tag_name").toString();


                    if (!gitVersion.equalsIgnoreCase(pl.getDescription().getVersion())) {
                        Bukkit.getConsoleSender().sendMessage("Latest " + pl.getDescription().getName() + " Version is " + gitVersion);
                        Bukkit.getConsoleSender().sendMessage("Your " + pl.getDescription().getName() + " Version is " + pl.getDescription().getVersion());
                        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "Please consider updating " + pl.getDescription().getName() + " with version at " + pl.getConfig().getString("releasePage"));
                    }
                    break;
                }
                in.close();

                return true;
            } catch (Exception e) {
                pl.getServer().getLogger().warning("Something went wrong while downloading an update.");
                pl.getServer().getLogger().info("Please check the plugin's release page to see if there are any updates available.");
                pl.getServer().getLogger().info("" + pl.getConfig().getString("releasePage"));
                pl.getServer().getLogger().fine(e.getMessage());
                return false;
            }
        }
        return false;
    }

}
