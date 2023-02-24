package wb.plugin.wbutils.commands.system;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import wb.plugin.wbutils.wbUtils;

import java.util.Random;

public class DoSpecialAction2 implements CommandExecutor {

    private final wbUtils plugin;

    /**
     * Initialize a constructor.
     * @param inputPlugin inputPlugin
     */
    public DoSpecialAction2(final wbUtils inputPlugin) {
        this.plugin = inputPlugin;
    }

    /**
     * Do something.
     * @param sender  sender
     * @param command command
     * @param label   label
     * @param args    args
     * @return some boolean
     */
    @Override
    public boolean onCommand(final @NotNull CommandSender sender, final @NotNull Command command, final @NotNull String label, final @NotNull String[] args) {
        if (!(sender instanceof Player)) { return true; }
        if (args.length != 1) { return true; }

        switch (args[0]) {
            case "merchantwood_tkg95mdxa0" -> {
                if (!sender.hasPermission("wb.talkpresent.4")) {
                    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                    Bukkit.dispatchCommand(console, "lp user " + sender.getName() + " permission set wb.talkpresent.4");
                    Player p = (Player) sender;
                    Bukkit.dispatchCommand(console, "effect give " + sender.getName() + " minecraft:slowness 14 3 true");
                    sender.sendMessage(ChatColor.DARK_GREEN + "Купец: " + ChatColor.GREEN + "Чего ты пришёл? Не видно, что я не могу сейчас говорить?");
                    p.playSound(p.getLocation(), Sound.ENTITY_EVOKER_HURT, 1, 1);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            sender.sendMessage(ChatColor.DARK_GREEN + "Купец: " + ChatColor.GREEN + "Грузим сейчас дерево на корабль для отпраления на новый континент, о котором еще никто не слышал.");
                            p.playSound(p.getLocation(), Sound.ENTITY_EVOKER_HURT, 1, 1);
                        }
                    }.runTaskLater(plugin, 60);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            sender.sendMessage(ChatColor.DARK_GREEN + "Купец: " + ChatColor.GREEN + "Да, если у тебя будет лишняя деревяшка - приноси мне сразу. Куплю по хорошей цене!");
                            p.playSound(p.getLocation(), Sound.ENTITY_EVOKER_HURT, 1, 1);
                        }
                    }.runTaskLater(plugin, 120);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            sender.sendMessage(ChatColor.DARK_GREEN + "Купец: " + ChatColor.GREEN + "Нравишься ты мне. Молчишь много. На вот, возьми от меня небольшой подарок...");
                            p.playSound(p.getLocation(), Sound.ENTITY_EVOKER_HURT, 1, 1);
                        }
                    }.runTaskLater(plugin, 180);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Bukkit.dispatchCommand(console, "dm open merchant_forest " + sender.getName());
                        }
                    }.runTaskLater(plugin, 250);
                    return true;
                }
                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                Bukkit.dispatchCommand(console, "dm open merchant_forest " + sender.getName());
            }
            case "seakippar_fl6pqa1bcz" -> {
                if (!sender.hasPermission("wb.talkpresent.5")) {
                    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                    Bukkit.dispatchCommand(console, "lp user " + sender.getName() + " permission set wb.talkpresent.5");
                    Player player = (Player) sender;
                    Bukkit.dispatchCommand(console, "effect give " + sender.getName() + " minecraft:slowness 14 3 true");
                    sender.sendMessage(ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Привет дружище! Привет мой друг! Рад тебя видеть, хоть и не видел тебя раньше.");
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            sender.sendMessage(ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Ты знал что тебе крупно повезло меня встретить?");
                            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
                        }
                    }.runTaskLater(plugin, 65);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            sender.sendMessage(ChatColor.WHITE + sender.getName() + ": " + ChatColor.GRAY + "Ну и с чем мне повезло-то?");
                        }
                    }.runTaskLater(plugin, 100);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            sender.sendMessage(ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Я могу довезти тебя в любую точку света! На незиведанные континеты, в тёплые страны на белые пески, либо же к Даннам и Норвегам на север...");
                            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
                        }
                    }.runTaskLater(plugin, 135);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
                        }
                    }.runTaskLater(plugin, 145);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            sender.sendMessage(ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Ты бы видел их воительниц!");
                            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_TRADE, 1, 1);
                        }
                    }.runTaskLater(plugin, 180);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            sender.sendMessage(ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Я бы покрутил штурвал их драккара...");
                            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_TRADE, 1, 1);
                        }
                    }.runTaskLater(plugin, 205);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            sender.sendMessage(ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Забыл сказать, бесплатно я тебя не повезу.");
                            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
                        }
                    }.runTaskLater(plugin, 230);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Bukkit.dispatchCommand(console, "dm open seaskipper_0 " + sender.getName());
                        }
                    }.runTaskLater(plugin, 125);
                    return true;
                }
                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                Bukkit.dispatchCommand(console, "dm open sea_skipper " + sender.getName());
            }
            case "seakippar_lg0o561dh7" -> {
                Random random = new Random();
                Player player = (Player) sender;
                int generatedNumber = random.nextInt(0, 4);

                switch (generatedNumber) {
                    case 0 -> {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                sender.sendMessage(ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Да, было дело, я уже плавал этим маршрутом...");
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
                            }
                        }.runTaskLater(plugin, 20);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                sender.sendMessage(ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Потерял половину корабля на мелководье, кучу шелков, металлов и драгоценных снадобий с самого востока.");
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
                            }
                        }.runTaskLater(plugin, 110);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                sender.sendMessage(ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Теперь я не повторю ошибок прошлого, и буду плыть на большей скорости! ПОДНЯТЬ ПАРУСА!");
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
                            }
                        }.runTaskLater(plugin, 210);
                    }
                    case 1 -> {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                sender.sendMessage(ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Первый раз плыву в это место, буду честен.");
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
                            }
                        }.runTaskLater(plugin, 20);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                sender.sendMessage(ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Не то чтобы я боюсь туда плыть, но запасов едва хватит прокормить меня и мою семью!");
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
                            }
                        }.runTaskLater(plugin, 100);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                sender.sendMessage(ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "И не надо мне говорить что у меня нет семьи! Меня это нервирует...");
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
                            }
                        }.runTaskLater(plugin, 180);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                sender.sendMessage(ChatColor.WHITE + sender.getName() + ": " + ChatColor.GRAY + "Всё как всегда, без малейших изменений.");
                            }
                        }.runTaskLater(plugin, 260);
                    }
                    case 2 -> {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                sender.sendMessage(ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Сегодня мы отправляемся в самое невероятное путешествие, которое когда-либо видел этот бренный мир.");
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
                            }
                        }.runTaskLater(plugin, 20);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                sender.sendMessage(ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Мои торговые пути принесут мне несметные богатства, которые и не снились моему прадеду.");
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
                            }
                        }.runTaskLater(plugin, 100);
                    }
                    case 3 -> {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                sender.sendMessage(ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Смотри как на палубе стало чисто, теперь корабль как новый! Несмотря на то что он уже крушений пять пережил. Хех... ");
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
                            }
                        }.runTaskLater(plugin, 20);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                sender.sendMessage(ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Ого! А это что за недекларированный груз лежит? Еще и чистоту портит!");
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
                            }
                        }.runTaskLater(plugin, 160);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                sender.sendMessage(ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Вот, это для тебя подарок будет! И не смей отказываться.");
                                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
                            }
                        }.runTaskLater(plugin, 280);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                                Bukkit.dispatchCommand(console, "si give misc_stone_dirty 1 " + sender.getName());
                            }
                        }.runTaskLater(plugin, 350);
                    }
                }
            }
            default -> { return true; }
        }
        return true;
    }

}
