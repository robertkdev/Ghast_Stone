package scaryboi.ghast_stone.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import scaryboi.ghast_stone.Ghast_stone;
import scaryboi.ghast_stone.entity.StoneGhast;

public class StoneGhastModel<T extends StoneGhast> extends EntityModel<T> {
    private final ModelPart body;

    public static final ResourceLocation TEXTURE = new ResourceLocation(Ghast_stone.MODID, "textures/entity/stone_ghast.png");

    public StoneGhastModel(ModelPart root) {
        super(RenderType::entityTranslucent);
        this.body = root.getChild("body");
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        // Implement animations if needed
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay);
    }
}
