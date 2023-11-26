package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.entities.ActorPlayer;
import com.mygdx.game.entities.ActorStar;
import com.mygdx.game.entities.BallEntity;
import com.mygdx.game.entities.FloorEntity;

import java.util.ArrayList;


public class GameScreen extends BaseScreen{

    private Stage stage;
    private World world;
    private Label label;
    private int puntos= 0;
    private ActorPlayer player;
    private FloorEntity floor;
    private BallEntity ball;
    private ActorStar star;
    /////////////////////////////////////////////
    private Texture textura;
    private Animation animation;
    private float tiempo;
    private ArrayList<Texture> frames = new ArrayList<Texture>();

    /////////////////////////////////////////////////////////////
    private Music bgMusic;
    private Sound coinSound;
    private Sound deathSound;

    private boolean contactoEstrella, contactoBola;
    public GameScreen(final MainJuego juego) {
        super(juego);

        stage = new Stage(new FitViewport(640, 480));
        //stage = new Stage(new FitViewport(640, 360));        //stage.setDebugAll(true);
        world = new World(new Vector2(0, -7), true);
        bgMusic = juego.getManager().get("music.mp3");
        coinSound = juego.getManager().get("star.mp3");
        deathSound = juego.getManager().get("death.mp3");
        world.setContactListener(new ContactListener(){

            private boolean colision(Contact contact, Object userA, Object userB){
                return (((contact.getFixtureA().getUserData().equals(userA))&&(contact.getFixtureB().getUserData().equals(userB)))||((contact.getFixtureA().getUserData().equals(userB))&&(contact.getFixtureB().getUserData().equals(userA))));
            }
            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA(), fixtureB = contact.getFixtureB();
                if (colision(contact, "pingu", "star")&& player.isPinguVivo()){
                    contactoEstrella=true;
                    coinSound.play();
                    player.setTexture((Texture) juego.getManager().get("pinguCE.png"));
                    puntos++;
                }
                if (colision(contact, "pingu", "ball")){
                    player.setPinguVivo(false);
                    deathSound.play();

                    stage.addAction(
                            Actions.sequence(
                                    Actions.delay(2f),
                                    Actions.run(new Runnable() {
                                        @Override
                                        public void run() {
                                            juego.setScreen(juego.gameOverScreen);
                                        }
                                    })
                            )
                    );
                }
                if (colision(contact, "ball", "floor")){
                    contactoBola=true;
                }
                if (colision(contact, "star", "floor")){
                    contactoEstrella=true;
                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
        frames.add((Texture)juego.getManager().get("pingu.png"));
        frames.add((Texture)juego.getManager().get("pinguTV.png"));
        frames.add((Texture)juego.getManager().get("pinguT.png"));
        animation=new Animation<>(1,frames);
        tiempo=0f;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.4f, 0.6f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        world.step(1/60f, 6, 2);
        stage.draw();

        if(contactoEstrella&&player.isPinguVivo()){
            star.getBody().setTransform(((int)(Math.random()*8)),6, 0);
            star.getBody().setLinearVelocity(0,0);
            star.getBody().setAngularVelocity(0);
            contactoEstrella=false;
        }
        if(contactoBola&&player.isPinguVivo()){
            ball.getBody().setTransform(((int)(Math.random()*8)),7, 0);
            ball.getBody().setLinearVelocity(0,0);
            ball.getBody().setAngularVelocity(0);
            contactoBola=false;
        }
        label.setText("Score :  "+puntos);
        if(puntos>MainJuego.getPuntuacion()){
            MainJuego.setPuntuacion(puntos);
        }
        tiempo+=Gdx.graphics.getDeltaTime();
       if((player.getBody().getLinearVelocity().x>0.8)&& player.isPinguVivo()){
            //textura=(Texture) animation.getKeyFrame(tiempo,true);
            player.setTexture((Texture)juego.getManager().get("pinguT.png"));
        }else if((player.getBody().getLinearVelocity().x<0.8)&&(player.getBody().getLinearVelocity().x>-0.8)&& player.isPinguVivo()){
            player.setTexture((Texture)juego.getManager().get("pinguC.png"));
        }else{
           player.setTexture((Texture)juego.getManager().get("pinguTV.png"));
       }
        if(!player.isPinguVivo()){
            player.getBody().setLinearVelocity(0,0);
            player.getBody().setAngularVelocity(0);
            player.setTexture((Texture) juego.getManager().get("pinguM.png"));
            bgMusic.stop();
            puntos=0;
        }
    }

    @Override
    public void show() {
        Texture texture = juego.getManager().get("pinguC.png");
        Texture textureFloor = juego.getManager().get("hielo3.png");
        Texture textureStar = juego.getManager().get("star.png");
        Texture textureBall = juego.getManager().get("bola.png");

        floor=new FloorEntity(textureFloor, world, new Vector2(0f,-0.25f));
        stage.addActor(floor);

        floor=new FloorEntity(textureFloor, world, new Vector2(-8f,1.25f));
        stage.addActor(floor);

        floor=new FloorEntity(textureFloor, world, new Vector2(15.5f,1.25f));
        stage.addActor(floor);

        player=new ActorPlayer(texture, world, new Vector2(3.5f,1.15f));
        stage.addActor(player);

        star=new ActorStar(textureStar, world, new Vector2(2f,7f));
        stage.addActor(star);

        ball=new BallEntity(textureBall, world, new Vector2(6f,7f));
        stage.addActor(ball);

        label= new Label("Puntuacion: "+puntos,new Skin(Gdx.files.internal("skin/uiskin.json")));
        label.setPosition(500, 400);
        stage.addActor(label);

        bgMusic.setVolume(0.15f);
        bgMusic.setLooping(true);
        bgMusic.play();

    }

    @Override
    public void hide() {
        player.detach();
        player.remove();
        floor.detach();
        floor.remove();
        star.detach();
        star.remove();
        ball.detach();
        ball.remove();
        label.remove();
    }

    @Override
    public void dispose() {
        world.dispose();
        stage.dispose();
    }
}
