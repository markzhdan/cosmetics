package me.cuft.MorvenCosmetics.Events;

import me.cuft.MorvenCosmetics.Main;
import me.cuft.MorvenCosmetics.Util.particleUtil;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class playerFlyEvent implements Listener
{
    particleUtil summon = new particleUtil();

    private final Main main;

    me.cuft.MorvenCosmetics.Util.Particle particle = null;

    public playerFlyEvent(Main main)
    {
        this.main = main;
    }

    @EventHandler
    public void onMove(EntityToggleGlideEvent event) {
        Player player = (Player) event.getEntity();
        
        if (!(event.getEntity() instanceof Player))
            return;

        String name = main.getPlayerDataHashMap().get(player.getUniqueId()).getCurrentFireworkTrail();
        if(name.equalsIgnoreCase("none"))
            return;

        for (me.cuft.MorvenCosmetics.Util.Particle p : main.getFireworksParticleArrayList()) {
            if (p.getParticleName().equals(name))
            {
                particle = p;
                break;
            }
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                //methods
                summon.particle(player.getLocation(), player.getWorld(), name, particle.getAmount());

                if((!player.isGliding()) || player.isDead() || (!player.isOnline()))
                {
                    cancel();
                }
            }
        }.runTaskTimer(main, 0, 2);

    }
}
