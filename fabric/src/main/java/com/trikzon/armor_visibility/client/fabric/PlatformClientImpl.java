package com.trikzon.armor_visibility.client.fabric;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class PlatformClientImpl {
    public static KeyBinding registerKeyBinding(Identifier id, int key, String category) {
        return KeyBindingHelper.registerKeyBinding(new KeyBinding(
                String.format("key.%s.%s", id.getNamespace(), id.getPath()),
                InputUtil.Type.KEYSYM,
                key,
                category
        ));
    }

    public static void registerClientTickEvent(Consumer<MinecraftClient> callback) {
        ClientTickEvents.END_CLIENT_TICK.register(callback::accept);
    }

    public static void registerJoinEvent(Consumer<ClientPlayerEntity> callback) {
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> callback.accept(client.player));
    }
}
