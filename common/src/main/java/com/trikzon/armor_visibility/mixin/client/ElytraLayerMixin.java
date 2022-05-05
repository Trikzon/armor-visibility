package com.trikzon.armor_visibility.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.trikzon.armor_visibility.ArmorVisibility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ElytraLayer.class)
public abstract class ElytraLayerMixin<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    public ElytraLayerMixin(RenderLayerParent<T, M> renderLayerParent) {
        super(renderLayerParent);
    }

    @Inject(
            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void armor_visibility_renderElytra(
            PoseStack poseStack,
            MultiBufferSource multiBufferSource,
            int i,
            T livingEntity,
            float f, float g, float h, float j, float k, float l,
            CallbackInfo ci
    ) {
        if (!ArmorVisibility.saveFile.keepElytraVisible) {
            if (livingEntity instanceof Player) {
                if (!ArmorVisibility.saveFile.allArmorVisibilityToggle) {
                    ci.cancel();
                }
                if (!ArmorVisibility.saveFile.myArmorVisibilityToggle) {
                    if (livingEntity.equals(Minecraft.getInstance().player)) {
                        ci.cancel();
                    }
                }
            }
        }
    }
}
