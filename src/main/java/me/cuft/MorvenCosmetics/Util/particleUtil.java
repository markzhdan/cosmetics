package me.cuft.MorvenCosmetics.Util;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;

public class particleUtil
{
    public void particle(Location loc, World world, String particleName, int amount)
    {
        org.bukkit.Particle particle = Particle.valueOf(particleName);
        world.spawnParticle(particle, loc, amount);
    }
}
