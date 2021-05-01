package com.trikzon.armor_visibility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class ArmorVisibility {
    public static final String MOD_ID = "armor-visibility";
    public static final Logger LOGGER = LogManager.getLogger("MOD_ID");
    public static final File MOD_CONFIG_FILE = new File("./config/" + MOD_ID + ".json");

    public static Save save;

    public static void initialize() {
        ArmorVisibility.save = Save.load(MOD_CONFIG_FILE);
    }

    public static void writeSaveFile() {
        save.writeSaveFile(MOD_CONFIG_FILE);
    }
}
