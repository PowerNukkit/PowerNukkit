package cn.nukkit.blockentity;

import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;

/**
 * @author joserobjr
 * @since 2021-09-26
 */
@PowerNukkitOnly
@Since("FUTURE")
public class BlockEntityCustomDataStorage extends BlockEntity {

    @PowerNukkitOnly
    @Since("FUTURE")
    public BlockEntityCustomDataStorage(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public boolean isBlockEntityValid() {
        CompoundTag root = namedTag.getCompound(BlockEntity.CUSTOM_STORAGE);
        if (root.getBoolean("AlwaysValid")) {
            return true;
        }
        if (root.getCompound(BlockEntity.CUSTOM_STORAGE).isEmpty()) {
            return false;
        }
        int[] allowedIds = root.getIntArray("ValidBlockIds");
        int currentId = getLevelBlockState().getBlockId();
        for (int allowedId: allowedIds) {
            if (allowedId == currentId) {
                return true;
            }
        }
        return false;
    }
}
