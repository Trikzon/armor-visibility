/*
 * This file is part of Armor Visibility.
 * A copy of this program can be found at https://github.com/Trikzon/armor-visibility.
 * Copyright (C) 2023 Dion Tryban
 *
 * Armor Visibility is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * Armor Visibility is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Armor Visibility. If not, see <https://www.gnu.org/licenses/>.
 */

package com.diontryban.armor_visibility.mixin.client;

import com.diontryban.armor_visibility.client.ArmorVisibilityClient;
import com.mojang.blaze3d.vertex.PoseStack;
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

    @Inject(at = @At("HEAD"), cancellable = true, method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;FFFFFF)V")
    private void injectBeforeRender(
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            int i,
            AbstractClientPlayer player,
            float f, float g, float h, float j, float k, float l,
            CallbackInfo ci
    ) {
        if (ArmorVisibilityClient.config.keepCapeVisible) {
            return;
        }

        ArmorVisibilityClient.maybeCancelRender(player, ci);
    }
}
