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

import com.diontryban.armor_visibility.ArmorVisibility;
import com.diontryban.armor_visibility.client.ArmorVisibilityClient;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CapeLayer.class)
public abstract class CapeLayerMixin extends RenderLayer<PlayerRenderState, PlayerModel> {
    @Unique
    private PlayerRenderState armorVisibility$renderState;

    public CapeLayerMixin(RenderLayerParent<PlayerRenderState, PlayerModel> renderer) {
        super(renderer);
    }

    @Inject(at = @At("HEAD"), cancellable = true, method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/state/PlayerRenderState;FF)V")
    private void injectBeforeRender(
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            int packedLight,
            PlayerRenderState renderState,
            float yRot,
            float xRot,
            CallbackInfo ci
    ) {
        armorVisibility$renderState = renderState;

        if (ArmorVisibility.OPTIONS.get().keepCapeVisible) {
            return;
        }

        ArmorVisibilityClient.maybeCancelRender(renderState, ci::cancel);
    }

    // Render the player's cape if the player is wearing an elytra,
    // keepCapeVisible is true, and the elytra has been made invisible.
    @WrapOperation(
            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/state/PlayerRenderState;FF)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/CapeLayer;hasLayer(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/resources/model/EquipmentClientInfo$LayerType;)Z")
    )
    private boolean redirectHasLayer(
            CapeLayer instance,
            ItemStack itemStack,
            EquipmentClientInfo.LayerType stack,
            Operation<Boolean> original
    ) {
        // If it's not WINGS or HUMANOID, we don't know about it.
        if (stack != EquipmentClientInfo.LayerType.WINGS && stack != EquipmentClientInfo.LayerType.HUMANOID) {
            return original.call(instance, itemStack, stack);
        }

        // If it's HUMANOID (chest plate), we return the original if we're not hiding the chestplate.
        if (stack == EquipmentClientInfo.LayerType.HUMANOID && !ArmorVisibility.OPTIONS.get().togglesChestplate) {
            return original.call(instance, itemStack, stack);
        }

        return original.call(instance, itemStack, stack) && !ArmorVisibilityClient.maybeCancelRender(
                armorVisibility$renderState, () -> {}
        );
    }

    // Uncomment code below to give all players a debug cape.
//    @WrapOperation(
//            method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/state/PlayerRenderState;FF)V",
//            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/PlayerSkin;capeTexture()Lnet/minecraft/resources/ResourceLocation;")
//    )
//    public ResourceLocation redirectCapeTextureInRender(PlayerSkin instance, Operation<ResourceLocation> original) {
//        return ResourceLocation.fromNamespaceAndPath(ArmorVisibility.MOD_ID, "textures/cape.png");
//    }
}
