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
	private Rectangle card;
	public AssetManager assets;
	private int worldSizeX;
	private int worldSizeY;

	public int getWorldSizeX() {
		return worldSizeX;
	}

	public int getWorldSizeY() {
		return worldSizeY;
	}

	Pixmap initPixmap, resizePixmap;

	@Override
	public void create () {
		assets = new AssetManager();
		assets.load("cards/large-fat.png", Texture.class);
		assets.load("cards/burrower-fat.png", Texture.class);
		font = new BitmapFont();
		batch = new SpriteBatch();
		worldSizeX = 1360;
		worldSizeY = 720;
		this.setScreen(new LoadingScreen(this));


		//SLAVA's STUFF
		/*batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		card = new Rectangle();
//		initPixmap = new Pixmap(Gdx.files.internal("cards/big-fat.png"));

		int size = 75;
		float coeff = 1.4f;
//		resizePixmap = new Pixmap(size, (int) (size*coeff), initPixmap.getFormat());
//		initPixmap.dispose();
		bigFatCard = new Texture(Gdx.files.internal("cards/big-fat.png"));
//		resizePixmap.dispose();
		card.x = 800 / 2 - size / 2;
		card.y = 5;
		card.height = (int) size*coeff;
		card.width = size;*/

	}

	@Override
	public void render () {
		super.render();
		/* SLAVA'S STUFF
		camera.update();
		batch.begin();
		batch.draw(bigFatCard, card.x, card.y);
		batch.end();

		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
//			System.out.println(touchPos.x > card.x);
//			System.out.println(touchPos.x < (card.x + card.width));
//			System.out.println((480-touchPos.y) > card.y);
//			System.out.println((480-touchPos.y) < (card.y + card.height));
//			System.out.println("--------------------------");
			//Тут проверка на то, что нажатие внутри карты почему-то не работает, надо разобраться
			if (touchPos.x > card.x && touchPos.x < (card.x + card.width) && (480-touchPos.y) < card.y && (480-touchPos.y) > (card.y + card.height) ) {
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
