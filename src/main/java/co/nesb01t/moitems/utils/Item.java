package co.nesb01t.moitems.utils;

import co.nesb01t.moitems.api.Attr;
import co.nesb01t.moitems.file.FileController;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Item {
    public static ItemStack menuAttrItem(Player player){
        int str = Attr.getPlayerAttr(player).strength;
        int agi = Attr.getPlayerAttr(player).agility;
        int sta = Attr.getPlayerAttr(player).stamina;

        ItemStack item = new ItemStack(Material.CLOCK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("个人属性统计");
        List<String> lore = new ArrayList<>();
        lore.add("力量: "+str);
        lore.add("敏捷: "+agi);
        lore.add("耐力: "+sta);
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack genertorItem(String id){
        Material material = null;
        String name = "";
        String type = "";
        String quality = "";
        ArrayList<String> lore = new ArrayList<>();

        Boolean enchanted = false;

        ArrayList<YamlConfiguration> yamlList = FileController.yamlList;
        for (int i = 0; i < yamlList.size(); i++) { // 暴力获取物品信息, 后续进行优化
            Map<String, Object> listMap = yamlList.get(i).getValues(true);
            for (Map.Entry<String, Object> entry: listMap.entrySet()) {
                if (entry.getKey().equals(id + ".Item") && entry.getValue() instanceof String) {
                    material = Material.getMaterial((String) entry.getValue());
                }
                if (entry.getKey().equals(id + ".Name") && entry.getValue() instanceof String) {
                    name = (String) entry.getValue();
                    type = parseItemType(FileController.typeList.get(i));
                }
                if (entry.getKey().equals(id + ".Enchanted") && entry.getValue() instanceof Boolean) {
                    enchanted = (Boolean) entry.getValue();
                }
                if (entry.getKey().equals(id + ".Quality") && entry.getValue() instanceof String) {
                    quality = (String) entry.getValue();
                }
                if (entry.getKey().equals(id + ".Lore") && entry.getValue() instanceof ArrayList) {
                    lore = (ArrayList<String>) entry.getValue();
                }
            }
        }

        // 设置物品信息
        ItemStack item = new ItemStack(Material.IRON_SWORD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(type+quality+name);
        meta.setLore(lore);
        if (enchanted) { meta.addEnchant(Enchantment.DURABILITY, 1, false); }

        item.setItemMeta(meta);
        return item;
    }

    public static String parseItemType(String source){
        if (source.equals("Weapon")) return "武器";
        if (source.equals("Armor")) return "护甲";
        if (source.equals("Consume")) return "消耗品";
        if (source.equals("Material")) return "材料";
        return "未知";
    }
}
