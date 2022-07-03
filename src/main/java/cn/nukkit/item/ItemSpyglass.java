package cn.nukkit.item;

import cn.nukkit.api.Since;

/**
 * @author LT_Name
 */
@Since("1.6.0.0-PN")
public class ItemSpyglass extends Item {

    @Since("1.6.0.0-PN")
    public ItemSpyglass() {
        this(0, 1);
    }

    @Since("1.6.0.0-PN")
    public ItemSpyglass(Integer meta) {
        this(meta, 1);
    }

    @Since("1.6.0.0-PN")
    public ItemSpyglass(Integer meta, int count) {
        super(SPYGLASS, meta, count, "Spyglass");
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
