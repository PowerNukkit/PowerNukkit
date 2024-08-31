package cn.nukkit.block;

import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;

/**
 * @author LoboMetalurgico
 * @since 2021-06-08
 */

@PowerNukkitOnly
@Since("FUTURE")
public class BlockDeepslatePolished extends BlockDeepslateCobbled {
    @PowerNukkitOnly
    @Since("FUTURE")
    public BlockDeepslatePolished() {
        // Does nothing
    }

    @Override
    public String getName() {
        return "Polished Deepslate";
    }

    @Override
    public int getId() {
        return POLISHED_DEEPSLATE;
    }
}
