package org.redkiller.system;

public class SystemInfo {
    public static SystemInfo systemInfo;

    public static SystemInfo getSystemInfo() {
        if (systemInfo == null) {
            systemInfo = new SystemInfo();
        }

        return systemInfo;
    }

    private SystemInfo() {

    }
}
