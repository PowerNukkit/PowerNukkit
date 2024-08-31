package cn.nukkit.block;

import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;

/**
 * @author LoboMetalurgico
 * @since 2021-06-13
 */

@PowerNukkitOnly
@Since("FUTURE")
public class BlockRootsHanging extends BlockRoots {
    @PowerNukkitOnly
    @Since("FUTURE")
    public BlockRootsHanging() {
        // Does nothing
    }

    @Override
    public String getName() {
        return "Hanging Roots";
    }

    @Override
    public int getId() {
        return HANGING_ROOTS;
    }
}
