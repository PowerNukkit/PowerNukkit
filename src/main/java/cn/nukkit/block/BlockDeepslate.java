package cn.nukkit.block;

import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;
import cn.nukkit.blockproperty.BlockProperties;
import cn.nukkit.blockproperty.CommonBlockProperties;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemTool;
import cn.nukkit.item.MinecraftItemID;
import cn.nukkit.math.BlockFace;
import cn.nukkit.utils.BlockColor;

import javax.annotation.Nonnull;

import static cn.nukkit.blockproperty.CommonBlockProperties.PILLAR_AXIS;

/**
 * @author LoboMetalurgico
 * @since 2021-06-08
 */

@PowerNukkitOnly
@Since("FUTURE")
public class BlockDeepslate extends BlockSolid {
    @PowerNukkitOnly
    @Since("FUTURE")
    protected static final BlockProperties PILLAR_PROPERTIES = new BlockProperties(CommonBlockProperties.PILLAR_AXIS);

    @PowerNukkitOnly
    @Since("FUTURE")
    public BlockDeepslate() {
        // Does nothing
    }

    @PowerNukkitOnly
    @Since("FUTURE")
    public static final BlockProperties PROPERTIES = new BlockProperties(PILLAR_AXIS);

    @Nonnull
    @Override
    public BlockProperties getProperties() {
        return PROPERTIES;
    }

    @Override
    public String getName() {
        return "Deepslate";
    }

    @Override
    public int getId() {
        return DEEPSLATE;
    }

    @Override
    public double getHardness() {
        return 3;
    }

    @Override
    public double getResistance() {
        return 6;
    }

    @PowerNukkitOnly
    @Since("FUTURE")
    public BlockFace.Axis getPillarAxis() {
        return getPropertyValue(PILLAR_AXIS);
    }

    @PowerNukkitOnly
    @Since("FUTURE")
    public void setPillarAxis(BlockFace.Axis axis) {
        setPropertyValue(PILLAR_AXIS, axis);
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_PICKAXE;
    }

    @Since("1.4.0.0-PN")
    @PowerNukkitOnly
    @Override
    public int getToolTier() {
        return ItemTool.TIER_WOODEN;
    }

    @Override
    public boolean canHarvestWithHand() {
        return false;
    }

    @Override
    public Item[] getDrops(Item item) {
        if (!canHarvest(item)) {
            return Item.EMPTY_ARRAY;
        }

        return new Item[]{MinecraftItemID.COBBLED_DEEPSLATE.get(1)};
    }

    @Override
    public boolean canSilkTouch() {
        return true;
    }

    @Override
    public BlockColor getColor() {
        return BlockColor.DEEPSLATE_GRAY;
    }
}
