package co.nesb01t.moitems.event;

import co.nesb01t.moitems.api.Attr;
import co.nesb01t.moitems.utils.Random;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.*;

public class AttrEvent implements Listener {

    /*
    玩家Attr对象初始化
     */
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Attr.createPlayerAttr(e.getPlayer());
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        Attr.removePlayerAttr(e.getPlayer());
    }
    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e){
        Attr.getPlayerAttr(e.getPlayer()).updatePlayerAttr();
    }
    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent e){
        Attr.getPlayerAttr(e.getPlayer()).updatePlayerAttr();
    }
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e){
        Attr.getPlayerAttr((Player) e.getPlayer()).updatePlayerAttr();
    }

    /*
    伤害行为计算
     */
    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent event){
        if (event.getDamager() instanceof Player && event.getFinalDamage() > 0) { // 判断伤害正常
            double damage = event.getDamage();
            double attackPower = Attr.getPlayerAttr((Player) event.getDamager()).attackPower;
            double critical = Attr.getPlayerAttr((Player) event.getDamager()).critical;
            damage += attackPower;
            if (Random.Chance(critical*100)) {
                damage *= 1.5;
//                event.getDamager().sendMessage("critical!");
            }
            event.setDamage(damage);

//            event.getDamager().sendMessage(String.valueOf(damage));
        }
    }
    @EventHandler
    public void onPlayerDamaged(EntityDamageByEntityEvent event){
        if (event.getEntity() instanceof Player && event.getFinalDamage() > 0) {
            double damage = event.getDamage();
            double parry = Attr.getPlayerAttr((Player) event.getEntity()).resistance;
            if (Random.Chance(parry*100)) {
                damage = 0;
                event.getEntity().sendMessage("parried!");
            }
            event.setDamage(damage);
        }
    }

}
