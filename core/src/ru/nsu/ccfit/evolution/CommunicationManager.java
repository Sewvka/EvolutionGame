package ru.nsu.ccfit.evolution;

public class CommunicationManager {
    private final TableModel table;
    private final HandModel hand;

    public CommunicationManager(EvolutionGame game) {
        table = new TableModel(game);
        hand = new HandModel(game);
    }

    public boolean requestCreaturePlacement(int selectedCard) {
        if (hand.getCardCount() > 0) {
            hand.removeCard(selectedCard);
            table.addCreature();
            return true;
        }
        return false;
    }

    public boolean requestAbilityPlacement(String ability, int selectedCard, int selectedCreature) {
        if (hand.containsAbility(ability, selectedCard)) {
            hand.removeCard(selectedCard);
            table.addAbility(selectedCreature, ability);
            return true;
        }
        return false;
    }

    public boolean requestCardAddition(int id) {
        hand.addCard(id);
        return true;
    }
}
