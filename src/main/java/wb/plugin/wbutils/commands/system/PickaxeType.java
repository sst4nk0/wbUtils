package wb.plugin.wbutils.commands.system;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public enum PickaxeType {

    STONE("Каменная", (byte) 2, (byte) 25, (byte) 1),
    IRON("Железная", (byte) 3, (byte) 38, (byte) 1),
    GOLD("Золотая", (byte) 4, (byte) 50, (byte) 2);

    private static final ConcurrentMap<String, PickaxeType> BY_DISPLAY_NAME = new ConcurrentHashMap<>();

    static {
        for (final PickaxeType type : values()) {
            BY_DISPLAY_NAME.put(type.displayName, type);
        }
    }

    private final String displayName;
    private final byte miningStrength;
    private final byte digChance;
    private final byte mineQuantity;

    PickaxeType(final String displayName, final byte miningStrength, final byte digChance, final byte mineQuantity) {
        this.displayName = displayName;
        this.miningStrength = miningStrength;
        this.digChance = digChance;
        this.mineQuantity = mineQuantity;
    }

    public static PickaxeType fromDisplayName(final String findName) {
        return BY_DISPLAY_NAME.get(findName);
    }

    public String getDisplayName() {
        return displayName;
    }

    public byte getMiningStrength() {
        return miningStrength;
    }

    public byte getLuckPercent() {
        return digChance;
    }

    public byte getMineQuantity() {
        return mineQuantity;
    }
}
