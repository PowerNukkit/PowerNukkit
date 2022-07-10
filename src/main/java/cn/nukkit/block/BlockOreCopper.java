package cn.nukkit.block;

import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemRawCopper;
import cn.nukkit.item.ItemTool;
import cn.nukkit.item.enchantment.Enchantment;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author GoodLucky777
 */
public class BlockOreCopper extends BlockSolid {

    public BlockOreCopper() {
    }

    @Override
    public int getId() {
        return COPPER_ORE;
    }

    @Override
    public double getHardness() {
        return 3;
    }

    @Override
    public double getResistance() {
        return 3;
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_PICKAXE;
    }

    @Override
    @PowerNukkitOnly
    public int getToolTier() {
        return ItemTool.TIER_STONE;
    }

    @Override
    public String getName() {
        return "Copper Ore";
    }

    @Override
    public boolean canHarvestWithHand() {
        return false;
    }

    @PowerNukkitOnly
    @Since("FUTURE")
    @Override
    public boolean canSilkTouch() {
        return true;
    }

    @PowerNukkitOnly
    @Since("FUTURE")
    @Override
    public Item[] getDrops(Item item) {
        if (item.isPickaxe() && item.getTier() >= getToolTier()) {
            int count = 1;
            Enchantment fortune = item.getEnchantment(Enchantment.ID_FORTUNE_DIGGING);
            if (fortune != null && fortune.getLevel() >= 1) {
                int i = ThreadLocalRandom.current().nextInt(fortune.getLevel() + 2) - 1;
                if (i < 0) {
                    i = 0;
                }

                count = i + 1;
            }

            return new Item[]{ new ItemRawCopper(0, count) };
        } else {
            return Item.EMPTY_ARRAY;
        }
    }
}
