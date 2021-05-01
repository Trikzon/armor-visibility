package com.trikzon.armor_visibility.client.forge;

import com.trikzon.armor_visibility.ArmorVisibility;
import com.trikzon.armor_visibility.client.ArmorVisibilityClient;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ArmorVisibilityClientForge {
    public ArmorVisibilityClientForge() {
        ArmorVisibility.initialize();
        ArmorVisibilityClient.initialize();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(PlatformClientImpl::onClientSetup);
    }
}
