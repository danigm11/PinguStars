package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class MainJuego extends Game {

	private AssetManager manager;

	public static int getPuntuacion() {
		return puntuacion;
	}

	public static void setPuntuacion(int puntuacion) {
		MainJuego.puntuacion = puntuacion;
	}

	private static int puntuacion;

	public GameScreen gameScreen;
	public GameOverScreen gameOverScreen;
	public MainMenuScreen homeScreen;

	public AssetManager getManager(){
		return this.manager;
	}
	@Override
	public void create () {
		manager = new AssetManager();
		manager.load("pingu.png",Texture.class);
		manager.load("pingu2.png",Texture.class);
		manager.load("pinguT.png",Texture.class);
		manager.load("pinguTV.png",Texture.class);
		manager.load("pinguM.png",Texture.class);
		manager.load("bola.png",Texture.class);
		manager.load("star.png",Texture.class);
		manager.load("hielo.png",Texture.class);
		manager.load("hielo3.png",Texture.class);
		manager.load("pinguC.png",Texture.class);
		manager.load("pinguCE.png",Texture.class);
		manager.load("title2.png",Texture.class);
		manager.load("gameover.png",Texture.class);
		manager.load("sprite-animation4.png",Texture.class);
		manager.load("music.mp3", Music.class);
		manager.load("star.mp3", Sound.class);
		manager.load("death.mp3", Sound.class);
		manager.finishLoading();

		gameScreen= new GameScreen(this);
		gameOverScreen = new GameOverScreen(this);
		homeScreen = new MainMenuScreen(this);

		setScreen(homeScreen);
	}
	
	@Override
	public void dispose () {

	}

}
