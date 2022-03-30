package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.graphics.Texture;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DeckCard extends GameSprite {
    private String ability1;
    private String ability2;
    private static final Map<Integer, String> cardIDs;
    static {
        HashMap<Integer, String> aMap = new HashMap<>();
        aMap.put(1, "burrowing-fat");
        aMap.put(2, "high_body_weight-fat");
        cardIDs = Collections.unmodifiableMap(aMap);
    }

    public boolean inDeck;

    public DeckCard(float w, float h) {
        super(w, h);
        this.inDeck = false;
    }

    public void init(EvolutionGame game, Integer id) {
        super.init();

        String cardname = cardIDs.get(id);
        this.image = game.assets.get("cards/" + cardname + ".png", Texture.class);
        this.inDeck = true;

        //несколько костыльно, но делим название файла на название свойств
        this.ability1 = cardname.substring(0, cardname.indexOf('-'));
        this.ability2 = cardname.substring(cardname.indexOf('-')+1);
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
    }
}
