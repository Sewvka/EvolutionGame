package ru.nsu.ccfit.evolution.user.framework;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import ru.nsu.ccfit.evolution.common.Abilities;
import ru.nsu.ccfit.evolution.server.Client;
import ru.nsu.ccfit.evolution.server.GameStage;
import ru.nsu.ccfit.evolution.user.actors.*;

public class SessionScreen extends GameScreen {
    private final SessionStage sessionStage;
    private final Stage overlayStage;
    private final Stage uiStage;

    private CardView queuedCard;
    private boolean queuedCoopAbilityBoolean;
    private AbilityView queuedAbilityActivation;
    private CreatureView queuedCreature;

    public SessionScreen(final EvolutionGame game, final Client client) {
        super(game, client);
        sessionStage = new SessionStage(game, game.getGameWorldState().getPlayers().size(), this);
        overlayStage = new Stage(getViewport());
        uiStage = new Stage(getViewport());
        addStage(sessionStage);
        addStage(overlayStage);
        addStage(uiStage);

        TextButton passButton = new TextButton("Pass turn", game.getAssets().getSkin());
        passButton.setPosition(GameScreen.WORLD_SIZE_X / 32, GameScreen.WORLD_SIZE_Y / 18);
        passButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (queuedCard == null && queuedAbilityActivation == null) {
                    client.passMove(game.getGameWorldState().getSelfID());
                }
            }
        });
        uiStage.addActor(passButton);
        initDevelopment();
    }

    public void initDevelopment() {
        sessionStage.initDevelopment();
    }

    public void initFeeding() {
        sessionStage.initFeeding(game.getGameWorldState().getFoodAvailable());
    }

    public void playCard(CardView card) {
        if (sessionStage.isCreatureSelected()) {
            if (!card.getAbility1().equals(card.getAbility2())) {
                showAbilityDialog(card);
            } else playAbility(card, true);
        } else if (sessionStage.isTableSelected()) {
            playCreature(card);
        } else {
            card.putInDeck();
        }
    }

    public void showAbilityDialog(final CardView card) {
        final CreatureView selectedCreature = sessionStage.getSelectedCreature();
        Dialog dialog = new Dialog("Ability Selection", game.getAssets().getSkin()) {
            @Override
            protected void result(Object object) {
                switch ((Integer) object) {
                    case 1:
                        playAbility(card, selectedCreature, true);
                        getParent().removeActor(this);
                        break;
                    case 2:
                        playAbility(card, selectedCreature, false);
                        getParent().removeActor(this);
                        break;
                    case 3:
                        card.putInDeck();
                        getParent().removeActor(this);
                }
            }
        };
        dialog.text("Select which ability to play");
        dialog.button(card.getAbility1(), 1);
        dialog.button(card.getAbility2(), 2);
        dialog.button("Cancel", 3);
        dialog.show(uiStage, null);
        dialog.setPosition((GameScreen.WORLD_SIZE_X - dialog.getWidth()) / 2, (GameScreen.WORLD_SIZE_Y - dialog.getHeight()) / 2);
        dialog.setMovable(false);
    }

    public void playAbility(CardView card, CreatureView selectedCreature, boolean firstAbility) {
        String ability = firstAbility ? card.getAbility1() : card.getAbility2();
        TableView selectedTable = (TableView) selectedCreature.getParent();
        HandView parentHand = (HandView) card.getTrueParent();
        PlayerView player = (PlayerView) parentHand.getParent();
        PlayerView target = (PlayerView) selectedTable.getParent();
        if (Abilities.isCooperative(ability)) {
            if (selectedTable.creatureCount() > 1) {
                card.setTouchable(Touchable.disabled);
                sessionStage.setHandTouchable(Touchable.disabled);
                queuedCard = card;
                queuedCoopAbilityBoolean = firstAbility;
                queuedCreature = selectedCreature;
            } else {
                card.putInDeck();
            }
            return;
        }
        game.getGameWorldState().setPlacedCardIndex(player.getHand().getCardIndex(card));
        client.addProperty(game.getGameWorldState().getSelfID(), card.getId(), selectedCreature.getID(), selectedCreature.getID(), ability);
        sessionStage.putCardsInDeck();
    }

    public void playAbility(CardView card, boolean firstAbility) {
        CreatureView selectedCreature = sessionStage.getSelectedTable().getSelectedCreature();
        playAbility(card, selectedCreature, firstAbility);
    }

    public boolean activateAbility(AbilityView abilityView) {
        CreatureView parent = (CreatureView) abilityView.getParent();
        switch (abilityView.getName()) {
            case "carnivorous":
            case "piracy":
                queueAbility(abilityView);
                return true;
            case "fat":
                activateFat(parent);
                return true;
            case "grazing":
                activateGrazer(parent);
                return true;
            case "hibernation_ability":
                activateHibernation(parent);
                return true;
        }
        return false;
    }

    private void queueAbility(AbilityView abilityView) {
//        if (server.getGameStage() == 2) {
//            queuedAbilityActivation = ability;
//        }
    }

    private void activateHibernation(CreatureView creature) {
//        PlayerView player = (PlayerView) creature.getParent().getParent();
//        server.requestHibernationActivation(player.getID(), player.getTable().getCreatureIndex(creature));
    }

    private void activateFat(CreatureView creature) {
//        PlayerView player = (PlayerView) creature.getParent().getParent();
//        int fatConsumed = server.requestFatActivation(player.getID(), player.getTable().getCreatureIndex(creature));
//        if (fatConsumed > 0) creature.consumeFat(fatConsumed);
    }

    private void activateGrazer(CreatureView creature) {
//        PlayerView player = (PlayerView) creature.getParent().getParent();
//        if (server.requestGrazerActivation(player.getID(), player.getTable().getCreatureIndex(creature))) {
//            sessionStage.getFood().removeFood();
//        }
    }

    public void playCreature(CardView card) {
        TableView selectedTable = sessionStage.getSelectedTable();
        PlayerView target = (PlayerView) selectedTable.getParent();
        HandView parentHand = (HandView) card.getTrueParent();
        PlayerView player = (PlayerView) parentHand.getParent();
        if (player.getID() != target.getID()) {
            card.putInDeck();
            return;
        }
        game.getGameWorldState().setPlacedCardIndex(player.getHand().getCardIndex(card));
        client.cardPlacement(game.getGameWorldState().getSelfID(), card.getId());
        sessionStage.putCardsInDeck();
    }

    public void moveActorToFront(GameActor actor) {
        Vector2 coords = new Vector2(actor.getX(), actor.getY());
        coords = actor.localToStageCoordinates(coords);
        actor.clearActions();
        actor.remove();
        overlayStage.addActor(actor);
        actor.setPosition(coords.x, coords.y);
    }

    public void moveActorToBack(GameActor actor) {
        Vector2 coords = new Vector2(actor.getX(), actor.getY());
        coords = actor.getTrueParent().stageToLocalCoordinates(coords);
        actor.clearActions();
        actor.remove();
        actor.getTrueParent().addActor(actor);
        actor.setPosition(coords.x, coords.y);
    }

    private void resumeCoopCardPlay(CreatureView targetCreature1, CreatureView targetCreature2) {
        TableView selectedTable = sessionStage.getSelectedTable();
        PlayerView target = (PlayerView) selectedTable.getParent();
        HandView parentHand = (HandView) queuedCard.getTrueParent();
        PlayerView player = (PlayerView) parentHand.getParent();
        if (player.getID() != target.getID()) {
            queuedCard.putInDeck();
            return;
        }

        int creatureID1 = targetCreature1.getID();
        int creatureID2 = targetCreature2.getID();
        String ability = queuedCoopAbilityBoolean ? queuedCard.getAbility1() : queuedCard.getAbility2();
        game.getGameWorldState().setPlacedCardIndex(player.getHand().getCardIndex(queuedCard));
        client.addProperty(game.getGameWorldState().getSelfID(), queuedCard.getId(), creatureID1, creatureID2, ability);
        queuedCard = null;
        sessionStage.putCardsInDeck();
    }

    public boolean isAbilityQueued() {
        return queuedAbilityActivation != null;
    }

    public void creatureClicked(CreatureView targetCreature) {
        if (queuedCard != null && !queuedCreature.equals(targetCreature)) {
            queuedCard.setTouchable(Touchable.enabled);
            sessionStage.setHandTouchable(Touchable.enabled);
            resumeCoopCardPlay(queuedCreature, targetCreature);
            return;
        }
        if (queuedAbilityActivation != null) {
            TableView targetTable = (TableView) targetCreature.getParent();
            TableView parentTable = (TableView) queuedAbilityActivation.getParent().getParent();
            PlayerView target = (PlayerView) targetTable.getParent();
            PlayerView player = (PlayerView) parentTable.getParent();
            CreatureView parentCreature = (CreatureView) queuedAbilityActivation.getParent();
            switch (queuedAbilityActivation.getName()) {
                case "carnivorous":
//                    if (server.requestPredation(parentTable.getCreatureIndex(parentCreature), targetTable.getCreatureIndex(targetCreature), player.getID(), target.getID())) {
//                        queuedAbilityActivation.resumeActivation(targetCreature);
//                    }
                    break;
                case "piracy":
//                    if (server.requestPiracy(parentTable.getCreatureIndex(parentCreature), targetTable.getCreatureIndex(targetCreature), player.getID(), target.getID())) {
//                        queuedAbilityActivation.resumeActivation(targetCreature);
//                    }
//                    break;
            }
        }
    }

    public void cancelAbilityUsage() {
        queuedAbilityActivation = null;
    }

    public void feedToken(FoodToken f) {
        if (sessionStage.isTableSelected()) {
            if (sessionStage.getSelectedTable().isCreatureSelected()) {
                PlayerView player = (PlayerView) sessionStage.getSelectedTable().getParent();
                  if (game.getGameWorldState().getSelfID() == player.getID()) {
                    client.feed(player.getID(), sessionStage.getSelectedTable().getSelectedCreature().getID());
                }
            }
        }
        moveActorToBack(f);
        f.setPosition(0, 0);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        updateTurn();
        updateStage();
        sessionStage.update();
    }

    private void updateTurn() {
        int activePlayerID = game.getGameWorldState().getActivePlayerID();
        if (activePlayerID != -1) {
            if (activePlayerID == game.getGameWorldState().getSelfID()) {
                sessionStage.setHandTouchable(Touchable.enabled);
                sessionStage.setTableTouchable(Touchable.enabled);
            } else {
                sessionStage.setHandTouchable(Touchable.disabled);
                sessionStage.setTableTouchable(Touchable.disabled);
            }
            game.getGameWorldState().setActivePlayerID(-1);
        }
    }

    private void updateStage() {
        GameStage stage = game.getGameWorldState().getGameStage();
        if (stage != null) {
            switch (stage) {
                case DEVELOPMENT:
                    initDevelopment();
                    break;
                case FEEDING:
                    initFeeding();
                    break;
            }
            game.getGameWorldState().setGameStage(null);
        }
    }
}
