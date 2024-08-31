package cn.nukkit.block;

import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;

/**
 * @author LoboMetalurgico
 * @since 2021-06-13
 */

@PowerNukkitOnly
@Since("FUTURE")
public class BlockAzaleaFlowering extends BlockAzalea {
    @PowerNukkitOnly
    @Since("FUTURE")
    public BlockAzaleaFlowering() {
        // Does nothing
    }

    @Override
    public String getName() {
        return "Flowering Azalea";
    }

    @Override
    public int getId() {
        return FLOWERING_AZALEA;
    }
}
