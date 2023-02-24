package wb.plugin.wbutils.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import wb.plugin.wbutils.wbUtils;

public class SetSpawn implements CommandExecutor {

    private final wbUtils plugin;

    public SetSpawn(wbUtils plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p){
            Location location = p.getLocation();

        }
        return false;
    }
}
