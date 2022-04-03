package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.utils.Pool;

public class CardModel implements Pool.Poolable {
    private String ability1;
    private String ability2;

    public CardModel() {

    }

    public void init(Integer id) {
        String cardname = Cards.getName(id);

        int i = cardname.indexOf('-');
        if (i >= 0) {
            ability1 = cardname.substring(0, cardname.indexOf('-'));
        } else ability1 = cardname;

        this.ability2 = cardname.substring(cardname.indexOf('-') + 1);
    }

    public String getAbility1() {
        return ability1;
    }

    public String getAbility2() {
        return ability2;
    }

    @Override
    public void reset() {
        this.ability1 = null;
        this.ability2 = null;
    }
}
