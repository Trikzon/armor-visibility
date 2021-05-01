package com.trikzon.armor_visibility.mixin.client;

import com.trikzon.armor_visibility.ArmorVisibility;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorFeatureRenderer.class)
public abstract class ArmorFeatureRendererMixin
        <T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>>
        extends FeatureRenderer<T, M> {

    public ArmorFeatureRendererMixin(FeatureRendererContext<T, M> featureRendererContext) {
        super(featureRendererContext);
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void armor_visibility_renderArmor(
            MatrixStack matrixStack,
            VertexConsumerProvider vertexConsumerProvider,
            int i,
            T livingEntity,
            float f, float g, float h, float j, float k, float l,
            CallbackInfo ci
    ) {
        if (livingEntity instanceof PlayerEntity) {
            if (!ArmorVisibility.save.all_armor_visibility_toggle) {
                ci.cancel();
            }
            if (
                !ArmorVisibility.save.my_armor_visibility_toggle &&
                livingEntity.equals(MinecraftClient.getInstance().player
            )) {
                ci.cancel();
            }
        }
    }
}
