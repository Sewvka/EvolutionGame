package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;



public class EvolutionGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture bigFatCard;
	private Rectangle card;
	Pixmap initPixmap, resizePixmap;


	@Override
	public void create () {
		batch = new SpriteBatch();
		card = new Rectangle();
		initPixmap = new Pixmap(Gdx.files.internal("cards/big-fat.png"));
		int size = 200;
		float coeff = 1.4f;
		resizePixmap = new Pixmap(size, (int) (size*coeff), initPixmap.getFormat());
		initPixmap.dispose();
		bigFatCard = new Texture(resizePixmap);
		resizePixmap.dispose();
		card.x = 800 / 2 - size / 2;
		card.y = 5;
		card.height = (int) size*coeff;
		card.width = size;

	}

	@Override
	public void render () {
		ScreenUtils.clear(220, 220, 220, 0);
		batch.begin();
		batch.draw(bigFatCard, card.x, card.y);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
