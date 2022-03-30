package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class AssetLoader {
    private final AssetManager assets;
    private final BitmapFont font;

    public AssetLoader() {
        assets = new AssetManager();
        font = new BitmapFont();
    }

    public void loadAll() {
        for (int i = 1; i <= 20; i++) {
            assets.load("cards/" + Cards.getName(i) + ".png", Texture.class);
         }
        assets.load("cover.png", Texture.class);
        assets.load("table.png", Texture.class);
    }

    public BitmapFont getFont() {
        return font;
    }

    public Texture getCardTexture(String cName) {
        return assets.get("cards/"+cName+".png", Texture.class);
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
}
