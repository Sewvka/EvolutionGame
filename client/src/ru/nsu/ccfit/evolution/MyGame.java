package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import ru.nsu.ccfit.evolution.network.MyClient;

public class MyGame {

    private MyGame() {}

    public static final String GAME_TITLE = "Evolution";

    /* Player. */
    public static MyClient client;
    //public static Controller controller;

    /* Display. */
    //public static ShapeDrawer shapeDrawer;
    public static SpriteBatch batch;
    public static BitmapFont font;
    public static Skin skin;
    public static Table root;
    public static Stage stage;

}
