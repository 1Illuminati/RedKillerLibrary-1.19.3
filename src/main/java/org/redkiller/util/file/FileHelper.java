package org.redkiller.util.file;

import org.redkiller.RedKillerLibrary;

import java.io.File;

public class FileHelper {

    private FileHelper() {
        throw new IllegalStateException("Utility class");
    }
    private static File makeFolder(String path) {
        File folder = new File("plugins/" + path);
        if(!folder.exists()) {
            folder.mkdir();
            RedKillerLibrary.sendLog(path + " mkdir");
        }

        return folder;
    }

    public static File mkdir(String path) {
        File folder = new File("plugins/org.redKillerLibrary/" + path);
        if(!folder.exists()) {
            folder.mkdir();
            RedKillerLibrary.sendLog("redKillerLibrary/" + path + " mkdir");
        }

        return folder;
    }

    public static void setDoublePluginFolder() {
        makeFolder("redKillerLibrary");
        makeFolder("redKillerLibrary/nbt");
        makeFolder("redKillerLibrary/nbt/plugin");
        makeFolder("redKillerLibrary/nbt/player");
        makeFolder("redKillerLibrary/nbt/server");
        makeFolder("redKillerLibrary/nbt/world");
    }
}
