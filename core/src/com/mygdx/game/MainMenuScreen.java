package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MainMenuScreen extends BaseScreen{
    private Stage stage;

    private Skin skin;

    private Image icon;

    private Button start;
    private int record;
    private Label label;
    public MainMenuScreen(final MainJuego juego) {
        super(juego);

        stage = new Stage(new FitViewport(640,480));
        //stage = new Stage(new FitViewport(640,360));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        icon= new Image(juego.getManager().get("title2.png", Texture.class));
        start = new TextButton("START", skin);

        icon.setPosition(150, 250);
        icon.setSize(350,150);
        start.setSize(200,100);
        start.setPosition(220,50);
        stage.addActor(start);
        stage.addActor(icon);

        label= new Label("MAX Score :  "+record,new Skin(Gdx.files.internal("skin/uiskin.json")));
        label.setPosition(500, 400);
        stage.addActor(label);

        start.addCaptureListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                juego.setScreen(juego.gameScreen);
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
        Gdx.gl.glClearColor(0.65f, 0.75f, 0.85f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        record = MainJuego.getPuntuacion();
        label.setText("MAX Score :  "+record);
    }
}
