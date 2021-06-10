package com.trikzon.armor_visibility.mixin.client;

import com.trikzon.armor_visibility.ArmorVisibility;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ElytraFeatureRenderer.class)
public abstract class ElytraFeatureRendererMixin
        <T extends LivingEntity, M extends EntityModel<T>>
        extends FeatureRenderer<T, M> {

    public ElytraFeatureRendererMixin(FeatureRendererContext<T, M> featureRendererContext) {
        super(featureRendererContext);
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void armor_visibility_renderElytra(
            MatrixStack matrixStack,
            VertexConsumerProvider vertexConsumerProvider,
            int i,
            T livingEntity,
            float f, float g, float h, float j, float k, float l,
            CallbackInfo ci
    ) {
        if (!ArmorVisibility.save.keepElytraVisible) {
            if (livingEntity instanceof PlayerEntity) {
                if (!ArmorVisibility.save.allArmorVisibilityToggle) {
                    ci.cancel();
                }
                if (!ArmorVisibility.save.myArmorVisibilityToggle) {
                    if (livingEntity.equals(MinecraftClient.getInstance().player)) {
                        ci.cancel();
                    }
                }
            }
        }
    }
}
