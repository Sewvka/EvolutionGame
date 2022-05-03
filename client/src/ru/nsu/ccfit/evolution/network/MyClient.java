package ru.nsu.ccfit.evolution.network;

import com.esotericsoftware.kryonet.Client;
import lombok.Getter;
import lombok.SneakyThrows;
import ru.nsu.ccfit.evolution.constants.NetworkConstants;
import ru.nsu.ccfit.evolution.network.dto.Dto;
import ru.nsu.ccfit.evolution.EvolutionGame;

import java.net.InetAddress;

@Getter
public class MyClient {

    //private final InetAddress inetAddress;
    private final Client client;
    private final EvolutionGame evolutionGame;

    @SneakyThrows
    public MyClient(boolean isLocalServer, EvolutionGame evolutionGame) {
        this.client = new Client(
                NetworkConstants.WRITE_BUFFER_SIZE, NetworkConstants.OBJECT_BUFFER_SIZE
        );
        client.getKryo().setRegistrationRequired(false);
        client.getKryo().setWarnUnregisteredClasses(true);
        this.evolutionGame = evolutionGame;
        //this.inetAddress = isLocalServer
                //? InetAddress.getLocalHost()
                //: InetAddress.getByName(NetworkConstants.REMOTE_SERVER_IP);
    }

    public void connect() {
        //client.addListener();
    }

    public <T extends Dto> void sendTCP(T dto) {
        client.sendTCP(dto);
    }

}
