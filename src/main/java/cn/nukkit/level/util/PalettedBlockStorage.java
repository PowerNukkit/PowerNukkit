package cn.nukkit.level.util;

import cn.nukkit.api.DeprecationDetails;
import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;
import cn.nukkit.block.BlockID;
import cn.nukkit.blockstate.BlockStateRegistry;
import cn.nukkit.utils.BinaryStream;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;

import java.util.function.IntConsumer;

public class PalettedBlockStorage {

    private static final int SIZE = 4096;

    private final IntList palette;
    private BitArray bitArray;

    @Since("1.6.0.0-PN")
    public static PalettedBlockStorage createFromBlockPalette() {
        return createFromBlockPalette(BitArrayVersion.V2);
    }

    @Since("1.6.0.0-PN")
    public static PalettedBlockStorage createFromBlockPalette(BitArrayVersion version) {
        int runtimeId = BlockStateRegistry.getRuntimeId(BlockID.AIR);
        return new PalettedBlockStorage(version, runtimeId);
    }

    @Since("1.6.0.0-PN")
    public static PalettedBlockStorage createWithDefaultState(int defaultState) {
        return createWithDefaultState(BitArrayVersion.V2, defaultState);
    }

    @Since("1.6.0.0-PN")
    public static PalettedBlockStorage createWithDefaultState(BitArrayVersion version, int defaultState) {
        return new PalettedBlockStorage(version, defaultState);
    }

    @PowerNukkitOnly("This was removed on 1.6.0.0-PN by Cloudburst Nukkit and re-added to fix plugin compatibility issue on 1.6.0.1-PN")
    @Deprecated
    @DeprecationDetails(
            since = "1.6.0.0-PN",
            reason = "Refactored by Cloudburst Nukkit to use static method instead of constructor",
            replaceWith = "createFromBlockPalette()"
    )
    public PalettedBlockStorage() {
        this(BitArrayVersion.V2);
    }

    @PowerNukkitOnly("This was removed on 1.6.0.0-PN by Cloudburst Nukkit and re-added to fix plugin compatibility issue on 1.6.0.1-PN")
    @Deprecated
    @DeprecationDetails(
            since = "1.6.0.0-PN",
            reason = "Refactored by Cloudburst Nukkit to use static method instead of constructor",
            replaceWith = "createFromBlockPalette(BitArrayVersion)"
    )
    public PalettedBlockStorage(BitArrayVersion version) {
        this(version, BlockStateRegistry.getRuntimeId(BlockID.AIR));
    }

    private PalettedBlockStorage(BitArrayVersion version, int defaultState) {
        this.bitArray = version.createPalette(SIZE);
        this.palette = new IntArrayList(16);
        this.palette.add(defaultState);
    }

    private PalettedBlockStorage(BitArray bitArray, IntList palette) {
        this.palette = palette;
        this.bitArray = bitArray;
    }

    private int getPaletteHeader(BitArrayVersion version, boolean runtime) {
        return (version.getId() << 1) | (runtime ? 1 : 0);
    }

    private int getIndex(int x, int y, int z) {
        return (x << 8) | (z << 4) | y;
    }

    @Since("1.6.0.0-PN")
    public void setBlock(int x, int y, int z, int runtimeId) {
        this.setBlock(this.getIndex(x, y, z), runtimeId);
    }

    public void setBlock(int index, int runtimeId) {
        try {
            int id = this.idFor(runtimeId);
            this.bitArray.set(index, id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unable to set block runtime ID: " + runtimeId + ", palette: " + palette, e);
        }
    }

    public void writeTo(BinaryStream stream) {
        stream.putByte((byte) getPaletteHeader(bitArray.getVersion(), true));

        for (int word : bitArray.getWords()) {
            stream.putLInt(word);
        }

        stream.putVarInt(palette.size());
        palette.forEach((IntConsumer) stream::putVarInt);
    }

    private void onResize(BitArrayVersion version) {
        BitArray newBitArray = version.createPalette(SIZE);

        for (int i = 0; i < SIZE; i++) {
            newBitArray.set(i, this.bitArray.get(i));
        }
        this.bitArray = newBitArray;
    }

    private int idFor(int runtimeId) {
        int index = this.palette.indexOf(runtimeId);
        if (index != -1) {
            return index;
        }

        index = this.palette.size();
        BitArrayVersion version = this.bitArray.getVersion();
        if (index > version.getMaxEntryValue()) {
            BitArrayVersion next = version.next();
            if (next != null) {
                this.onResize(next);
            }
        }
        this.palette.add(runtimeId);
        return index;
    }

    public boolean isEmpty() {
        if (this.palette.size() == 1) {
            return true;
        }
        for (int word : this.bitArray.getWords()) {
            if (Integer.toUnsignedLong(word) != 0L) {
                return false;
            }
        }
        return true;
    }

    public PalettedBlockStorage copy() {
        return new PalettedBlockStorage(this.bitArray.copy(), new IntArrayList(this.palette));
    }
}
