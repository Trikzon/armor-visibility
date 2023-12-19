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

package com.diontryban.armor_visibility;

import com.diontryban.armor_visibility.client.ArmorVisibilityClientForge;
import com.diontryban.ash_api.modloader.ForgeModInitializer;
import net.minecraftforge.fml.common.Mod;

@Mod(ArmorVisibility.MOD_ID)
public class ArmorVisibilityForge extends ForgeModInitializer {
    public ArmorVisibilityForge() {
        super(ArmorVisibility.MOD_ID, null, ArmorVisibilityClientForge::new);
    }
}
