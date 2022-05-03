package ru.nsu.ccfit.evolution.util;

import lombok.*;

import java.util.concurrent.atomic.AtomicLong;

@EqualsAndHashCode
@ToString
public final class UUID {

    private static final AtomicLong COUNTER = new AtomicLong(0);

    @Getter
    private long id;

    public UUID(long id) {
        this.id = id;
    }

    public static UUID getNext() {
        return new UUID(COUNTER.getAndIncrement());
    }
}
