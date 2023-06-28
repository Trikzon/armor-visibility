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

import com.diontryban.armor_visibility.options.ArmorVisibilityOptions;
import com.diontryban.ash.api.options.ModOptionsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArmorVisibility {
    public static final String MOD_ID = "armor_visibility";
    public static final String MOD_NAME = "Armor Visibility";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
    public static final ModOptionsManager<ArmorVisibilityOptions> OPTIONS = new ModOptionsManager<>(MOD_ID, ArmorVisibilityOptions.class);
}
