package co.nesb01t.moitems.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Cmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // 不允许非玩家执行
        if(!(sender instanceof Player)) { return false; }

        Player player = (Player) sender;
        player.sendMessage("hello man, you execute the command moitems, good luck");

        return true;
    }
}
