package com.hollingsworth.arsnouveau.client.renderer.entity;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ArmorItem;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.core.util.Color;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;
import software.bernie.geckolib3.util.RenderUtils;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class ModelLayerRenderer<T extends Entity & IAnimatable> extends GeoLayerRenderer implements IGeoRenderer<T> {
    AnimatedGeoModel geoModelProvider;
    private static Map<Class<? extends ArmorItem>, GeoArmorRenderer> renderers = new ConcurrentHashMap<>();

    static
    {
        AnimationController.addModelFetcher((IAnimatable object) ->
        {
            if (object instanceof Entity)
            {

            }
            return null;
        });
    }
    public ModelLayerRenderer(IGeoRenderer<T> entityRendererIn, AnimatedGeoModel modelProvider) {
        super(entityRendererIn);
        this.geoModelProvider = modelProvider;
    }

    @Override
    public AnimatedGeoModel<T> getGeoModelProvider() {
        return geoModelProvider;
    }

    @Override
    public ResourceLocation getTextureLocation(T instance) {
        return geoModelProvider.getTextureLocation(instance);
    }



    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, Entity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

        matrixStackIn.pushPose();
        EntityModelData entityModelData = new EntityModelData();
//        entityModelData.headPitch = headPitch;
//        entityModelData.netHeadYaw = netHeadYaw;
        GeoModel model = geoModelProvider.getModel(geoModelProvider.getModelLocation(entity));
        AnimationEvent predicate = new AnimationEvent((IAnimatable)entity, limbSwing, limbSwingAmount, partialTicks, !(limbSwingAmount > -0.15F && limbSwingAmount < 0.15F), Collections.singletonList(entityModelData));
        IBone carbuncle = ((StarbuncleModel)getEntityModel()).getBone("starbuncle");
//        geoModelProvider.setLivingAnimations((IAnimatable) entity, this.getUniqueID((T) entity), predicate);
        IBone head = ((StarbuncleModel)getEntityModel()).getBone("head");

        RenderUtils.translate((GeoBone) head, matrixStackIn);
        RenderUtils.moveToPivot((GeoBone) head, matrixStackIn);
        RenderUtils.rotate((GeoBone) head, matrixStackIn);

//        matrixStackIn.translate((carbuncle.getPositionX())/32f, (carbuncle.getPositionY())/16f ,
//                (carbuncle.getPositionZ()));
//
////        matrixStackIn.translate((double)0f, (double)0.5f, .2D);
////        matrixStackIn.scale(0.75f, 0.75f, 0.75f);
//
//        Quaternion quaternion = Vector3f.ZP.rotationDegrees(carbuncle.getRotationZ());
//
//        matrixStackIn.mulPose(quaternion);
       // matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180f));


        RenderSystem.setShaderTexture(0, getTextureLocation((T)entity));
        Color renderColor = getRenderColor((T) entity, partialTicks, matrixStackIn, bufferIn, null, packedLightIn);
        RenderType renderType = getRenderType((T) entity, partialTicks, matrixStackIn, bufferIn, null, packedLightIn, getTextureLocation((T) entity));
        render(model, (T) entity, partialTicks, renderType, matrixStackIn, bufferIn, null, packedLightIn, OverlayTexture.NO_OVERLAY, (float) renderColor.getRed() / 255f, (float) renderColor.getGreen() / 255f, (float) renderColor.getBlue() / 255f, (float) renderColor.getAlpha() / 255);
        matrixStackIn.popPose();
    }

}
