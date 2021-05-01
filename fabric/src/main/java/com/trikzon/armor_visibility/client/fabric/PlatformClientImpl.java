package com.trikzon.armor_visibility.client.fabric;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
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
}
