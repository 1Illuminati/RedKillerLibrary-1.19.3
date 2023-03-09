package org.redkiller.util;

import org.redkiller.util.map.dataMap.DataMap;

public class CoolTime {
    private final DataMap dataMap;
    public CoolTime(DataMap dataMap) {
        this.dataMap = dataMap.getDataMap("RedKillerLibrary_CoolTime_DataMap");
    }

    public void setCoolTime(String name, int time) {
        this.setCoolTime(name, time, TimeType.SECOND);
    }

    public void setCoolTime(String name, int time, TimeType type) {
        this.dataMap.set(name, timeToType(time, type) + System.currentTimeMillis());
    }

    public void removeCoolTime(String name) {
        this.dataMap.remove(name);
    }

    public void reduceCoolTime(String name, int reduceSecond) {
        this.reduceCoolTime(name, reduceSecond, TimeType.SECOND);
    }

    public void reduceCoolTime(String name, int reduceSecond, TimeType type) {
        this.dataMap.set(name, this.getCoolTime(name) - timeToType(reduceSecond, type));
    }

    public long getCoolTime(String name) {
        return this.dataMap.getLong(name);
    }

    public double getLessCoolTime(String name) {
        return this.getLessCoolTime(name, TimeType.SECOND);
    }

    public double getLessCoolTime(String name, TimeType type) {
        long lessTime = this.getCoolTime(name) - System.currentTimeMillis();

        return lessTime / (double) timeToType(1, type);
    }

    public boolean checkCoolTime(String name) {
        if (!dataMap.containsKey(name))
            return true;

        if (this.getCoolTime(name) <= System.currentTimeMillis()) {
            this.removeCoolTime(name);
            return true;
        }

        return false;
    }

    private int timeToType(int time, TimeType type) {
        switch (type) {
            case YEAR:
                time *= 365;
            case WEEK:
                time *= 7;
            case DAY:
                time *= 24;
            case HOUR:
                time *= 60;
            case MINUTE:
                time *= 60;
            case SECOND:
                time *= 1000;
            case MILLISECOND:
            break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }

        return time;
    }

    public enum TimeType {
        SECOND, MINUTE, HOUR, DAY, YEAR, WEEK, MILLISECOND
    }
}
