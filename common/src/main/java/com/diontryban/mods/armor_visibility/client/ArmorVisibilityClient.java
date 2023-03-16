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

package com.diontryban.mods.armor_visibility.client;

import com.diontryban.mods.armor_visibility.ArmorVisibility;
import com.diontryban.mods.ash.api.client.event.ClientTickEvents;
import com.diontryban.mods.ash.api.client.input.KeyMappingRegistry;
import com.diontryban.mods.ash.api.modloader.ModLoader;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

public class ArmorVisibilityClient {
    private static final File MOD_CONFIG_FILE = ModLoader
            .getConfigDir()
            .resolve(ArmorVisibility.MOD_ID + ".json")
            .toFile();

    private static final KeyMapping KEY = KeyMappingRegistry.registerKeyMapping(
            new ResourceLocation(ArmorVisibility.MOD_ID, "armor_visibility_toggle"),
            GLFW.GLFW_KEY_V,
            ArmorVisibility.MOD_ID
    );

    private static boolean keyWasDown = false;

    public static Config config;
    public static boolean hideMyArmor;
    public static boolean hideAllArmor;

    public static void init() {
        config = Config.read(MOD_CONFIG_FILE);
        ClientTickEvents.registerStart(ArmorVisibilityClient::onClientStartTick);
    }

    private static void onClientStartTick(Minecraft client) {
        final LocalPlayer player = client.player;
        if (player == null) { return; }

        if (KEY.isDown() && !keyWasDown) {
            if (hideAllArmor || hideMyArmor) {
                player.playSound(SoundEvents.TRIPWIRE_CLICK_OFF, 0.5f, 1.0f);

                hideMyArmor = false;
                hideAllArmor = false;

                player.displayClientMessage(Component.translatable(
                        "message." + ArmorVisibility.MOD_ID + ".show_armor"
                ), true);
            } else {
                player.playSound(SoundEvents.TRIPWIRE_CLICK_ON, 0.5f, 1.0f);

                if (player.isShiftKeyDown()) {
                    hideAllArmor = true;

                    player.displayClientMessage(Component.translatable(
                            "message." + ArmorVisibility.MOD_ID + ".hide_all_armor"
                    ), true);
                } else {
                    hideMyArmor = true;

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
        if (config.playersOnly && !(livingEntity instanceof Player)) {
            return;
        }

        if (hideAllArmor) {
            ci.cancel();
        } else if (hideMyArmor) {
            if (livingEntity.equals(Minecraft.getInstance().player)) {
                ci.cancel();
            }
        }
    }
}
