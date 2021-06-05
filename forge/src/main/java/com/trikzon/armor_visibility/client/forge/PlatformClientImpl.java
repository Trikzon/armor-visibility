package com.trikzon.armor_visibility.client.forge;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PlatformClientImpl {
    private static final List<KeyBinding> keyBindings = new ArrayList<>();

    public static KeyBinding registerKeyBinding(Identifier id, int key, String category) {
        KeyBinding keyBinding = new KeyBinding(
                String.format("key.%s.%s", id.getNamespace(), id.getPath()),
                InputUtil.Type.KEYSYM,
                key,
                category
        );
        keyBindings.add(keyBinding);
        return keyBinding;
    }

    public static void registerClientTickEvent(Consumer<MinecraftClient> callback) {
        MinecraftForge.EVENT_BUS.<TickEvent.ClientTickEvent>addListener(e -> {
            callback.accept(MinecraftClient.getInstance());
        });
    }

    public static void onClientSetup(final FMLClientSetupEvent event) {
        for (KeyBinding keyBinding : keyBindings) {
            ClientRegistry.registerKeyBinding(keyBinding);
        }
    }

    public static void registerJoinEvent(Consumer<ClientPlayerEntity> callback) {
        MinecraftForge.EVENT_BUS.<EntityJoinWorldEvent>addListener(e -> {
            Entity entity = e.getEntity();
            if (entity instanceof ClientPlayerEntity) {
                callback.accept((ClientPlayerEntity) entity);
            }
        });
    }
}
