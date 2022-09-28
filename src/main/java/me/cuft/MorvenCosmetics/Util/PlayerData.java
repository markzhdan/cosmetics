package me.cuft.MorvenCosmetics.Util;

import java.util.HashMap;

public class PlayerData
{
    private String uuid;
    private String ign;
    private String currentBowTrail;
    private String currentFireworkTrail;
    private HashMap<String, Boolean> unlocked;

    public PlayerData(String uuid, String ign, String currentBowTrail, String currentFireworkTrail, HashMap<String, Boolean> unlocked) {
        this.uuid = uuid;
        this.ign = ign;
        this.currentBowTrail = currentBowTrail;
        this.currentFireworkTrail = currentFireworkTrail;
        this.unlocked = unlocked;
    }

    public String getIgn() {
        return ign;
    }

    public void setIgn(String ign) {
        this.ign = ign;
    }
    public HashMap<String, Boolean> getUnlocked()
    {
        return unlocked;
    }

    public void updateUnlocked(String key, boolean YesOrNo)
    {
        unlocked.replace(key, YesOrNo);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCurrentBowTrail() {
        return currentBowTrail;
    }

    public void setCurrentBowTrail(String currentBowTrail) {
        this.currentBowTrail = currentBowTrail;
    }

    public String getCurrentFireworkTrail() {
        return currentFireworkTrail;
    }

    public void setCurrentFireworkTrail(String currentFireworkTrail) {
        this.currentFireworkTrail = currentFireworkTrail;
    }
}
