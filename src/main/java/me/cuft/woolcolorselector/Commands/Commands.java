package me.cuft.woolcolorselector.Commands;

import me.cuft.woolcolorselector.Gui.WoolGui;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor
{
    public String cmd1 = "wool";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player)
        {
            if (command.getName().equalsIgnoreCase(cmd1))
            {
                Player player = (Player) sender;
                WoolGui menu = new WoolGui();
                menu.openInventory(player);
                return true;
            }

        } else {
            sender.sendMessage(ChatColor.RED + "Only players can use this command!");
            return true;
        }
        return false;
    }
}
