package co.nesb01t.moitems.event;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class Event implements Listener {

    @org.bukkit.event.EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        e.setJoinMessage(ChatColor.GREEN + "[test]" + e.getJoinMessage());
        Player p = e.getPlayer();
        Location p_loc = p.getLocation();
        World w = p.getWorld();
        w.playSound(p_loc, Sound.ENTITY_PLAYER_LEVELUP, 0.5f, 0.5f);
    }

    @org.bukkit.event.EventHandler
    public void onPlayerHitSheep(EntityDamageEvent e){

    }
}