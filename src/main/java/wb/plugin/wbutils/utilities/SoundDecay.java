package wb.plugin.wbutils.utilities;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SoundDecay extends BukkitRunnable {
    private final int range;
    private final Location music;
    private final Player player;
    private final String sound;


    public SoundDecay(Player player, Location music, String sound, int range) {
        this.player = player;
        this.music = music;
        this.range = range;
        this.sound = sound;

        run();
    }

    @Override
    public void run() {
        final Location playerLocation = player.getLocation();
        player.playSound(playerLocation, sound, convertForSound((float) playerLocation.distance(music), range), 1);
    }

    private float convertForSound(float x, int range) {
        return Math.max(0, 1 - (x / range));
    }

}