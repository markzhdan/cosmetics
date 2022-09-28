package me.cuft.MorvenCosmetics.Events;

import me.cuft.MorvenCosmetics.Main;
import me.cuft.MorvenCosmetics.Util.particleUtil;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class bowShootEvent implements Listener
{
    particleUtil summon = new particleUtil();

    private final Main main;

    me.cuft.MorvenCosmetics.Util.Particle particle = null;

    public bowShootEvent(Main main)
    {
        this.main = main;
    }

    @EventHandler
    public void bowShoot(EntityShootBowEvent e)
    {
        Player player = (Player) e.getEntity();

        if (!(e.getEntity() instanceof Player))
            return;

        String name = main.getPlayerDataHashMap().get(player.getUniqueId()).getCurrentBowTrail();
        if(name.equalsIgnoreCase("none"))
            return;

        Entity arrow = e.getProjectile();
        for (me.cuft.MorvenCosmetics.Util.Particle p : main.getBowParticleArrayList()) {
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
                summon.particle(arrow.getLocation(), arrow.getWorld(), name, particle.getAmount());

                if(arrow.isOnGround() || arrow.isDead())
                {
                    cancel();
                }
            }
        }.runTaskTimer(main, 0, 1);

    }
}
