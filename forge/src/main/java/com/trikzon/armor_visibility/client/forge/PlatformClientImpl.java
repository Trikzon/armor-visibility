package com.trikzon.armor_visibility.client.forge;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PlatformClientImpl {
    private static final List<KeyMapping> keyMappings = new ArrayList<>();

    public static KeyMapping registerKeyMapping(ResourceLocation resLoc, int key, String category) {
        KeyMapping keyMapping = new KeyMapping(
                String.format("key.%s.%s", resLoc.getNamespace(), resLoc.getPath()),
                InputConstants.Type.KEYSYM,
                key,
                category
        );
        keyMappings.add(keyMapping);
        return keyMapping;
    }

    public static void registerClientTickEvent(Consumer<Minecraft> callback) {
        MinecraftForge.EVENT_BUS.<TickEvent.ClientTickEvent>addListener(e -> {
            callback.accept(Minecraft.getInstance());
        });
    }

    public static void onRegisterKeyMappings(final RegisterKeyMappingsEvent event) {
        for (KeyMapping keyMapping : keyMappings) {
            event.register(keyMapping);
        }
    }

    public static void registerJoinEvent(Consumer<LocalPlayer> callback) {
        MinecraftForge.EVENT_BUS.<EntityJoinLevelEvent>addListener(e -> {
            Entity entity = e.getEntity();
            if (entity instanceof LocalPlayer) {
                callback.accept((LocalPlayer) entity);
            }
        });
    }
}
