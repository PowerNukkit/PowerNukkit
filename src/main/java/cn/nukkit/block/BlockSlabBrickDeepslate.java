package cn.nukkit.block;

import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;
import cn.nukkit.blockproperty.BlockProperties;

import javax.annotation.Nonnull;

/**
 * @author GoodLucky777
 */
@PowerNukkitOnly
@Since("FUTURE")
public class BlockSlabBrickDeepslate extends BlockSlab {

    @PowerNukkitOnly
    @Since("FUTURE")
    public BlockSlabBrickDeepslate() {
        this(0);
    }
    
    @PowerNukkitOnly
    @Since("FUTURE")
    public BlockSlabBrickDeepslate(int meta) {
        super(meta, DEEPSLATE_BRICK_SLAB);
    }
    
    @Override
    public int getId() {
        return DEEPSLATE_BRICK_SLAB;
    }
    
    @Override
    public String getSlabName() {
        return "Deepslate Brick";
    }
    
    @Nonnull
    @Override
    public BlockProperties getProperties() {
        return SIMPLE_SLAB_PROPERTIES;
    }
    
    @Override
    public boolean isSameType(BlockSlab slab) {
        return getId() == slab.getId();
    }
}
