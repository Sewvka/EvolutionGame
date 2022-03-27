package ru.nsu.ccfit.evolution;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import javax.sound.midi.Soundbank;

public class newDialog implements Screen {

    private Stage stage;
    @Override
    public void show() {
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("skin/uiskin.atlas"));
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        skin.addRegions(atlas);
        System.out.println("1");
        chooseDialog newDialog = new chooseDialog("New dialog", skin);
        System.out.println("2");
        stage = new Stage();
        newDialog.show(stage);
        System.out.println("3");
    }

    public static class chooseDialog extends Dialog {

        public chooseDialog(String title, Skin skin) {
            super(title, skin);
        }

        public chooseDialog(String title, Skin skin, String windowStyleName) {
            super(title, skin, windowStyleName);
        }

        public chooseDialog(String title, WindowStyle windowStyle) {
            super(title, windowStyle);
        }
        {
            text("Choose smth");
            button("First", "First was chosen");
            button("Second", "Second was chosen");
        }

        @Override
        protected void result(Object object) {
            System.out.println(object);
            super.result(object);
        }


    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
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
