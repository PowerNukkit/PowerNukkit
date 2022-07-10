package cn.nukkit.block;

import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemID;
import cn.nukkit.item.ItemTool;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.math.NukkitRandom;

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
        if (!item.isPickaxe() || item.getTier() < this.getToolTier()) {
            return Item.EMPTY_ARRAY;
        }

        Enchantment enchantment = item.getEnchantment(Enchantment.ID_FORTUNE_DIGGING);
        int fortune = 0;
        if (enchantment != null) {
            fortune = enchantment.getLevel();
        }

        NukkitRandom nukkitRandom = new NukkitRandom();
        int count = nukkitRandom.nextRange(2, 5);
        switch (fortune) {
            case 0:
                // Does nothing
                break;
            case 1:
                if (nukkitRandom.nextRange(0, 2) == 0) {
                    count *= 2;
                }
                break;
            case 2:
                if (nukkitRandom.nextRange(0, 1) == 0) {
                    count *= nukkitRandom.nextRange(2, 3);
                }
                break;
            default:
            case 3:
                if (nukkitRandom.nextRange(0, 4) < 3) {
                    count *= nukkitRandom.nextRange(2, 4);
                }
                break;
        }

        return new Item[]{ Item.get(ItemID.RAW_COPPER, 0, count) };
    }
}
