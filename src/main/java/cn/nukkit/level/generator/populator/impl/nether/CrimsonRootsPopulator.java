package cn.nukkit.level.generator.populator.impl.nether;

import cn.nukkit.block.Block;
import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.level.generator.populator.type.Populator;
import cn.nukkit.math.NukkitMath;
import cn.nukkit.math.NukkitRandom;

import java.util.ArrayList;

/**
 * This populator is separated from {@link CrimsonGrassesPopulator} since crimson roots also spawn rarely in
 * Warped Forest and Soul Sand Valley biomes
 */
public class CrimsonRootsPopulator extends Populator {
    private ChunkManager level;

    @Override
    public void populate(ChunkManager level, int chunkX, int chunkZ, NukkitRandom random, FullChunk chunk) {
        this.level = level;
        if(random.nextBoundedInt(15) != 0) return;

        int amount = random.nextBoundedInt(7)+3;
        for (int i = 0; i < amount; ++i) {
            int x = NukkitMath.randomRange(random, chunkX << 4, (chunkX << 4) + 15);
            int z = NukkitMath.randomRange(random, chunkZ << 4, (chunkZ << 4) + 15);
            ArrayList<Integer> ys = this.getHighestWorkableBlocks(x, z);
            for(int y : ys) {
                if (y <= 1) continue;
                this.level.setBlockAt(x, y, z, CRIMSON_ROOTS);
            }
        }
    }

    private ArrayList<Integer> getHighestWorkableBlocks(int x, int z) {
        int y;
        ArrayList<Integer> blockYs = new ArrayList<>();
        for (y = 128; y > 0; y--) {
            int b = this.level.getBlockIdAt(x, y, z);
            if ((b == Block.WARPED_NYLIUM || b == Block.SOUL_SOIL || b == Block.SOUL_SAND) && this.level.getBlockIdAt(x, y+1, z) == 0) {
                blockYs.add(y+1);
            }
        }
        return blockYs;
    }
}
