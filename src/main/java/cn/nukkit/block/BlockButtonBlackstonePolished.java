package cn.nukkit.block;

import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;

@PowerNukkitOnly
@Since("1.4.0.0-PN")
public class BlockButtonBlackstonePolished extends BlockButtonStone {
    public BlockButtonBlackstonePolished() {
        this(0);
    }

    public BlockButtonBlackstonePolished(int meta) {
        super(meta);
    }

    @Override
    public int getId() {
        return POLISHED_BLACKSTONE_BUTTON;
    }

    @Override
    public String getName() {
        return "Polished Blackstone Button";
    }
}
