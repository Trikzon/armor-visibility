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
import com.diontryban.ash_api.client.gui.screens.ModOptionsScreen;
import com.diontryban.ash_api.options.ModOptionsManager;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class ArmorVisibilityOptionsScreen extends ModOptionsScreen<ArmorVisibilityOptions> {
    public ArmorVisibilityOptionsScreen(@NotNull ModOptionsManager<ArmorVisibilityOptions> options, Screen parent) {
        super(Component.literal(ArmorVisibility.MOD_NAME), options, parent);
    }

    @Override
    protected void addOptions() {
        this.list.addSmall(
                OptionInstance.createBoolean(
                        "armor_visibility.options.keep_elytra_visible",
                        value -> Tooltip.create(Component.translatable("armor_visibility.options.keep_elytra_visible.tooltip")),
                        options.get().keepElytraVisible,
                        value -> options.get().keepElytraVisible = value
                ),
                OptionInstance.createBoolean(
                        "armor_visibility.options.keep_cape_visible",
                        value -> Tooltip.create(Component.translatable("armor_visibility.options.keep_cape_visible.tooltip")),
                        options.get().keepCapeVisible,
                        value -> options.get().keepCapeVisible = value
                )
        );

        this.list.addBig(OptionInstance.createBoolean(
                "armor_visibility.options.players_only",
                value -> Tooltip.create(Component.translatable("armor_visibility.options.players_only.tooltip")),
                options.get().playersOnly,
                value -> options.get().playersOnly = value
        ));

        this.list.addSmall(
                OptionInstance.createBoolean(
                        "armor_visibility.options.toggles_helmet",
                        value -> Tooltip.create(Component.translatable("armor_visibility.options.toggles_helmet.tooltip")),
                        options.get().togglesHelmet,
                        value -> options.get().togglesHelmet = value
                ),
                OptionInstance.createBoolean(
                        "armor_visibility.options.toggles_chestplate",
                        value -> Tooltip.create(Component.translatable("armor_visibility.options.toggles_chestplate.tooltip")),
                        options.get().togglesChestplate,
                        value -> options.get().togglesChestplate = value
                )
        );
        this.list.addSmall(
                OptionInstance.createBoolean(
                        "armor_visibility.options.toggles_leggings",
                        value -> Tooltip.create(Component.translatable("armor_visibility.options.toggles_leggings.tooltip")),
                        options.get().togglesLeggings,
                        value -> options.get().togglesLeggings = value
                ),
                OptionInstance.createBoolean(
                        "armor_visibility.options.toggles_boots",
                        value -> Tooltip.create(Component.translatable("armor_visibility.options.toggles_boots.tooltip")),
                        options.get().togglesBoots,
                        value -> options.get().togglesBoots = value
                )
        );

        this.list.addBig(OptionInstance.createBoolean(
                "armor_visibility.options.save_between_launches",
                value -> Tooltip.create(Component.translatable("armor_visibility.options.save_between_launches.tooltip")),
                options.get().saveBetweenLaunches,
                value -> options.get().saveBetweenLaunches = value
        ));
    }
}
