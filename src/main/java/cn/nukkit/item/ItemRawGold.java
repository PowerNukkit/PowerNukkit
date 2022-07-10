package cn.nukkit.item;

/**
 * @author GoodLucky777
 */
public class ItemRawGold extends Item {

    public ItemRawGold() {
        this(0, 1);
    }

    public ItemRawGold(Integer meta) {
        this(meta, 1);
    }

    public ItemRawGold(Integer meta, int count) {
        super(RAW_GOLD, meta, count, "Raw Gold");
    }
}
