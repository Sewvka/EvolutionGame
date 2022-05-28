package ru.nsu.ccfit.evolution.user.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.Viewport;
import ru.nsu.ccfit.evolution.user.framework.EvolutionGame;
import ru.nsu.ccfit.evolution.user.framework.GameScreen;

public class PlayerView extends Group {
    private final TableView table;
    private HandView hand;
    private final int playerID;
    private final String playerName;
    private final Label nameLabel;
    private final Group labelWrapper;

    public PlayerView(EvolutionGame game, int playerID, String playerName) {
        this.playerID = playerID;
        this.playerName = playerName;
        boolean isSelf = game.getGameWorldState().getSelfID() == playerID;
        setSize(GameScreen.WORLD_SIZE_X / 16 * 6, GameScreen.WORLD_SIZE_Y / 9 * 5);
        if (isSelf) {
            hand = new HandView(game, isSelf, getWidth() / 2, 0);
            addActor(hand);
        }
        table = new TableView(game, 0, getHeight() / 5 * 2, getWidth(), getHeight() / 5 * 3);
        addActor(table);

        nameLabel = new Label(playerName, game.getAssets().getSkin());
        nameLabel.setColor(Color.WHITE);
        labelWrapper = new Group();
        labelWrapper.setPosition(0, getHeight() * 0.35f);
        labelWrapper.addActor(nameLabel);
        addActor(labelWrapper);
    }

    public HandView getHand() {
        return hand;
    }

    public TableView getTable() {
        return table;
    }

    public int getID() {
        return playerID;
    }

    public String getName() {
        return playerName;
    }

    public Label getNameLabel() {
        return nameLabel;
    }

    public void setAlignment(String alignment) {
        float worldW = GameScreen.WORLD_SIZE_X;
        float worldH = GameScreen.WORLD_SIZE_Y;
        switch (alignment) {
            case "bottom":
                setPosition(worldW / 2 - getWidth() / 2, 0);
                break;
            case "left":
                setPosition(-worldW / 16, worldH / 2 + getWidth() / 2);
                setRotation(-90);
                labelWrapper.rotateBy(90);
                labelWrapper.setPosition(0, getHeight()*0.4f);
                break;
            case "right":
                setPosition(worldW / 16 + worldW, worldH / 2 - getWidth() / 2);
                setRotation(90);
                labelWrapper.rotateBy(-90);
                labelWrapper.setPosition(getWidth(), getHeight());
                break;
            case "top":
                setPosition(worldW / 2 + getWidth() / 2, worldH / 9 + worldH);
                setRotation(180);
                labelWrapper.rotateBy(180);
                labelWrapper.setPosition(getWidth(), getHeight()*0.4f);
                labelWrapper.setPosition(getWidth(), getHeight()*0.4f);
                break;
            case "topL":
                setPosition(worldW / 4 + getWidth() / 2, worldH / 9 + worldH);
                setRotation(180);
                labelWrapper.rotateBy(180);
                labelWrapper.setPosition(getWidth(), getHeight()*0.4f);
                labelWrapper.setPosition(getWidth(), getHeight()*0.4f);
                break;
            case "topR":
                setPosition(worldW / 4 * 3 + getWidth() / 2, worldH / 9 + worldH);
                setRotation(180);
                labelWrapper.rotateBy(180);
                labelWrapper.setPosition(getWidth(), getHeight()*0.4f);
                labelWrapper.setPosition(getWidth(), getHeight()*0.4f);
                break;
        }
    }
}
