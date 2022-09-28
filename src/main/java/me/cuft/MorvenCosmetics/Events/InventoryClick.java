package me.cuft.MorvenCosmetics.Events;

import me.cuft.MorvenCosmetics.Gui.bowGui;
import me.cuft.MorvenCosmetics.Gui.cosmeticGui;
import me.cuft.MorvenCosmetics.Gui.elytraGui;
import me.cuft.MorvenCosmetics.Main;
import me.cuft.MorvenCosmetics.Util.Particle;
import net.wesjd.anvilgui.AnvilGUI;
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
    private final Main main;

    public InventoryClick(Main main)
    {
        this.main = main;
    }

    public void buyOrEquip(Player player, String particleName, String cosmetic)
    {
        if(cosmetic.equalsIgnoreCase("bow"))
        {
            Particle particle = null;
            for(Particle partic : main.getBowParticleArrayList())
            {
                if(partic.getParticleName().equalsIgnoreCase(particleName))
                {
                    particle = partic;
                }
            }

            if(main.getPlayerDataHashMap().get(player.getUniqueId()).getUnlocked().get(particle.getParticleName()) || player.isOp())
            {
                main.updateValue(player,"bow", particle.getParticleName());
                player.sendMessage(particle.getDisplayName() + ChatColor.WHITE +  " Selected");
            }
            else
            {
                if(player.getInventory().containsAtLeast(main.getCurrency(), particle.getPrice()))
                {
                    ItemStack remove = main.getCurrency();
                    for(int i = 0; i < particle.getPrice(); i++)
                    {
                        player.getInventory().removeItem(remove);
                    }
                    main.updateUnlockables(player, particle.getParticleName(), true);
                    player.sendMessage(ChatColor.GREEN + "Successfully Bought " + particle.getDisplayName());
                    player.closeInventory();
                    player.openInventory(new bowGui(player, main).getInventory());
                }
                else
                {
                    player.sendMessage(ChatColor.RED + "You Do Not Have Enough " + main.getCurrency().getItemMeta().getDisplayName() +"To Buy This Trail!");
                }
            }
        }
        else if(cosmetic.equalsIgnoreCase("firework"))
        {
            Particle particle = null;
            for(Particle partic : main.getFireworksParticleArrayList())
            {
                if(partic.getParticleName().equalsIgnoreCase(particleName))
                {
                    particle = partic;
                }
            }

            if(main.getPlayerDataHashMap().get(player.getUniqueId()).getUnlocked().get(particle.getParticleName()) || player.isOp())
            {
                main.updateValue(player,"firework", particle.getParticleName());
                player.sendMessage(particle.getDisplayName() + ChatColor.WHITE +  " Selected");
            }
            else
            {
                if(player.getInventory().containsAtLeast(main.getCurrency(), particle.getPrice()))
                {
                    ItemStack remove = main.getCurrency();
                    for(int i = 0; i < particle.getPrice(); i++)
                    {
                        player.getInventory().removeItem(remove);
                    }
                    main.updateUnlockables(player, particle.getParticleName(), true);
                    player.sendMessage(ChatColor.GREEN + "Successfully Bought " + particle.getDisplayName());
                    player.closeInventory();
                    player.openInventory(new elytraGui(player, main).getInventory());
                }
                else
                {
                    player.sendMessage(ChatColor.RED + "You Do Not Have Enough " + main.getCurrency().getItemMeta().getDisplayName() +"To Buy This Trail!");
                }
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event)
    {
        Player player = (Player) event.getWhoClicked();
        String invName = player.getOpenInventory().getTitle();
        player.sendMessage("bow" + main.getBowParticleArrayList());
        if(!(invName.equalsIgnoreCase("Bow Trails") || invName.equalsIgnoreCase("Cosmetics") || invName.equalsIgnoreCase("Elytra Trails") || invName.equalsIgnoreCase("Admin Menu") || invName.equalsIgnoreCase("Enter Price")))
        {
            return;
        }

        final ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem == null || clickedItem.getType() == Material.AIR)
        {
            return;
        }

        if(!invName.equalsIgnoreCase("Enter Price"))
        {
            event.setCancelled(true);
        }

        if(event.getClickedInventory().getType() == InventoryType.PLAYER && !invName.equalsIgnoreCase("Enter Price"))
        {
            return;
        }

        if(player.getOpenInventory().getTitle().equalsIgnoreCase("cosmetics"))
        {
            switch(event.getSlot())
            {
                case 0:
                    player.closeInventory();
                    break;
                case 1:
                    player.closeInventory();
                    player.openInventory(new bowGui(player, main).getInventory());
                    break;
                case 2:
                    player.closeInventory();
                    player.openInventory(new elytraGui(player, main).getInventory());
                    break;
            }
        }
        else if(player.getOpenInventory().getTitle().equalsIgnoreCase("Bow Trails"))
        {
            int slot = event.getSlot();
            if(slot == 0)
            {
                player.closeInventory();
                player.openInventory(new cosmeticGui(main).getInventory());
            }
            else if(slot == 1)
            {
                main.updateValue(player,"bow", "NONE");
                player.sendMessage("Turned Off Bow Trails");
            }
            else
            {
                slot -= 2;
                if(slot < main.getBowParticleArrayList().size())
                {
                    buyOrEquip(player, main.getBowParticleArrayList().get(slot).getParticleName(), "bow");
                }
            }
        }
        else if(player.getOpenInventory().getTitle().equalsIgnoreCase("Elytra Trails"))
        {
            int slot = event.getSlot();
            if(slot == 0)
            {
                player.closeInventory();
                player.openInventory(new cosmeticGui(main).getInventory());
            }
            else if(slot == 1)
            {
                main.updateValue(player,"firework", "NONE");
                player.sendMessage("Turned Off Firework Trails");
            }
            else
            {
                slot -= 2;
                if(slot < main.getFireworksParticleArrayList().size())
                {
                    buyOrEquip(player, main.getFireworksParticleArrayList().get(slot).getParticleName(), "firework");
                }
            }
        }
        else if(player.getOpenInventory().getTitle().equalsIgnoreCase("Admin Menu"))
        {
            int slot = event.getSlot();
            if(slot == 0)
            {
                player.sendMessage(ChatColor.LIGHT_PURPLE + "Click A Bow Trial To Change It's Price");
            }
            else if(slot == 18)
            {
                player.sendMessage(ChatColor.LIGHT_PURPLE + "Click An Elytra Trial To Change It's Price");
            }
            else
            {
                Particle particle = null;

                for(Particle particle1 : main.getBowParticleArrayList())
                {
                    if(particle1.getDisplayName().equalsIgnoreCase(event.getCurrentItem().getItemMeta().getDisplayName()))
                        particle = particle1;
                }
                for(Particle particle1 : main.getFireworksParticleArrayList())
                {
                    if(particle1.getDisplayName().equalsIgnoreCase(event.getCurrentItem().getItemMeta().getDisplayName()))
                        particle = particle1;
                }

                player.closeInventory();
                Particle finalParticle = particle;
                new AnvilGUI.Builder().plugin(main).onComplete((player1, text) -> {           //called when the inventory output slot is clicked
                    if(main.numberOrNot(text)) {
                        main.setPrice(finalParticle.getParticleName(), Integer.parseInt(text));
                        player1.sendMessage(finalParticle.getDisplayName() + ChatColor.DARK_GREEN + " Price Set To §l" + text);
                        return AnvilGUI.Response.close();
                    } else {
                        return AnvilGUI.Response.text("Enter A Number");
                    }
                })
                        .item(new ItemStack(event.getCurrentItem().getType()))
                        .text("" + ChatColor.GREEN + particle.getPrice())
                        .title("Enter Price").open(player);
            }
        }
    }
}
