package ru.nsu.ccfit.evolution.user.actors;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import ru.nsu.ccfit.evolution.common.Abilities;
import ru.nsu.ccfit.evolution.user.actors.listeners.Hoverable;
import ru.nsu.ccfit.evolution.user.actors.listeners.HoverableListener;
import ru.nsu.ccfit.evolution.user.framework.EvolutionGame;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;

public class CreatureView extends GameActor implements Hoverable {
    private final EvolutionGame game;
    private final Cover cover;
    private final ArrayList<AbilityView> abilities;
    private int id;
    private AtomicBoolean dead = new AtomicBoolean(false);

    public CreatureView(EvolutionGame game, float w, float h) {
        super(null, w, h);
        this.game = game;
        abilities = new ArrayList<>();
        cover = new Cover(game, w, h);
        addActor(cover);
        addListener(new HoverableListener(this));
    }

    public void die() {
        final CreatureView c = this;
        addAction(sequence(
                parallel(scaleTo(0.1f, 0.1f, 0.3f),
                        Actions.rotateTo(45, 0.3f)),
                Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        TableView parent = (TableView) getParent();
                        dead.set(true);
                        parent.getDeadCreatures().add(c);
                    }
                })));
    }

    public boolean isDead() {
        return dead.get();
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
        for (AbilityView a : abilities) {
            if (Abilities.isCooperative(a.getName())) {
                CreatureView c = (CreatureView) a.getBuddy().getParent();
                c.removeAbility(a.getBuddy());
            }
        }
    }

    public void removeAbility(AbilityView a) {
        abilities.remove(a);
        removeActor(a);
    }

    public void removeAbility(String ability) {
        for (AbilityView a : abilities) {
            if (a.getName().equals(ability)) {
                abilities.remove(a);
                break;
            }
        }
    }

    public AbilityView addAbility(String abilityName) {
        AbilityView abilityView = new AbilityView(game, getWidth(), getHeight()/5, abilityName);
        addActorBefore(cover, abilityView);
        abilities.add(abilityView);
        return abilityView;
    }

    public int getFatMax() {
        int res = 0;
        for (AbilityView a : abilities) {
            if (a.getName().equals("fat")) {
                res++;
            }
        }
        return res;
    }

    public int getFoodCount() {
        int res = 0;
        for (AbilityView a : abilities) {
            if (!a.getName().equals("fat")) res += a.getChildren().size;
        }
        res += cover.getChildren().size;
        return res;
    }

    public int getFatCount() {
        int res = 0;
        for (AbilityView a : abilities) {
            if (a.getName().equals("fat")) res += a.getChildren().size;
        }
        return res;
    }

    public boolean hasAbility(String abilityName, int partnerID) {
        for (AbilityView a : abilities) {
            if (a.getName().equals(abilityName)) {
                if (partnerID == getID()) return true;
                else {
                    CreatureView partner = (CreatureView) a.getBuddy().getParent();
                    return partner.getID() == partnerID;
                }
            }
        }
        return false;
    }

    public void clearFood() {
        for (AbilityView a : abilities) {
            if (!a.getName().equals("fat")) a.clearChildren();
        }
        cover.clearChildren();
    }

    public void removeFood() {
        for (AbilityView a : abilities) {
            if (a.hasChildren() && !a.getName().equals("fat")) {
                a.clearChildren();
                return;
            }
        }
        if (cover.hasChildren()) {
            cover.clearChildren();
        }
    }

    public void removeFat() {
        for (AbilityView a : abilities) {
            if (a.hasChildren() && a.getName().equals("fat")) {
                a.clearChildren();
                return;
            }
        }
    }

    public void addFood() {
        FoodToken f = new FoodToken(game, FoodTray.TOKEN_SIZE, false, "red");
        int hbwIndex = getAbilityIndex("high_body_weight");
        int parasiteIndex = getAbilityIndex("parasite");
        int carnivorousIndex = getAbilityIndex("carnivorous");
        int parasiteCount = -1;
        if (parasiteIndex != -1) {
            parasiteCount = abilities.get(parasiteIndex).getChildren().size;
        }

        if (getFoodCount() == 0) {
            cover.addActor(f);
            f.setPosition(10, getHeight() - f.getHeight() - 10);
        } else if (hbwIndex != -1 && !abilities.get(hbwIndex).hasChildren()) {
            addFoodToAbility(f, hbwIndex, 0);
        } else if (carnivorousIndex != -1 && !abilities.get(carnivorousIndex).hasChildren()) {
            addFoodToAbility(f, carnivorousIndex, 0);
        } else if (parasiteIndex != -1 && parasiteCount < 2) {
            addFoodToAbility(f, parasiteIndex, parasiteCount);
        }
    }

    public void addFat() {
        FoodToken f = new FoodToken(game, FoodTray.TOKEN_SIZE, false, "yellow");
        int nextFat = getAvailableFatIndex();
        if (nextFat != -1) {
            addFoodToAbility(f, nextFat, 0);
        }
    }

    private void addFoodToAbility(FoodToken f, int abilityIndex, int shift) {
        AbilityView a = abilities.get(abilityIndex);
        a.addActor(f);
        f.addAction(moveTo(10 + shift * (f.getWidth() + 10), a.getHeight() - f.getHeight() - 5, 0.1f));
    }

    public void consumeFat(int fatConsumed) {
        int fatRemaining = fatConsumed;
        for (AbilityView a : abilities) {
            if (a.getName().equals("fat")) {
                if (a.getChildren().size > 0) {
                    a.clearChildren();
                    fatRemaining--;
                    addFood();
                }
            }
            if (fatRemaining <= 0) break;
        }
    }

    private int getAbilityIndex(String ability) {
        int i = 0;
        for (AbilityView a : abilities) {
            if (a.getName().equals(ability)) return i;
            i++;
        }
        return -1;
    }

    private int getAvailableFatIndex() {
        int i = 0;
        for (AbilityView a : abilities) {
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
        for (AbilityView a : abilities) {
            float y = (abilities.size() - i - 1) * a.getHeight() + cover.getHeight();
            a.addAction(moveTo(a.getX(), y, 0.1f));
            i++;
        }
    }

    @Override
    public void reset() {
        setScale(1);
        setRotation(0);
        abilities.clear();
        clearChildren();
        addActor(cover);
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }
}
