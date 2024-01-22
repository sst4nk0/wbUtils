package wb.plugin.wbutils.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class SetSpawnCommand implements CommandExecutor {

    private final JavaPlugin plugin;

    /**
     * Initialize a constructor.
     */
    public SetSpawnCommand(final JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Do something.
     *
     * @param sender  sender
     * @param command command
     * @param label   label
     * @param args    args
     * @return some boolean
     */
    @Override
    public boolean onCommand(final @NotNull CommandSender sender, final @NotNull Command command,
                             final @NotNull String label, final @NotNull String[] args) {
        if (sender instanceof Player player) {
            Location location = player.getLocation();
        }
        return false;
    }

}
