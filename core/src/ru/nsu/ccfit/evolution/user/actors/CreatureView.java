package ru.nsu.ccfit.evolution.user.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Array;
import ru.nsu.ccfit.evolution.common.Abilities;
import ru.nsu.ccfit.evolution.user.actors.listeners.Hoverable;
import ru.nsu.ccfit.evolution.user.actors.listeners.HoverableListener;
import ru.nsu.ccfit.evolution.user.framework.EvolutionGame;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class CreatureView extends GameActor implements Hoverable {
    private final EvolutionGame game;
    private final Cover cover;
    private final Array<Ability> abilities;
    private int foodCount;

    public CreatureView(EvolutionGame game, float w, float h) {
        super(null, w, h);
        this.game = game;
        abilities = new Array<>();
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

    public void removeBuddies() {
        for (Ability a : new Array.ArrayIterator<>(abilities)) {
            if (Abilities.isCooperative(a.getName())) {
                CreatureView c = (CreatureView) a.getBuddy().getParent();
                c.removeAbility(a);
            }
        }
    }

    public void removeAbility(Ability a) {
        abilities.removeValue(a, true);
    }

    public Ability addAbility(int cardID, boolean firstAbility) {
        Ability ability = new Ability(game, getWidth(), getHeight(), cardID, firstAbility, null);
        addActorBefore(cover, ability);
        abilities.add(ability);
        return ability;
    }

    public void clearFood() {
        for (Ability a : new Array.ArrayIterator<>(abilities)) {
            if (!a.getName().equals("fat")) a.clearChildren();
        }
        cover.clearChildren();
    }

    public void addFood(FoodToken f) {
        int hbwIndex = getAbilityIndex("high_body_weight");
        int parasiteIndex = getAbilityIndex("parasite");
        int carnivorousIndex = getAbilityIndex("carnivorous");
        int parasiteCount = -1;
        if (parasiteIndex != -1) {
            parasiteCount = abilities.get(parasiteIndex).getChildren().size;
        }
        int nextFat = getNextFat();

        if (foodCount == 0) {
            cover.addActor(f);
            f.setPosition(10, getHeight() - f.getHeight() - 10);
            foodCount++;
        } else if (hbwIndex != -1 && !abilities.get(hbwIndex).hasChildren()) {
            addFoodToAbility(f, hbwIndex, 0);
            foodCount++;
        } else if (carnivorousIndex != -1 && !abilities.get(carnivorousIndex).hasChildren()) {
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
        Ability a = abilities.get(abilityIndex);
        a.addActor(f);
        f.addAction(moveTo(10 + shift * (f.getWidth() + 10), a.getHeight() - f.getHeight() - 5, 0.1f));
    }

    public void consumeFat(int fatConsumed) {
        int fatRemaining = fatConsumed;
        for (Ability a : new Array.ArrayIterator<>(abilities)) {
            if (a.getName().equals("fat")) {
                if (a.getChildren().size > 0) {
                    a.clearChildren();
                    fatRemaining--;
                    addFood(new FoodToken(game, FoodTray.TOKEN_SIZE, false));
                }
            }
            if (fatRemaining <= 0) break;
        }
    }

    private int getAbilityIndex(String ability) {
        int i = 0;
        for (Ability a : new Array.ArrayIterator<>(abilities)) {
            if (a.getName().equals(ability)) return i;
            i++;
        }
        return -1;
    }

    private int getNextFat() {
        int i = 0;
        for (Ability a : new Array.ArrayIterator<>(abilities)) {
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
        for (Ability a : new Array.ArrayIterator<>(abilities)) {
            float y = (abilities.size - i) * a.getHeight() / 5;
            a.addAction(moveTo(a.getX(), y, 0.1f));
            i++;
        }
    }

    @Override
    public void reset() {
        setScale(1);
        setRotation(0);
        abilities.clear();
        foodCount = 0;
    }
}
