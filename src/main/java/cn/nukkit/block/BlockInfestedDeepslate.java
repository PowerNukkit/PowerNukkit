package cn.nukkit.block;

import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;
import cn.nukkit.item.Item;

/**
 * @author GoodLucky777
 */
@PowerNukkitOnly
@Since("FUTURE")
public class BlockInfestedDeepslate extends BlockDeepslate {

    @PowerNukkitOnly
    @Since("FUTURE")
    public BlockInfestedDeepslate() {
        // Does nothing
    }
    
    @Override
    public int getId() {
        return INFESTED_DEEPSLATE;
    }
    
    @Override
    public String getName() {
        return "Infested Deepslate";
    }
    
    @Override
    public double getHardness() {
        return 0;
    }
    
    @Override
    public double getResistance() {
        return 0.75;
    }
    
    @Override
    public Item[] getDrops(Item item) {
        return Item.EMPTY_ARRAY;
    }

    @Override
    public boolean canSilkTouch() {
        return true;
    }
}
