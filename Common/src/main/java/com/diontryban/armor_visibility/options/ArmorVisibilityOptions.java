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

package com.diontryban.armor_visibility.options;

import com.diontryban.ash.api.options.ModOptions;
import com.google.gson.annotations.SerializedName;

public class ArmorVisibilityOptions extends ModOptions {
    @SerializedName("keep_elytra_visible")
    public boolean keepElytraVisible = false;
    @SerializedName("keep_cape_visible")
    public boolean keepCapeVisible = true;
    @SerializedName("players_only")
    public boolean playersOnly = true;

    @Override
    protected int getVersion() {
        return 3;
    }
}
