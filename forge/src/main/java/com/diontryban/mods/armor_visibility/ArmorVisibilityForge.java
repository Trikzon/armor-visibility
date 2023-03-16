/*
 * This file is part of Armor Visibility.
 * A copy of this program can be found at https://github.com/Trikzon/armor-visibility.
 * Copyright (C) 2023 Dion Tryban
 *
 * Armor Visibility is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * Armor Visibility is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Armor Visibility. If not, see <https://www.gnu.org/licenses/>.
 */

package com.diontryban.mods.armor_visibility;

import com.diontryban.mods.armor_visibility.client.ArmorVisibilityClientForge;
import com.diontryban.mods.ash.api.modloader.forge.ModEventBus;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ArmorVisibility.MOD_ID)
public class ArmorVisibilityForge {
    public ArmorVisibilityForge() {
        ModEventBus.register(ArmorVisibility.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        DistExecutor.safeCallWhenOn(Dist.CLIENT, () -> ArmorVisibilityClientForge::new);
    }
}
