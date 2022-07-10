package cn.nukkit.item;

/**
 * @author GoodLucky777
 */
public class ItemRawIron extends Item {

    public ItemRawIron() {
        this(0, 1);
    }

    public ItemRawIron(Integer meta) {
        this(meta, 1);
    }

    public ItemRawIron(Integer meta, int count) {
        super(RAW_IRON, meta, count, "Raw Iron");
    }
}
