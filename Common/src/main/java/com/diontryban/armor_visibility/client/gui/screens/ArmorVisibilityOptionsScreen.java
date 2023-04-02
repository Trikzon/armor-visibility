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
