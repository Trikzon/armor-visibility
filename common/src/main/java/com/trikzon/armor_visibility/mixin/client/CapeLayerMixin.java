package com.trikzon.armor_visibility.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.trikzon.armor_visibility.ArmorVisibility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CapeLayer.class)
public abstract class CapeLayerMixin extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    public CapeLayerMixin(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderLayerParent) {
        super(renderLayerParent);
    }

    @Inject(
            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;FFFFFF)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void armor_visibility_renderCape(
            PoseStack poseStack,
            MultiBufferSource multiBufferSource,
            int i,
            AbstractClientPlayer abstractClientPlayer,
            float f, float g, float h, float j, float k, float l,
            CallbackInfo ci
    ) {
        if (ArmorVisibility.saveFile.keepCapeVisible) return;

        if (ArmorVisibility.saveFile.hideAllArmorToggle) {
            ci.cancel();
        } else if (ArmorVisibility.saveFile.hideMyArmorToggle) {
            if (abstractClientPlayer.equals(Minecraft.getInstance().player)) {
                ci.cancel();
            }
        }
    }
}
