package co.nesb01t.moitems.file;

import com.google.common.collect.Lists;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FileController {
    public String root = "plugins/MoItems/Items/";
    public ArrayList<File> fileList = new ArrayList<>();
    public ArrayList<FileConfiguration> fileConfList = new ArrayList<>();
    public ArrayList<String> typeList = Lists.newArrayList("Weapon", "Armor", "Consume", "Material");

    public void initConfig() throws IOException {
        File dir = new File(root);
        if (!dir.exists()){
            dir.mkdirs();
        }

        for (int i = 0; i < typeList.size(); i++) {
            fileList.add(new File(root + typeList.get(i) + ".yml"));
            if (!fileList.get(i).exists()){
                fileList.get(i).createNewFile();
            }
            fileConfList.add(YamlConfiguration.loadConfiguration(fileList.get(i)));
            fileConfList.get(i).save(fileList.get(i));
        }
    }
}
