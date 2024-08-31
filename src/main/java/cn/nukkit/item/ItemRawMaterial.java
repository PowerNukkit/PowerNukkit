package cn.nukkit.item;

import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;

/**
 * @author joserobjr
 * @since 2021-06-12
 */
@PowerNukkitOnly
@Since("FUTURE")
public abstract class ItemRawMaterial extends Item {

    @PowerNukkitOnly
    @Since("FUTURE")
    public ItemRawMaterial(int id, String name) {
        super(id, 0, 1, name);
    }
}
