package ru.nsu.ccfit.evolution.user.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import ru.nsu.ccfit.evolution.user.actors.listeners.Hoverable;
import ru.nsu.ccfit.evolution.user.actors.listeners.HoverableListener;
import ru.nsu.ccfit.evolution.user.framework.EvolutionGame;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class CreatureView extends GameActor implements Hoverable {
    private final EvolutionGame game;
    private final Cover cover;
    private final Array<Ability> abilityArray;
    private int foodCount;

    public CreatureView(EvolutionGame game, float w, float h) {
        super(null, w, h);
        this.game = game;
        abilityArray = new Array<>();
        cover = new Cover(game, w, h);
        addActor(cover);
        addListener(new HoverableListener(this));
        foodCount = 0;
    }

    @Override
    public void hover() {
        TableView parent = (TableView) getParent();
        parent.setSelectedCreature(this);
    }

    @Override
    public void unhover() {
        TableView parent = (TableView) getParent();
        if (parent != null)
            parent.setSelectedCreature(null);
    }

    @Override
    public boolean isHoverable() {
        return true;
    }

    public Ability addAbility(int cardID, boolean firstAbility) {
        Ability ability = new Ability(game, getWidth(), getHeight(), cardID, firstAbility, null);
        addActorBefore(cover, ability);
        abilityArray.add(ability);
        return ability;
    }

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
            foodCount++;
        } else if (hbwIndex != -1 && !abilityArray.get(hbwIndex).hasChildren()) {
            addFoodToAbility(f, hbwIndex, 0);
            foodCount++;
        } else if (carnivorousIndex != -1 && !abilityArray.get(carnivorousIndex).hasChildren()) {
            addFoodToAbility(f, carnivorousIndex, 0);
            foodCount++;
        } else if (parasiteIndex != -1 && parasiteCount < 2) {
            addFoodToAbility(f, carnivorousIndex, parasiteCount);
            foodCount++;
        } else if (nextFat != -1) {
            addFoodToAbility(f, nextFat, 0);
            f.addAction(color(Color.YELLOW));
            foodCount++;
        }
    }

    private void addFoodToAbility(FoodToken f, int abilityIndex, int shift) {
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
}