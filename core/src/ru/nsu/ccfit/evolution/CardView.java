package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class CardView extends GameActor {
    private String ability1;
    private String ability2;
    private boolean inDeck;

    public boolean isInDeck() {
        return inDeck;
    }

    public void putInDeck() {
        this.inDeck = true;
    }

    public void takeFromDeck() {
        this.inDeck = false;
    }

    public CardView(float w, float h) {
        super(w, h);
        this.inDeck = false;
    }

    public void select() {
        addAction(scaleTo(1.1f, 1.1f,0.2f));
    }

    public void deselect() {
        addAction(scaleTo(1,1, 0.2f));
    }

    public void init(EvolutionGame game, Integer id) {
        String cardname = Cards.getName(id);
        this.texture = new TextureRegion(game.getLoader().getCardTexture(cardname));
        this.inDeck = true;

        //несколько костыльно, но делим название файла на название свойств
        int i = cardname.indexOf('-');
        if (i >= 0) {
            ability1 = cardname.substring(0, cardname.indexOf('-'));
        } else ability1 = cardname;
        this.ability2 = cardname.substring(cardname.indexOf('-') + 1);
    }

    public void move(float x, float y, float t) {
        addAction(parallel(rotateTo(0, t), moveTo(x, y, t)));
    }

    public String getAbility1() {
        return ability1;
    }

    public String getAbility2() {
        return ability2;
    }

    @Override
    public void reset() {
        super.reset();
        this.inDeck = false;
        this.ability1 = null;
        this.ability2 = null;
    }

    public void updateDeckPosition(int count, int total) {
        float x = (count - (float) (total - 1) / 2) * getWidth() / 2 - getWidth() / 2;
        float y = - Math.abs(count - (float) (total - 1) / 2) * getHeight() / 8;
        addAction(parallel(moveTo( x, y, 0.1f), rotateTo(-(count - (float) (total - 1) / 2) * 8, 0.1f)));
    }
}
