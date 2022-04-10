package ru.nsu.ccfit.evolution;

public class CardModel {
    private final String ability1;
    private final String ability2;

    public CardModel(Integer id) {
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
}
