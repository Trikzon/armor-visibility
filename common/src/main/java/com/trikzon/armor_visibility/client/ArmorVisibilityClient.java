package com.trikzon.armor_visibility.client;

import com.trikzon.armor_visibility.ArmorVisibility;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
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
        PlatformClient.registerJoinEvent(ArmorVisibilityClient::onJoin);
    }

    private static void onClientTick(MinecraftClient client) {
        if (client.player == null) {
            return;
        }

        if (keyBinding.isPressed() && !keyWasDown) {
            keyWasDown = true;

            client.player.playSound(SoundEvents.BLOCK_TRIPWIRE_CLICK_ON, 0.5f, 1.0f);

            if (client.player.isSneaking()) {
                ArmorVisibility.save.all_armor_visibility_toggle = !ArmorVisibility.save.all_armor_visibility_toggle;
                client.player.addChatMessage(new TranslatableText(
                        "message." +
                            ArmorVisibility.MOD_ID +
                            ".all_armor_visibility_toggle." +
                            ArmorVisibility.save.all_armor_visibility_toggle
                ), true);
                ArmorVisibility.writeSaveFile();
                return;
            }

            ArmorVisibility.save.my_armor_visibility_toggle = !ArmorVisibility.save.my_armor_visibility_toggle;
            client.player.addChatMessage(new TranslatableText(
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

    private static void onJoin(ClientPlayerEntity player) {
        if (ArmorVisibility.save.show_join_message) {
            player.sendMessage(new TranslatableText(
                    "message." + ArmorVisibility.MOD_ID + ".join",
                    visibleToString(ArmorVisibility.save.my_armor_visibility_toggle),
                    visibleToString(ArmorVisibility.save.all_armor_visibility_toggle)
            ));
        }
    }

    private static String visibleToString(boolean visible) {
        return visible ? "Visible" : "Invisible";
    }
}
