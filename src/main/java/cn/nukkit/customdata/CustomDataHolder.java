package cn.nukkit.customdata;

import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Manages custom persistent data to this object. The custom data are persisted in the object's NBT tags.
 *
 * @author joserobjr
 * @since 2021-09-26
 */
@PowerNukkitOnly
@Since("FUTURE")
public interface CustomDataHolder {
    /**
     * The root tag where the custom data are stored. Changes to the returned object may or may not affect the
     * state of this object.
     */
    @PowerNukkitOnly
    @Since("FUTURE")
    @Nonnull
    CompoundTag getRootCustomDataStorageTag();

    /**
     * Changes the content of all custom data.
     */
    @PowerNukkitOnly
    @Since("FUTURE")
    void setRootCustomDataStorageTag(@Nonnull CompoundTag root);

    /**
     * Defines a value to root/customdata/plugin-name/key.
     */
    @PowerNukkitOnly
    @Since("FUTURE")
    default void setCustomData(@Nonnull Plugin plugin, String key, boolean value) {
        setCustomData(plugin, getCustomData(plugin).putBoolean(key, value));
    }

    /**
     * Defines a value to root/customdata/plugin-name/key.
     */
    @PowerNukkitOnly
    @Since("FUTURE")
    default void setCustomData(@Nonnull Plugin plugin, String key, byte value) {
        setCustomData(plugin, getCustomData(plugin).putByte(key, value));
    }

    /**
     * Defines a value to root/customdata/plugin-name/key.
     */
    @PowerNukkitOnly
    @Since("FUTURE")
    default void setCustomData(@Nonnull Plugin plugin, String key, short value) {
        setCustomData(plugin, getCustomData(plugin).putShort(key, value));
    }

    /**
     * Defines a value to root/customdata/plugin-name/key.
     */
    @PowerNukkitOnly
    @Since("FUTURE")
    default void setCustomData(@Nonnull Plugin plugin, String key, int value) {
        setCustomData(plugin, getCustomData(plugin).putInt(key, value));
    }

    /**
     * Defines a value to root/customdata/plugin-name/key.
     */
    @PowerNukkitOnly
    @Since("FUTURE")
    default void setCustomData(@Nonnull Plugin plugin, String key, long value) {
        setCustomData(plugin, getCustomData(plugin).putLong(key, value));
    }

    /**
     * Defines a value to root/customdata/plugin-name/key.
     */
    @PowerNukkitOnly
    @Since("FUTURE")
    default void setCustomData(@Nonnull Plugin plugin, String key, float value) {
        setCustomData(plugin, getCustomData(plugin).putFloat(key, value));
    }

    /**
     * Defines a value to root/customdata/plugin-name/key.
     */
    @PowerNukkitOnly
    @Since("FUTURE")
    default void setCustomData(@Nonnull Plugin plugin, String key, double value) {
        setCustomData(plugin, getCustomData(plugin).putDouble(key, value));
    }

    /**
     * Defines a value to root/customdata/plugin-name/key.
     */
    @PowerNukkitOnly
    @Since("FUTURE")
    default void setCustomData(@Nonnull Plugin plugin, String key, byte[] value) {
        setCustomData(plugin, getCustomData(plugin).putByteArray(key, value));
    }

    /**
     * Defines a value to root/customdata/plugin-name/key.
     */
    @PowerNukkitOnly
    @Since("FUTURE")
    default void setCustomData(@Nonnull Plugin plugin, String key, int[] value) {
        setCustomData(plugin, getCustomData(plugin).putIntArray(key, value));
    }

    /**
     * Defines a value to root/customdata/plugin-name/key.
     */
    @PowerNukkitOnly
    @Since("FUTURE")
    default void setCustomData(@Nonnull Plugin plugin, ListTag<?> namedListTag) {
        setCustomData(plugin, getCustomData(plugin).putList((ListTag<?>) namedListTag.copy()));
    }

    /**
     * Defines a value to root/customdata/plugin-name/key.
     */
    @PowerNukkitOnly
    @Since("FUTURE")
    default void setCustomData(@Nonnull Plugin plugin, String key, CompoundTag subTag) {
        setCustomData(plugin, getCustomData(plugin).putCompound(key, subTag.copy()));
    }

    /**
     * Remove the value at root/customdata/plugin-name/key, return true if the key existed.
     */
    @PowerNukkitOnly
    @Since("FUTURE")
    default boolean clearCustomData(@Nonnull Plugin plugin, @Nonnull String tagName) {
        CompoundTag data = getCustomData(plugin);
        if (!data.contains(tagName)) {
            return false;
        }
        data.remove(tagName);
        setCustomData(plugin, data);
        return true;
    }

    /**
     * Returns a compound containing all custom data set by the plugin.
     */
    @PowerNukkitOnly
    @Since("FUTURE")
    @Nonnull
    default CompoundTag getCustomData(@Nonnull Plugin plugin) {
        return getRootCustomDataStorageTag()
                .getCompound(BlockEntity.CUSTOM_STORAGE)
                .getCompound(plugin.getName())
                .copy();
    }

    /**
     * Returns true if the plugin have any custom data.
     */
    @PowerNukkitOnly
    @Since("FUTURE")
    default boolean hasCustomData(@Nonnull Plugin plugin) {
        CompoundTag custom = getRootCustomDataStorageTag()
                .getCompound(BlockEntity.CUSTOM_STORAGE);
        return custom.containsCompound(plugin.getName()) && !custom.getCompound(plugin.getName()).isEmpty();
    }

    /**
     * Clear all custom data set by the plugin. Returns true if the plugin had data.
     */
    @PowerNukkitOnly
    @Since("FUTURE")
    default boolean clearCustomData(@Nonnull Plugin plugin) {
        CompoundTag root = getRootCustomDataStorageTag();
        CompoundTag compound = root.getCompound(BlockEntity.CUSTOM_STORAGE);
        CompoundTag previous = compound.removeAndGet(plugin.getName());
        root.putCompound(BlockEntity.CUSTOM_STORAGE, compound);
        setRootCustomDataStorageTag(root);
        return previous != null && !previous.isEmpty();
    }

    /**
     * Replace all custom data by the give compound.
     */
    @PowerNukkitOnly
    @Since("FUTURE")
    default void setCustomData(@Nonnull Plugin plugin, @Nullable CompoundTag data) {
        if (data == null) {
            clearCustomData(plugin);
            return;
        }
        CompoundTag root = getRootCustomDataStorageTag();
        CompoundTag storage = root.getCompound(BlockEntity.CUSTOM_STORAGE).putCompound(plugin.getName(), data.copy());
        root.putCompound(BlockEntity.CUSTOM_STORAGE, storage);
        setRootCustomDataStorageTag(root);
    }

    /**
     * Returns all custom data from all plugins.
     */
    @PowerNukkitOnly
    @Since("FUTURE")
    @Nonnull
    default Map<String, CompoundTag> getAllCustomData() {
        return getRootCustomDataStorageTag()
                .getCompound(BlockEntity.CUSTOM_STORAGE)
                .getTags()
                .entrySet()
                .stream()
                .map(e-> new AbstractMap.SimpleEntry<>(e.getKey(), (CompoundTag) e.getValue().copy()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
