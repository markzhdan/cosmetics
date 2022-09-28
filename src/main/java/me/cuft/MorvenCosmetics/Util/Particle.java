package me.cuft.MorvenCosmetics.Util;

import org.bukkit.Material;

public class Particle
{
    private String particleName;
    private Material displayItem;
    private String displayName;
    private int price;
    private int amount;

    public String getParticleName() {
        return particleName;
    }

    public void setParticleName(String particleName) {
        this.particleName = particleName;
    }

    public Material getDisplayItem() {
        return displayItem;
    }

    public void setDisplayItem(Material displayItem) {
        this.displayItem = displayItem;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Particle(String particleName, Material displayItem, String displayName, int price, int amount)
    {
        this.particleName = particleName;
        this.displayItem =  displayItem;
        this.displayName = displayName;
        this.price = price;
        this.amount = amount;
    }


}
