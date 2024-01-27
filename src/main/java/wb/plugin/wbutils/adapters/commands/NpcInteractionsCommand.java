package wb.plugin.wbutils.adapters.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.common.value.qual.IntRange;

import java.util.concurrent.ThreadLocalRandom;

public class NpcInteractionsCommand implements CommandExecutor {

    private static final String PERMISSION_PREFIX = "wb.talkpresent.";
    private static final String EFFECT_SLOWNESS = "minecraft:slowness";
    private final @NonNull JavaPlugin plugin;

    public NpcInteractionsCommand(final @NonNull JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(final @NonNull CommandSender sender, final @NonNull Command command, final @NonNull String label, final @NonNull String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        } else if (args.length != 1) {
            return true;
        }

        final @NonNull String merchant = args[0];
        switch (merchant) {
            case "merchantwood_tkg95mdxa0" -> handleMerchantWood(player);
            case "merchantpickaxe_zxj45gns09" -> handleMerchantPickaxe(player);
            case "seakippar_fl6pqa1bcz" -> handleSeakipparIdle(player);
            case "seakippar_ht3bko421z" -> handleSeakipparEscape(player);
            case "seakippar_lg0o561dh7" -> handleSeaKipparInTrip(player);
            default -> {
                return true;
            }
        }
        return true;
    }

    private void handleMerchantWood(final @NonNull Player player) {
        String permission = "wb.talkpresent.4";
        final String playerName = player.getName();
        if (player.hasPermission(permission)) {
            openMerchant(playerName, "merchant_forest");
        } else {
            grantPermission(playerName, permission);
            applyEffect(playerName, EFFECT_SLOWNESS, 14, 3);
            final @NonNull String npcName = "Купец: ";
            sendMessage(player, ChatColor.DARK_GREEN + npcName + ChatColor.GREEN + "Чего ты пришёл? Не видно, что я не могу сейчас говорить?");
            playSound(player, Sound.ENTITY_EVOKER_HURT, 1, 1);
            new BukkitRunnable() {
                @Override
                public void run() {
                    sendMessage(player, ChatColor.DARK_GREEN + npcName + ChatColor.GREEN + "Грузим сейчас дерево на корабль для отпраления на новый континент, о котором еще никто не слышал.");
                    playSound(player, Sound.ENTITY_EVOKER_HURT, 1, 1);
                }
            }.runTaskLater(plugin, 60);
            new BukkitRunnable() {
                @Override
                public void run() {
                    sendMessage(player, ChatColor.DARK_GREEN + npcName + ChatColor.GREEN + "Да, если у тебя будет лишняя деревяшка - приноси мне сразу. Куплю по хорошей цене!");
                    playSound(player, Sound.ENTITY_EVOKER_HURT, 1, 1);
                }
            }.runTaskLater(plugin, 120);
            new BukkitRunnable() {
                @Override
                public void run() {
                    sendMessage(player, ChatColor.DARK_GREEN + npcName + ChatColor.GREEN + "Нравишься ты мне. Молчишь много. На вот, возьми от меня небольшой подарок...");
                    playSound(player, Sound.ENTITY_EVOKER_HURT, 1, 1);
                }
            }.runTaskLater(plugin, 180);
            new BukkitRunnable() {
                @Override
                public void run() {
                    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                    Bukkit.dispatchCommand(console, "si give crate_wooden 1 " + playerName);
                    Bukkit.dispatchCommand(console, "dm open merchant_forest " + playerName);
                }
            }.runTaskLater(plugin, 250);
        }
    }

    private void handleMerchantPickaxe(final @NonNull Player player) {
        final String permission = "wb.talkpresent.6";
        final String playerName = player.getName();
        if (player.hasPermission(permission)) {
            openMerchant(playerName, "merchant_tool_mineshaft");
        } else {
            grantPermission(playerName, permission);
            applyEffect(playerName, EFFECT_SLOWNESS, 14, 3);
            sendMessage(player, ChatColor.DARK_GREEN + "Главный шахтёр: " + ChatColor.GREEN + "В здравѥ естѥ, не видѣти тобє тутєм раншэ. Полагаю ты будетъ дѣлати для мнѥ работу что я даяти тобє..");
            playSound(player, Sound.ENTITY_EVOKER_HURT, 1, 1);
            // ---
            player.playSound(player.getLocation(), Sound.ENTITY_EVOKER_HURT, 1, 1);
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.playSound(player.getLocation(), Sound.ENTITY_EVOKER_HURT, 1, 1);
                }
            }.runTaskLater(plugin, 25);
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.sendMessage(ChatColor.WHITE + playerName + ": " + ChatColor.GRAY + "Амм, ну я не очень понимаю ваш язык, извините.");
                }
            }.runTaskLater(plugin, 145);
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.sendMessage(ChatColor.WHITE + playerName + ": " + ChatColor.GRAY + "Мне кирка нужна. Самая простая. Поможете?");
                }
            }.runTaskLater(plugin, 180);
            new BukkitRunnable() {
                @Override
                public void run() {
                    player.sendMessage(ChatColor.DARK_GREEN + "Главный шахтёр: " + ChatColor.GREEN + "Добрє.");
                    player.playSound(player.getLocation(), Sound.ENTITY_EVOKER_HURT, 1, 1);
                }
            }.runTaskLater(plugin, 220);
            new BukkitRunnable() {
                @Override
                public void run() {
                    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                    Bukkit.dispatchCommand(console, "si give crate_wooden 1 " + playerName);
                    Bukkit.dispatchCommand(console, "dm open merchant_tool_mineshaft " + playerName);
                }
            }.runTaskLater(plugin, 245);
        }
    }

    /**
     * Talk to the captain of the ship before the trip.
     */
    private void handleSeakipparIdle(final @NonNull Player player) {
        final String playerName = player.getName();
        if (player.hasPermission(PERMISSION_PREFIX + "wb.talkpresent.5")) {
            openMerchant(playerName, "seaskipper");
        } else {
            grantPermission(playerName, "wb.talkpresent.5");
            applyEffect(playerName, EFFECT_SLOWNESS, 14, 3);
            final @NonNull String npcName = "Капитан: ";
            sendMessage(player, ChatColor.DARK_GREEN + npcName + ChatColor.GREEN + "Привет дружище! Привет мой друг! Рад тебя видеть, хоть и не видел тебя раньше.");
            // ---
            playSound(player, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            new BukkitRunnable() {
                @Override
                public void run() {
                    sendMessage(player, ChatColor.DARK_GREEN + npcName + ChatColor.GREEN + "Ты знал что тебе крупно повезло меня встретить?");
                    playSound(player, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
                }
            }.runTaskLater(plugin, 65);
            new BukkitRunnable() {
                @Override
                public void run() {
                    sendMessage(player, ChatColor.WHITE + playerName + ": " + ChatColor.GRAY + "Ну и с чем мне повезло-то?");
                }
            }.runTaskLater(plugin, 100);
            new BukkitRunnable() {
                @Override
                public void run() {
                    sendMessage(player, ChatColor.DARK_GREEN + npcName + ChatColor.GREEN + "Я могу довезти тебя в любую точку света! На незиведанные континеты, в тёплые страны на белые пески, либо же к Даннам и Норвегам на север...");
                    playSound(player, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
                }
            }.runTaskLater(plugin, 135);
            new BukkitRunnable() {
                @Override
                public void run() {
                    playSound(player, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
                }
            }.runTaskLater(plugin, 145);
            new BukkitRunnable() {
                @Override
                public void run() {
                    sendMessage(player, ChatColor.DARK_GREEN + npcName + ChatColor.GREEN + "Ты бы видел их воительниц!");
                    playSound(player, Sound.ENTITY_VILLAGER_TRADE, 1, 1);
                }
            }.runTaskLater(plugin, 185);
            new BukkitRunnable() {
                @Override
                public void run() {
                    sendMessage(player, ChatColor.DARK_GREEN + npcName + ChatColor.GREEN + "Я бы покрутил штурвал их драккара...");
                    playSound(player, Sound.ENTITY_VILLAGER_TRADE, 1, 1);
                }
            }.runTaskLater(plugin, 215);
            new BukkitRunnable() {
                @Override
                public void run() {
                    sendMessage(player, ChatColor.DARK_GREEN + npcName + ChatColor.GREEN + "Забыл сказать, бесплатно я тебя не повезу.");
                    playSound(player, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
                }
            }.runTaskLater(plugin, 245);
            new BukkitRunnable() {
                @Override
                public void run() {
                    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                    Bukkit.dispatchCommand(console, "si give crate_wooden 1 " + playerName);
                    Bukkit.dispatchCommand(console, "dm open seaskipper " + playerName);
                }
            }.runTaskLater(plugin, 270);
        }
    }

    /**
     * Talk to the captain of the ship, if the trip is longer than expected.
     */
    private void handleSeakipparEscape(final @NonNull Player player) {
        if (!player.hasPermission("wb.tripescape")) {
            return;
        }

        final String playerName = player.getName();
        sendMessage(player, ChatColor.WHITE + playerName + ": " + ChatColor.GRAY + "Мы уже долго плывём, ты вообще знаешь куда нужно плыть?");
        final @NonNull String npcName = "Капитан: ";
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(player, ChatColor.DARK_GREEN + npcName + ChatColor.GREEN + "Этот маршрут...");
                playSound(player, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 50);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(player, ChatColor.DARK_GREEN + npcName + ChatColor.GREEN + "Мне страшно по нему плыть, давай лучше развернёмся?");
                playSound(player, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 70);
        new BukkitRunnable() {
            @Override
            public void run() {
                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                Bukkit.dispatchCommand(console, "dm open seaskipper_escape " + playerName);
            }
        }.runTaskLater(plugin, 115);
    }

    /**
     * Talk to the captain of the ship during the trip.
     */
    private void handleSeaKipparInTrip(final @NonNull Player player) {
        final ThreadLocalRandom random = ThreadLocalRandom.current();
        int generatedNumber = random.nextInt(0, 7);
        switch (generatedNumber) {
            case 0 -> executeSeakipparInTrip0(player);
            case 1 -> executeSeakipparInTrip1(player);
            case 2 -> executeSeakipparInTrip2(player);
            case 3 -> executeSeakipparInTrip3(player);
            case 4 -> executeSeakipparInTrip4(player);
            case 5 -> executeSeakipparInTrip5(player);
            case 6 -> executeSeakipparInTrip6(player);
            default -> throw new IllegalStateException("Unexpected value: " + generatedNumber);
        }
    }

    private void executeSeakipparInTrip0(final @NonNull Player player) {
        final @NonNull String npcName = "Капитан: ";
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(player, ChatColor.DARK_GREEN + npcName + ChatColor.GREEN + "Да, было дело, я уже плавал этим маршрутом...");
                playSound(player, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 20);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(player, ChatColor.DARK_GREEN + npcName + ChatColor.GREEN + "Потерял половину корабля на мелководье, кучу шелков, металлов и драгоценных снадобий с самого востока.");
                playSound(player, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 110);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(player, ChatColor.DARK_GREEN + npcName + ChatColor.GREEN + "Теперь я не повторю ошибок прошлого, и буду плыть на большей скорости! ПОДНЯТЬ ПАРУСА!");
                playSound(player, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 210);
    }

    private void executeSeakipparInTrip1(final @NonNull Player player) {
        final @NonNull String npcName = "Капитан: ";
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(player, ChatColor.DARK_GREEN + npcName + ChatColor.GREEN + "Первый раз плыву в это место, буду честен.");
                playSound(player, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 20);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(player, ChatColor.DARK_GREEN + npcName + ChatColor.GREEN + "Не то чтобы я боюсь туда плыть, но запасов едва хватит прокормить меня и мою семью!");
                playSound(player, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 100);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(player, ChatColor.DARK_GREEN + npcName + ChatColor.GREEN + "И не надо мне говорить что у меня нет семьи! Меня это нервирует...");
                playSound(player, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 180);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(player, ChatColor.WHITE + player.getName() + ": " + ChatColor.GRAY + "Всё как всегда, без малейших изменений.");
            }
        }.runTaskLater(plugin, 260);
    }

    private void executeSeakipparInTrip2(final @NonNull Player player) {
        final @NonNull String npcName = "Капитан: ";
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(player, ChatColor.DARK_GREEN + npcName + ChatColor.GREEN + "Сегодня мы отправляемся в самое невероятное путешествие, которое когда-либо видел этот бренный мир.");
                playSound(player, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 20);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(player, ChatColor.DARK_GREEN + npcName + ChatColor.GREEN + "Мои торговые пути принесут мне несметные богатства, которые и не снились моему прадеду.");
                playSound(player, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 100);
    }

    private void executeSeakipparInTrip3(final @NonNull Player player) {
        final @NonNull String npcName = "Капитан: ";
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(player, ChatColor.DARK_GREEN + npcName + ChatColor.GREEN + "Смотри как на палубе стало чисто, теперь корабль как новый! Несмотря на то что он уже крушений пять пережил. Хех... ");
                playSound(player, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 20);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(player, ChatColor.DARK_GREEN + npcName + ChatColor.GREEN + "Ого! А это что за недекларированный груз лежит? Еще и чистоту портит!");
                playSound(player, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 160);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(player, ChatColor.DARK_GREEN + npcName + ChatColor.GREEN + "Вот, это для тебя подарок будет! И не смей отказываться.");
                playSound(player, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 280);
        new BukkitRunnable() {
            @Override
            public void run() {
                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                Bukkit.dispatchCommand(console, "si give misc_stone_dirty 1 " + player.getName());
            }
        }.runTaskLater(plugin, 340);
    }

    private void executeSeakipparInTrip4(final @NonNull Player player) {
        final @NonNull String npcName = "Капитан: ";
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(player, ChatColor.DARK_GREEN + npcName + ChatColor.GREEN + "Прошлое путешествие совсем кошмарное вышло, я до сих пор морально опустошён и духовно выжат.");
                playSound(player, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 20);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(player, ChatColor.WHITE + player.getName() + ": " + ChatColor.GRAY + "Что в тот раз произошло?");
            }
        }.runTaskLater(plugin, 95);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(player, ChatColor.DARK_GREEN + npcName + ChatColor.GREEN + "У меня украли любимый алмазный топор.");
                playSound(player, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 135);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(player, ChatColor.WHITE + player.getName() + ": " + ChatColor.GRAY + "Откуда у тебя алмазный топор?");
            }
        }.runTaskLater(plugin, 175);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(player, ChatColor.DARK_GREEN + npcName + ChatColor.GREEN + "Отдолжил у незнакомой варяжки на неопределенный срок...");
                playSound(player, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 210);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(player, ChatColor.WHITE + player.getName() + ": " + ChatColor.GRAY + "Боги тебя не простят, ни старые, ни новые!");
            }
        }.runTaskLater(plugin, 270);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(player, ChatColor.DARK_GREEN + npcName + ChatColor.GREEN + "Ой, только давай без нравоучений, без того тошно.");
                playSound(player, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 320);
    }

    private void executeSeakipparInTrip5(final @NonNull Player player) {
        final @NonNull String npcName = "Капитан: ";
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(player, ChatColor.DARK_GREEN + npcName + ChatColor.GREEN + "*читает записку*");
            }
        }.runTaskLater(plugin, 20);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(player, ChatColor.DARK_GREEN + npcName + ChatColor.GREEN + "Мгм, мгм.. Аммм. Еб...");
                playSound(player, Sound.ENTITY_VILLAGER_AMBIENT, 1, 2);
            }
        }.runTaskLater(plugin, 55);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(player, ChatColor.WHITE + player.getName() + ": " + ChatColor.GRAY + "НУ НУ НУ! Что тебя так взволновало?");
            }
        }.runTaskLater(plugin, 70);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(player, ChatColor.DARK_GREEN + npcName + ChatColor.GREEN + "Ты знал кто такие мужеложцы?");
                playSound(player, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 120);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(player, ChatColor.WHITE + player.getName() + ": " + ChatColor.GRAY + "Не озадачивался таким вопросом. Просвятишь?");
            }
        }.runTaskLater(plugin, 155);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(player, ChatColor.DARK_GREEN + npcName + ChatColor.GREEN + "Давай ты сам узнаешь, а то нам еще вместе плыть.");
                playSound(player, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 185);
    }

    private void executeSeakipparInTrip6(final @NonNull Player player) {
        final @NonNull String npcName = "Капитан: ";
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(player, ChatColor.DARK_GREEN + npcName + ChatColor.GREEN + "Все только и говорят о новом континенте который мир не видывал. Интересно, какой он? Там есть женщины? Потому что иначе делать там мне нечего.");
                playSound(player, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 20);
        new BukkitRunnable() {
            @Override
            public void run() {
                playSound(player, Sound.ENTITY_VILLAGER_YES, 1, 1);

            }
        }.runTaskLater(plugin, 50);
        new BukkitRunnable() {
            @Override
            public void run() {
                playSound(player, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 75);
        new BukkitRunnable() {
            @Override
            public void run() {
                playSound(player, Sound.ENTITY_VILLAGER_NO, 1, 1);
            }
        }.runTaskLater(plugin, 105);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(player, ChatColor.WHITE + player.getName() + ": " + ChatColor.GRAY + "А как же увидеть новый мир? Остепениться там? Начать жизнь с чистого листа в новом поселении?");
            }
        }.runTaskLater(plugin, 200);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(player, ChatColor.DARK_GREEN + npcName + ChatColor.GREEN + "Тебе оно самому надо это всё? Вопрос риторический.");
                playSound(player, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 320);
    }

    private void grantPermission(final @NonNull String playerName, final @NonNull String permission) {
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        Bukkit.dispatchCommand(console, "lp user " + playerName + " permission set " + permission);
    }

    private void applyEffect(final @NonNull String playerName, final @NonNull String effect,
                             final @IntRange(from = 0, to = 1000000) int duration,
                             final @IntRange(from = 0, to = 255) int amplifier) {
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        Bukkit.dispatchCommand(console, "effect give " + playerName + ' ' + effect + ' ' + duration + ' ' + amplifier + " true");
    }

    private void sendMessage(final @NonNull Player player, @NonNull String message) {
        player.sendMessage(message);
    }

    private void playSound(final @NonNull Player player, @NonNull Sound sound, @NonNegative float volume,
                           @NonNegative float pitch) {
        final Location playerLocation = player.getLocation();
        player.playSound(playerLocation, sound, volume, pitch);
    }

    private void openMerchant(final @NonNull String playerName, final @NonNull String merchant) {
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        Bukkit.dispatchCommand(console, "dm open " + merchant + ' ' + playerName);
    }
}
