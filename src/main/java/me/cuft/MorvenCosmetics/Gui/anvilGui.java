package me.cuft.MorvenCosmetics.Gui;

import me.cuft.MorvenCosmetics.Main;
import me.cuft.MorvenCosmetics.Util.Particle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class anvilGui implements Listener
{
    private final Main main;

    private static Inventory inv;

    ItemStack item;

    Player player;

    private int size = 9;

    public anvilGui(ItemStack item, Player player, Main main) {
        this.item = item;
        this.player = player;
        this.main = main;
        // Create a new inventory, with no owner (as this isn't a real inventory), a size of nine, called example
        inv = Bukkit.createInventory(null, InventoryType.ANVIL, "Enter Price");

        // Put the items into the inventory
        initializeItems();
    }

    // You can call this whenever you want to put the items in
    public void initializeItems() {
        for(Particle particle : main.getBowParticleArrayList())
        {
            if(particle.getDisplayItem().equals(item.getType()))
            {
                player.getInventory().setItem(0, main.createGuiItem(item.getType(), "" + particle.getPrice()));
            }
        }
        for(Particle particle : main.getFireworksParticleArrayList())
        {
            if(particle.getDisplayItem().equals(item.getType()))
            {
                player.getInventory().setItem(0, main.createGuiItem(item.getType(), "" + particle.getPrice()));
            }
        }
    }

    // Nice little method to create a gui item with a custom name, and description

    // You can open the inventory with this
    public void openInventory(final Player player) {
        player.openInventory(inv);
    }

    public Inventory getInventory()
    {
        return inv;
    }
}
