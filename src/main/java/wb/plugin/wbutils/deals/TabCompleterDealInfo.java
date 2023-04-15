package wb.plugin.wbutils.deals;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TabCompleterDealInfo implements TabCompleter {

    /**
     * Do something.
     * @param sender  sender
     * @param command command
     * @param alias   alias
     * @param args    args
     * @return some List
     */
    @Override
    public @Nullable List<String> onTabComplete(final @NotNull CommandSender sender, final @NotNull Command command, final @NotNull String alias, final @NotNull String[] args) {
        List<String> commands = new ArrayList<>();
        switch (args.length) {
            case 1 -> {
                List<String> tabs = new ArrayList<>();
                for (int i = 1; i <= DatabaseDeals.getDealsQuantity(); i++){
                    tabs.add(Integer.toString(i));
                }
                for (String s : tabs) {
                    if (s.toLowerCase().startsWith(args[0].toLowerCase())) {
                        commands.add(s);
                    }
                }
                return commands;
            }
            case 2 -> {
                List<String> tabs = new ArrayList<>();
                tabs.add("owner");
                tabs.add("materials");
                tabs.add("coins_gold");
                tabs.add("coins_silver");
                tabs.add("coins_copper");
                for (String s : tabs) {
                    if (s.toLowerCase().startsWith(args[1].toLowerCase())) {
                        commands.add(s);
                    }
                }
                return commands;
            }
            case 3 -> {
                return commands;
            }
        }
        return null;
    }

}
