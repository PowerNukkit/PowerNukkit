package cn.nukkit.item;

import cn.nukkit.api.Since;

@Since("1.6.0.0-PN")
public class ItemPhantomMembrane extends Item {

    @Since("1.6.0.0-PN")
    public ItemPhantomMembrane() {
        this(0, 1);
    }

    @Since("1.6.0.0-PN")
    public ItemPhantomMembrane(Integer meta) {
        this(meta, 1);
    }

    @Since("1.6.0.0-PN")
    public ItemPhantomMembrane(Integer meta, int count) {
        super(PHANTOM_MEMBRANE, meta, count, "Phantom Membrane");
    }
}
