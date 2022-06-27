package cn.nukkit.item;

import cn.nukkit.api.Since;

@Since("1.6.0.0-PN")
public class ItemScute extends Item {

    @Since("1.6.0.0-PN")
    public ItemScute() {
        this(0, 1);
    }

    @Since("1.6.0.0-PN")
    public ItemScute(Integer meta) {
        this(meta, 1);
    }

    @Since("1.6.0.0-PN")
    public ItemScute(Integer meta, int count) {
        super(SCUTE, meta, count, "Scute");
    }
}
