package wb.plugin.wbutils.commands.system;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public enum WoodType {

    OAK("oak_ehmyzz2lgq", "oak"),
    SPRUCE("spruce_yy974jutou", "spruce"),
    BIRCH("birch_t899c168nx", "birch");

    private static final ConcurrentMap<String, WoodType> BY_ID = new ConcurrentHashMap<>();

    static {
        for (final WoodType type : values()) {
            BY_ID.put(type.woodId, type);
        }
    }

    private final String woodId;
    private final String woodName;

    WoodType(final String commandArg, final String woodName) {
        this.woodId = commandArg;
        this.woodName = woodName;
    }

    public static WoodType fromWoodId(final String findArg) {
        return BY_ID.get(findArg);
    }

    public String getWoodId() {
        return woodId;
    }

    public String getWoodName() {
        return woodName;
    }

    public String getWoodJobName() {
        return "job_" + woodName;
    }
}
