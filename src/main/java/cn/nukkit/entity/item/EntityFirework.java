package cn.nukkit.entity.item;

import cn.nukkit.Server;
import cn.nukkit.api.PowerNukkitDifference;
import cn.nukkit.api.PowerNukkitOnly;
import cn.nukkit.api.Since;
import cn.nukkit.block.Block;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.data.ByteEntityData;
import cn.nukkit.entity.data.IntEntityData;
import cn.nukkit.entity.data.NBTEntityData;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityDamageEvent.DamageCause;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemFirework;
import cn.nukkit.level.Position;
import cn.nukkit.level.Sound;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.network.protocol.EntityEventPacket;
import cn.nukkit.network.protocol.LevelSoundEventPacket;
import cn.nukkit.utils.DyeColor;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author CreeperFace
 */
public class EntityFirework extends Entity {

    public static final int NETWORK_ID = 72;

    @PowerNukkitOnly @Since("FUTURE") public static final String TAG_LIFE = "Life";
    @PowerNukkitOnly @Since("FUTURE") public static final String TAG_LIFE_TIME = "LifeTime";

    private int life;
    private int lifeTime;
    private Item firework;
    private boolean hadCollision;

    @PowerNukkitDifference(info = "Will default to a black-creeper-face if the firework data is missing", since = "1.3.1.2-PN")
    @PowerNukkitDifference(info = "Move initialization to initEntity()", since = "FUTURE")
    public EntityFirework(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @PowerNukkitOnly
    @Since("FUTURE")
    @Override
    protected void initEntity() {
        super.initEntity();

        if (this.namedTag.contains(TAG_LIFE)) {
            this.setLife(this.namedTag.getInt(TAG_LIFE));
        }

        if (this.namedTag.contains(TAG_LIFE_TIME)) {
            this.setLifeTime(this.namedTag.getInt(TAG_LIFE_TIME));
        } else {
            this.setLifeTime(this.generateLifeTime());
        }

        if (this.namedTag.contains("FireworkItem")) {
            firework = NBTIO.getItemHelper(this.namedTag.getCompound("FireworkItem"));
        } else {
            firework = new ItemFirework();
        }

        if (!firework.hasCompoundTag() || !firework.getNamedTag().contains("Fireworks")) {
            CompoundTag tag = firework.getNamedTag();
            if (tag == null) {
                tag = new CompoundTag();
            }

            CompoundTag ex = new CompoundTag()
                    .putByteArray("FireworkColor", new byte[]{(byte) DyeColor.BLACK.getDyeData()})
                    .putByteArray("FireworkFade", new byte[]{})
                    .putBoolean("FireworkFlicker", false)
                    .putBoolean("FireworkTrail", false)
                    .putByte("FireworkType", ItemFirework.FireworkExplosion.ExplosionType.CREEPER_SHAPED.ordinal());

            tag.putCompound("Fireworks", new CompoundTag("Fireworks")
                    .putList(new ListTag<CompoundTag>("Explosions").add(ex))
                    .putByte("Flight", 1)
            );

            firework.setNamedTag(tag);
        }

        this.setDataProperty(new NBTEntityData(Entity.DATA_DISPLAY_ITEM, firework.getNamedTag()));
        this.setDataProperty(new IntEntityData(Entity.DATA_DISPLAY_OFFSET, 1));
        this.setDataProperty(new ByteEntityData(Entity.DATA_HAS_DISPLAY, 1));

        ThreadLocalRandom random = ThreadLocalRandom.current();
        this.motionX = random.nextGaussian() * 0.001D;
        this.motionZ = random.nextGaussian() * 0.001D;
        this.motionY = 0.05D;
    }

    @Override
    public int getNetworkId() {
        return NETWORK_ID;
    }

    @Override
    public boolean onUpdate(int currentTick) {
        if (this.closed) {
            return false;
        }

        int tickDiff = currentTick - this.lastUpdate;

        if (tickDiff <= 0 && !this.justCreated) {
            return true;
        }

        this.lastUpdate = currentTick;

        this.timing.startTiming();


        boolean hasUpdate = this.entityBaseTick(tickDiff);

        if (this.isAlive()) {
            this.motionX *= 1.15D;
            this.motionZ *= 1.15D;
            this.motionY += 0.04D;

            Position position = getPosition();
            Vector3 motion = getMotion();

            this.move(this.motionX, this.motionY, this.motionZ);

            if (this.isCollided && !this.hadCollision) { //collide with block
                this.hadCollision = true;

                for (Block collisionBlock : level.getCollisionBlocks(getBoundingBox().grow(0.1, 0.1, 0.1))) {
                    collisionBlock.onProjectileHit(this, position, motion);
                }
                
            } else if (!this.isCollided && this.hadCollision) {
                this.hadCollision = false;
            }

            this.updateMovement();


            float f = (float) Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.yaw = (float) (Math.atan2(this.motionX, this.motionZ) * (180D / Math.PI));

            this.pitch = (float) (Math.atan2(this.motionY, f) * (180D / Math.PI));


            if (this.life == 0) {
                this.getLevel().addSound(this, Sound.FIREWORK_LAUNCH);
            }

            this.life++;

            hasUpdate = true;
            if (this.life >= this.lifeTime) {
                EntityEventPacket pk = new EntityEventPacket();
                pk.data = 0;
                pk.event = EntityEventPacket.FIREWORK_EXPLOSION;
                pk.eid = this.getId();

                level.addLevelSoundEvent(this, LevelSoundEventPacket.SOUND_LARGE_BLAST, -1, NETWORK_ID);

                Server.broadcastPacket(getViewers().values(), pk);

                this.kill();
            }
        }

        this.timing.stopTiming();

        return hasUpdate || !this.onGround || Math.abs(this.motionX) > 0.00001 || Math.abs(this.motionY) > 0.00001 || Math.abs(this.motionZ) > 0.00001;
    }

    @PowerNukkitOnly
    @Since("FUTURE")
    @Override
    public void saveNBT() {
        super.saveNBT();

        this.namedTag.putInt(TAG_LIFE, this.getLife());
        this.namedTag.putInt(TAG_LIFE_TIME, this.getLifeTime());
    }

    @Override
    public boolean attack(EntityDamageEvent source) {
        return (source.getCause() == DamageCause.VOID ||
                source.getCause() == DamageCause.FIRE_TICK ||
                source.getCause() == DamageCause.ENTITY_EXPLOSION ||
                source.getCause() == DamageCause.BLOCK_EXPLOSION)
                && super.attack(source);
    }

    public void setFirework(Item item) {
        this.firework = item;
        this.setDataProperty(new NBTEntityData(Entity.DATA_DISPLAY_ITEM, item.getNamedTag()));
    }

    @Override
    public float getWidth() {
        return 0.25f;
    }

    @Override
    public float getHeight() {
        return 0.25f;
    }


    @PowerNukkitOnly
    @Since("1.5.1.0-PN")
    @Override
    public String getOriginalName() {
        return "Firework Rocket";
    }

    /*
     * Generate lifeTime of the firework when it created first time.
     */
    @PowerNukkitOnly
    @Since("FUTURE")
    public int generateLifeTime() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        // TODO: Implement flight duration
        int flightDuration = 2;
        return (flightDuration + 1) * 10 + random.nextInt(6) + random.nextInt(7);
    }

    /*
     * Get how many ticks the firework flew.
     */
    @PowerNukkitOnly
    @Since("FUTURE")
    public int getLife() {
        return this.life;
    }

    /*
     * Get how many ticks the firework can fly.
     */
    @PowerNukkitOnly
    @Since("FUTURE")
    public int getLifeTime() {
        return this.lifeTime;
    }

    @PowerNukkitOnly
    @Since("FUTURE")
    public void setLife(int life) {
        this.life = life;
    }

    @PowerNukkitOnly
    @Since("FUTURE")
    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }
}
