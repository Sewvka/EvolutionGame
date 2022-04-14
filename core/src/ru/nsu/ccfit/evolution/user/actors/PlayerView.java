package ru.nsu.ccfit.evolution.user.actors;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.viewport.Viewport;
import ru.nsu.ccfit.evolution.user.framework.EvolutionGame;

public class PlayerView extends Group {
    private final TableView table;
    private final HandView hand;
    private final int playerID;
    private final Viewport viewport;

    public PlayerView(EvolutionGame game, Viewport viewport, boolean isUser, int playerID) {
        this.playerID = playerID;
        this.viewport = viewport;
        setSize(viewport.getWorldWidth() / 16 * 6, viewport.getWorldHeight() / 9 * 5);
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
        switch (alignment) {
            case "bottom":
                setPosition(viewport.getWorldWidth() / 2 - getWidth() / 2, 0);
                break;
            case "left":
                setPosition(-viewport.getWorldWidth() / 16, viewport.getWorldHeight() / 2 + getWidth() / 2);
                setRotation(-90);
                break;
            case "right":
                setPosition(viewport.getWorldWidth() / 16 + viewport.getWorldWidth(), viewport.getWorldHeight() / 2 - getWidth() / 2);
                setRotation(90);
                break;
            case "top":
                setPosition(viewport.getWorldWidth() / 2 + getWidth() / 2, viewport.getWorldHeight() / 9 + viewport.getWorldHeight());
                setRotation(180);
                break;
            case "topL":
                setPosition(viewport.getWorldWidth() / 4 + getWidth() / 2, viewport.getWorldHeight() / 9 + viewport.getWorldHeight());
                setRotation(180);
                break;
            case "topR":
                setPosition(viewport.getWorldWidth() / 4 * 3 + getWidth() / 2, viewport.getWorldHeight() / 9 + viewport.getWorldHeight());
                setRotation(180);
                break;
        }
    }
}
