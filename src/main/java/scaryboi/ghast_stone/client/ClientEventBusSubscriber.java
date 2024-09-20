package scaryboi.ghast_stone.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import scaryboi.ghast_stone.Ghast_stone;
import scaryboi.ghast_stone.client.renderer.StoneGhastRenderer;
import scaryboi.ghast_stone.init.ModEntities;

@Mod.EventBusSubscriber(modid = Ghast_stone.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.STONE_GHAST.get(), StoneGhastRenderer::new);
    }
}
