package cn.nukkit.block;

/**
 * @author xtypr
 * @since 2015/12/2
 */
public class BlockDandelion extends BlockFlower {
    public BlockDandelion() {
        this(0);
    }

    public BlockDandelion(int meta) {
        super(0);
    }

    @Override
    public String getName() {
        return "Dandelion";
    }

    @Override
    public int getId() {
        return DANDELION;
    }

    @Override
    protected Block getUncommonFlower() {
        return get(POPPY);
    }
}
