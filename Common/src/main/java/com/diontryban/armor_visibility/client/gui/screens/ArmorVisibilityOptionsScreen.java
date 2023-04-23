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

package com.diontryban.armor_visibility.client.gui.screens;

import com.diontryban.armor_visibility.ArmorVisibility;
import com.diontryban.armor_visibility.options.ArmorVisibilityOptions;
import com.diontryban.ash.api.client.gui.screens.ModOptionsScreen;
import com.diontryban.ash.api.options.ModOptionsManager;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ArmorVisibilityOptionsScreen extends ModOptionsScreen<ArmorVisibilityOptions> {
    public ArmorVisibilityOptionsScreen(ModOptionsManager<ArmorVisibilityOptions> options, Screen parent) {
        super(Component.literal(ArmorVisibility.MOD_NAME), options, parent);
    }

    @Override
    protected void addOptions() {
        this.list.addBig(OptionInstance.createBoolean(
                "armor_visibility.options.keep_elytra_visible",
                value -> Tooltip.create(Component.translatable("armor_visibility.options.keep_elytra_visible.tooltip")),
                options.get().keepElytraVisible,
                value -> options.get().keepElytraVisible = value
        ));
        this.list.addBig(OptionInstance.createBoolean(
                "armor_visibility.options.keep_cape_visible",
                value -> Tooltip.create(Component.translatable("armor_visibility.options.keep_cape_visible.tooltip")),
                options.get().keepCapeVisible,
                value -> options.get().keepCapeVisible = value
        ));
        this.list.addBig(OptionInstance.createBoolean(
                "armor_visibility.options.players_only",
                value -> Tooltip.create(Component.translatable("armor_visibility.options.players_only.tooltip")),
                options.get().playersOnly,
                value -> options.get().playersOnly = value
        ));
    }
}
