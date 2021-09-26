package cn.nukkit.customdata;

import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.IntTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.plugin.Plugin;
import io.netty.util.internal.EmptyArrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.annotation.Nonnull;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author joserobjr
 * @since 2021-09-26
 */
@ExtendWith(MockitoExtension.class)
class CustomDataHolderTest {

    static String PLUGIN_NAME = "ThePluginName";

    @Mock
    Plugin plugin;
    CustomDataHolder holder;

    @BeforeEach
    void setUp() {
        holder = new Custom();
        when(plugin.getName()).thenReturn(PLUGIN_NAME);
    }


    @Test
    void setCustomData() {
        CompoundTag data = new CompoundTag(plugin.getName()).putBoolean("a", true).putInt("b", 2);
        holder.setCustomData(plugin, data);
        CompoundTag compound = ((Custom) holder).root.getCompound(BlockEntity.CUSTOM_STORAGE).getCompound(BlockEntity.CUSTOM_STORAGE).getCompound(plugin.getName());
        assertNotSame(data, compound);
        assertEquals(data, compound);
        assertEquals(data, holder.getCustomData(plugin));
    }

    @Test
    void testSetCustomData() {
        assertFalse(holder.getCustomData(plugin).getBoolean("bool"));
        holder.setCustomData(plugin, "bool", true);
        assertTrue(holder.getCustomData(plugin).getBoolean("bool"));
    }

    @Test
    void testSetCustomData1() {
        assertEquals(0, holder.getCustomData(plugin).getByte("byte"));
        holder.setCustomData(plugin, "byte", (byte) 1);
        assertEquals(1, holder.getCustomData(plugin).getByte("byte"));
    }

    @Test
    void testSetCustomData2() {
        assertEquals(0, holder.getCustomData(plugin).getShort("short"));
        holder.setCustomData(plugin, "short", (short) 1);
        assertEquals(1, holder.getCustomData(plugin).getShort("short"));
    }

    @Test
    void testSetCustomData3() {
        assertEquals(0, holder.getCustomData(plugin).getInt("short"));
        holder.setCustomData(plugin, "short", 1);
        assertEquals(1, holder.getCustomData(plugin).getInt("short"));
    }

    @Test
    void testSetCustomData4() {
        assertEquals(0, holder.getCustomData(plugin).getInt("long"));
        holder.setCustomData(plugin, "long", 1L);
        assertEquals(1, holder.getCustomData(plugin).getInt("long"));
    }

    @Test
    void testSetCustomData5() {
        assertEquals(0, holder.getCustomData(plugin).getInt("float"));
        holder.setCustomData(plugin, "float", 1F);
        assertEquals(1, holder.getCustomData(plugin).getInt("float"));
    }

    @Test
    void testSetCustomData6() {
        assertEquals(0, holder.getCustomData(plugin).getInt("double"));
        holder.setCustomData(plugin, "double", 1.0);
        assertEquals(1, holder.getCustomData(plugin).getInt("double"));
    }

    @Test
    void testSetCustomData7() {
        assertArrayEquals(EmptyArrays.EMPTY_INTS, holder.getCustomData(plugin).getIntArray("intarray"));
        holder.setCustomData(plugin, "intarray", new int[]{1,2,3});
        assertArrayEquals(new int[]{1,2,3}, holder.getCustomData(plugin).getIntArray("intarray"));
    }

    @Test
    void testSetCustomData8() {
        assertArrayEquals(EmptyArrays.EMPTY_BYTES, holder.getCustomData(plugin).getByteArray("bytearray"));
        holder.setCustomData(plugin, "bytearray", new byte[]{1,2,3});
        assertArrayEquals(new byte[]{1,2,3}, holder.getCustomData(plugin).getByteArray("bytearray"));
    }

    @Test
    void testSetCustomData9() {
        assertEquals(new CompoundTag("subtag"), holder.getCustomData(plugin).getCompound("subtag"));
        holder.setCustomData(plugin, "subtag", new CompoundTag().putInt("a", 5));
        assertEquals(new CompoundTag("subtag").putInt("a", 5), holder.getCustomData(plugin).getCompound("subtag"));
    }

    @Test
    void testSetCustomData10() {
        assertEquals(new ListTag<>("list"), holder.getCustomData(plugin).getList("list"));
        holder.setCustomData(plugin, new ListTag<>("list").add(new IntTag("", 1)));
        assertEquals(new ListTag<>("list").add(new IntTag("", 1)), holder.getCustomData(plugin).getList("list"));
    }

    @Test
    void clearCustomData() {
        holder.setCustomData(plugin, "clear", 2);
        assertEquals(2, holder.getCustomData(plugin).getInt("clear"));
        holder.clearCustomData(plugin);
        assertFalse(holder.hasCustomData(plugin));
        assertEquals(0, holder.getCustomData(plugin).getInt("clear"));
    }

    @Test
    void getCustomData() {
        holder.setCustomData(plugin, "clear", 2);
        holder.setCustomData(plugin, "dontclear", 3);
        assertEquals(2, holder.getCustomData(plugin).getInt("clear"));
        holder.clearCustomData(plugin, "clear");
        assertTrue(holder.hasCustomData(plugin));
        assertEquals(0, holder.getCustomData(plugin).getInt("clear"));
        assertEquals(3, holder.getCustomData(plugin).getInt("dontclear"));
    }

    @Test
    void hasCustomData() {
        assertFalse(holder.hasCustomData(plugin));
        holder.setCustomData(plugin, "data", 2);
        assertTrue(holder.hasCustomData(plugin));
        holder.clearCustomData(plugin);
        assertFalse(holder.hasCustomData(plugin));
    }

    @Test
    void getAllCustomData() {
        holder.setCustomData(plugin, "clear", 2);
        holder.setCustomData(plugin, "dontclear", 3);
        Plugin plugin2 = mock(Plugin.class);
        when(plugin2.getName()).thenReturn("PL2");
        holder.setCustomData(plugin2, "d2", 2);
        holder.setCustomData(plugin2, "d3", 3);
        Map<String, CompoundTag> expected = new LinkedHashMap<>();
        expected.put(plugin.getName(), new CompoundTag(plugin.getName()).putInt("clear", 2).putInt("dontclear", 3));
        expected.put(plugin2.getName(), new CompoundTag(plugin2.getName()).putInt("d2", 2).putInt("d3", 3));
        assertEquals(expected, holder.getAllCustomData());
    }

    static class Custom implements CustomDataHolder {
        final CompoundTag root = new CompoundTag();

        @Since("FUTURE")
        @PowerNukkitOnly
        @Nonnull
        @Override
        public CompoundTag getRootCustomDataStorageTag() {
            return root.getCompound(BlockEntity.CUSTOM_STORAGE).copy();
        }

        @Since("FUTURE")
        @PowerNukkitOnly
        @Override
        public void setRootCustomDataStorageTag(@Nonnull CompoundTag root) {
            this.root.putCompound(BlockEntity.CUSTOM_STORAGE, root.copy());
        }
    }
}
