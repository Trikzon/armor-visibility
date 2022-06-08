package com.trikzon.armor_visibility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SaveFile {
    public int version = 2;
    @SerializedName("hide_all_armor_toggle")
    public boolean hideAllArmorToggle = false;
    @SerializedName("hide_my_armor_toggle")
    public boolean hideMyArmorToggle = false;
    @SerializedName("show_join_message")
    public boolean showJoinMessage = true;
    @SerializedName("keep_elytra_visible")
    public boolean keepElytraVisible = false;

    public static SaveFile read(File file) {
        if (!file.exists()) {
            return new SaveFile().write(file);
        }

        try (FileReader reader = new FileReader(file)) {
            Gson gson = new Gson();
            SaveFile saveFile = gson.fromJson(reader, SaveFile.class);

            if (saveFile.version < 2) {
                ArmorVisibility.LOGGER.info("Found deprecated save file. Updating.");
                return new SaveFile().write(file);
            }
            return saveFile;
        } catch (IOException e) {
            ArmorVisibility.LOGGER.error("Failed to read from save file.");
            return new SaveFile();
        }
    }

    public SaveFile write(File file) {
        try (FileWriter writer = new FileWriter(file)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            writer.write(gson.toJson(this));
            writer.flush();
        } catch (IOException e) {
            ArmorVisibility.LOGGER.error("Failed to write to save file.");
        }
        return this;
    }
}
