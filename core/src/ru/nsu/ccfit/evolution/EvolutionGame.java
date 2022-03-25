package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;



public class EvolutionGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture bigFatCard;
	private Rectangle card;
	Pixmap initPixmap, resizePixmap;
	private OrthographicCamera camera;



	@Override
	public void create () {
		batch = new SpriteBatch();
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
		card.width = size;

	}

	@Override
	public void render () {
		ScreenUtils.clear(220, 220, 220, 0);
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

	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
