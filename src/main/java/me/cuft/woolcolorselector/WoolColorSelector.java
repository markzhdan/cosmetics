package me.cuft.woolcolorselector;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import me.cuft.woolcolorselector.Commands.Commands;
import me.cuft.woolcolorselector.Events.InventoryClick;
import me.cuft.woolcolorselector.Events.blockPlaced;
import me.cuft.woolcolorselector.Util.PlayerData;
import org.bson.Document;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class WoolColorSelector extends JavaPlugin implements Listener {

    private HashMap<UUID, PlayerData> playerDataHashMap;

    private MongoCollection<Document> collection;

    private final Commands commands = new Commands();

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.playerDataHashMap = new HashMap<>();

        getCommand(commands.cmd1).setExecutor(commands);

        getServer().getPluginManager().registerEvents(new InventoryClick(this), this);
        getServer().getPluginManager().registerEvents(new blockPlaced(this), this);
        getServer().getPluginManager().registerEvents(this, this);

        MongoClient mongoClient = MongoClients.create("mongodb+srv://Cuft:Cuft@morvencosmetics.alnd1.mongodb.net/<dbname>?retryWrites=true&w=majority");
        MongoDatabase database = mongoClient.getDatabase("woolTest");
        collection = database.getCollection("wool");

        System.out.println("Plugin has started up");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }

    public MongoCollection<Document> getCollection()
    {
        return collection;
    }

    public HashMap<UUID,PlayerData> getPlayerDataHashMap()
    {
        return playerDataHashMap;
    }

    public void setPlayerDataHashMap(UUID uuid,PlayerData playerData)
    {
        playerDataHashMap.put(uuid, playerData);
    }

    @EventHandler
    public void join(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Document playerdoc = new Document("UUID", player.getUniqueId().toString());
        Document found = collection.find(playerdoc).first();
        if (found == null){
            playerdoc.append("Wool_Color", "WHITE_WOOL");
            collection.insertOne(playerdoc);
            playerDataHashMap.put(player.getUniqueId(), new PlayerData(player.getUniqueId().toString(),"WHITE_WOOL"));
            getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "Player created in Database");

        }else{
            String currentWool = found.getString("Wool_Color");
            playerDataHashMap.put(player.getUniqueId(), new PlayerData(player.getUniqueId().toString(), currentWool));
            getServer().getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "Player found in Database");
        }
    }
}
