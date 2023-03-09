package org.redkiller.system.gamerule;

public interface HasCustomGameRule {
    public <T> T getCustomGameRuleValue(CustomGameRule<T> gameRule);

    public <T> void setCustomGameRuleValue(CustomGameRule<T> gameRule, T value);

}
