package com.trikzon.armor_visibility.client;

import com.trikzon.armor_visibility.ArmorVisibility;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import org.lwjgl.glfw.GLFW;

public class ArmorVisibilityClient {
    private static KeyMapping keyMapping;
    private static boolean keyWasDown;

    public static void initialize() {
        ArmorVisibilityClient.keyMapping = PlatformClient.registerKeyMapping(
                new ResourceLocation(ArmorVisibility.MOD_ID, "armor_visibility_toggle"),
                GLFW.GLFW_KEY_V,
                "key.categories." + ArmorVisibility.MOD_ID
        );
        PlatformClient.registerClientTickEvent(ArmorVisibilityClient::onClientTick);
        PlatformClient.registerJoinEvent(ArmorVisibilityClient::onJoin);
    }

    private static void onClientTick(Minecraft client) {
        if (client.player == null) {
            return;
        }

        if (keyMapping.isDown() && !keyWasDown) {
            keyWasDown = true;

            if (ArmorVisibility.saveFile.hideAllArmorToggle || ArmorVisibility.saveFile.hideMyArmorToggle) {
                client.player.playSound(SoundEvents.TRIPWIRE_CLICK_OFF, 0.5f, 1.0f);

                ArmorVisibility.saveFile.hideAllArmorToggle = false;
                ArmorVisibility.saveFile.hideMyArmorToggle = false;

                client.player.displayClientMessage(Component.translatable(
                        "message." +
                                ArmorVisibility.MOD_ID +
                                ".show_armor"
                ), true);
            } else {
                client.player.playSound(SoundEvents.TRIPWIRE_CLICK_ON, 0.5f, 1.0f);

                if (client.player.isShiftKeyDown()) {
                    ArmorVisibility.saveFile.hideAllArmorToggle = true;
                    client.player.displayClientMessage(Component.translatable(
                            "message." +
                                    ArmorVisibility.MOD_ID +
                                    ".hide_all_armor"
                    ), true);
                } else {
                    ArmorVisibility.saveFile.hideMyArmorToggle = true;
                    client.player.displayClientMessage(Component.translatable(
                            "message." +
                                    ArmorVisibility.MOD_ID +
                                    ".hide_my_armor"
                    ), true);
                }
            }
            ArmorVisibility.writeSaveFile();
        } else if (!keyMapping.isDown() && keyWasDown) {
            keyWasDown = false;
        }
    }

    private static void onJoin(LocalPlayer player) {
        if (ArmorVisibility.saveFile.showJoinMessage && isArmorHidden()) {
            player.sendSystemMessage(Component.translatable(
                    "message." +
                            ArmorVisibility.MOD_ID +
                            "." +
                            (ArmorVisibility.saveFile.hideAllArmorToggle ? "all" : "my") +
                            "_join"
            ));
        }
    }

    private static boolean isArmorHidden() {
        return ArmorVisibility.saveFile.hideAllArmorToggle || ArmorVisibility.saveFile.hideMyArmorToggle;
    }
}
