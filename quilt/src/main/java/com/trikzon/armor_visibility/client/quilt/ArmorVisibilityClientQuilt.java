package com.trikzon.armor_visibility.client.quilt;

import com.trikzon.armor_visibility.ArmorVisibility;
import com.trikzon.armor_visibility.client.ArmorVisibilityClient;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

public class ArmorVisibilityClientQuilt implements ClientModInitializer {
    @Override
    public void onInitializeClient(ModContainer mod) {
        ArmorVisibility.initialize();
        ArmorVisibilityClient.initialize();
    }
}
