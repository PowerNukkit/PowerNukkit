package cn.nukkit.block;

import cn.nukkit.item.Item;
import cn.nukkit.item.ItemDoorWarped;
import cn.nukkit.utils.BlockColor;

public class BlockDoorWarped extends BlockDoorWood {

    public BlockDoorWarped() {
        this(0);
    }

    public BlockDoorWarped(int meta) {
        super(meta);
    }

    @Override
    public String getName() {
        return "Warped Door Block";
    }

    @Override
    public int getId() {
        return WARPED_DOOR_BLOCK;
    }

    @Override
    public Item toItem() {
        return new ItemDoorWarped();
    }

    /*@Override
    public BlockColor getColor() {
        return BlockColor.BLACK_BLOCK_COLOR;
    }*/
}