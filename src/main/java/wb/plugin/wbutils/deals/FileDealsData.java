package wb.plugin.wbutils.deals;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class FileDealsData {

    private static File file;
    private static FileConfiguration dataFile;

    /**
     * Do something.
     */
    public static void setup() {
        file = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("wbUtils")).getDataFolder(), "dealsdata.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ignored) { }
        }
        dataFile = YamlConfiguration.loadConfiguration(file);
    }

    /**
     * Do something.
     * @return something
     */
    public static FileConfiguration get() {
        return dataFile;
    }

    public static void save() {
        try {
            dataFile.save(file);
        } catch (IOException e) {
            System.out.println("Couldn't save deals data file");
        }
    }

    public static void reload() {
        dataFile = YamlConfiguration.loadConfiguration(file);
    }

}
