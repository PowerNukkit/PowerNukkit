package cn.nukkit.block;

import cn.nukkit.Player;
import cn.nukkit.api.PowerNukkitDifference;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemTool;
import cn.nukkit.math.AxisAlignedBB;
import cn.nukkit.math.BlockFace;
import cn.nukkit.math.SimpleAxisAlignedBB;
import cn.nukkit.utils.Faceable;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;

/**
 * author: MagicDroidX
 * Nukkit Project
 */
public abstract class BlockStairs extends BlockTransparentMeta implements Faceable {
    private static final IntList FACES = new IntArrayList(new int[]{2, 1, 3, 0});
    

    protected BlockStairs(int meta) {
        super(meta);
    }

    @Override
    public double getMinY() {
        // TODO: this seems wrong
        return this.y + ((getDamage() & 0x04) > 0 ? 0.5 : 0);
    }

    @Override
    public double getMaxY() {
        // TODO: this seems wrong
        return this.y + ((getDamage() & 0x04) > 0 ? 1 : 0.5);
    }

    @Override
    public boolean place(Item item, Block block, Block target, BlockFace face, double fx, double fy, double fz, Player player) {
        this.setDamage(FACES.getInt(player != null ? player.getDirection().getHorizontalIndex() : 0));
        if ((fy > 0.5 && face != BlockFace.UP) || face == BlockFace.DOWN) {
            this.setDamage(this.getDamage() | 0x04); //Upside-down stairs
        }
        this.getLevel().setBlock(block, this, true, true);

        return true;
    }

    @Override
    public Item toItem() {
        Item item = super.toItem();
        item.setDamage(0);
        return item;
    }

    @Override
    public boolean collidesWithBB(AxisAlignedBB bb) {
        int damage = this.getDamage();
        int side = damage & 0x03;
        double f = 0;
        double f1 = 0.5;
        double f2 = 0.5;
        double f3 = 1;
        if ((damage & 0x04) > 0) {
            f = 0.5;
            f1 = 1;
            f2 = 0;
            f3 = 0.5;
        }

        if (bb.intersectsWith(new SimpleAxisAlignedBB(
                this.x,
                this.y + f,
                this.z,
                this.x + 1,
                this.y + f1,
                this.z + 1
        ))) {
            return true;
        }


        if (side == 0) {
            if (bb.intersectsWith(new SimpleAxisAlignedBB(
                    this.x + 0.5,
                    this.y + f2,
                    this.z,
                    this.x + 1,
                    this.y + f3,
                    this.z + 1
            ))) {
                return true;
            }
        } else if (side == 1) {
            if (bb.intersectsWith(new SimpleAxisAlignedBB(
                    this.x,
                    this.y + f2,
                    this.z,
                    this.x + 0.5,
                    this.y + f3,
                    this.z + 1
            ))) {
                return true;
            }
        } else if (side == 2) {
            if (bb.intersectsWith(new SimpleAxisAlignedBB(
                    this.x,
                    this.y + f2,
                    this.z + 0.5,
                    this.x + 1,
                    this.y + f3,
                    this.z + 1
            ))) {
                return true;
            }
        } else if (side == 3) {
            if (bb.intersectsWith(new SimpleAxisAlignedBB(
                    this.x,
                    this.y + f2,
                    this.z,
                    this.x + 1,
                    this.y + f3,
                    this.z + 0.5
            ))) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int getWaterloggingLevel() {
        return 1;
    }

    @PowerNukkitDifference(info = "Was returning the wrong face", since = "1.3.0.0-PN")
    @Override
    public BlockFace getBlockFace() {
        int stairFace = this.getDamage() & 0x3;
        int horizontalIndex = FACES.indexOf(stairFace);
        return BlockFace.fromHorizontalIndex(horizontalIndex).getOpposite();
    }
}
