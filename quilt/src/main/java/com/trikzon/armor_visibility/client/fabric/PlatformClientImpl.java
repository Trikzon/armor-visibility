package com.trikzon.armor_visibility.client.fabric;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import org.quiltmc.qsl.lifecycle.api.client.event.ClientTickEvents;
import org.quiltmc.qsl.networking.api.client.ClientPlayConnectionEvents;

import java.util.function.Consumer;

public class PlatformClientImpl {
    public static KeyMapping registerKeyMapping(ResourceLocation resLoc, int key, String category) {
        return KeyBindingHelper.registerKeyBinding(new KeyMapping(
                String.format("key.%s.%s", resLoc.getNamespace(), resLoc.getPath()),
                InputConstants.Type.KEYSYM,
                key,
                category
        ));
    }

    public static void registerClientTickEvent(Consumer<Minecraft> callback) {
        ClientTickEvents.END.register(callback::accept);
    }

    public static void registerJoinEvent(Consumer<LocalPlayer> callback) {
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> callback.accept(client.player));
    }
}
