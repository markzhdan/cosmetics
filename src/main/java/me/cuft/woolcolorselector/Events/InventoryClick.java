package me.cuft.woolcolorselector.Events;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import me.cuft.woolcolorselector.Gui.WoolGui;
import me.cuft.woolcolorselector.Util.PlayerData;
import me.cuft.woolcolorselector.WoolColorSelector;
import org.bson.Document;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class InventoryClick implements Listener
{
    private MongoCollection<Document> collection;

    private final WoolGui menu;

    private final WoolColorSelector main;

    public InventoryClick(WoolColorSelector main)
    {
        menu = new WoolGui();
        this.main = main;
    }

    //THIS COULD BE IMPROVED. IT WORKED AND I DIDNT WANNA BREAK IT
    public void updateColor(Player player, String color)
    {
        MongoClient mongoClient = MongoClients.create("mongodb+srv://Cuft:Cuft@morvencosmetics.alnd1.mongodb.net/<dbname>?retryWrites=true&w=majority");
        MongoDatabase database = mongoClient.getDatabase("woolTest");
        collection = database.getCollection("wool");

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.append("UUID", player.getUniqueId().toString());

        BasicDBObject updateQuery = new BasicDBObject();
        updateQuery.append("$set",
                new BasicDBObject().append("Wool_Color", color));

        collection.updateMany(searchQuery, updateQuery);

        main.setPlayerDataHashMap(player.getUniqueId(), new PlayerData(player.getUniqueId().toString(), color));
    }
    @EventHandler
    public void onClick(InventoryClickEvent event)
    {
        if(!event.getInventory().equals(menu.getInventory()))
        {
            return;
        }

        final ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem == null || clickedItem.getType() == Material.AIR)
        {
            return;
        }

        if(event.getView().getType() == InventoryType.PLAYER)
        {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);

        switch(event.getSlot())
        {
            case 0:
                updateColor(player, "WHITE_WOOL");
                player.sendMessage("White Wool Selected");
                break;
            case 1:
                updateColor(player, "BLACK_WOOL");
                player.sendMessage(ChatColor.BLACK + "Black Wool Selected");
                break;
            case 2:
                updateColor(player, "RED_WOOL");
                player.sendMessage(ChatColor.RED + "Red Wool Selected");
                break;
            case 3:
                updateColor(player, "BLUE_WOOL");
                player.sendMessage(ChatColor.BLUE + "Blue Wool Selected");
                break;
            case 4:
                updateColor(player, "YELLOW_WOOL");
                player.sendMessage(ChatColor.YELLOW + "Yellow Wool Selected");
                break;
            case 5:
                updateColor(player, "GREEN_WOOL");
                player.sendMessage(ChatColor.GREEN + "Green Wool Selected");
                break;
            default:
                System.out.println("error");
        }


    }
}
