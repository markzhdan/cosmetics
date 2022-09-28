package me.cuft.MorvenCosmetics.Gui;

import me.cuft.MorvenCosmetics.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

public class cosmeticGui implements Listener
{
    private static Inventory inv;

    private final Main main;

    public cosmeticGui(Main main) {
        this.main = main;
        // Create a new inventory, with no owner (as this isn't a real inventory), a size of nine, called example
        inv = Bukkit.createInventory(null, 9, "Cosmetics");

        // Put the items into the inventory
        initializeItems();
    }

    // You can call this whenever you want to put the items in
    public void initializeItems() {
        inv.addItem(main.createGuiItem(Material.BARRIER, "Close"));
        inv.addItem(main.createGuiItem(Material.BOW, "§§6Bow Trails"));
        inv.addItem(main.createGuiItem(Material.ELYTRA, "§3Elytra Trails"));
        for(int i = 3; i < 9; i++)
        {
            inv.setItem(i, main.createGuiItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " "));
        }
    }

    // You can open the inventory with this
    public void openInventory(final Player player) {
        player.openInventory(inv);
    }

    public Inventory getInventory()
    {
        return inv;
    }
}