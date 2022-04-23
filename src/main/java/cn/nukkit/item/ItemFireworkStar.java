package cn.nukkit.item;

import cn.nukkit.api.Since;

@Since("1.6.0.0-PN")
public class ItemFireworkStar extends Item {

    @Since("1.6.0.0-PN")
    public ItemFireworkStar() {
        this(0, 1);
    }

    @Since("1.6.0.0-PN")
    public ItemFireworkStar(Integer meta) {
        this(meta, 1);
    }

    @Since("1.6.0.0-PN")
    public ItemFireworkStar(Integer meta, int count) {
        super(FIREWORKSCHARGE, meta, count, "Firework Star");
    }
}
