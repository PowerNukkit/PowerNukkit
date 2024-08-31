package cn.nukkit.block;

import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;
import cn.nukkit.item.Item;

/**
 * @author LoboMetalurgico
 * @since 2021-06-13
 */

@PowerNukkitOnly
@Since("FUTURE")
public class BlockGlassTinted extends BlockGlass {
    @PowerNukkitOnly
    @Since("FUTURE")
    public BlockGlassTinted() {
        // Does nothing
    }

    @Override
    public String getName() {
        return "Tinted Glass";
    }

    @Override
    public int getId() {
        return TINTED_GLASS;
    }

    @Override
    public Item[] getDrops(Item item) {
        return new Item[] { toItem() };
    }

    @Override
    public boolean canSilkTouch() {
        return false;
    }
}
