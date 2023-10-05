package com.diontryban.armor_visibility.mixin.client;

import com.diontryban.armor_visibility.ArmorVisibility;
import com.diontryban.armor_visibility.client.ArmorVisibilityClient;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerModel.class)
public abstract class PlayerModelMixin {

    @Final
    @Shadow
    private ModelPart cloak;

    @Inject(at = @At("TAIL"), method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V")
    public void setupCloakAnim(LivingEntity entity, float walkPosition, float walkSpeed, float bob, float yRotation, float xRotation, CallbackInfo ci) {
        if (ArmorVisibility.OPTIONS.get().togglesChestplate) { // Check if the chestplate can be toggled
            ArmorVisibilityClient.maybeCancelRender(entity, () -> { // If the armor is hidden, change cloak location
                if (entity.isCrouching()) {
                    this.cloak.z = 1.4F;
                    this.cloak.y = 1.85F;
                } else {
                    this.cloak.z = 0.0F;
                    this.cloak.y = 0.0F;
                }
            });
        }
    }
}
