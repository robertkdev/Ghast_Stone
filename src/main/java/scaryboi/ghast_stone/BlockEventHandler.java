package scaryboi.ghast_stone;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scaryboi.ghast_stone.blocks.ModBlocks;


public class BlockEventHandler {

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {  // Removed 'static' keyword
        if (!event.getLevel().isClientSide()) {
            ServerLevel level = (ServerLevel) event.getLevel();
            BlockPos pos = event.getPos();

            // Get the block that was broken
            if (event.getState().getBlock() == ModBlocks.GHAST_STONE.get()) {
                GhastSpawner.spawnStoneGhast(level, pos, event.getPlayer());
            }
        }
    }
}
