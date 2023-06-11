package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameOverScreen extends BaseScreen {

    private Stage stage;

    private Skin skin;

    private Image gameOver;

    private Button retry, home;
    public GameOverScreen(final MainJuego juego) {
        super(juego);

        stage = new Stage(new FitViewport(640,480));
        //stage = new Stage(new FitViewport(640,360));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        gameOver= new Image(juego.getManager().get("gameover.png", Texture.class));
        retry = new TextButton("Retry", skin);
        home = new TextButton("Home", skin);

        gameOver.setPosition(120, 150);
        gameOver.setSize(400,300);
        retry.setSize(200,100);
        retry.setPosition(320,50);
        home.setSize(200,100);
        home.setPosition(80,50);
        stage.addActor(retry);
        stage.addActor(gameOver);
        stage.addActor(home);

        retry.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.setScreen(juego.gameScreen);
            }
        });
        home.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.setScreen(juego.homeScreen);
            }
        });

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.4f, 0.6f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }
}
