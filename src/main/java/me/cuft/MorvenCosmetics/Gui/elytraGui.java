package me.cuft.MorvenCosmetics.Gui;

import me.cuft.MorvenCosmetics.Main;
import me.cuft.MorvenCosmetics.Util.Particle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class elytraGui implements Listener
{
    private static Inventory inv;

    Player player;

    private final Main main;

    private int size = 9;

    public elytraGui(Player player, Main main) {
        // Create a new inventory, with no owner (as this isn't a real inventory), a size of nine, called example
        inv = Bukkit.createInventory(null, size, "Elytra Trails");

        this.player = player;
        this.main = main;
        // Put the items into the inventory
        initializeItems();
    }

    // You can call this whenever you want to put the items in
    public void initializeItems() {
        int count = 2;
        inv.addItem(main.createGuiItem(Material.ARROW, "Back"));
        inv.addItem(main.createGuiItem(Material.BARRIER, "None"));

        for(Particle particle : main.getFireworksParticleArrayList())
        {
            if(main.getPlayerDataHashMap().get(player.getUniqueId()).getUnlocked().get(particle.getParticleName()) || player.isOp())
            {
                inv.addItem(main.createGuiItem(particle.getDisplayItem(), particle.getDisplayName(),ChatColor.DARK_GREEN + "Unlocked", "", ChatColor.YELLOW + "Click To Equip!"));
            }
            else
            {
                inv.addItem(main.createGuiItem(particle.getDisplayItem(), particle.getDisplayName(), ChatColor.DARK_GRAY + "Not Available", "", ChatColor.GOLD + "Cost: " + ChatColor.GREEN + particle.getPrice()));
            }
            count++;
        }
        for(int i = count; i < size; i++)
        {
            inv.setItem(i, main.createGuiItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " "));
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
