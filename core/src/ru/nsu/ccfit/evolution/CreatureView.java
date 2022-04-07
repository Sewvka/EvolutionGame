package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class CreatureView extends Group implements Pool.Poolable {
    private final EvolutionGame game;
    private final Cover cover;
    private final Array<Ability> abilityArray;
    private int foodCount;

    public CreatureView(float w, float h, EvolutionGame game) {
        setSize(w, h);
        this.game = game;
        abilityArray = new Array<>();
        setOrigin(w / 2, h / 2);
        setPosition(0, 0);
        updateBounds();
        cover = new Cover(game, w, h);
        addActor(cover);
        addListener(new CreatureInputListener(this));
        foodCount = 0;
    }

    public void select() {
        TableView parent = (TableView) getParent();
        parent.setSelectedCreature(this);
    }

    public void deselect() {
        TableView parent = (TableView) getParent();
        parent.setSelectedCreature(null);
    }

    public Ability addAbility(int cardID, boolean firstAbility) {
        Ability ability = new Ability(game, getWidth(), getHeight(), cardID, firstAbility, null);
        addActorBefore(cover, ability);
        abilityArray.add(ability);
        return ability;
    }


    //очень некрасиво имхо
    public void addFood(FoodToken f) {
        int hbwIndex = getAbilityIndex("high_body_weight");
        int parasiteIndex = getAbilityIndex("parasite");
        int carnivorousIndex = getAbilityIndex("carnivorous");
        int parasiteCount = -1;
        if (parasiteIndex != -1) {
            parasiteCount = abilityArray.get(parasiteIndex).getChildren().size;
        }
        int nextFat = getNextFat();

        if (foodCount == 0) {
            cover.addActor(f);
            f.setPosition(10, getHeight() - f.getHeight() - 10);
        } else if (hbwIndex != -1 && !abilityArray.get(hbwIndex).hasChildren()) {
            moveToAbility(f, hbwIndex, 0);
        } else if (carnivorousIndex != -1 && !abilityArray.get(carnivorousIndex).hasChildren()) {
            moveToAbility(f, carnivorousIndex, 0);
        } else if (parasiteIndex != -1 && parasiteCount < 2) {
            moveToAbility(f, carnivorousIndex, parasiteCount);
        } else if (nextFat != -1) {
            moveToAbility(f, nextFat, 0);
        }
        foodCount++;
    }

    private void moveToAbility(FoodToken f, int abilityIndex, int shift) {
        Ability a = abilityArray.get(abilityIndex);
        a.addActor(f);
        f.addAction(moveTo(10 + shift * (f.getWidth() + 10), a.getHeight() - f.getHeight() - 5, 0.1f));
    }

    private boolean hasAbility(String ability) {
        for (Ability a : abilityArray) {
            if (a.getName().equals(ability)) return true;
        }
        return false;
    }

    private int getAbilityIndex(String ability) {
        int i = 0;
        for (Ability a : abilityArray) {
            if (a.getName().equals(ability)) return i;
            i++;
        }
        return -1;
    }

    private int getNextFat() {
        int i = 0;
        for (Ability a : abilityArray) {
            if (a.getName().equals("fat") && !a.hasChildren()) return i;
            i++;
        }
        return -1;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        updateBounds();
        updateAbilityPositions();
    }

    private void updateAbilityPositions() {
        int i = 0;
        for (Ability a : abilityArray) {
            float y = (abilityArray.size - i) * a.getHeight() / 5;
            a.addAction(moveTo(a.getX(), y, 0.1f));
            i++;
        }
    }

    @Override
    public void reset() {
        setScale(1);
        setRotation(0);
        abilityArray.clear();
        foodCount = 0;
    }

    private void updateBounds() {
        setBounds(getX(), getY(), getWidth(), getHeight());
    }

    private class CreatureInputListener extends InputListener {
        CreatureView parentCreature;

        private CreatureInputListener(CreatureView parent) {
            this.parentCreature = parent;
        }

        @Override
        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
            select();
        }

        @Override
        public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
            deselect();
        }
    }
}
