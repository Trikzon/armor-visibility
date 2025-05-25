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
import com.diontryban.ash_api.client.event.ClientTickEvent;
import com.diontryban.ash_api.client.gui.screens.ModOptionsScreenRegistry;
import com.diontryban.ash_api.client.input.KeyMappingRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import org.lwjgl.glfw.GLFW;

public class ArmorVisibilityClient {
    private static KeyMapping keyMapping;

    private static boolean keyWasDown = false;

    public static void init() {
        keyMapping = KeyMappingRegistry.register(
                ResourceLocation.fromNamespaceAndPath(ArmorVisibility.MOD_ID, "armor_visibility_toggle"),
                GLFW.GLFW_KEY_V,
                ArmorVisibility.MOD_ID
        );

        ClientTickEvent.Pre.register(ArmorVisibilityClient::onClientStartTick);
        ModOptionsScreenRegistry.register(ArmorVisibility.OPTIONS, ArmorVisibilityOptionsScreen::new);

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

        if (keyMapping.isDown() && !keyWasDown) {
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
        } else if (!keyMapping.isDown() && keyWasDown) {
            keyWasDown = false;
        }
    }

    public static boolean maybeCancelRender(LivingEntityRenderState renderState, Runnable onCancel) {
        var options = ArmorVisibility.OPTIONS.get();

        if (options.playersOnly && !(renderState instanceof PlayerRenderState)) {
            return false;
        }

        if (options.saveData.hideAllArmor) {
            onCancel.run();
            return true;
        } else if (options.saveData.hideMyArmor && renderState instanceof PlayerRenderState playerRenderState) {
            if (playerRenderState.id == Minecraft.getInstance().player.getId()) {
                onCancel.run();
                return true;
            }
        }

        return false;
    }
}
