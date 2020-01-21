package me.shakeforprotein.stoneores2;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;


public class UpdateChecker {

    private StoneOres2 pl;

    public String requiredPermission = "stoneores2.updatechecker";
    public HashMap runningVersion = new HashMap<Integer, Integer>();
    public HashMap currentVersion = new HashMap<Integer, Integer>();
    public HashMap updateNotified = new HashMap<Player, Boolean>();

    public UpdateChecker(StoneOres2 main) {
        this.pl = main;
    }

    static public boolean isOutOfDate;
    static public String gitVersion = "";
    public String newVersion = "";

    public Boolean getCheckDownloadURL() {
        // Code courtesy of Spigot user Ftbastler
        // Adjustments by ShakeforProtein
        if (pl.getConfig().getBoolean("externalFeatures.updates.enabled")) {
            try {
                System.out.println(pl.badge + "Checking for updates");
                URL url = new URL("https://api.github.com/repos/" +  pl.getConfig().getString("externalFeatures.updates.githubKey") + "/releases/latest");

                URLConnection conn = url.openConnection();
                conn.setRequestProperty("User-Agent", "Mozilla/1.0");
                conn.setConnectTimeout(5000);
                conn.connect();
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine = in.readLine();

                Boolean getLinkNow = false;
                while (inputLine != null) {
                    JSONParser parser = new JSONParser();
                    JSONObject json = (JSONObject) parser.parse(inputLine);
                    gitVersion = json.get("tag_name").toString();
                    String fullGitVersion = gitVersion;
                    gitVersion = gitVersion.split(" ")[0].split("-")[0];
                    currentVersion.put(0, Integer.parseInt(gitVersion.split("\\.")[0]));
                    currentVersion.put(1, Integer.parseInt(gitVersion.split("\\.")[1]));
                    currentVersion.put(2, Integer.parseInt(gitVersion.split("\\.")[2]));
                    String thisVersion = pl.getDescription().getVersion();
                    thisVersion = thisVersion.split(" ")[0].split("-")[0];
                    runningVersion.put(0, Integer.parseInt(thisVersion.split("\\.")[0]));
                    runningVersion.put(1, Integer.parseInt(thisVersion.split("\\.")[1]));
                    runningVersion.put(2, Integer.parseInt(thisVersion.split("\\.")[2]));
                    int cv0 = (int) currentVersion.get(0);
                    int cv1 = (int) currentVersion.get(1);
                    int cv2 = (int) currentVersion.get(2);
                    int rv0 = (int) runningVersion.get(0);
                    int rv1 = (int) runningVersion.get(1);
                    int rv2 = (int) runningVersion.get(2);
                    if (cv0 > rv0) {
                        isOutOfDate = true;
                    } else if (cv0 == rv0 && cv1 > rv1) {
                        isOutOfDate = true;
                    } else if (cv0 == rv0 && cv1 == rv1 && cv2 > rv2) {
                        isOutOfDate = true;
                    }

                    if (isOutOfDate) {
                        newVersion = pl.badge + ChatColor.RED + "is out of date. Please update to version " + fullGitVersion + " from " + "https://github.com/" + pl.getConfig().getString("externalFeatures.updates.githubKey") + "/releases/latest";
                        System.out.println(newVersion);}
                    else{
                        System.out.println(pl.badge +  "is up to date");
                    }
                    break;
                }
                in.close();

                return true;
            } catch (Exception e) {
                e.printStackTrace();
                pl.getServer().getLogger().warning("Something went wrong while downloading an update.");
                pl.getServer().getLogger().info("Please check the plugin's release page to see if there are any updates available.");
                pl.getServer().getLogger().info("https://github.com/" + pl.getConfig().getString("externalFeatures.updates.githubKey") + "/releases/latest");
                pl.getServer().getLogger().fine(e.getMessage());
                return false;
            }
        }
        return false;
    }

    public void checkUpdates(Player p) {
        if (isOutOfDate) {
            TextComponent updateMessage = new TextComponent(pl.badge + ChatColor.RED + "is out of date." + ChatColor.BOLD +" [Update - " + gitVersion + "]");
            ClickEvent updateClickEvent = new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/" + pl.getConfig().getString("externalFeatures.updates.githubKey") + "/releases/latest");
            updateMessage.setClickEvent(updateClickEvent);
            p.spigot().sendMessage(updateMessage);
            //newVersion = pl.badge + ChatColor.RED + "is out of date. Please update to version at " + pl.getConfig().getString("releasePage");
            //p.sendMessage(newVersion);}
        }
    }
}
