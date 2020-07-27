package me.cuft.woolcolorselector.Gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class WoolGui implements Listener
{
    private static Inventory inv;

    public WoolGui() {
        // Create a new inventory, with no owner (as this isn't a real inventory), a size of nine, called example
        inv = Bukkit.createInventory(null, 9, "Wool Colors");

        // Put the items into the inventory
        initializeItems();
    }

    // You can call this whenever you want to put the items in
    public void initializeItems() {
        inv.addItem(createGuiItem(Material.WHITE_WOOL, "§fWhite Wool"));
        inv.addItem(createGuiItem(Material.BLACK_WOOL, "§0Black Wool"));
        inv.addItem(createGuiItem(Material.RED_WOOL, "§4Red Wool"));
        inv.addItem(createGuiItem(Material.BLUE_WOOL, "§1Blue Wool"));
        inv.addItem(createGuiItem(Material.YELLOW_WOOL, "§eYellow Wool"));
        inv.addItem(createGuiItem(Material.GREEN_WOOL, "§aGreen Wool"));
    }

    // Nice little method to create a gui item with a custom name, and description
    protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        assert meta != null;
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
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