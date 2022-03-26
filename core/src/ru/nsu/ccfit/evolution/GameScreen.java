package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {

    final EvolutionGame game;
    OrthographicCamera camera;
    private final Deck deck;
    private final CreatureTable table;

    public GameScreen(final EvolutionGame game) {
        //вот всё это говно хорошо бы по методам для красоты разобрать
        this.game = game;
        table = new CreatureTable(game, 700, 160);
        deck = new Deck(game, table);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.getWorldSizeX(), game.getWorldSizeY());

        //для эксперимента генерим в колоду карты (максимум карт - 6)
        deck.addCard(1);
        deck.addCard(2);
        deck.addCard(1);
        deck.addCard(1);
        deck.addCard(1);
        deck.addCard(2);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(220, 220, 220, 0);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(table.tableTexture, table.x, table.y, table.width, table.height);
        table.drawAll(game.batch);
        deck.drawAll(game.batch);
        game.batch.end();

        Vector3 mousePos = new Vector3();
        mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);

        deck.update(new Vector2(mousePos.x, mousePos.y));
        table.update();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
