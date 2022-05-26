package ru.nsu.ccfit.evolution.user.framework;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import ru.nsu.ccfit.evolution.server.AbilityModel;
import ru.nsu.ccfit.evolution.server.CreatureModel;
import ru.nsu.ccfit.evolution.server.TableModel;
import ru.nsu.ccfit.evolution.user.actors.*;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;

public class SessionStage extends Stage {
    public final Map<Integer, PlayerView> playerActors;
    public final FoodTray food;
    private final SessionScreen sessionScreen;
    private final EvolutionGame game;

    public SessionStage(EvolutionGame game, int playerCount, SessionScreen sessionScreen) {
        super(sessionScreen.getViewport());
        this.game = game;
        if (playerCount > 4) throw new InvalidParameterException("Game does not support more than 4 players!");
        if (playerCount < 1) throw new InvalidParameterException("Game requires at least one player!");
        this.sessionScreen = sessionScreen;
        playerActors = new HashMap<>();
        food = new FoodTray(game);
        food.setPosition(GameScreen.WORLD_SIZE_X / 16, GameScreen.WORLD_SIZE_Y/8);

        Map<Integer, String> players = game.getGameWorldState().getPlayers();

        for (int id : players.keySet()) {
            playerActors.put(id, new PlayerView(game, id, players.get(id)));
            addActor(playerActors.get(id));
        }
        playerActors.get(game.getGameWorldState().getSelfID()).getHand().setTouchable(Touchable.disabled);
        alignPlayers();
    }

    public void feedToken() {
        food.removeFood();
        getSelectedCreature().addFood();
    }

    public void feedCreature(int creatureIndex, int playerID, boolean fromTray) {
        if (fromTray) {
            food.removeFood();
        }
        playerActors.get(playerID).getTable().get(creatureIndex).addFood();
    }

    public SessionScreen getSessionScreen() {
        return sessionScreen;
    }

    public FoodTray getFood() {
        return food;
    }

    public void initDevelopment() {
        PlayerView user = playerActors.get(game.getGameWorldState().getSelfID());
        user.getHand().addAction(moveTo(user.getHand().getX(), 0, 0.3f));
        game.getClient().cardAllocation(user.getID());
        for (PlayerView p : playerActors.values()) {
            p.getTable().clearAllFood();
        }
        food.remove();
    }

    public void initFeeding(int foodTotal) {
        setHandTouchable(Touchable.disabled);
        PlayerView user = playerActors.get(game.getGameWorldState().getSelfID());
        user.getHand().addAction(moveTo(user.getHand().getX(), -GameScreen.WORLD_SIZE_Y/9, 0.3f));
        setHandTouchable(Touchable.disabled);
        food.init(foodTotal);
        addActor(food);
    }

    public void setHandTouchable(Touchable touchable) {
        playerActors.get(game.getGameWorldState().getSelfID()).getHand().setTouchable(touchable);
    }

    public void setTableTouchable(Touchable touchable) {
        for (PlayerView p : playerActors.values()) {
            p.getTable().setTouchable(touchable);
        }
    }

    public TableView getSelectedTable() {
        for (PlayerView p : playerActors.values()) {
            if (p.getTable().isSelected()) return p.getTable();
        }
        return null;
    }

    public boolean isTableSelected() {
        return getSelectedTable() != null;
    }

    private void alignPlayers() {
        if (playerActors.size() < 1) return;
        playerActors.get(game.getGameWorldState().getSelfID()).setAlignment("bottom");

        int traversed;
        switch (playerActors.size()) {
            case 2:
                for (int id : playerActors.keySet()) {
                    if (id != game.getGameWorldState().getSelfID()) {
                        playerActors.get(id).setAlignment("top");
                    }
                }
                break;
            case 3:
                traversed = 0;
                for (int id : playerActors.keySet()) {
                    if (id != game.getGameWorldState().getSelfID()) {
                        switch (traversed) {
                            case 0:
                                playerActors.get(id).setAlignment("topL");
                                traversed++;
                                break;
                            case 1:
                                playerActors.get(id).setAlignment("topR");
                                traversed++;
                                break;
                        }
                    }
                }
                break;
            case 4:
                traversed = 0;
                for (int id : playerActors.keySet()) {
                    if (id != game.getGameWorldState().getSelfID()) {
                        switch (traversed) {
                            case 0:
                                playerActors.get(id).setAlignment("left");
                                traversed++;
                                break;
                            case 1:
                                playerActors.get(id).setAlignment("top");
                                traversed++;
                                break;
                            case 2:
                                playerActors.get(id).setAlignment("right");
                                traversed++;
                                break;
                        }
                    }
                }
                break;
        }
    }

    public boolean isCreatureSelected() {
        for (PlayerView p : playerActors.values()) {
            if (p.getTable().isCreatureSelected()) return true;
        }
        return false;
    }

    public CreatureView getSelectedCreature() {
        for (PlayerView p : playerActors.values()) {
            if (p.getTable().isCreatureSelected()) return p.getTable().getSelectedCreature();
        }
        return null;
    }

    public void update() {
        updateHand();
        for (int playerID : game.getGameWorldState().getPlayers().keySet()) {
            updatePlayer(playerID);
        }
    }

    private void updatePlayer(int playerID) {
        TableModel tableModel = game.getGameWorldState().getTables().get(playerID);
        TableView tableView = playerActors.get(playerID).getTable();
        for (int creatureID : tableModel.getCreatures().keySet()) {
            updateCreature(playerID, creatureID);
        }
        for (int creatureID : tableView.getCreatures().keySet()) {
            if (!tableModel.getCreatures().containsKey(creatureID)) {
                tableView.removeCreature(creatureID);
            }
        }
    }

    private void updateCreature(int playerID, int creatureID) {
        CreatureModel creatureModel = game.getGameWorldState().getTables().get(playerID).getCreatures().get(creatureID);
        TableView tableView = playerActors.get(playerID).getTable();
        if (!tableView.getCreatures().containsKey(creatureID)) {
            tableView.addCreature(creatureID);
        }
        CreatureView creatureView = tableView.getCreatures().get(creatureID);
        for (AbilityModel abilityModel : creatureModel.getAbilities()) {
            if (!creatureView.hasAbility(abilityModel.getName(), abilityModel.getCreatureID2())) {
                if (abilityModel.isCooperative()) {
                    int partnerID = abilityModel.getCreatureID2();
                    AbilityView a1 = creatureView.addAbility(abilityModel.getName());
                    AbilityView a2 = tableView.get(partnerID).addAbility(abilityModel.getName());
                    a1.setBuddy(a2);
                    a2.setBuddy(a1);
                }
                else {
                    creatureView.addAbility(abilityModel.getName());
                }
            }
        }

        int fatDiff = creatureModel.getFatMax() - creatureView.getFatCount();
        for (int i = 0; i < fatDiff; i++) {
            creatureView.addAbility("fat");
        }
        for (int i = 0; i < -fatDiff; i++) {
            creatureView.removeAbility("fat");
        }
    }

    private void updateHand() {
        PlayerView playerView = playerActors.get(game.getGameWorldState().getSelfID());
        ArrayList<Integer> handModel = game.getGameWorldState().getHand();
        for (int i = 0; i < handModel.size(); i++) {
            if (i >= playerView.getHand().getCards().size) {
                playerView.getHand().addCard(handModel.get(i));
            }
            else if (playerView.getHand().getCards().get(i).getId() != handModel.get(i)) {
                i+=0;
                playerView.getHand().removeCardAt(i);
                i--;
            }
        }
        for (int i = handModel.size(); i < playerView.getHand().getCards().size; i++) {
            playerView.getHand().removeCardAt(i);
        }
    }

    public void putCardsInDeck() {
        playerActors.get(game.getGameWorldState().getSelfID()).getHand().putCardsInDeck();
    }
}
