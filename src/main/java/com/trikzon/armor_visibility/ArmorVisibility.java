/*
 * Copyright 2020 Trikzon
 *
 * Armor Visibility is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * https://www.gnu.org/licenses/
 */
package com.trikzon.armor_visibility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ArmorVisibility implements ClientModInitializer
{
    public static final String MOD_ID = "armor-visibility";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    private static final File MOD_CONFIG_FILE = new File("./config/" + MOD_ID + ".json");
    public static ConfigBean CONFIG = new ConfigBean();

    private static FabricKeyBinding keyBinding;
    private static boolean keyWasDown;

    @Override
    public void onInitializeClient()
    {
        if (!MOD_CONFIG_FILE.exists())
        {
            writeConfigFile();
        }
        else
        {
            readConfigFile();
        }

        keyBinding = FabricKeyBinding.Builder.create(
                new Identifier(MOD_ID, "armor_visible_toggle"),
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                "category." + MOD_ID
        ).build();
        KeyBindingRegistry.INSTANCE.register(keyBinding);
        KeyBindingRegistry.INSTANCE.addCategory("category." + MOD_ID);

        ClientTickCallback.EVENT.register(this::onClientTick);
    }

    private void onClientTick(MinecraftClient client)
    {
        if (client.player == null) return;
        ClientPlayerEntity player = client.player;

        if (keyBinding.isPressed() && !keyWasDown)
        {
            keyWasDown = true;
            if (player.isSneaking())
            {
                CONFIG.allArmorInvis = !CONFIG.allArmorInvis;
                player.addChatMessage(new TranslatableText(
                        "message." + MOD_ID + ".all_armor_invis." + CONFIG.allArmorInvis
                ), true);
                writeConfigFile();
                return;
            }
            CONFIG.myArmorInvis = !CONFIG.myArmorInvis;
            player.addChatMessage(new TranslatableText(
                    "message." + MOD_ID + ".my_armor_invis." + CONFIG.myArmorInvis
            ), true);
            writeConfigFile();
        }
        else if (!keyBinding.isPressed() && keyWasDown)
        {
            keyWasDown = false;
        }
    }

    private void writeConfigFile()
    {
        MOD_CONFIG_FILE.getParentFile().mkdirs();
        try (FileWriter file = new FileWriter(MOD_CONFIG_FILE))
        {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            file.write(gson.toJson(CONFIG));
            file.flush();
        }
        catch (IOException e)
        {
            LOGGER.error("Failed to write to config file.");
        }
    }

    private void readConfigFile()
    {
        try (FileReader file = new FileReader(MOD_CONFIG_FILE))
        {
            Gson gson = new Gson();
            CONFIG = gson.fromJson(file, ConfigBean.class);
        }
        catch (IOException e)
        {
            LOGGER.error("Failed to read from config file.");
        }
    }

    public static class ConfigBean
    {
        public boolean myArmorInvis;
        public boolean allArmorInvis;
    }
}
