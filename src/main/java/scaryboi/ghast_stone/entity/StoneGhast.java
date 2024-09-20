package scaryboi.ghast_stone.entity;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;


import java.util.EnumSet;

public class StoneGhast extends Ghast {
    public int initialChargeTime = 20;


    public StoneGhast(EntityType<? extends StoneGhast> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new StoneGhastMoveControl(this);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.removeAllGoals();
        this.goalSelector.addGoal(5, new StoneGhastShootFireballGoal(this));
        this.goalSelector.addGoal(6, new StoneGhastRandomFloatAroundGoal(this));
        this.goalSelector.addGoal(7, new StoneGhastLookGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));

    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return EntityDimensions.scalable(1.0F, 1.0F); // Set size to match a stone block
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return 0.9F; // Adjusted eye height
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        // Prevent explosion damage
        if (source.isExplosion()) {
            return false; // Ignores explosion damage
        }
        if (source.isFire()) {
            return false;
        }

        // Otherwise, call the regular hurt method for other damage types
        return super.hurt(source, amount);
    }


    // Custom move control
    static class StoneGhastMoveControl extends MoveControl {
        private final StoneGhast ghast;
        private int floatDuration;

        public StoneGhastMoveControl(StoneGhast ghast) {
            super(ghast);
            this.ghast = ghast;
        }

        @Override
        public void tick() {
            if (this.operation == MoveControl.Operation.MOVE_TO) {
                if (this.floatDuration-- <= 0) {
                    this.floatDuration += this.ghast.getRandom().nextInt(5) + 2;
                    Vec3 direction = new Vec3(this.wantedX - this.ghast.getX(), this.wantedY - this.ghast.getY(), this.wantedZ - this.ghast.getZ());
                    double distance = direction.length();
                    direction = direction.normalize();
                    if (this.canReach(direction, Mth.ceil(distance))) {
                        this.ghast.setDeltaMovement(this.ghast.getDeltaMovement().add(direction.scale(0.1D)));
                    } else {
                        this.operation = MoveControl.Operation.WAIT;
                    }
                }
            }
        }

        private boolean canReach(Vec3 direction, int steps) {
            AABB boundingBox = this.ghast.getBoundingBox();

            for (int i = 1; i < steps; ++i) {
                boundingBox = boundingBox.move(direction);
                if (!this.ghast.level.noCollision(this.ghast, boundingBox)) {
                    return false;
                }
            }

            return true;
        }
    }

    // Custom random floating goal
    static class StoneGhastRandomFloatAroundGoal extends Goal {
        private final StoneGhast ghast;

        public StoneGhastRandomFloatAroundGoal(StoneGhast ghast) {
            this.ghast = ghast;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            MoveControl moveControl = this.ghast.getMoveControl();
            if (!moveControl.hasWanted()) {
                return true;
            } else {
                double dx = moveControl.getWantedX() - this.ghast.getX();
                double dy = moveControl.getWantedY() - this.ghast.getY();
                double dz = moveControl.getWantedZ() - this.ghast.getZ();
                double distanceSq = dx * dx + dy * dy + dz * dz;
                return distanceSq < 1.0D || distanceSq > 3600.0D;
            }
        }

        @Override
        public boolean canContinueToUse() {
            return false;
        }

        @Override
        public void start() {
            RandomSource random = this.ghast.getRandom();
            double x = this.ghast.getX() + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double y = this.ghast.getY() + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double z = this.ghast.getZ() + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.ghast.getMoveControl().setWantedPosition(x, y, z, 1.0D);
        }
    }

    // Custom look goal
    static class StoneGhastLookGoal extends Goal {
        private final StoneGhast ghast;

        public StoneGhastLookGoal(StoneGhast ghast) {
            this.ghast = ghast;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return true;
        }

        @Override
        public void tick() {
            if (this.ghast.getTarget() == null) {
                Vec3 motion = this.ghast.getDeltaMovement();
                this.ghast.setYRot(-((float) Mth.atan2(motion.x, motion.z)) * (180F / (float) Math.PI));
                this.ghast.yBodyRot = this.ghast.getYRot();
            } else {
                LivingEntity target = this.ghast.getTarget();
                double maxDistance = 64.0D;
                if (target.distanceToSqr(this.ghast) < maxDistance * maxDistance) {
                    double dx = target.getX() - this.ghast.getX();
                    double dz = target.getZ() - this.ghast.getZ();
                    this.ghast.setYRot(-((float) Mth.atan2(dx, dz)) * (180F / (float) Math.PI));
                    this.ghast.yBodyRot = this.ghast.getYRot();
                }
            }
        }
    }

    // Custom shoot fireball goal
    static class StoneGhastShootFireballGoal extends Goal {
        private final StoneGhast ghast;
        public int chargeTime;

        public StoneGhastShootFireballGoal(StoneGhast ghast) {
            this.ghast = ghast;
        }

        @Override
        public boolean canUse() {
            boolean canUse = this.ghast.getTarget() != null;
            return canUse;
        }

        @Override
        public void start() {
            this.chargeTime = 0;
        }

        @Override
        public void stop() {
            this.ghast.setCharging(false);
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            LivingEntity target = this.ghast.getTarget();
            if (target != null) {
                double distanceSq = 64.0D * 64.0D;
                if (this.ghast.distanceToSqr(target) < distanceSq && this.ghast.hasLineOfSight(target)) {
                    Level level = this.ghast.level;
                    ++this.chargeTime;

                    if (this.chargeTime == 10 && !this.ghast.isSilent()) {
                        level.levelEvent(null, 1015, this.ghast.blockPosition(), 0);
                    }

                    if (this.chargeTime == 20) {
                        Vec3 viewVector = this.ghast.getViewVector(1.0F);
                        double offset = 0.5D; // Adjusted offset for StoneGhast's size

                        // Calculate the spawn position of the fireball
                        double x = this.ghast.getX() + viewVector.x * offset;
                        double y = this.ghast.getEyeY() - 0.9D; // Slightly below eye level for realism
                        double z = this.ghast.getZ() + viewVector.z * offset;

                        // Calculate the direction towards the target
                        double dx = target.getX() - x;
                        double dy = target.getEyeY() - y;
                        double dz = target.getZ() - z;

                        // Play the fireball launch sound effect if the ghast is not silent
                        if (!this.ghast.isSilent()) {
                            level.levelEvent(null, 1016, this.ghast.blockPosition(), 0);
                        }

                        // Create and spawn the fireball entity
                        LargeFireball fireball = new LargeFireball(level, this.ghast, dx, dy, dz, this.ghast.getExplosionPower());
                        fireball.setPos(x, y, z);
                        level.addFreshEntity(fireball);
                        this.chargeTime = -40;
                    }

                } else if (this.chargeTime > 0) {
                    --this.chargeTime;
                }

                this.ghast.setCharging(this.chargeTime > 10);
            }
        }
    }
}