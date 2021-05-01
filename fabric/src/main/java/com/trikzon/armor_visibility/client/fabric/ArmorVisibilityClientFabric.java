package com.trikzon.armor_visibility.client.fabric;

import com.trikzon.armor_visibility.ArmorVisibility;
import com.trikzon.armor_visibility.client.ArmorVisibilityClient;
import net.fabricmc.api.ClientModInitializer;

public final class ArmorVisibilityClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ArmorVisibility.initialize();
        ArmorVisibilityClient.initialize();
    }
}
