package wb.plugin.wbutils.adapters.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

public class DoSpecialAction2Command implements CommandExecutor {

    private static final String PERMISSION_PREFIX = "wb.talkpresent.";
    private static final String EFFECT_SLOWNESS = "minecraft:slowness";
    private static final String SOUND_EVOKER_HURT = "ENTITY_EVOKER_HURT";
    private final JavaPlugin plugin;

    public DoSpecialAction2Command(final JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(final @NotNull CommandSender sender, final @NotNull Command command, final @NotNull String label, final @NotNull String[] args) {
        if (!(sender instanceof Player) || args.length != 1) {
            return true;
        }

        switch (args[0]) {
            case "merchantwood_tkg95mdxa0" -> handleMerchantWood(sender);
            case "merchantpickaxe_zxj45gns09" -> handleMerchantPickaxe(sender);
            case "seakippar_fl6pqa1bcz" -> handleSeaKippar(sender);
            case "seakippar_ht3bko421z" -> handleSeaKipparHt(sender);
            case "seakippar_lg0o561dh7" -> handleSeaKipparLg(sender);
            default -> { return true; }
        }
        return true;
    }

    private void handleMerchantWood(CommandSender sender) {
        String permission = "wb.talkpresent.4";
        if (sender.hasPermission(permission)) {
            openMerchant(sender, "merchant_forest");
        } else {
            grantPermission(sender, permission);
            applyEffect(sender, EFFECT_SLOWNESS, 14, 3);
            sendMessage(sender, ChatColor.DARK_GREEN + "Купец: " + ChatColor.GREEN + "Чего ты пришёл? Не видно, что я не могу сейчас говорить?");
            playSound(sender, Sound.ENTITY_EVOKER_HURT, 1, 1);
            // ---
            new BukkitRunnable() {
                @Override
                public void run() {
                    sendMessage(sender, ChatColor.DARK_GREEN + "Купец: " + ChatColor.GREEN + "Грузим сейчас дерево на корабль для отпраления на новый континент, о котором еще никто не слышал.");
                    playSound(sender, Sound.ENTITY_EVOKER_HURT, 1, 1);
                }
            }.runTaskLater(plugin, 60);
            new BukkitRunnable() {
                @Override
                public void run() {
                    sendMessage(sender, ChatColor.DARK_GREEN + "Купец: " + ChatColor.GREEN + "Да, если у тебя будет лишняя деревяшка - приноси мне сразу. Куплю по хорошей цене!");
                    playSound(sender, Sound.ENTITY_EVOKER_HURT, 1, 1);
                }
            }.runTaskLater(plugin, 120);
            new BukkitRunnable() {
                @Override
                public void run() {
                    sendMessage(sender, ChatColor.DARK_GREEN + "Купец: " + ChatColor.GREEN + "Нравишься ты мне. Молчишь много. На вот, возьми от меня небольшой подарок...");
                    playSound(sender, Sound.ENTITY_EVOKER_HURT, 1, 1);
                }
            }.runTaskLater(plugin, 180);
            new BukkitRunnable() {
                @Override
                public void run() {
                    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                    Bukkit.dispatchCommand(console, "si give crate_wooden 1 " + sender.getName() );
                    Bukkit.dispatchCommand(console, "dm open merchant_forest " + sender.getName());
                }
            }.runTaskLater(plugin, 250);
        }
    }

    private void handleMerchantPickaxe(CommandSender sender) {
        final String permission = "wb.talkpresent.6";
        if (sender.hasPermission(permission)) {
            openMerchant(sender, "merchant_tool_mineshaft");
        } else {
            grantPermission(sender, permission);
            applyEffect(sender, EFFECT_SLOWNESS, 14, 3);
            sendMessage(sender, ChatColor.DARK_GREEN + "Главный шахтёр: " + ChatColor.GREEN + "В здравѥ естѥ, не видѣти тобє тутєм раншэ. Полагаю ты будетъ дѣлати для мнѥ работу что я даяти тобє..");
            playSound(sender, Sound.ENTITY_EVOKER_HURT, 1, 1);
            // ---
            Player player = (Player) sender;
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
                    sender.sendMessage(ChatColor.WHITE + sender.getName() + ": " + ChatColor.GRAY + "Амм, ну я не очень понимаю ваш язык, извините.");
                }
            }.runTaskLater(plugin, 145);
            new BukkitRunnable() {
                @Override
                public void run() {
                    sender.sendMessage(ChatColor.WHITE + sender.getName() + ": " + ChatColor.GRAY + "Мне кирка нужна. Самая простая. Поможете?");
                }
            }.runTaskLater(plugin, 180);
            new BukkitRunnable() {
                @Override
                public void run() {
                    sender.sendMessage(ChatColor.DARK_GREEN + "Главный шахтёр: " + ChatColor.GREEN + "Добрє.");
                    player.playSound(player.getLocation(), Sound.ENTITY_EVOKER_HURT, 1, 1);
                }
            }.runTaskLater(plugin, 220);
            new BukkitRunnable() {
                @Override
                public void run() {
                    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                    Bukkit.dispatchCommand(console, "si give crate_wooden 1 " + player.getName() );
                    Bukkit.dispatchCommand(console, "dm open merchant_tool_mineshaft " + sender.getName());
                }
            }.runTaskLater(plugin, 245);
        }
    }

    private void handleSeaKippar(CommandSender sender) {
        if (sender.hasPermission(PERMISSION_PREFIX + "wb.talkpresent.5")) {
            openMerchant(sender, "seaskipper");
        } else {
            grantPermission(sender, "wb.talkpresent.5");
            applyEffect(sender, EFFECT_SLOWNESS, 14, 3);
            sendMessage(sender, ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Привет дружище! Привет мой друг! Рад тебя видеть, хоть и не видел тебя раньше.");
            // ---
            Player player = (Player) sender;
            playSound(player, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            new BukkitRunnable() {
                @Override
                public void run() {
                    sendMessage(sender, ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Ты знал что тебе крупно повезло меня встретить?");
                    playSound(player, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
                }
            }.runTaskLater(plugin, 65);
            new BukkitRunnable() {
                @Override
                public void run() {
                    sendMessage(sender, ChatColor.WHITE + sender.getName() + ": " + ChatColor.GRAY + "Ну и с чем мне повезло-то?");
                }
            }.runTaskLater(plugin, 100);
            new BukkitRunnable() {
                @Override
                public void run() {
                    sendMessage(sender, ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Я могу довезти тебя в любую точку света! На незиведанные континеты, в тёплые страны на белые пески, либо же к Даннам и Норвегам на север...");
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
                    sendMessage(sender, ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Ты бы видел их воительниц!");
                    playSound(player, Sound.ENTITY_VILLAGER_TRADE, 1, 1);
                }
            }.runTaskLater(plugin, 185);
            new BukkitRunnable() {
                @Override
                public void run() {
                    sendMessage(sender, ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Я бы покрутил штурвал их драккара...");
                    playSound(player, Sound.ENTITY_VILLAGER_TRADE, 1, 1);
                }
            }.runTaskLater(plugin, 215);
            new BukkitRunnable() {
                @Override
                public void run() {
                    sendMessage(sender, ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Забыл сказать, бесплатно я тебя не повезу.");
                    playSound(player, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
                }
            }.runTaskLater(plugin, 245);
            new BukkitRunnable() {
                @Override
                public void run() {
                    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                    Bukkit.dispatchCommand(console, "si give crate_wooden 1 " + player.getName() );
                    Bukkit.dispatchCommand(console, "dm open seaskipper " + sender.getName());
                }
            }.runTaskLater(plugin, 270);
        }
    }

    private void handleSeaKipparHt(CommandSender sender) {
        if (!sender.hasPermission("wb.tripescape")) {
            return;
        }

        sendMessage(sender, ChatColor.WHITE + sender.getName() + ": " + ChatColor.GRAY + "Мы уже долго плывём, ты вообще знаешь куда нужно плыть?");
        Player player = (Player) sender;
        // ---
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(sender, ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Этот маршрут...");
                playSound(player, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 50);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(sender, ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Мне страшно по нему плыть, давай лучше развернёмся?");
                playSound(player, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 70);
        new BukkitRunnable() {
            @Override
            public void run() {
                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                Bukkit.dispatchCommand(console, "dm open seaskipper_escape " + sender.getName());
            }
        }.runTaskLater(plugin, 115);
    }

    private void handleSeaKipparLg(CommandSender sender) {
        final ThreadLocalRandom random = ThreadLocalRandom.current();
        Player player = (Player) sender;
        int generatedNumber = random.nextInt(0, 7);
        switch (generatedNumber) {
            case 0 -> executeSeaKipparLg0(sender, player);
            case 1 -> executeSeaKipparLg1(sender, player);
            case 2 -> executeSeaKipparLg2(sender, player);
            case 3 -> executeSeaKipparLg3(sender, player);
            case 4 -> executeSeaKipparLg4(sender, player);
            case 5 -> executeSeaKipparLg5(sender, player);
            case 6 -> executeSeaKipparLg6(sender, player);
            default -> throw new IllegalStateException("Unexpected value: " + generatedNumber);
        }

        openMerchant(sender, "merchant_forest");
    }

    private void executeSeaKipparLg0(CommandSender sender, Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(sender, ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Да, было дело, я уже плавал этим маршрутом...");
                playSound(sender, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 20);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(sender, ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Потерял половину корабля на мелководье, кучу шелков, металлов и драгоценных снадобий с самого востока.");
                playSound(sender, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 110);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(sender, ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Теперь я не повторю ошибок прошлого, и буду плыть на большей скорости! ПОДНЯТЬ ПАРУСА!");
                playSound(sender, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 210);
    }

    private void executeSeaKipparLg1(CommandSender sender, Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(sender, ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Первый раз плыву в это место, буду честен.");
                playSound(sender, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 20);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(sender, ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Не то чтобы я боюсь туда плыть, но запасов едва хватит прокормить меня и мою семью!");
                playSound(sender, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 100);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(sender, ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "И не надо мне говорить что у меня нет семьи! Меня это нервирует...");
                playSound(sender, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 180);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(sender, ChatColor.WHITE + sender.getName() + ": " + ChatColor.GRAY + "Всё как всегда, без малейших изменений.");
            }
        }.runTaskLater(plugin, 260);
    }

    private void executeSeaKipparLg2(CommandSender sender, Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(sender, ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Сегодня мы отправляемся в самое невероятное путешествие, которое когда-либо видел этот бренный мир.");
                playSound(sender, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 20);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(sender, ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Мои торговые пути принесут мне несметные богатства, которые и не снились моему прадеду.");
                playSound(sender, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 100);
    }

    private void executeSeaKipparLg3(CommandSender sender, Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(sender, ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Смотри как на палубе стало чисто, теперь корабль как новый! Несмотря на то что он уже крушений пять пережил. Хех... ");
                playSound(sender, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 20);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(sender, ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Ого! А это что за недекларированный груз лежит? Еще и чистоту портит!");
                playSound(sender, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 160);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(sender, ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Вот, это для тебя подарок будет! И не смей отказываться.");
                playSound(sender, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 280);
        new BukkitRunnable() {
            @Override
            public void run() {
                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                Bukkit.dispatchCommand(console, "si give misc_stone_dirty 1 " + sender.getName());
            }
        }.runTaskLater(plugin, 340);
    }

    private void executeSeaKipparLg4(CommandSender sender, Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(sender, ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Прошлое путешествие совсем кошмарное вышло, я до сих пор морально опустошён и духовно выжат.");
                playSound(sender, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 20);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(sender, ChatColor.WHITE + sender.getName() + ": " + ChatColor.GRAY + "Что в тот раз произошло?");
            }
        }.runTaskLater(plugin, 95);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(sender, ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "У меня украли любимый алмазный топор.");
                playSound(sender, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 135);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(sender, ChatColor.WHITE + sender.getName() + ": " + ChatColor.GRAY + "Откуда у тебя алмазный топор?");
            }
        }.runTaskLater(plugin, 175);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(sender, ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Отдолжил у незнакомой варяжки на неопределенный срок...");
                playSound(sender, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 210);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(sender, ChatColor.WHITE + sender.getName() + ": " + ChatColor.GRAY + "Боги тебя не простят, ни старые, ни новые!");
            }
        }.runTaskLater(plugin, 270);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(sender, ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Ой, только давай без нравоучений, без того тошно.");
                playSound(sender, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 320);
    }

    private void executeSeaKipparLg5(CommandSender sender, Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(sender, ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "*читает записку*");
            }
        }.runTaskLater(plugin, 20);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(sender, ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Мгм, мгм.. Аммм. Еб...");
                playSound(sender, Sound.ENTITY_VILLAGER_AMBIENT, 1, 2);
            }
        }.runTaskLater(plugin, 55);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(sender, ChatColor.WHITE + sender.getName() + ": " + ChatColor.GRAY + "НУ НУ НУ! Что тебя так взволновало?");
            }
        }.runTaskLater(plugin, 70);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(sender, ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Ты знал кто такие мужеложцы?");
                playSound(sender, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 120);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(sender, ChatColor.WHITE + sender.getName() + ": " + ChatColor.GRAY + "Не озадачивался таким вопросом. Просвятишь?");
            }
        }.runTaskLater(plugin, 155);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(sender, ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Давай ты сам узнаешь, а то нам еще вместе плыть.");
                playSound(sender, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 185);
    }

    private void executeSeaKipparLg6(CommandSender sender, Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(sender, ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Все только и говорят о новом континенте который мир не видывал. Интересно, какой он? Там есть женщины? Потому что иначе делать там мне нечего.");
                playSound(sender, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 20);
        new BukkitRunnable() {
            @Override
            public void run() {
                playSound(sender, Sound.ENTITY_VILLAGER_YES, 1, 1);

            }
        }.runTaskLater(plugin, 50);
        new BukkitRunnable() {
            @Override
            public void run() {
                playSound(sender, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 75);
        new BukkitRunnable() {
            @Override
            public void run() {
                playSound(sender, Sound.ENTITY_VILLAGER_NO, 1, 1);
            }
        }.runTaskLater(plugin, 105);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(sender, ChatColor.WHITE + sender.getName() + ": " + ChatColor.GRAY + "А как же увидеть новый мир? Остепениться там? Начать жизнь с чистого листа в новом поселении?");
            }
        }.runTaskLater(plugin, 200);
        new BukkitRunnable() {
            @Override
            public void run() {
                sendMessage(sender, ChatColor.DARK_GREEN + "Капитан: " + ChatColor.GREEN + "Тебе оно самому надо это всё? Вопрос риторический.");
                playSound(sender, Sound.ENTITY_VILLAGER_AMBIENT, 1, 1);
            }
        }.runTaskLater(plugin, 320);
    }

    private void grantPermission(CommandSender sender, String permission) {
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        String senderName = sender.getName();
        Bukkit.dispatchCommand(console, "lp user " + senderName + " permission set " + permission);
    }

    private void applyEffect(CommandSender sender, String effect, int duration, int amplifier) {
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        String senderName = sender.getName();
        Bukkit.dispatchCommand(console, "effect give " + senderName + ' ' + effect + ' ' + duration + ' ' + amplifier + " true");
    }

    private void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(message);
    }

    private void playSound(CommandSender sender, Sound sound, float volume, float pitch) {
        Player player = (Player) sender;
        player.playSound(player.getLocation(), sound, volume, pitch);
    }

    private void openMerchant(CommandSender sender, String merchant) {
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        String senderName = sender.getName();
        Bukkit.dispatchCommand(console, "dm open " + merchant + ' ' + senderName);
    }
}
