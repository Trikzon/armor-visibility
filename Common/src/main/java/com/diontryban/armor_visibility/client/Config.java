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

package com.diontryban.armor_visibility.client;

import com.diontryban.armor_visibility.ArmorVisibility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config {
    public int version = 3;
    @SerializedName("keep_elytra_visible")
    public boolean keepElytraVisible = false;
    @SerializedName("keep_cape_visible")
    public boolean keepCapeVisible = true;
    @SerializedName("players_only")
    public boolean playersOnly = true;

    public static Config read(File file) {
        if (!file.exists()) {
            return new Config().write(file);
        }

        try (FileReader reader = new FileReader(file)) {
            Gson gson = new Gson();
            Config config = gson.fromJson(reader, Config.class);

            if (config.version < 3) {
                ArmorVisibility.LOG.info("Found deprecated config file. Updating.");
                return new Config().write(file);
            }
            return config;
        } catch (IOException e) {
            ArmorVisibility.LOG.error("Failed to read from config file.");
            return new Config();
        }
    }

    public Config write(File file) {
        try (FileWriter writer = new FileWriter(file)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            writer.write(gson.toJson(this));
            writer.flush();
        } catch (IOException e) {
            ArmorVisibility.LOG.error("Failed to write to config file.");
        }
        return this;
    }
}
