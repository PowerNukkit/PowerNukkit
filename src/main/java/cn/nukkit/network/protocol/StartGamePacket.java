package cn.nukkit.network.protocol;

import cn.nukkit.api.DeprecationDetails;
import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;
import cn.nukkit.item.RuntimeItems;
import cn.nukkit.level.GameRules;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.nio.ByteOrder;
import java.util.UUID;

/**
 * @since 15-10-13
 */
@Log4j2
@ToString
public class StartGamePacket extends DataPacket {

    public static final byte NETWORK_ID = ProtocolInfo.START_GAME_PACKET;

    public static final int GAME_PUBLISH_SETTING_NO_MULTI_PLAY = 0;
    public static final int GAME_PUBLISH_SETTING_INVITE_ONLY = 1;
    public static final int GAME_PUBLISH_SETTING_FRIENDS_ONLY = 2;
    public static final int GAME_PUBLISH_SETTING_FRIENDS_OF_FRIENDS = 3;
    public static final int GAME_PUBLISH_SETTING_PUBLIC = 4;

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    public long entityUniqueId;
    public long entityRuntimeId;
    public int playerGamemode;
    public float x;
    public float y;
    public float z;
    public float yaw;
    public float pitch;
    @Deprecated @DeprecationDetails(since = "FUTURE", by = "PowerNukkit", reason = "Minecraft have started supporting 64-bit seed since v1.18.30 update.", replaceWith="seed64") public int seed;
    @PowerNukkitOnly @Since("FUTURE") public Long seed64 = null;
    public byte dimension;
    public int generator = 1;
    public int worldGamemode;
    public int difficulty;
    public int spawnX;
    public int spawnY;
    public int spawnZ;
    public boolean hasAchievementsDisabled = true;
    public int dayCycleStopTime = 0;
    public int eduEditionOffer = 0;
    public boolean hasEduFeaturesEnabled = false;
    @PowerNukkitOnly @Since("FUTURE") public String educationProductionId = "";
    public float rainLevel;
    public float lightningLevel;
    public boolean hasConfirmedPlatformLockedContent = false;
    public boolean multiplayerGame = true;
    public boolean broadcastToLAN = true;
    public int xblBroadcastIntent = GAME_PUBLISH_SETTING_PUBLIC;
    public int platformBroadcastIntent = GAME_PUBLISH_SETTING_PUBLIC;
    public boolean commandsEnabled;
    public boolean isTexturePacksRequired = false;
    public GameRules gameRules;
    @PowerNukkitOnly @Since("FUTURE") public ExperimentData[] experiments = ExperimentData.EMPTY_ARRAY;
    @PowerNukkitOnly @Since("FUTURE") public boolean experimentsPreviouslyToggled = false;
    public boolean bonusChest = false;
    public boolean hasStartWithMapEnabled = false;
    public int permissionLevel = 1;
    public int serverChunkTickRange = 4;
    public boolean hasLockedBehaviorPack = false;
    public boolean hasLockedResourcePack = false;
    public boolean isFromLockedWorldTemplate = false;
    public boolean isUsingMsaGamertagsOnly = false;
    public boolean isFromWorldTemplate = false;
    public boolean isWorldTemplateOptionLocked = false;
    public boolean isOnlySpawningV1Villagers = false;
    public String vanillaVersion = "*";
    @PowerNukkitOnly @Since("FUTURE") public int limitedWorldWidth = 16;
    @PowerNukkitOnly @Since("FUTURE") public int limitedWorldHeight = 16;
    @PowerNukkitOnly @Since("FUTURE") public boolean netherType = false;
    @PowerNukkitOnly @Since("FUTURE") public EduSharedUriResource eduSharedUriResource = EduSharedUriResource.EMPTY;
    @PowerNukkitOnly @Since("FUTURE") public boolean forceExperimentalGameplay = false;
    public String levelId = ""; //base64 string, usually the same as world folder name in vanilla
    public String worldName;
    public String premiumWorldTemplateId = "00000000-0000-0000-0000-000000000000";
    public boolean isTrial = false;
    @Deprecated @DeprecationDetails(since = "FUTURE", by = "PowerNukkit", reason = "Minecraft update", replaceWith="playerMovementSettings") public boolean isMovementServerAuthoritative;
    @PowerNukkitOnly @Since("FUTURE") public SyncedPlayerMovementSettings playerMovementSettings = null;
    public long currentTick;
    public int enchantmentSeed;
    @PowerNukkitOnly @Since("FUTURE") public BlockPropertyData[] properties = BlockPropertyData.EMPTY_ARRAY;
    
    public String multiplayerCorrelationId = "";
    @Since("1.3.0.0-PN") public boolean isInventoryServerAuthoritative;
    @PowerNukkitOnly @Since("FUTURE") public String serverEngine = ProtocolInfo.MINECRAFT_VERSION_NETWORK;
    @PowerNukkitOnly @Since("FUTURE") public long blockRegistryChecksum;
    @PowerNukkitOnly @Since("FUTURE") public UUID worldTemplateId = new UUID(0, 0);
    
    @Override
    public void decode() {

    }

    @Override
    public void encode() {
        this.reset();
        this.putEntityUniqueId(this.entityUniqueId);
        this.putEntityRuntimeId(this.entityRuntimeId);
        this.putVarInt(this.playerGamemode);
        this.putVector3f(this.x, this.y, this.z);
        this.putLFloat(this.yaw);
        this.putLFloat(this.pitch);

        if (this.seed64 == null) {
            this.putLLong(this.seed);
        } else {
            this.putLLong(this.seed64);
        }
        this.putLShort(0x00); // SpawnBiomeType - Default
        this.putString("plains"); // UserDefinedBiomeName
        this.putVarInt(this.dimension);
        this.putVarInt(this.generator);
        this.putVarInt(this.worldGamemode);
        this.putVarInt(this.difficulty);
        this.putBlockVector3(this.spawnX, this.spawnY, this.spawnZ);
        this.putBoolean(this.hasAchievementsDisabled);
        this.putVarInt(this.dayCycleStopTime);
        this.putVarInt(this.eduEditionOffer);
        this.putBoolean(this.hasEduFeaturesEnabled);
        this.putString(this.educationProductionId);
        this.putLFloat(this.rainLevel);
        this.putLFloat(this.lightningLevel);
        this.putBoolean(this.hasConfirmedPlatformLockedContent);
        this.putBoolean(this.multiplayerGame);
        this.putBoolean(this.broadcastToLAN);
        this.putVarInt(this.xblBroadcastIntent);
        this.putVarInt(this.platformBroadcastIntent);
        this.putBoolean(this.commandsEnabled);
        this.putBoolean(this.isTexturePacksRequired);
        this.putGameRules(this.gameRules);
        this.putLInt(this.experiments.length);
        for (ExperimentData experiment : this.experiments) {
            this.putString(experiment.getName());
            this.putBoolean(experiment.isEnabled());
        }
        this.putBoolean(this.experimentsPreviouslyToggled);
        this.putBoolean(this.bonusChest);
        this.putBoolean(this.hasStartWithMapEnabled);
        this.putVarInt(this.permissionLevel);
        this.putLInt(this.serverChunkTickRange);
        this.putBoolean(this.hasLockedBehaviorPack);
        this.putBoolean(this.hasLockedResourcePack);
        this.putBoolean(this.isFromLockedWorldTemplate);
        this.putBoolean(this.isUsingMsaGamertagsOnly);
        this.putBoolean(this.isFromWorldTemplate);
        this.putBoolean(this.isWorldTemplateOptionLocked);
        this.putBoolean(this.isOnlySpawningV1Villagers);
        this.putString(this.vanillaVersion);
        this.putLInt(this.limitedWorldWidth);
        this.putLInt(this.limitedWorldHeight);
        this.putBoolean(this.netherType);
        this.putString(this.eduSharedUriResource.getButtonName());
        this.putString(this.eduSharedUriResource.getLinkUri());
        this.putBoolean(this.forceExperimentalGameplay);
        // TODO: handle force experimental
        this.putString(this.levelId);
        this.putString(this.worldName);
        this.putString(this.premiumWorldTemplateId);
        this.putBoolean(this.isTrial);
        if (playerMovementSettings == null) { // For backward compatibility
            this.putVarInt(this.isMovementServerAuthoritative ? 1 : 0); // 2 - rewind
            this.putVarInt(0); // RewindHistorySize
            this.putBoolean(false); // isServerAuthoritativeBlockBreaking
        } else {
            this.putVarInt(playerMovementSettings.getMovementMode().ordinal());
            this.putVarInt(playerMovementSettings.getRewindHistorySize());
            this.putBoolean(playerMovementSettings.isServerAuthoritativeBlockBreaking());
        }
        this.putLLong(this.currentTick);
        this.putVarInt(this.enchantmentSeed);
        this.putUnsignedVarInt(this.properties.length);
        try {
            for (BlockPropertyData property : this.properties) {
                this.putString(property.getName());
                this.put(NBTIO.write(property.getProperties(), ByteOrder.LITTLE_ENDIAN, true));
            }
        } catch (IOException e) {
            log.error("Error while encoding properties data of StartGamePacket", e);
        }
        this.put(RuntimeItems.getRuntimeMapping().getItemDataPalette());
        this.putString(this.multiplayerCorrelationId);
        this.putBoolean(this.isInventoryServerAuthoritative);
        this.putString(this.serverEngine);
        try {
            this.put(NBTIO.writeNetwork(new CompoundTag(""))); // playerPropertyData
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.putLLong(this.blockRegistryChecksum);
        this.putUUID(this.worldTemplateId);
    }
    
    @ToString
    @PowerNukkitOnly
    @Since("FUTURE")
    public static class ExperimentData {
    
        public static final ExperimentData[] EMPTY_ARRAY = new ExperimentData[0];
        
        private final String name;
        private final boolean enabled;
        
        public ExperimentData(String name, boolean enabled) {
            this.name = name;
            this.enabled = enabled;
        }
        
        public String getName() {
            return name;
        }
        
        public boolean isEnabled() {
            return enabled;
        }
    }

    @Getter
    @ToString
    @PowerNukkitOnly
    @Since("FUTURE")
    public static class EduSharedUriResource {

        public static final EduSharedUriResource EMPTY = new EduSharedUriResource("", "");

        private final String buttonName;
        private final String linkUri;

        public EduSharedUriResource(String buttonName, String linkUri) {
            this.buttonName = buttonName;
            this.linkUri = linkUri;
        }
    }

    @Getter
    @ToString
    @PowerNukkitOnly
    @Since("FUTURE")
    public static class SyncedPlayerMovementSettings {
    
        private final AuthoritativeMovementMode movementMode;
        private final int rewindHistorySize;
        private final boolean serverAuthoritativeBlockBreaking;
        
        public SyncedPlayerMovementSettings(AuthoritativeMovementMode movementMode, int rewindHistorySize, boolean serverAuthoritativeBlockBreaking) {
            this.movementMode = movementMode;
            this.rewindHistorySize = rewindHistorySize;
            this.serverAuthoritativeBlockBreaking = serverAuthoritativeBlockBreaking;
        }
    }
    
    @ToString
    @PowerNukkitOnly
    @Since("FUTURE")
    public static class BlockPropertyData {
    
        public static final BlockPropertyData[] EMPTY_ARRAY = new BlockPropertyData[0];
        
        private final String name;
        private final CompoundTag properties;
        
        public BlockPropertyData(String name, CompoundTag properties) {
            this.name = name;
            this.properties = properties;
        }
        
        public String getName() {
            return name;
        }
        
        public CompoundTag getProperties() {
            return properties;
        }
    }
    
    @PowerNukkitOnly
    @Since("FUTURE")
    public enum AuthoritativeMovementMode {
        CLIENT,
        SERVER,
        SERVER_WITH_REWIND
    }
}
