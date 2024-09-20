package scaryboi.ghast_stone.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.GhastRenderer;
import net.minecraft.resources.ResourceLocation;
import scaryboi.ghast_stone.Ghast_stone;
import net.minecraft.world.entity.monster.Ghast;  // Use Ghast from the superclass

public class StoneGhastRenderer extends GhastRenderer {
    private static final ResourceLocation STONE_GHAST_TEXTURE = new ResourceLocation(Ghast_stone.MODID, "textures/entity/stone_ghast.png");
    private static final ResourceLocation STONE_GHAST_SHOOTING_TEXTURE = new ResourceLocation(Ghast_stone.MODID, "textures/entity/stone_ghast_shooting.png");

    public StoneGhastRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(Ghast entity) {  // Use Ghast here
        return entity.isCharging() ? STONE_GHAST_SHOOTING_TEXTURE : STONE_GHAST_TEXTURE;
    }

    @Override
    protected void scale(Ghast entity, PoseStack poseStack, float partialTickTime) {  // Use Ghast here
        // Since StoneGhast is smaller, we don’t need to scale it up like a normal ghast.
        // You can keep the scale neutral here.
        poseStack.scale(1.0F, 1.0F, 1.0F); // Scaling to the size of a stone block
    }
}
