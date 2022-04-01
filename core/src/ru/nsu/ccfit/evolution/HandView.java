package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class HandView extends Group {
    final float CARD_W = 150;
    final float CARD_H = 210;
    private final Array<CardView> activeCards;
    private final Pool<CardView> cardPool;
    private final EvolutionGame game;
    private int selectedCard;
    private final TableView table;

    public HandView(EvolutionGame game, float x, float y, TableView table) {
        setPosition(x, y);
        this.game = game;
        this.table = table;
        activeCards = new Array<>(6);
        cardPool = new Pool<CardView>() {
            @Override
            protected CardView newObject() {
                return new CardView(CARD_W, CARD_H);
            }
        };
        selectedCard = -1;
    }

    public void addCard(int id) {
        if (game.getCommunicationManager().requestCardAddition(id)) {
            CardView c = cardPool.obtain();
            c.init(game, id);
            activeCards.add(c);
            addActor(c);
        }
    }

    public void removeCard(int i) {
        if (i >= 0) {
            CardView c = activeCards.get(i);
            cardPool.free(c);
            activeCards.removeIndex(i);
            removeActor(c);
        }
    }

    @Override
    public void act(float delta) {
        int i = 0;
        for (CardView c : activeCards) {
            c.updateDeckPosition(i, activeCards.size);
            i++;
        }
        super.act(delta);
        Vector2 mousepos = parentToLocalCoordinates(game.getMouseCoords());

        Actor hit = hit(mousepos.x, mousepos.y, true);
        if (hit != null) {
            CardView c = (CardView) hit;
            if (selectedCard != activeCards.indexOf(c, true)) {
                c.select();
                if (selectedCard != -1) activeCards.get(selectedCard).deselect();
                selectedCard = activeCards.indexOf(c, true);
            }
        }
        else if (selectedCard != -1) {
            activeCards.get(selectedCard).deselect();
            selectedCard = -1;
        }

        /*

        int i = 0;
        //ля, хорошо бы переделать так, чтобы прямо во время итерации удалять можно было
        int forRemoval = -1;

        //была ли выбрана карта в цикле
        //нужно, чтобы сбросить выбор карты после цикла в случае, если ни одна не выбрана.
        boolean selectionFlag = false;

        //проходимся по всем картам
        for (CardView c : activeCards) {
            //если карта вне колоды
            if (!c.isInDeck()) {
                //пока мышка не отпущена, двигается за курсором
                if (Gdx.input.isTouched()) {
                    c.moveWithCursor(mousepos);
                    selectionFlag = true;
                }
                else {
                    //карта играется на стол
                    if (table.contains(mousepos)) {
                        //добавляется новое свойство
                        if (table.creatureSelected()) {
                            if (table.addAbility(c.getAbility1(), selectedCard)) forRemoval = i;
                            else c.putInDeck();
                        }
                        //добавляется новое существо
                        else {
                            if (table.addCreature(selectedCard)) forRemoval = i;
                            else c.putInDeck();
                        }
                    }
                    //карта попадает обратно в колоду
                    else {
                        c.putInDeck();
                    }
                    selectionFlag = false;
                    selectedCard = -1;
                }
            } else {
                //если карта в колоде
                c.updateDeckPosition(i, activeCards.size);
                //если на карту навели мышкой
                if (c.contains(mousepos)) {
                    //если карта не выбрана, выбираем эту
                    if (selectedCard == -1) {
                        c.setSizeMod(1.1f);
                        selectedCard = i;
                        selectionFlag = true;
                    } else if (selectedCard == i) { //если это и есть выбранная карта, то ничего не меняется
                        selectionFlag = true;
                        //если на карту нажали, достаём её из колоды
                        if (Gdx.input.isTouched()) {
                            c.takeFromDeck();
                        }
                    }
                //если на карту не навели мышкой, то она рисуется обычного размера
                } else {
                    c.setSizeMod(1);
                }
            }
            i++;
        }
        //ни одна карта не выбрана
        if (!selectionFlag) selectedCard = -1;
        //удаляем карту
        removeCard(forRemoval);*/
    }
}
