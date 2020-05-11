package io.github.nucleuspowered.proton.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.concurrent.TimeUnit;

@ConfigSerializable
public class CacheConfig {

    @Setting
    private int expiration = 10;

    @Setting
    private TimeUnit unit = TimeUnit.MINUTES;

    @Setting
    private long maxSize = 10000;

    @Setting
    private int startupHistoryLimit = 100;

    public int getExpiration() {
        return expiration;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    public long getMaxSize() {
        return maxSize;
    }

    public int getStartupHistoryLimit() {
        return startupHistoryLimit;
    }
}
