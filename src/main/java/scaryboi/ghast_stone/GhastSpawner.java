package scaryboi.ghast_stone;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import scaryboi.ghast_stone.entity.StoneGhast;
import scaryboi.ghast_stone.init.ModEntities;

public class GhastSpawner {

    public static void spawnStoneGhast(ServerLevel level, BlockPos pos, Player player) {
        StoneGhast ghast = ModEntities.STONE_GHAST.get().create(level);
        if (ghast != null) {
            // Position the ghast at the block position
            double x = pos.getX() + 0.5;
            double y = pos.getY() + 0.09; // Adjusted Y position
            double z = pos.getZ() + 0.5;
            ghast.setPos(x, y, z);

            // Make the ghast face the player
            Vec3 ghastPosition = ghast.position();
            Vec3 playerPosition = player.position();
            double dx = playerPosition.x - ghastPosition.x;
            double dz = playerPosition.z - ghastPosition.z;
            float yaw = (float)(Math.atan2(dz, dx) * (180F / Math.PI)) - 90F;
            ghast.setYRot(yaw);
            ghast.yHeadRot = yaw;
            ghast.yBodyRot = yaw;

            // Set the ghast's target to the player
            ghast.setTarget(player);

            // Set initial charge time to make it shoot immediately
            ghast.initialChargeTime = 19;

            // Add the ghast to the world
            level.addFreshEntity(ghast);
        }
    }
}
