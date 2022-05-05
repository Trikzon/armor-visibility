package com.trikzon.armor_visibility.client;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

public class PlatformClient {
    @ExpectPlatform
    public static KeyMapping registerKeyMapping(ResourceLocation resLoc, int key, String category) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerClientTickEvent(Consumer<Minecraft> callback) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerJoinEvent(Consumer<LocalPlayer> callback) {
        throw new AssertionError();
    }
}
