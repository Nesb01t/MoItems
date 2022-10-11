package co.nesb01t.moitems.file;

import co.nesb01t.moitems.api.ItemList;
import com.google.common.collect.Lists;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FileController {
    public static String root = "plugins/MoItems/Items/";
    public static ArrayList<File> fileList; // 存放文件列表
    public static ArrayList<YamlConfiguration> yamlList; // 存放yaml数据列表
    public static ArrayList<String> typeList = Lists.newArrayList(
            "Weapon", "Armor", "Consume", "Material");

    public static void reloadConfig() throws IOException { // 重新加载配置文件
        File dir = new File(root);
        fileList = new ArrayList<>();
        yamlList = new ArrayList<>();

        if (!dir.exists()){
            dir.mkdirs();
        }

        for (int i = 0; i < typeList.size(); i++) {
            fileList.add(new File(root + typeList.get(i) + ".yml"));
            if (!fileList.get(i).exists()){
                fileList.get(i).createNewFile();
            }
            yamlList.add(YamlConfiguration.loadConfiguration(fileList.get(i)));
            yamlList.get(i).save(fileList.get(i));
        }
        ItemList.updateItemIdList(); // ItemIdList
        ItemList.updateItemList(); // ItemList
    }
}
