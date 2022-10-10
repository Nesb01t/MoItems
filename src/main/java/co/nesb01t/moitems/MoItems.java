package co.nesb01t.moitems;

import co.nesb01t.moitems.cmd.Cmd;
import co.nesb01t.moitems.event.Event;
import co.nesb01t.moitems.file.FileController;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class MoItems extends JavaPlugin {

    @Override
    public void onEnable() {
        // CONFIG
        saveDefaultConfig(); // 保存 config.yml 文件
        try {
            FileController fileController = new FileController();
            fileController.initConfig();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // COMMAND
        Bukkit.getPluginManager().registerEvents(new Event(), this);
        this.getCommand("moitems").setExecutor(new Cmd());

        // INTRO
        Bukkit.getLogger().info("MoItems 已启动");
    }

    @Override
    public void onDisable() {

    }
}
