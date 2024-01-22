package wb.plugin.wbutils.entities;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public enum PickaxeType {

    STONE("Каменная", (byte) 2, (byte) 25, (byte) 1),
    IRON("Железная", (byte) 3, (byte) 38, (byte) 1),
    GOLD("Золотая", (byte) 4, (byte) 50, (byte) 2);

    private static final ConcurrentMap<String, PickaxeType> BY_DISPLAY_NAME = new ConcurrentHashMap<>();

    static {
        for (final PickaxeType type : values()) {
            BY_DISPLAY_NAME.put(type.typeName, type);
        }
    }

    @NotNull
    private final String typeName;
    private final byte miningStrength;
    private final byte digChance;
    private final byte mineQuantity;

    PickaxeType(final @NotNull String typeName, final byte miningStrength, final byte digChance,
                final byte mineQuantity) {
        this.typeName = typeName;
        this.miningStrength = miningStrength;
        this.digChance = digChance;
        this.mineQuantity = mineQuantity;
    }

    public static @NotNull Optional<PickaxeType> fromTypeName(final @NotNull String findName) {
        return Optional.ofNullable(BY_DISPLAY_NAME.get(findName));
    }

    public @NotNull String getTypeName() {
        return typeName;
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
