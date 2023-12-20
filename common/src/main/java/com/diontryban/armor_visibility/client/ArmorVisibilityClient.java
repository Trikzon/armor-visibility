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

package com.diontryban.armor_visibility.client;

import com.diontryban.armor_visibility.ArmorVisibility;
import com.diontryban.armor_visibility.client.gui.screens.ArmorVisibilityOptionsScreen;
import com.diontryban.ash_api.client.event.ClientTickEvents;
import com.diontryban.ash_api.client.gui.screens.ModOptionsScreenRegistry;
import com.diontryban.ash_api.client.input.KeyMappingRegistry;
import com.diontryban.ash_api.modloader.CommonClientModInitializer;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class ArmorVisibilityClient extends CommonClientModInitializer {
    private static final KeyMapping KEY = KeyMappingRegistry.registerKeyMapping(
            new ResourceLocation(ArmorVisibility.MOD_ID, "armor_visibility_toggle"),
            GLFW.GLFW_KEY_V,
            ArmorVisibility.MOD_ID
    );

    private static boolean keyWasDown = false;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.registerStart(ArmorVisibilityClient::onClientStartTick);
        ModOptionsScreenRegistry.registerModOptionsScreen(ArmorVisibility.OPTIONS, ArmorVisibilityOptionsScreen::new);

        var options = ArmorVisibility.OPTIONS.get();
        if (!options.saveBetweenLaunches) {
            options.saveData.hideMyArmor = false;
            options.saveData.hideAllArmor = false;
        }
    }

    private static void onClientStartTick(Minecraft client) {
        final var player = client.player;
        if (player == null) { return; }

        var options = ArmorVisibility.OPTIONS.get();

        if (KEY.isDown() && !keyWasDown) {
            if (options.saveData.hideAllArmor || options.saveData.hideMyArmor) {
                player.playSound(SoundEvents.TRIPWIRE_CLICK_OFF, 0.5f, 1.0f);

                options.saveData.hideMyArmor = false;
                options.saveData.hideAllArmor = false;
                ArmorVisibility.OPTIONS.write();

                player.displayClientMessage(Component.translatable(
                        "message." + ArmorVisibility.MOD_ID + ".show_armor"
                ), true);
            } else {
                player.playSound(SoundEvents.TRIPWIRE_CLICK_ON, 0.5f, 1.0f);

                if (player.isShiftKeyDown()) {
                    options.saveData.hideAllArmor = true;
                    ArmorVisibility.OPTIONS.write();

                    player.displayClientMessage(Component.translatable(
                            "message." + ArmorVisibility.MOD_ID + ".hide_all_armor"
                    ), true);
                } else {
                    options.saveData.hideMyArmor = true;
                    ArmorVisibility.OPTIONS.write();

                    player.displayClientMessage(Component.translatable(
                            "message." + ArmorVisibility.MOD_ID + ".hide_my_armor"
                    ), true);
                }
            }
            keyWasDown = true;
        } else if (!KEY.isDown() && keyWasDown) {
            keyWasDown = false;
        }
    }

    public static void maybeCancelRender(LivingEntity livingEntity, CallbackInfo ci) {
        maybeCancelRender(livingEntity, ci::cancel);
    }

    public static void maybeCancelRender(LivingEntity livingEntity, Runnable onCancel) {
        var options = ArmorVisibility.OPTIONS.get();

        if (options.playersOnly && !(livingEntity instanceof Player)) {
            return;
        }

        if (options.saveData.hideAllArmor) {
            onCancel.run();
        } else if (options.saveData.hideMyArmor) {
            if (livingEntity.equals(Minecraft.getInstance().player)) {
                onCancel.run();
            }
        }
    }
}
