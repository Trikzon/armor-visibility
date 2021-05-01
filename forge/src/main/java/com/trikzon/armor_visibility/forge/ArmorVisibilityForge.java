package com.trikzon.armor_visibility.forge;

import com.trikzon.armor_visibility.ArmorVisibility;
import com.trikzon.armor_visibility.client.forge.ArmorVisibilityClientForge;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;

@Mod(ArmorVisibility.MOD_ID)
public class ArmorVisibilityForge {
    public ArmorVisibilityForge() {
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ArmorVisibilityClientForge::new);
    }
}
