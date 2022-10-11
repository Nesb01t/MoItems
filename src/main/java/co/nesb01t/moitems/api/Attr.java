package co.nesb01t.moitems.api;

import co.nesb01t.moitems.MoItems;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Attr {
    public int strength = 0; // attack power
    public int agility = 0; // critical+parry
    public int stamina = 0; // health

    public double attackPower = 0;
    public double critical = 0;
    public double resistance = 0;
    public double health = 0;
    public double block;

    private Player player;
    private static final Map<Player, Attr> playerAttrHashMap = new HashMap<>();


    /**
     * 实例方法
     */
    public void changePlayerAttr(){
        player.setMaxHealth(20+health);
    }
    public void updatePlayerAttr(){ // 刷新玩家状态
        new BukkitRunnable(){
            @Override
            public void run(){
                calcPlayerAttr();
                changePlayerAttr();
            }
        }.runTaskAsynchronously(Bukkit.getPluginManager().getPlugin("MoItems"));
    }

    public void calcPlayerAttr(){ // 统计装备属性计算PlayerAttr
        /*
        通过属性计算取值
         */
        ItemStack tool = this.player.getInventory().getItemInMainHand();
        ItemMeta meta = tool.getItemMeta();
        if (meta != null) {
            if (meta.getDisplayName().contains("Sword")) this.stamina += 16;
        }
        /*
        1 strength = 0.5 attackPower
        1 agility = 0.01 critical + 0.01 parry
        1 spirit = 0.5 regeneration
        1 stamina = 1 health
         */

        this.attackPower = 0.5 * this.strength;
        this.critical = 0.01 * this.agility;
        this.resistance = 0.01 * this.agility;
        this.health = this.stamina;
    }

    /**
     * 静态方法
     */
    public static void createPlayerAttr(Player player) {
        Attr playerAttr = new Attr();
        playerAttr.player = player;
        playerAttr.updatePlayerAttr();
        playerAttrHashMap.put(player, playerAttr);
    }

    public static void removePlayerAttr(Player player){
        playerAttrHashMap.remove(player);
    }

    public static Attr getPlayerAttr(Player player){
        return playerAttrHashMap.get(player);
    }
}
