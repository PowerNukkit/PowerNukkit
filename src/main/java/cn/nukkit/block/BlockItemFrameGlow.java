package cn.nukkit.block;

import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;

/**
 * @author LoboMetalurgico
 * @since 2021-06-13
 */

// TODO: This is a basic implementation just to allow for use, fixes must be made
@PowerNukkitOnly
@Since("FUTURE")
public class BlockItemFrameGlow extends BlockItemFrame {
    @PowerNukkitOnly
    @Since("FUTURE")
    public BlockItemFrameGlow() {
        this(0);
    }

    @PowerNukkitOnly
    @Since("FUTURE")
    public BlockItemFrameGlow(int meta) {
        super(meta);
    }

    @Override
    public String getName() {
        return "Glow Item Frame";
    }

    @Override
    public int getId() {
        return GLOW_FRAME;
    }
}
