package cn.nukkit.block;

import cn.nukkit.Server;
import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;
import cn.nukkit.event.block.BlockFadeEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemTool;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.level.Level;
import cn.nukkit.utils.BlockColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author MagicDroidX (Nukkit Project)
 */
public class BlockIce extends BlockTransparent {

    public BlockIce() {
    }

    @Override
    public int getId() {
        return ICE;
    }

    @Override
    public String getName() {
        return "Ice";
    }

    @Override
    public double getResistance() {
        return 2.5;
    }

    @Override
    public double getHardness() {
        return 0.5;
    }

    @Override
    public double getFrictionFactor() {
        return 0.98;
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_PICKAXE;
    }

    @Override
    public boolean onBreak(Item item) {
        if (this.getLevel().getDimension() != Level.DIMENSION_NETHER) {
            Enchantment enchantment = item.getEnchantment(Enchantment.ID_SILK_TOUCH);
            int level = 0;
            if (enchantment != null || (this.getLocation().clone().subtract(0,1,0).getLevelBlock() instanceof BlockAir) ) {
                level = enchantment.getLevel();
                return super.onBreak(item);
            }
            return this.getLevel().setBlock(this, Block.get(BlockID.WATER), true);
            Enchantment silkTouch = item.getEnchantment(Enchantment.ID_SILK_TOUCH);
            if(silkTouch != null && silkTouch.getLevel() > 0) {
                return this.getLevel().setBlock(this, Block.get(BlockID.AIR), true);
            }else{
                return this.getLevel().setBlock(this, Block.get(BlockID.WATER), true);
            }
        } else {
            return super.onBreak(item);
        }
    }

    @Override
    public int onUpdate(int type) {
        if (type == Level.BLOCK_UPDATE_RANDOM) {
            if (this.getLevel().getDimension() != Level.DIMENSION_NETHER) {
                if (this.getLevel().getBlockLightAt((int) this.x, (int) this.y, (int) this.z) >= 12) {
                    BlockFadeEvent event = new BlockFadeEvent(this, get(WATER));
                    level.getServer().getPluginManager().callEvent(event);
                    if (!event.isCancelled()) {
                        level.setBlock(this, event.getNewState(), true);
                    }
                    return Level.BLOCK_UPDATE_NORMAL;
                }
            }
        }
        return 0;
    }

    @Override
    public Item[] getDrops(Item item) {
        return new Item[0];
    }

    @Override
    public BlockColor getColor() {
        return BlockColor.ICE_BLOCK_COLOR;
    }

    @Override
    public boolean canSilkTouch() {
        return true;
    }

    @Since("1.4.0.0-PN")
    @PowerNukkitOnly
    @Override
    public int getLightFilter() {
        return 2;
    }
}
