package cn.nukkit.item;

import cn.nukkit.plugin.Plugin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.powernukkit.tests.junit.jupiter.PowerNukkitExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author joserobjr
 * @since 2021-09-26
 */
@ExtendWith(PowerNukkitExtension.class)
class ItemTest {
    @Test
    void testCustomData() {
        Plugin plugin = mock(Plugin.class);
        when(plugin.getName()).thenReturn("plugin");
        Item stick = MinecraftItemID.STICK.get(1);
        stick.setCustomData(plugin, "MyData", 5f);
        assertEquals(5f, stick.getCustomData(plugin).getFloat("MyData"));
    }
}
