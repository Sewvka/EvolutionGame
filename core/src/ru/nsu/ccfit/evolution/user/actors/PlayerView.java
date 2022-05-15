package ru.nsu.ccfit.evolution.user.actors;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.viewport.Viewport;
import ru.nsu.ccfit.evolution.user.framework.EvolutionGame;
import ru.nsu.ccfit.evolution.user.framework.GameScreen;

public class PlayerView extends Group {
    private final TableView table;
    private final HandView hand;
    private final int playerID;

    public PlayerView(EvolutionGame game, boolean isUser, int playerID) {
        this.playerID = playerID;
        setSize(GameScreen.WORLD_SIZE_X / 16 * 6, GameScreen.WORLD_SIZE_Y / 9 * 5);
        hand = new HandView(game, isUser, getWidth() / 2, 0);
        table = new TableView(game, 0, getHeight() / 5 * 2, getWidth(), getHeight() / 5 * 3);
        addActor(table);
        addActor(hand);
    }

    public HandView getHand() {
        return hand;
    }

    public TableView getTable() {
        return table;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setAlignment(String alignment) {
        float worldW = GameScreen.WORLD_SIZE_X;
        float worldH = GameScreen.WORLD_SIZE_Y;
        switch (alignment) {
            case "bottom":
                setPosition(worldW/2 - getWidth() / 2, 0);
                break;
            case "left":
                setPosition(-worldW / 16, worldH / 2 + getWidth() / 2);
                setRotation(-90);
                break;
            case "right":
                setPosition(worldW / 16 + worldW, worldH / 2 - getWidth() / 2);
                setRotation(90);
                break;
            case "top":
                setPosition(worldW / 2 + getWidth() / 2, worldH / 9 + worldH);
                setRotation(180);
                break;
            case "topL":
                setPosition(worldW / 4 + getWidth() / 2, worldH / 9 + worldH);
                setRotation(180);
                break;
            case "topR":
                setPosition(worldW / 4 * 3 + getWidth() / 2, worldH / 9 + worldH);
                setRotation(180);
                break;
        }
    }
}
