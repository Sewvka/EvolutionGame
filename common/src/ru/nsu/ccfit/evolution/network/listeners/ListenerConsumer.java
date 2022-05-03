package ru.nsu.ccfit.evolution.network.listeners;

import com.esotericsoftware.kryonet.Connection;

public interface ListenerConsumer<T> {
    void accept(Connection connection, T elem);
}
