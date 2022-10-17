package com.trikzon.armor_visibility.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.trikzon.armor_visibility.ArmorVisibility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidArmorLayer.class)
public abstract class HumanoidArmorLayerMixin
        <T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>>
        extends RenderLayer<T, M> {

    public HumanoidArmorLayerMixin(RenderLayerParent<T, M> renderLayerParent) {
        super(renderLayerParent);
    }

    @Inject(
            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void armor_visibility_renderArmor(
            PoseStack poseStack,
            MultiBufferSource multiBufferSource,
            int i,
            T livingEntity,
            float f, float g, float h, float j, float k, float l,
            CallbackInfo ci
    ) {
        if (ArmorVisibility.saveFile.playersOnly && !(livingEntity instanceof Player)) return;

        if (ArmorVisibility.saveFile.hideAllArmorToggle) {
            ci.cancel();
        } else if (ArmorVisibility.saveFile.hideMyArmorToggle) {
            if (livingEntity.equals(Minecraft.getInstance().player)) {
                ci.cancel();
            }
        }
    }
}
