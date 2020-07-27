package me.cuft.woolcolorselector.Events;

import me.cuft.woolcolorselector.WoolColorSelector;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class blockPlaced implements Listener
{
    private final WoolColorSelector main;

    public blockPlaced(WoolColorSelector main)
    {
        this.main = main;
    }

    @EventHandler
    public void onBlockPlaced(BlockPlaceEvent event)
    {
        Block block = event.getBlock();
        Player player = event.getPlayer();

        if(block.getType() == Material.WHITE_WOOL)
        {
            block.setType(Material.getMaterial(main.getPlayerDataHashMap().get(player.getUniqueId()).getWoolColor()));
        }
    }
}
