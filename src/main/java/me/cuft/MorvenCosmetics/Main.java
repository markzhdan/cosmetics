package me.cuft.MorvenCosmetics;

import com.mongodb.client.*;
import me.cuft.MorvenCosmetics.Commands.Commands;
import me.cuft.MorvenCosmetics.Events.InventoryClick;
import me.cuft.MorvenCosmetics.Events.bowShootEvent;
import me.cuft.MorvenCosmetics.Events.playerFlyEvent;
import me.cuft.MorvenCosmetics.Util.Particle;
import me.cuft.MorvenCosmetics.Util.PlayerData;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;


public final class Main extends JavaPlugin implements Listener {

    ArrayList<Particle> bowParticleArrayList = new ArrayList<Particle>() {{
        add(new Particle("DRIP_LAVA", Material.LAVA_BUCKET, "§cLava Drip Trail", 1, 1));
        add(new Particle("DRIP_WATER", Material.WATER_BUCKET, "§9Water Drip Trail", 1, 1));
        add(new Particle("NOTE", Material.NOTE_BLOCK, "§dNote Trail", 1, 1));
        add(new Particle("SPELL_MOB", Material.TROPICAL_FISH, "§1R§2a§3i§4n§5b§6o§1w §2S§3w§4i§5r§6l §fTrail", 1, 5));
        add(new Particle("NAUTILUS", Material.CONDUIT, "§1Conduit Trail", 1, 5));
    }};
    ArrayList<Particle> fireworksParticleArrayList = new ArrayList<Particle>() {{
        add(new Particle("HEART", Material.RED_DYE, "§cHeart Trail", 1, 1));
        add(new Particle("LAVA", Material.MAGMA_BLOCK, "§4Magma Trail", 1, 3));
        add(new Particle("SPELL", Material.STRING, "§fWhite Swirl Trail", 1, 5));
        add(new Particle("VILLAGER_HAPPY", Material.EMERALD, "§aGreen Sparkle Trail", 1, 3));
        add(new Particle("SPELL_WITCH", Material.PURPLE_DYE, "§5Purple Sparkle Trail", 1, 8));

    }};

    private HashMap<UUID, PlayerData> playerDataHashMap;

    private MongoCollection<Document> collection;

    private final Commands commands = new Commands(this);

    ItemStack currency;

    Plugin plugin = this;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.playerDataHashMap = new HashMap<>();

        currency = getConfig().getItemStack("currency");

        getCommand(commands.cmd1).setExecutor(commands);
        getCommand(commands.cmd2).setExecutor(commands);

        getServer().getPluginManager().registerEvents(new InventoryClick(this), this);
        getServer().getPluginManager().registerEvents(new bowShootEvent(this), this);
        getServer().getPluginManager().registerEvents(new playerFlyEvent(this), this);
        getServer().getPluginManager().registerEvents(this, this);

        MongoClient mongoClient = MongoClients.create("mongodb+srv://Cuft:Cuft@morvencosmetics.alnd1.mongodb.net/<dbname>?retryWrites=true&w=majority");
        MongoDatabase database = mongoClient.getDatabase("MorvenCosmetics");
        collection = database.getCollection("PlayerCosmetics");
        
        System.out.println("Plugin has started up");
        updatePrices();

        for(Player player : this.getServer().getOnlinePlayers())
        {
            savePlayerData(player);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        saveConfig();
    }

    public ItemStack getCurrency()
    {
        return currency;
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

    public Plugin getPlugin()
    {
         return plugin;
    }

    public void setCurrency(ItemStack currency)
    {
        this.currency = currency;
        getConfig().set("currency", currency);
    }

    public ArrayList<Particle> getBowParticleArrayList()
    {
        return bowParticleArrayList;
    }

    public ArrayList<Particle> getFireworksParticleArrayList() {
        return fireworksParticleArrayList;
    }

    public void updatePrices()
    {
        Document doc = (Document) collection.find(new Document("PRICES", "PRICES")).first();
        for(Particle particle : bowParticleArrayList)
        {
            particle.setPrice(doc.getInteger(particle.getParticleName()));
        }
        for(Particle particle : fireworksParticleArrayList)
        {
            particle.setPrice(doc.getInteger(particle.getParticleName()));
        }
    }

    public void setPrice(String particleName, int price)
    {
        Document doc = (Document) collection.find(new Document("PRICES", "PRICES")).first();
        if(doc != null)
        {
            Bson updatedValue = new Document(particleName, price);
            Bson updatedOperation = new Document("$set", updatedValue);
            collection.updateOne(doc, updatedOperation);
        }

        updatePrices();
    }

    public void updateValue(Player player, String cosmetic, String trail)
    {
        Document doc = (Document) collection.find(new Document("UUID", player.getUniqueId().toString())).first();

        if(doc != null)
        {
            if(cosmetic.equalsIgnoreCase("bow"))
            {
                Bson updatedValue = new Document("currentBowTrail", trail);
                Bson updatedOperation = new Document("$set", updatedValue);
                collection.updateOne(doc, updatedOperation);

                playerDataHashMap.get(player.getUniqueId()).setCurrentBowTrail(trail);
            }
            else if(cosmetic.equalsIgnoreCase("firework"))
            {
                Bson updatedValue = new Document("currentFireworksTrail", trail);
                Bson updatedOperation = new Document("$set", updatedValue);
                collection.updateOne(doc, updatedOperation);

                playerDataHashMap.get(player.getUniqueId()).setCurrentFireworkTrail(trail);
            }
        }
    }

    public boolean numberOrNot(String input)
    {
        try
        {
            Integer.parseInt(input);
        }
        catch(NumberFormatException ex)
        {
            return false;
        }
        return true;
    }

    public void addVariable(String key, Boolean value, Document document)
    {
        Bson updatedValue = new Document(key, value);
        Bson updatedOperation = new Document("$set", updatedValue);
        collection.updateOne(document, updatedOperation);
    }

    public void updateUnlockables(Player player, String particle, boolean trueOrFalse)
    {
        Document doc = (Document) collection.find(new Document("UUID", player.getUniqueId().toString())).first();
        addVariable(particle, trueOrFalse, doc);

        playerDataHashMap.get(player.getUniqueId()).updateUnlocked(particle, trueOrFalse);
    }

    public ItemStack createGuiItem(final Material material, final String name, final String... lore) {
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

    public void savePlayerData(Player player)
    {
        Document playerdoc = new Document("UUID", player.getUniqueId().toString());
        Document found = collection.find(playerdoc).first();

        if(found != null)
        {
            String currentBow = found.getString("currentBowTrail");
            String currentFirework = found.getString("currentFireworksTrail");
            HashMap<String, Boolean> unlocked = new HashMap<>();
            for(Particle particle : bowParticleArrayList)
            {
                if(found.getBoolean(particle.getParticleName()) == null)
                {
                    addVariable(particle.getParticleName(), false, playerdoc);
                }
                unlocked.put(particle.getParticleName(), found.getBoolean(particle.getParticleName()));
            }

            for(Particle particle : fireworksParticleArrayList)
            {
                if(found.getBoolean(particle.getParticleName()) == null)
                {
                    addVariable(particle.getParticleName(), false, playerdoc);
                }
                unlocked.put(particle.getParticleName(), found.getBoolean(particle.getParticleName()));
            }

            playerDataHashMap.put(player.getUniqueId(), new PlayerData(player.getUniqueId().toString(), player.getDisplayName(), currentBow, currentFirework, unlocked));
            getServer().getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "Player found in Database");
        }
    }

    @EventHandler
    public void join(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Document playerdoc = new Document("UUID", player.getUniqueId().toString());
        Document found = collection.find(playerdoc).first();
        HashMap<String, Boolean> unlocked = new HashMap<>();
        if (found == null){
            playerdoc.append("ign", player.getDisplayName());
            playerdoc.append("currentBowTrail", "NONE");
            playerdoc.append("currentFireworksTrail", "NONE");

            for(Particle particle : bowParticleArrayList)
            {
                playerdoc.append(particle.getParticleName(), false);
                unlocked.put(particle.getParticleName(), false);
            }

            for(Particle particle : fireworksParticleArrayList)
            {
                playerdoc.append(particle.getParticleName(), false);
                unlocked.put(particle.getParticleName(), false);
            }

            collection.insertOne(playerdoc);
            playerDataHashMap.put(player.getUniqueId(), new PlayerData(player.getUniqueId().toString(), player.getDisplayName(),"NONE", "NONE", unlocked));
            getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "Player created in Database");

        }else{
            savePlayerData(player);
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        playerDataHashMap.remove(player.getUniqueId());
    }
}
