package com.trikzon.armor_visibility.client;

import com.trikzon.armor_visibility.ArmorVisibility;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public final class ArmorVisibilityClient {
    private static KeyBinding keyBinding;
    private static boolean keyWasDown;

    public static void initialize() {
        ArmorVisibilityClient.keyBinding = PlatformClient.registerKeyBinding(
                new Identifier(ArmorVisibility.MOD_ID, "armor_visibility_toggle"),
                GLFW.GLFW_KEY_V,
                "key.categories.movement"
        );
        PlatformClient.registerClientTickEvent(ArmorVisibilityClient::onClientTick);
    }

    private static void onClientTick(MinecraftClient client) {
        if (client.player == null) {
            return;
        }

        client.player.playSound(SoundEvents.BLOCK_TRIPWIRE_CLICK_ON, 0.5f, 1.0f);

        if (keyBinding.isPressed() && !keyWasDown) {
            keyWasDown = true;

            if (client.player.isSneaking()) {
                ArmorVisibility.save.all_armor_visibility_toggle = !ArmorVisibility.save.all_armor_visibility_toggle;
                client.player.sendMessage(new TranslatableText(
                        "message." +
                            ArmorVisibility.MOD_ID +
                            ".all_armor_visibility_toggle." +
                            ArmorVisibility.save.all_armor_visibility_toggle
                ), true);
                ArmorVisibility.writeSaveFile();
                return;
            }

            ArmorVisibility.save.my_armor_visibility_toggle = !ArmorVisibility.save.my_armor_visibility_toggle;
            client.player.sendMessage(new TranslatableText(
                    "message." +
                        ArmorVisibility.MOD_ID +
                        ".my_armor_visibility_toggle." +
                        ArmorVisibility.save.my_armor_visibility_toggle
            ), true);
            ArmorVisibility.writeSaveFile();

        } else if (!keyBinding.isPressed() && keyWasDown) {
            keyWasDown = false;
        }
    }
}
