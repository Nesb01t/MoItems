package co.nesb01t.moitems.event;

import co.nesb01t.moitems.utils.Item;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class Menu implements Listener {

    private static final int PLAYER_CRAFT_INV_SIZE = 5;
    private static final long INV_UPDATE_INTERVAL = 10L;
    private final Map<Player, BukkitTask> itemRetentionTasks = new HashMap<>();

    /*
    玩家加入时加入调度器
     */
    @org.bukkit.event.EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        BukkitTask task = Bukkit.getScheduler().runTaskTimer(Bukkit.getPluginManager().getPlugin("MoItems"), () -> {
            InventoryView view = player.getOpenInventory();

            if (isPlayerCraftingInv(view)) {
                Inventory crafting = view.getTopInventory();

                crafting.setItem(1, Item.menuAttrItem(player));
            }
        }, 0L, INV_UPDATE_INTERVAL);

        this.itemRetentionTasks.put(player, task);
    }

    /*
    玩家退出时删除调度器
     */
    @org.bukkit.event.EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        BukkitTask task = this.itemRetentionTasks.remove(player);
        if (task != null) {
            task.cancel();
        }
    }

    /*
    关闭背包时候清除掉落物
     */
    @org.bukkit.event.EventHandler
    public void onClose(InventoryCloseEvent event) {
        InventoryView view = event.getView();
        if (isPlayerCraftingInv(view)) {
            view.getTopInventory().clear();
        }
    }

    /*
    玩家丢出时菜单时清除掉落物
     */
    @EventHandler
    public void onDropItem(PlayerDropItemEvent e){
        e.getItemDrop().remove();
    }

    /*
    禁止物品点击
     */
    @org.bukkit.event.EventHandler
    public void onClick(InventoryClickEvent event) {
        InventoryView view = event.getView();
        if (isPlayerCraftingInv(view) &&
            event.getClickedInventory() != event.getWhoClicked().getInventory()) {
            if (event.getSlot() < 5) {
                event.setCancelled(true);
            }
        }
    }

    /*
    检查是否为玩家背包(包含工作台)
     */
    private static boolean isPlayerCraftingInv(InventoryView view) {
        return view.getTopInventory().getSize() == PLAYER_CRAFT_INV_SIZE;
    }
}
