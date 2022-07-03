package cn.nukkit.level;

import cn.nukkit.api.Since;

@Since("1.6.0.0-PN")
public enum DimensionEnum {
    @Since("1.6.0.0-PN")
    OVERWORLD(new DimensionData(Level.DIMENSION_OVERWORLD, -64, 319)),

    @Since("1.6.0.0-PN")
    NETHER(new DimensionData(Level.DIMENSION_NETHER, 0, 127)),

    @Since("1.6.0.0-PN")
    END(new DimensionData(Level.DIMENSION_THE_END, 0, 255));

    private final DimensionData dimensionData;

    DimensionEnum(DimensionData dimensionData) {
        this.dimensionData = dimensionData;
    }

    @Since("1.6.0.0-PN")
    public DimensionData getDimensionData() {
        return this.dimensionData;
    }

    @Since("1.6.0.0-PN")
    public static DimensionData getDataFromId(int dimension) {
        for (DimensionEnum value : values()) {
            if (value.getDimensionData().getDimensionId() == dimension) {
                return value.getDimensionData();
            }
        }
        return null;
    }
}
