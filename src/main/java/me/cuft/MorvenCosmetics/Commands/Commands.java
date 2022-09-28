package me.cuft.MorvenCosmetics.Commands;

import me.cuft.MorvenCosmetics.Gui.adminGui;
import me.cuft.MorvenCosmetics.Gui.cosmeticGui;
import me.cuft.MorvenCosmetics.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Commands implements CommandExecutor
{
    public String cmd1 = "cosmetics";
    public String cmd2 = "setcurrency";

    private final Main main;

    public Commands(Main main)
    {
        this.main = main;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player)
        {
            Player player = (Player) sender;
            if(command.getName().equalsIgnoreCase(cmd1))
            {
                if (args.length > 0)
                {
                    if (args[0].equalsIgnoreCase("admin"))
                    {
                        if(player.isOp())
                        {
                            adminGui menu = new adminGui(player, main);
                            menu.openInventory(player);
                        }
                        else
                        {
                            player.sendMessage(ChatColor.RED + "You do not have the required permissions!");
                        }
                        return true;
                    }
                    else return false;
                }
                else
                {
                    cosmeticGui menu = new cosmeticGui(main);
                    menu.openInventory(player);
                    return true;
                }
            }
            else if (command.getName().equalsIgnoreCase(cmd2))
            {
                if(player.isOp())
                {
                    ItemStack item = player.getInventory().getItemInMainHand();
                    item.setAmount(1);
                    if(item == null || item.getType().name().equalsIgnoreCase("air"))
                    {
                        player.sendMessage(ChatColor.RED + "Cannot Set This Item To Currency");
                        return true;
                    }
                    else
                    {
                        main.setCurrency(item);
                        main.saveConfig();
                        main.setCurrency(main.getCurrency());
                        if(item.getItemMeta().getDisplayName() == "")
                        {
                            player.sendMessage("Set currecy to " + ChatColor.RED + item.getType().name());
                            return true;
                        }
                        else
                        {
                            player.sendMessage("Set currecy to " + ChatColor.RED + item.getItemMeta().getDisplayName());
                            return true;
                        }
                    }
                }
                else
                {
                    player.sendMessage(ChatColor.RED + "You do not have the required permissions!");
                    return true;
                }
            }
        }
        else
            {
            sender.sendMessage(ChatColor.RED + "Only players can use this command!");
            return true;
        }
        return false;
    }
}
