package ru.nsu.ccfit.evolution;

public class PlayerState {
    private boolean passed;
    private boolean acted;
    private final TableModel table;
    private final HandModel hand;

    public PlayerState() {
        passed = false;
        acted = false;
        table = new TableModel();
        hand = new HandModel();
    }

    public HandModel getHand() {
        return hand;
    }

    public TableModel getTable() {
        return table;
    }

    public boolean hasPassed() {
        return passed;
    }

    public boolean hasActed() {
        return acted;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public void setActed(boolean acted) {
        this.acted = acted;
    }
}
