package cn.nukkit.item;

import cn.nukkit.api.Since;

@Since("1.6.0.0-PN")
public class ItemHeartOfTheSea extends Item {

    @Since("1.6.0.0-PN")
    public ItemHeartOfTheSea() {
        this(0, 1);
    }

    @Since("1.6.0.0-PN")
    public ItemHeartOfTheSea(Integer meta) {
        this(meta, 1);
    }

    @Since("1.6.0.0-PN")
    public ItemHeartOfTheSea(Integer meta, int count) {
        super(HEART_OF_THE_SEA, meta, count, "Heart Of The Sea");
    }
}
