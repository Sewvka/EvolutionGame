package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;


//TODO Branch for development
public class EvolutionGame extends Game {
	SpriteBatch batch;
	public BitmapFont font;
	public AssetManager assets;
	private float worldSizeX;
	private float worldSizeY;

	public float getWorldSizeX() {
		return worldSizeX;
	}

	public float getWorldSizeY() {
		return worldSizeY;
	}

	@Override
	public void create () {
		//подгрузка ассетов
		assets = new AssetManager();
		assets.load("cards/large-fat.png", Texture.class);
		assets.load("cards/burrower-fat.png", Texture.class);
		assets.load("oldcards/cover.png", Texture.class);
		assets.load("table.png", Texture.class);

		font = new BitmapFont();
		batch = new SpriteBatch();
		worldSizeX = 1360;
		worldSizeY = 720;
		this.setScreen(new LoadingScreen(this));
	}

	@Override
	public void render () {

		super.render();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		assets.dispose();
	}
}
