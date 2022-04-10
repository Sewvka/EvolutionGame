package ru.nsu.ccfit.evolution;

import java.util.Random;

//на данный момент этот класс симулирует работу удалённого сервера.
public class ServerEmulator {
    private final static int DEVELOPMENT = 1;
    private final static int FEEDING = 2;
    private final static int EXTINCTION = 3;
    private final PlayerState[] players;
    private int gameStage;
    private int foodTotal;
    private int foodLeft;

    public ServerEmulator() {
        players = new PlayerState[1];
        players[0] = new PlayerState();
        gameStage = DEVELOPMENT;
    }

    public boolean requestCreaturePlacement(int selectedCard, int playerID) {
        if (players[playerID].getHand().getCardCount() > 0) {
            players[playerID].getHand().removeCard(selectedCard);
            players[playerID].getTable().addCreature();
            return true;
        }
        return false;
    }

    public boolean requestCardAddition(int id, int playerID) {
        players[playerID].getHand().addCard(id);
        return true;
    }

    public boolean requestAbilityPlacement(String ability, int selectedCard, int selectedCreature, int playerID) {
        if (players[playerID].getHand().containsAbility(ability, selectedCard) && (!players[playerID].getTable().getCreature(selectedCreature).hasAbility(ability) || ability.equals("fat"))) {
            players[playerID].getHand().removeCard(selectedCard);
            players[playerID].getTable().addAbility(selectedCreature, ability);
            return true;
        }
        return false;
    }

    public boolean requestCoopAbilityPlacement(String ability, int selectedCard, int selectedCreatureIndex1, int selectedCreatureIndex2, int playerID) {
        if (players[playerID].getHand().containsAbility(ability, selectedCard) && players[playerID].getTable().getCreatureCount() > 1) {
            players[playerID].getHand().removeCard(selectedCard);
            players[playerID].getTable().addCoopAbility(selectedCreatureIndex1, selectedCreatureIndex2, ability);
            return true;
        }
        return false;
    }

    public boolean requestPassTurn(int playerID) {
        players[playerID].setPassed(true);
        return true;
    }

    public int requestAdvanceStage() {
        if (!allPlayersPassed()) return -1;
        for (PlayerState p : players) p.setPassed(false);

        if (gameStage == DEVELOPMENT) {
            gameStage = FEEDING;
            for (PlayerState p : players) {
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

    public int getFoodTotal() {
        return foodTotal;
    }

    public boolean requestFeed(int selectedCreature, int playerID) {
        CreatureModel c = players[playerID].getTable().getCreature(selectedCreature);

        if (foodLeft > 0) {
            if (c.getFood() < c.foodRequired()+c.getFat()) {
                c.addFood();
                foodLeft--;
                return true;
            }
        }
        return false;
    }

    public boolean requestPredation(int predatorIndex, int preyIndex, int playerID, int targetID) {
        if (preyIndex < 0 || predatorIndex < 0 || predatorIndex >= players[playerID].getTable().getCreatureCount() || preyIndex >= players[targetID].getTable().getCreatureCount())
            return false;

        CreatureModel predator = players[playerID].getTable().getCreature(predatorIndex);
        CreatureModel prey = players[targetID].getTable().getCreature(preyIndex);

        if (checkPredationConditions(predator, prey)) {
            players[targetID].getTable().removeCreature(preyIndex);
            predator.addFood();
            predator.addFood();
            return true;
        }
        return false;
    }

    private boolean checkPredationConditions(CreatureModel predator, CreatureModel prey) {
        if (predator.preyedThisRound || predator.getFood() >= predator.foodRequired() + predator.getFat()) return false;
        if (prey.hasAbility("burrowing") && prey.isFed()) return false;
        if (prey.hasAbility("camouflage") && !predator.hasAbility("sharp_vision")) return false;
        if (prey.hasAbility("high_body_weight") && !predator.hasAbility("high_body_weight")) return false;
        if (prey.hasAbility("swimmer") != predator.hasAbility("swimmer"));

        return true;
    }

    private boolean allPlayersPassed() {
        for (PlayerState player : players) {
            if (!player.hasPassed()) return false;
        }
        return true;
    }
}
