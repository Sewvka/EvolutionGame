package ru.nsu.ccfit.evolution.user.framework;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import ru.nsu.ccfit.evolution.user.actors.PlayerView;
import ru.nsu.ccfit.evolution.common.Abilities;
import ru.nsu.ccfit.evolution.server.ServerEmulator;
import ru.nsu.ccfit.evolution.user.actors.*;

public class SessionScreen extends GameScreen {
    private final SessionStage sessionStage;
    private final Stage overlayStage;
    private final Stage uiStage;
    private final TextButton passButton;
    private final ServerEmulator server;
    private CardView queuedCard;
    private boolean queuedAbilityBoolean;
    private Ability queuedAbilityActivation;
    private CreatureView queuedCreature;

    public SessionScreen(final EvolutionGame game) {
        super(game);
        sessionStage = new SessionStage(game, game.getServerEmulator().getPlayerCount(), this);
        overlayStage = new Stage(getViewport());
        uiStage = new Stage(getViewport());
        server = game.getServerEmulator();
        addStage(sessionStage);
        addStage(overlayStage);
        addStage(uiStage);

        passButton = new TextButton("Pass turn", game.getAssets().getSkin());
        //initUI();
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
            ;
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
        dialog.setPosition((getViewport().getWorldWidth() - dialog.getWidth()) / 2, (getViewport().getWorldHeight() - dialog.getHeight()) / 2);
        dialog.setMovable(false);
    }

    public void playAbility(CardView card, CreatureView selectedCreature, boolean firstAbility) {
        String ability = firstAbility ? card.getAbility1() : card.getAbility2();
        TableView selectedTable = (TableView) selectedCreature.getParent();
        PlayerView player = (PlayerView) card.getParentHand().getParent();
        PlayerView target = (PlayerView) selectedTable.getParent();
        if (Abilities.isCooperative(ability)) {
            if (selectedTable.creatureCount() > 1) {
                card.setTouchable(Touchable.disabled);
                sessionStage.setHandTouchable(Touchable.disabled);
                queuedCard = card;
                queuedAbilityBoolean = firstAbility;
                queuedCreature = selectedCreature;
                return;
            } else {
                card.putInDeck();
                return;
            }
        }
        if (server.requestAbilityPlacement(ability, card.getParentHand().getCardIndex(card), selectedTable.getCreatureIndex(selectedCreature), player.getPlayerID(), target.getPlayerID())) {
            card.getParentHand().removeCard(card);
            card.remove();
            selectedCreature.addAbility(card.getId(), firstAbility);
        } else card.putInDeck();
    }

    public void playAbility(CardView card, boolean firstAbility) {
        CreatureView selectedCreature = sessionStage.getSelectedTable().getSelectedCreature();
        playAbility(card, selectedCreature, firstAbility);
    }

    public void playCreature(CardView card) {
        TableView selectedTable = sessionStage.getSelectedTable();
        PlayerView target = (PlayerView) selectedTable.getParent();
        PlayerView player = (PlayerView) card.getParentHand().getParent();
        if (player.getPlayerID() != target.getPlayerID()) {
            card.putInDeck();
            return;
        }
        if (server.requestCreaturePlacement(card.getParentHand().getCardIndex(card), player.getPlayerID())) {
            selectedTable.addCreature();
            card.getParentHand().removeCard(card);
            card.remove();
        } else card.putInDeck();
    }

    public void moveCardToFront(CardView card) {
        Vector2 coords = new Vector2(card.getX(), card.getY());
        System.out.println("old coords: " + coords.x + "; " + coords.y);
        coords = card.localToStageCoordinates(coords);
        System.out.println("new coords: " + coords.x + "; " + coords.y);
        card.clearActions();
        card.remove();
        overlayStage.addActor(card);
        card.setPosition(coords.x, coords.y);
    }

    public void moveCardToBack(CardView card) {
        Vector2 coords = new Vector2(card.getX(), card.getY());
        System.out.println("old coords: " + coords.x + "; " + coords.y);
        coords = card.getParentHand().stageToLocalCoordinates(coords);
        System.out.println("new coords: " + coords.x + "; " + coords.y);
        card.clearActions();
        card.remove();
        card.getParentHand().addActor(card);
        card.setPosition(coords.x, coords.y);
    }

    public void creatureClicked(CreatureView targetCreature) {
        if (queuedCard != null && !queuedCreature.equals(targetCreature)) {
            queuedCard.setTouchable(Touchable.enabled);
            sessionStage.setHandTouchable(Touchable.enabled);
            resumeCoopCardPlay(queuedCreature, targetCreature);
        }
        if (queuedAbilityActivation != null) {
            TableView targetTable = (TableView) targetCreature.getParent();
            TableView parentTable = (TableView) queuedAbilityActivation.getParent().getParent();
            PlayerView target = (PlayerView) targetTable.getParent();
            PlayerView player = (PlayerView) parentTable.getParent();
            CreatureView parentCreature = (CreatureView) queuedAbilityActivation.getParent();
            if (queuedAbilityActivation.getName().equals("carnivorous")) {
                if (server.requestPredation(parentTable.getCreatureIndex(parentCreature), targetTable.getCreatureIndex(targetCreature), player.getPlayerID(), target.getPlayerID())) {
                    queuedAbilityActivation.resumeActivation(targetCreature);
                }
            }
        }
    }

    private void resumeCoopCardPlay(CreatureView targetCreature1, CreatureView targetCreature2) {
        TableView selectedTable = sessionStage.getSelectedTable();
        PlayerView target = (PlayerView) selectedTable.getParent();
        PlayerView player = (PlayerView) queuedCard.getParentHand().getParent();
        if (player.getPlayerID() != target.getPlayerID()) {
            queuedCard.putInDeck();
            return;
        }

        int selectedCreature1 = selectedTable.getCreatureIndex(targetCreature1);
        int selectedCreature2 = selectedTable.getCreatureIndex(targetCreature2);
        String ability = queuedAbilityBoolean ? queuedCard.getAbility1() : queuedCard.getAbility2();
        if (server.requestCoopAbilityPlacement(ability, queuedCard.getParentHand().getCardIndex(queuedCard), selectedCreature1, selectedCreature2, player.getPlayerID())) {
            Ability ab1 = targetCreature1.addAbility(queuedCard.getId(), queuedAbilityBoolean);
            Ability ab2 = targetCreature2.addAbility(queuedCard.getId(), queuedAbilityBoolean);
            ab1.setBuddy(ab2);
            ab2.setBuddy(ab1);
            queuedCard.getParentHand().removeCard(queuedCard);
            queuedCard.remove();
            queuedCard = null;
        } else queuedCard.putInDeck();
    }

    public void queueAbility(Ability ability, CreatureView parentCreature) {
        if (server.getGameStage() == 2) {
            queuedAbilityActivation = ability;
        }
    }

    public boolean isAbilityQueued() {
        return queuedAbilityActivation != null;
    }

    public void cancelAbilityUsage() {
        queuedAbilityActivation = null;
    }

    public void feedToken(FoodToken token) {
        TableView selectedTable = sessionStage.getSelectedTable();
        PlayerView player = (PlayerView) selectedTable.getParent();
        if (server.requestFeed(sessionStage.getSelectedTable().getSelectedCreatureIndex(), player.getPlayerID())) {
            sessionStage.feed(token);
        } else token.placeInTray();
    }
/*
    private void initUI() {
        passButton.setPosition(getViewport().getWorldWidth()/32, getViewport().getWorldHeight()/18);
        passButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.getServerEmulator().passTurn();
            }
        });
        uiStage.addActor(passButton);
    }



    public Stage getUiStage() {
        return uiStage;
    }


    private void initFeedingPhase() {
        playerHand.addAction(moveTo(playerHand.getX(), playerHand.getY()-150, 0.3f));
        playerHand.setTouchable(Touchable.disabled);
        passButton.setVisible(false);
        food.init();
        stage.addActor(food);
    }

    public TableManager getTables() {
        return tables;
    }*/
}
