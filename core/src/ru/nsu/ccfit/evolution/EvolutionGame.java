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
		assets.load("cards/cover.png", Texture.class);
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
		/* SLAVA'S STUFF
		camera.update();
		batch.begin();
		batch.draw(bigFatCard, card.x, card.y);
		batch.end();

		//TODO make it work with all cards, maybe as a class dnd(drag and drop)
		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			if ((touchPos.x > card.x && touchPos.x < (card.x + card.width)) && ((480-touchPos.y) > card.y && (480-touchPos.y) < (card.y + card.height))) {
				camera.unproject(touchPos);
				card.x = touchPos.x - card.width / 2;
				card.y = touchPos.y - card.height / 2;
			}
		}

		 */
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		assets.dispose();
	}
}
