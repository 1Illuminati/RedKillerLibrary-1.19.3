package org.redkiller.util.key;

import java.util.UUID;

public record RedKillerKey(String key, String value) {
    public RedKillerKey(String key, UUID value) {
        this(key, value.toString());
    }

    public String toString() {
        return key + "-" + value;
    }

    public static RedKillerKey randomKey(String key) {
        return new RedKillerKey(key, UUID.randomUUID());
    }

    public static RedKillerKey formString(String string) {
        String[] split = string.split("-");
        return new RedKillerKey(split[0], split[1]);
    }
}
