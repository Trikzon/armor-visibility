package com.trikzon.armor_visibility.client;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class PlatformClient {
    @ExpectPlatform
    public static KeyBinding registerKeyBinding(Identifier id, int key, String category) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerClientTickEvent(Consumer<MinecraftClient> callback) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerJoinEvent(Consumer<ClientPlayerEntity> callback) {
        throw new AssertionError();
    }
}
