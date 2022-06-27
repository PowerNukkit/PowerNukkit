package cn.nukkit.item;

import cn.nukkit.api.Since;

@Since("1.6.0.0-PN")
public class ItemNautilusShell extends Item {

    @Since("1.6.0.0-PN")
    public ItemNautilusShell() {
        this(0, 1);
    }

    @Since("1.6.0.0-PN")
    public ItemNautilusShell(Integer meta) {
        this(meta, 1);
    }

    @Since("1.6.0.0-PN")
    public ItemNautilusShell(Integer meta, int count) {
        super(NAUTILUS_SHELL, meta, count, "Nautilus Shell");
    }
}
