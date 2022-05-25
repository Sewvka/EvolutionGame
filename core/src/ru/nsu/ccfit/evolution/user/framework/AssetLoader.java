package ru.nsu.ccfit.evolution.user.framework;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import ru.nsu.ccfit.evolution.common.Cards;

public class AssetLoader {
    private final AssetManager assets;
    private final BitmapFont font;
    private final Skin skin;

    public AssetLoader() {
        skin = new Skin(Gdx.files.internal("styles/uiskin.json"), new TextureAtlas(Gdx.files.internal("styles/uiskin.atlas")));
        assets = new AssetManager();
        font = new BitmapFont();
    }

    public void loadAll() {
        for (int i = 1; i <= 20; i++) {
            assets.load("cards/" + Cards.getName(i) + ".png", Texture.class);
        }
        assets.load("cards/cover.png", Texture.class);
        assets.load("table.png", Texture.class);
        assets.load("food/food_red.png", Texture.class);
        assets.load("food/food_yellow.png", Texture.class);
        assets.load("food/food_blue.png", Texture.class);
    }

    public BitmapFont getFont() {
        return font;
    }

    public Texture getCardTexture(String cardName) {
        return assets.get("cards/" + cardName + ".png", Texture.class);
    }

    public Texture getAbilityTexture(String abilityName) {
        return null;
    }

    public Texture getTexture(String filename) {
        return assets.get(filename, Texture.class);
    }

    public void dispose() {
        assets.dispose();
        font.dispose();
    }

    public boolean isLoaded() {
        return assets.update();
    }

    public float progress() {
        return assets.getProgress();
    }

    public Skin getSkin() {
        return skin;
    }
}
