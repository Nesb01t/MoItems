package co.nesb01t.moitems.api;

import co.nesb01t.moitems.file.FileController;
import co.nesb01t.moitems.utils.Item;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class ItemList {
    public static ArrayList<ItemStack> ItemList;
    public static ArrayList<String> ItemIdList;

    public static void updateItemIdList() { // 从yamlList读取数据
        ItemIdList = new ArrayList<String>();
        ArrayList<YamlConfiguration> yamlList = FileController.yamlList;
        for (int i = 0; i < yamlList.size(); i++) { // 遍历各个.yml文件
            Map<String, Object> listMap = yamlList.get(i).getValues(false);
            for (Map.Entry<String, Object> entry: listMap.entrySet()) {
                ItemIdList.add(entry.getKey());
            }
        }
    }
    public static void updateItemList() { // 从yamlList读取数据
        ItemList = new ArrayList<ItemStack>();
        for (String id: ItemIdList) { // 遍历各个.yml文件
            ItemList.add(Item.genertorItem(id));
        }
    }

    public static void showItemList(Player p) { // 显示物品列表
        StringBuilder str = new StringBuilder("物品列表: ");
        for (String id: ItemIdList) {
            if (!Objects.equals(id, ItemIdList.get(ItemIdList.size() - 1))) {
                str.append(id).append(", ");
            } else {
                str.append(id);
            }
        }
        p.sendMessage(str.toString());
    }

    public static void givePlayerWeapon(Player p, String id) {
        p.getInventory().addItem(Item.genertorItem(id));
    }
}
