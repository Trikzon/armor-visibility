package com.trikzon.armor_visibility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Save {
    public int version = 1;
    @SerializedName("my_armor_visibility_toggle")
    public boolean myArmorVisibilityToggle = true;
    @SerializedName("all_armor_visibility_toggle")
    public boolean allArmorVisibilityToggle = true;
    @SerializedName("show_join_message")
    public boolean showJoinMessage = true;

    public static Save load(File file) {
        if (file.exists()) {
            return readSaveFile(file);
        } else {
            return new Save().writeSaveFile(file);
        }
    }

    private static Save readSaveFile(File file) {
        try (FileReader reader = new FileReader(file)) {
            Gson gson = new Gson();
            Save save = gson.fromJson(reader, Save.class);

            if (save.version < 1) {
                ArmorVisibility.LOGGER.info("Found deprecated save file. Updating.");
                return new Save().writeSaveFile(file);
            }
            return save;
        } catch (IOException e) {
            ArmorVisibility.LOGGER.error("Failed to read from save file.");
            return new Save();
        }
    }

    public Save writeSaveFile(File file) {
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
