package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.Game;
import com.esotericsoftware.kryonet.Server;
import lombok.SneakyThrows;
import ru.nsu.ccfit.evolution.constants.NetworkConstants;

public class EvolutionServerGame extends Game {

    private final Server server;

    public EvolutionServerGame() {
        this.server = new Server(
                NetworkConstants.WRITE_BUFFER_SIZE, NetworkConstants.OBJECT_BUFFER_SIZE
        );
        server.getKryo().setRegistrationRequired(false);
        server.getKryo().setWarnUnregisteredClasses(true);
    }

    @SneakyThrows
    @Override
    public void create() {
        server.bind(NetworkConstants.PORT);
        server.start();
    }


}
