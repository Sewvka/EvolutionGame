package ru.nsu.ccfit.evolution.server;

import com.badlogic.gdx.utils.Array;
import ru.nsu.ccfit.evolution.common.Abilities;
import ru.nsu.ccfit.evolution.server.CreatureModel;
import ru.nsu.ccfit.evolution.server.Deck;
import ru.nsu.ccfit.evolution.server.PlayerModel;

//на данный момент этот класс симулирует работу удалённого сервера.
public class ServerEmulator {
    private final static int DEVELOPMENT = 1;
    private final static int FEEDING = 2;
    private final static int EXTINCTION = 3;
    private final PlayerModel[] players;
    private int gameStage;
    private int foodTotal;
    private int foodLeft;
    private int activePlayerIndex;
    private int playerCount;
    private final Deck deck;

    public ServerEmulator() {
        playerCount = 4;
        deck = new Deck();
        players = new PlayerModel[playerCount];
        for (int i = 0; i < playerCount; i++) {
            players[i] = new PlayerModel(i);
        }
        initDevelopment();
    }

    private void initDevelopment() {
        gameStage = DEVELOPMENT;
        //игроки получают необходимое кол-во карт
        for (PlayerModel p : players) {
            if (p.getHand().getCardCount() == 0 && p.getTable().getCreatureCount() == 0) {
                for (int i = 0; i < 6; i++) p.getHand().drawCard(deck.draw());
            } else {
                for (int i = 0; i < p.getTable().getCreatureCount() + 1; i++) {
                    p.getHand().drawCard(deck.draw());
                }
            }
        }
        activePlayerIndex = 0;
    }

    public void passTurn() {
        activePlayerIndex++;
        if (activePlayerIndex >= 4) activePlayerIndex = 0;
    }

    public Array<Integer> requestDrawnCards(int playerID) {
        PlayerModel player = getPlayer(playerID);
        if (player == null) return null;

        Array<Integer> drawn = new Array<>();
        drawn.addAll(player.getHand().getDrawnCards());
        player.getHand().commitDrawnCards();
        return drawn;
    }

    private PlayerModel getPlayer(int playerID) {
        for (PlayerModel p : players) {
            if (p.getID() == playerID) return p;
        }
        return null;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public int getGameStage() {
        return gameStage;
    }

    public boolean requestCreaturePlacement(int selectedCard, int playerID) {
        if (gameStage != DEVELOPMENT) return false;
        if (falseID(playerID)) return false;
        if (falseCardIndex(selectedCard, playerID)) return false;
        PlayerModel player = getPlayer(playerID);
        if (player.getHand().getCardCount() < 1) return false;
        player.getHand().removeCard(selectedCard);
        player.getTable().addCreature();
        return true;
    }

    public boolean requestAbilityPlacement(String ability, int selectedCard, int selectedCreature, int playerID, int targetID) {
        if (gameStage != DEVELOPMENT) return false;
        if (Abilities.isCooperative(ability)) return false;
        if (falseID(playerID) || falseID(targetID)) return false;
        if (falseCardIndex(selectedCard, playerID)) return false;
        if (falseCreatureIndex(selectedCreature, targetID)) return false;
        PlayerModel player = getPlayer(playerID);
        PlayerModel target = getPlayer(targetID);
        if (!ability.equals("parasite") && playerID != targetID) return false;
        if (!player.getHand().containsAbility(ability, selectedCard)) return false;
        if (target.getTable().getCreatureCount() < 1) return false;
        if (target.getTable().getCreature(selectedCreature).hasAbility(ability) && !ability.equals("fat")) return false;
        player.getHand().removeCard(selectedCard);
        target.getTable().addAbility(selectedCreature, ability);
        return true;
    }

    private boolean falseID(int playerID) {
        return players[activePlayerIndex].getID() != playerID || playerID >= playerCount || playerID < 0;
    }

    private boolean falseCreatureIndex(int creatureIndex, int playerID) {
        return (creatureIndex >= getPlayer(playerID).getTable().getCreatureCount() || creatureIndex < 0);
    }

    private boolean falseCardIndex(int cardIndex, int playerID) {
        return (cardIndex >= getPlayer(playerID).getHand().getCardCount() || cardIndex < 0);
    }

    public boolean requestCoopAbilityPlacement(String ability, int selectedCard, int selectedCreatureIndex1, int selectedCreatureIndex2, int playerID) {
        if (gameStage != DEVELOPMENT) return false;
        if (!Abilities.isCooperative(ability)) return false;
        if (falseID(playerID)) return false;
        if (falseCardIndex(selectedCard, playerID) || falseCreatureIndex(selectedCreatureIndex1, playerID) || falseCreatureIndex(selectedCreatureIndex2, playerID))
            return false;
        PlayerModel player = getPlayer(playerID);
        if (!player.getHand().containsAbility(ability, selectedCard) || player.getTable().getCreatureCount() < 2)
            return false;
        player.getHand().removeCard(selectedCard);
        player.getTable().addCoopAbility(selectedCreatureIndex1, selectedCreatureIndex2, ability);
        return true;
    }

    public boolean requestPredation(int predatorIndex, int preyIndex, int playerID, int targetID) {
        if (falseCreatureIndex(predatorIndex, playerID) || falseCreatureIndex(preyIndex, targetID)) return false;
        if (falseID(playerID) || falseID(targetID)) return false;
        if (gameStage != FEEDING) return false;
        PlayerModel player = getPlayer(playerID);
        PlayerModel target = getPlayer(targetID);
        CreatureModel predator = player.getTable().getCreature(predatorIndex);
        CreatureModel prey = target.getTable().getCreature(preyIndex);

        if (checkPredationConditions(predator, prey)) {
            target.getTable().removeCreature(preyIndex);
            predator.addFood();
            predator.addFood();
            return true;
        }
        return false;
    }

    private boolean checkPredationConditions(CreatureModel predator, CreatureModel prey) {
        if (predator.equals(prey)) return false;
        if (predator.preyedThisRound || predator.getFood() >= predator.foodRequired() + predator.getFat()) return false;
        if (prey.hasAbility("burrowing") && prey.isFed()) return false;
        if (prey.hasAbility("camouflage") && !predator.hasAbility("sharp_vision")) return false;
        if (prey.hasAbility("high_body_weight") && !predator.hasAbility("high_body_weight")) return false;
        if (prey.hasAbility("swimmer") != predator.hasAbility("swimmer")) ;

        return true;
    }

    public int getFoodTotal() {
        return foodTotal;
    }

    public boolean requestFeed(int selectedCreature, int playerID) {
        if (falseCreatureIndex(selectedCreature, playerID)) return false;
        CreatureModel c = getPlayer(playerID).getTable().getCreature(selectedCreature);

        if (foodLeft > 0) {
            if (c.getFood() < c.foodRequired() + c.getFat()) {
                c.addFood();
                foodLeft--;
                return true;
            }
        }
        return false;
    }
    /*
    public boolean requestPassTurn(int playerID) {
        players[playerID].setPassedTurn(true);
        return true;
    }

    public int requestAdvanceStage() {
        if (!allPlayersPassed()) return -1;
        for (PlayerModel p : players) p.setPassedTurn(false);

        if (gameStage == DEVELOPMENT) {
            gameStage = FEEDING;
            for (PlayerModel p : players) {
                p.getTable().resetActivations();
            }
            foodTotal = new Random().nextInt(6) + 3;
            foodLeft = foodTotal;
        } else if (gameStage == FEEDING) {
            gameStage = EXTINCTION;
        } else if (gameStage == EXTINCTION) {
            gameStage = DEVELOPMENT;
        }

        return gameStage;
    }




    private boolean allPlayersPassed() {
        for (PlayerModel player : players) {
            if (!player.hasPassed()) return false;
        }
        return true;
    }*/
}