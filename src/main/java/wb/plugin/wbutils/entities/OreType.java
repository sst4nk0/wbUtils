package wb.plugin.wbutils.entities;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;
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

    @NotNull
    private final String oreId;
    @NotNull
    private final String oreName;
    private final byte miningStrength;
    private final byte exp;

    OreType(final @NotNull String oreId, final @NotNull String oreName, final byte miningStrength, final byte exp) {
        this.oreId = oreId;
        this.oreName = oreName;
        this.miningStrength = miningStrength;
        this.exp = exp;
    }

    @NotNull
    public static Optional<OreType> fromOreId(final String oreId) {
        return Optional.ofNullable(BY_ID.get(oreId));
    }

    public @NotNull String getOreId() {
        return oreId;
    }

    public @NotNull String getOreName() {
        return oreName;
    }

    public byte getMiningStrength() {
        return miningStrength;
    }

    public byte getExp() {
        return exp;
    }
}
