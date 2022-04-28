package ru.nsu.ccfit.evolution.user.framework;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;
import ru.nsu.ccfit.evolution.user.actors.*;

import java.security.InvalidParameterException;
import java.util.ArrayList;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;

public class SessionStage extends Stage {
    public final ArrayList<PlayerView> players;
    public final FoodTray food;
    private int playerCount;
    private final EvolutionGame game;
    private final SessionScreen sessionScreen;

    public SessionStage(EvolutionGame game, int playerCount, SessionScreen sessionScreen) {
        if (playerCount > 4) throw new InvalidParameterException("Game does not support more than 4 players!");
        //if (playerCount < 2) throw new InvalidParameterException("Game requires at least two players!");
        if (playerCount < 1) throw new InvalidParameterException("Game requires at least one player!");
        this.game = game;
        this.playerCount = playerCount;
        this.sessionScreen = sessionScreen;
        players = new ArrayList<>(playerCount);
        food = new FoodTray(game);
        food.setPosition(sessionScreen.getViewport().getWorldWidth() / 16, sessionScreen.getViewport().getWorldHeight()/8);

        //в конечном итоге номера ID игроков должен будет предоставлять сервер
        players.add(new PlayerView(game, sessionScreen.getViewport(), true, 0));
        addActor(players.get(0));
        for (int i = 1; i < playerCount; i++) {
            players.add(new PlayerView(game, sessionScreen.getViewport(), false, i));
            addActor(players.get(i));
        }
        alignPlayers();
    }

    public void feed(FoodToken f) {
        f.remove();
        food.removeFood();
        getSelectedCreature().addFood(new FoodToken(game, FoodTray.TOKEN_SIZE, false));
    }

    public SessionScreen getSessionScreen() {
        return sessionScreen;
    }

    public void initDevelopment() {
        //всем игрокам даются карты
        for (PlayerView p : players) {
            Array<Integer> drawn = game.getServerEmulator().requestDrawnCards(p.getPlayerID());
            if (drawn != null) {
                p.getHand().addAll(drawn);
            }
        }
    }

    public void initFeeding(int foodTotal) {
        for (PlayerView p : players) {
            HandView h = p.getHand();
            h.addAction(moveTo(h.getX(), h.getY()-sessionScreen.getViewport().getWorldHeight()/9, 0.3f));
            h.setTouchable(Touchable.disabled);
        }
        food.init(foodTotal);
        addActor(food);
    }

    public void setHandTouchable(Touchable touchable) {
        for (PlayerView p : players) {
            p.getHand().setTouchable(touchable);
        }
    }

    public TableView getSelectedTable() {
        for (PlayerView p : players) {
            if (p.getTable().isSelected()) return p.getTable();
        }
        return null;
    }

    public boolean isTableSelected() {
        return getSelectedTable() != null;
    }

    private void alignPlayers() {
        if (playerCount < 1) return;
        players.get(0).setAlignment("bottom");
        switch (playerCount) {
            case 2:
                players.get(1).setAlignment("top");
                break;
            case 3:
                players.get(1).setAlignment("topL");
                players.get(2).setAlignment("topR");
                break;
            case 4:
                players.get(1).setAlignment("left");
                players.get(2).setAlignment("top");
                players.get(3).setAlignment("right");
                break;
        }
    }

    public boolean isCreatureSelected() {
        for (PlayerView p : players) {
            if (p.getTable().isCreatureSelected()) return true;
        }
        return false;
    }

    public CreatureView getSelectedCreature() {
        for (PlayerView p : players) {
            if (p.getTable().isCreatureSelected()) return p.getTable().getSelectedCreature();
        }
        return null;
    }
}
