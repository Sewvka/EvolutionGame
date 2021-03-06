package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class Deck {
    final float CARD_W = 150;
    final float CARD_H = 210;
    private final Array<Card> activeCards;

    private final Pool<Card> cardPool;
    private final EvolutionGame game;

    private int selectedCard;
    private final CreatureTable table;

    public Deck(EvolutionGame game, CreatureTable table) {
        this.game = game;
        this.table = table;
        activeCards = new Array<>(6);
        cardPool = new Pool<Card>() {
            @Override
            protected Card newObject() {
                return new Card(CARD_W, CARD_H);
            }
        };
        selectedCard = -1;
    }

    //добавляем карту в колоду
    public void addCard(int id) {
        Card c = cardPool.obtain();
        activeCards.add(c);
        //вот тут не знаю, мб стоит какие-то другие координаты по дефолту указывать
        c.init(game, id, 0, 0);
    }

    public void drawAll(SpriteBatch batch) {
        for (Card c : activeCards) {
            c.draw(batch);
        }
        //последней рисуем выбранную карту, чтобы она отображалась сверху.
        //правда, так получается, что мы рисуем её дважды, но в целом пофиг.
        if (selectedCard != -1) activeCards.get(selectedCard).draw(batch);
    }

    //вот это довольно костыльная штука, можно оптимизировать
    public void update(Vector2 mousepos) {
        int i = 0;

        //ля, хорошо бы переделать так, чтобы прямо во время итерации удалять можно было
        int forRemoval = -1;

        //была ли выбрана карта в цикле
        //нужно, чтобы сбросить после цикла выбор карты в случае, если ни одна не выбрана.
        boolean selectionFlag = false;

        //проходимся по всем картам
        for (Card c : activeCards) {
            //если карта вне колоды
            if (!c.inDeck) {
                //пока мышка не отпущена, двигается за курсором
                if (Gdx.input.isTouched()) {
                    c.inDeck = false;
                    c.x = mousepos.x - CARD_W / 2;
                    c.y = mousepos.y - CARD_H / 2;
                    c.rotation = 0;
                    selectionFlag = true;
                }
                else {
                    if (table.contains(mousepos)) {
                        forRemoval = i;
                        table.addCreature();
                        cardPool.free(c);
                    }
                    else {
                        c.inDeck = true;
                    }
                    selectionFlag = false;
                    selectedCard = -1;
                }
            } else { //если карта в колоде
                //Расчитываем координаты
                int count = activeCards.size;
                float xOffset = (i - (float) (count - 1) / 2) * CARD_W / 2;
                float yOffset = Math.abs(i - (float) (count - 1) / 2) * CARD_W / 8;
                c.x = game.getWorldSizeX() / 2 - CARD_W / 2 + xOffset;
                c.y = 50 - yOffset;
                c.rotation = -(i - (float) (count - 1) / 2) * 8;

                //если на карту навели мышкой
                if (c.contains(mousepos)) {
                    //если карта не выбрана, выбираем эту
                    if (selectedCard == -1) {
                        c.setSizeMod(1.1f);
                        selectedCard = i;
                        selectionFlag = true;

                        if (Gdx.input.isTouched()) {
                            c.inDeck = false;
                        }
                    } else if (selectedCard == i) { //если это и есть выбранная карта, то ничего не меняется
                        selectionFlag = true;
                        if (Gdx.input.isTouched()) {
                            c.inDeck = false;
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
        if (forRemoval != -1) activeCards.removeIndex(forRemoval);
    }
}
