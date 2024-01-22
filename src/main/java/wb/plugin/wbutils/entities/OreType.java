package wb.plugin.wbutils.entities;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public enum OreType {

    COPPER("copper_dl6mghd049", "copper", (byte) 2, (byte) 2),
    IRON("iron_gh5zdmgk4l", "iron", (byte) 3, (byte) 5),
    GOLD("gold_p1zfvb6jg7", "gold", (byte) 3, (byte) 10),
    REDSPICE("redspice_6g8bv31kju", "redspice", (byte) 4, (byte) 20);

    private static final ConcurrentMap<String, OreType> BY_ID = new ConcurrentHashMap<>();

    static {
        for (final OreType type : values()) {
            BY_ID.put(type.oreId, type);
        }
    }

    private final String oreId;
    private final String oreName;
    private final byte strength;
    private final byte exp;

    OreType(final String oreId, final String oreName, final byte strength, final byte exp) {
        this.oreId = oreId;
        this.oreName = oreName;
        this.strength = strength;
        this.exp = exp;
    }

    public static OreType fromOreId(final String findArg) {
        return BY_ID.get(findArg);
    }

    public String getOreId() {
        return oreId;
    }

    public String getOreName() {
        return oreName;
    }

    public byte getStrength() {
        return strength;
    }

    public byte getExp() {
        return exp;
    }
}
