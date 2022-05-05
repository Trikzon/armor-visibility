package com.trikzon.armor_visibility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class ArmorVisibility {
    public static final String MOD_ID = "armor_visibility";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static final File MOD_CONFIG_FILE = new File("./config/" + MOD_ID + ".json");

    public static SaveFile saveFile;

    public static void initialize() {
        ArmorVisibility.saveFile = SaveFile.read(MOD_CONFIG_FILE);
    }

    public static void writeSaveFile() {
        ArmorVisibility.saveFile.write(MOD_CONFIG_FILE);
    }
}
