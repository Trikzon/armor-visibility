package com.trikzon.armor_visibility.client;

import com.trikzon.armor_visibility.ArmorVisibility;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import org.lwjgl.glfw.GLFW;

import java.util.UUID;

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

            // TODO: Change sound depending on whether being toggled on or off.
            client.player.playSound(SoundEvents.TRIPWIRE_CLICK_ON, 0.5f, 1.0f);

            if (client.player.isShiftKeyDown()) {
                ArmorVisibility.saveFile.allArmorVisibilityToggle = !ArmorVisibility.saveFile.allArmorVisibilityToggle;
                client.player.displayClientMessage(new TranslatableComponent(
                        "message." +
                                ArmorVisibility.MOD_ID +
                                ".all_armor_visibility_toggle." +
                                ArmorVisibility.saveFile.allArmorVisibilityToggle
                ), true);
                ArmorVisibility.writeSaveFile();
                return;
            }
            ArmorVisibility.saveFile.myArmorVisibilityToggle = !ArmorVisibility.saveFile.myArmorVisibilityToggle;
            client.player.displayClientMessage(new TranslatableComponent(
                    "message." +
                            ArmorVisibility.MOD_ID +
                            ".my_armor_visibility_toggle." +
                            ArmorVisibility.saveFile.myArmorVisibilityToggle
            ), true);
            ArmorVisibility.writeSaveFile();
        } else if (!keyMapping.isDown() && keyWasDown) {
            keyWasDown = false;
        }
    }

    private static void onJoin(LocalPlayer player) {
        if (ArmorVisibility.saveFile.showJoinMessage && isVisibilityToggled()) {
            player.sendMessage(new TranslatableComponent(
                    "message." + ArmorVisibility.MOD_ID + ".join",
                    visibleToString(ArmorVisibility.saveFile.myArmorVisibilityToggle),
                    visibleToString(ArmorVisibility.saveFile.allArmorVisibilityToggle)
            ), UUID.randomUUID());
        }
    }

    private static boolean isVisibilityToggled() {
        return !ArmorVisibility.saveFile.myArmorVisibilityToggle || !ArmorVisibility.saveFile.allArmorVisibilityToggle;
    }

    private static String visibleToString(boolean visible) {
        return visible ? "Visible" : "Invisible";
    }
}
