package co.nesb01t.moitems.cmd;

import co.nesb01t.moitems.file.FileController;
import co.nesb01t.moitems.api.ItemList;
import co.nesb01t.moitems.api.Msg;
import co.nesb01t.moitems.api.Recipe;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class Cmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) { return false; } // 不允许非玩家执行

        Player player = (Player) sender;
        if (args[0].equals("re")) { // 重载配置文件
            try {
                FileController.reloadConfig();
                player.sendMessage(Msg.HEADER + "重载完成");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (args[0].equals("ls")) { // 显示物品列表
            ItemList.showItemList(player);
        } else if (args[0].equals("get")) { // 获取武器
            if (ItemList.ItemIdList.contains(args[1])){
                ItemList.givePlayerWeapon(player, args[1]);
            } else {
                player.sendMessage("can't find id");
            }
        }

        return true;
    }
}
