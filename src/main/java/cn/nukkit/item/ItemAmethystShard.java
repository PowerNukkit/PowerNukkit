package cn.nukkit.item;

import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;

/**
 * @author GoodLucky777
 */
@PowerNukkitOnly
@Since("FUTURE")
public class ItemAmethystShard extends Item {

    @PowerNukkitOnly
    @Since("FUTURE")
    public ItemAmethystShard() {
        this(0, 1);
    }

    @PowerNukkitOnly
    @Since("FUTURE")
    public ItemAmethystShard(Integer meta) {
        this(meta, 1);
    }

    @PowerNukkitOnly
    @Since("FUTURE")
    public ItemAmethystShard(Integer meta, int count) {
        super(AMETHYST_SHARD, meta, count, "Amethyst Shard");
    }
}
