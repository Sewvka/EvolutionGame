package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.math.Vector2;

public class CardView extends GameSprite {
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

    public void init(EvolutionGame game, Integer id) {
        super.init();

        String cardname = Cards.getName(id);
        this.image = game.getLoader().getCardTexture(cardname);
        this.inDeck = true;

        //несколько костыльно, но делим название файла на название свойств
        int i = cardname.indexOf('-');
        if (i >= 0) {
            ability1 = cardname.substring(0, cardname.indexOf('-'));
        } else ability1 = cardname;
        this.ability2 = cardname.substring(cardname.indexOf('-') + 1);
    }

    public void moveWithCursor(Vector2 mousepos) {
        inDeck = false;
        x = mousepos.x - width / 2;
        y = mousepos.y - height / 2;
        setRotation(0);
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

    public void updateDeckPosition(int count, int total, EvolutionGame game) {
        float xOffset = (count - (float) (total - 1) / 2) * width / 2;
        float yOffset = Math.abs(count - (float) (total - 1) / 2) * height / 8;
        x = EvolutionGame.WORLD_SIZE_X / 2 - width / 2 + xOffset;
        y = 50 - yOffset;
        setRotation(-(count - (float) (total - 1) / 2) * 8);
    }
}
