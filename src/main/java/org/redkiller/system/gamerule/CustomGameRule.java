package org.redkiller.system.gamerule;

import org.redkiller.util.key.Keyed;
import org.redkiller.util.key.RedKillerKey;

public class CustomGameRule<T> implements Keyed {
    public static final CustomGameRule<Boolean> CRAFT = new CustomGameRule<>("craft", false);
    public static final CustomGameRule<Boolean> ENCHANTMENT = new CustomGameRule<>("enchantment", false);
    public static final CustomGameRule<Boolean> RIDING = new CustomGameRule<>("riding", false);
    public static final CustomGameRule<Boolean> SPECTATOR_MOVE = new CustomGameRule<>("spectator_move", false);
    public static final CustomGameRule<Boolean> PVP = new CustomGameRule<>("pvp", false);
    public static final CustomGameRule<Boolean> LEFT_HAND = new CustomGameRule<>("left_hand", false);
    public static final CustomGameRule<Boolean> DROP = new CustomGameRule<>("drop", false);
    public static final CustomGameRule<Boolean> SEX = new CustomGameRule<>("sex", false);
    public static final CustomGameRule<Boolean> MOVE = new CustomGameRule<>("move", false);
    public static final CustomGameRule<Boolean> PLACE = new CustomGameRule<>("place", false);
    public static final CustomGameRule<Boolean> BREAK = new CustomGameRule<>("break", false);
    public static final CustomGameRule<Boolean> FISHING = new CustomGameRule<>("fishing", false);
    public static final CustomGameRule<Boolean> CHAT = new CustomGameRule<>("chat", false);
    public static final CustomGameRule<Boolean> PICK_UP = new CustomGameRule<>("pick_up", false);
    public static final CustomGameRule<Boolean> COMMAND = new CustomGameRule<>("command", false);

    public static final CustomGameRule[] CUSTOM_GAME_RULES = {CRAFT,ENCHANTMENT,RIDING,SPECTATOR_MOVE,PVP,LEFT_HAND,DROP,SEX,MOVE,BREAK,PLACE,PICK_UP,COMMAND};

    private final RedKillerKey key;
    private final String name;
    private final T defaultValue;
    private CustomGameRule(String ruleName, T value) {
        this.name = ruleName;
        this.defaultValue = value;
        this.key = RedKillerKey.randomKey(ruleName);
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public String getName() {
        return name;
    }
    @Override
    public RedKillerKey getKey() {
        return key;
    }
}
