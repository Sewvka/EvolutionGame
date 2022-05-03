package ru.nsu.ccfit.evolution.constants;

public class NetworkConstants {

    private NetworkConstants() {}

    public static final int WRITE_BUFFER_SIZE = 65536;
    public static final int OBJECT_BUFFER_SIZE = 8192 * 4;

    public static final int PORT = 80;
    public static final int TIMEOUT = 15000;
    // пока локалхост
    public static final String REMOTE_SERVER_IP = "127.0.0.1";

}
