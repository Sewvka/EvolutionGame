package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;

public class TableView extends Group {
    static final float CREATURE_W = 100;
    static final float CREATURE_H = 140;
    private final Array<CreatureView> activeCreatures;
    private CreatureView selectedCreature;
    private boolean tableSelected;
    private final Pool<CreatureView> creaturePool;
    private final EvolutionGame game;
    final TextureRegion tableTexture;

    public TableView(final EvolutionGame game, float x, float y, float tableW, float tableH) {
        super();
        tableSelected = false;
        this.game = game;
        setPosition(x, y);
        setSize(tableW, tableH);
        setOrigin(tableW/2, tableH/2);
        setBounds(x, y, tableW, tableH);
        tableTexture = new TextureRegion(game.getLoader().getTexture("table.png"));
        activeCreatures = new Array<>(6);
        creaturePool = new Pool<CreatureView>() {
            @Override
            protected CreatureView newObject() {
                return new CreatureView(CREATURE_W, CREATURE_H, game);
            }
        };
        addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                tableSelected = true;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                tableSelected = false;
            }
        });
    }

    public CreatureView getSelectedCreature() {
        return selectedCreature;
    }

    public void setSelectedCreature(CreatureView selectedCreature) {
        this.selectedCreature = selectedCreature;
    }

    public CreatureView get(int index) {
        return activeCreatures.get(index);
    }

    public int getSelectedCreatureIndex() {
        return activeCreatures.indexOf(selectedCreature, true);
    }

    public int getCreatureIndex(CreatureView c) {
        return activeCreatures.indexOf(c, true);
    }

    public boolean isCreatureSelected() {
        return (selectedCreature != null);
    }

    public int creatureCount() {
        return activeCreatures.size;
    }

    public boolean isSelected() {
        return tableSelected;
    }

    public boolean addCreature(int selectedCard) {
        if (game.getServerEmulator().requestCreaturePlacement(selectedCard, game.getPlayerID())) {
            CreatureView c = creaturePool.obtain();
            activeCreatures.add(c);
            addActor(c);
            return true;
        }
        return false;
    }

    public boolean addAbility(int cardID, boolean firstAbility, int selectedCard, int selectedCreatureIndex) {
        String ability = Cards.getAbilityFromName(Cards.getName(cardID), firstAbility);
        if (game.getServerEmulator().requestAbilityPlacement(ability, selectedCard, selectedCreatureIndex, game.getPlayerID())) {
            activeCreatures.get(selectedCreatureIndex).addAbility(cardID, firstAbility);
            return true;
        }
        return false;
    }

    public boolean addCoopAbility(int cardID, boolean firstAbility, int selectedCard, int selectedCreatureIndex1, int selectedCreatureIndex2) {
        String ability = Cards.getAbilityFromName(Cards.getName(cardID), firstAbility);
        if (game.getServerEmulator().requestCoopAbilityPlacement(ability, selectedCard, selectedCreatureIndex1, selectedCreatureIndex2, game.getPlayerID())) {
            Ability ab1 = activeCreatures.get(selectedCreatureIndex1).addAbility(cardID, firstAbility);
            Ability ab2 = activeCreatures.get(selectedCreatureIndex2).addAbility(cardID, firstAbility);
            ab1.setBuddy(ab2);
            ab2.setBuddy(ab1);
            return true;
        }
        return false;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(tableTexture, getX(), getY(), getWidth()/2, getHeight()/2, getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        super.draw(batch, parentAlpha);
    }

    public void act(float delta) {
        int i = 0;
        for (CreatureView c : activeCreatures) {
            float x = (i - (float) (activeCreatures.size - 1) / 2) * (c.getWidth() + 20) - c.getWidth() / 2 + c.getParent().getOriginX();
            c.addAction(moveTo(x, c.getY(), 0.1f));
            i++;
        }
        super.act(delta);
    }
}
