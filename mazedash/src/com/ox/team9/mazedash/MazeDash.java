package com.ox.team9.mazedash;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import com.ox.team9.mazedash.model.*;

public class MazeDash implements ApplicationListener {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture sprites;
	
	// ShapeRenderer object for HUD.
	private ShapeRenderer menuShapeRenderer;
	
	// Texture objects for HUD buttons.
	private Texture menuButton;
	private Texture resumeButton;
	private Texture quicksaveButton;
	private Texture quickloadButton;
	private Texture exitButton;
	
	// Sprite objects for HUD buttons.
	private Sprite menuSprite;
	private Sprite resumeSprite;
	private Sprite quicksaveSprite;
	private Sprite quickloadSprite;
	private Sprite exitSprite;
	
	// Game state.
	private GameState gameState;
	
	@Override
	public void create() {	
		
		Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound.mp3")); 
		
		long id = sound.play(1.0f);
		sound.setLooping(id, true);
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 780, 480);
		
		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined);
		
		sprites = new Texture(Gdx.files.internal("sprites.png"));
		
		
		// Handle the HUD.
		menuShapeRenderer = new ShapeRenderer();
		camera.update();
		menuShapeRenderer.setProjectionMatrix(camera.combined);
		
		menuButton = new Texture(Gdx.files.internal("MenuButton.png"));
		menuSprite = new Sprite(menuButton, 0, 0, 164, 44);
		menuSprite.setPosition(780 - 164, 480 - 44);
		
		int MENU_SIDE_X = (780 - 300) / 2;
		int MENU_SIDE_Y = (480 - 250) / 2;
		
		int LEFT_MARGIN = (300 - 164) / 2;
		int TOP_MARGIN = (250 - 44 * 4) / 2;
		
		resumeButton = new Texture(Gdx.files.internal("ResumeButton.png"));
		resumeSprite = new Sprite(resumeButton, 0, 0, 164, 44);
		resumeSprite.setPosition(MENU_SIDE_X + LEFT_MARGIN, 480 - TOP_MARGIN - MENU_SIDE_Y - 44);
		
		quicksaveButton = new Texture(Gdx.files.internal("QuicksaveButton.png"));
		quicksaveSprite = new Sprite(quicksaveButton, 0, 0, 164, 44);
		quicksaveSprite.setPosition(MENU_SIDE_X + LEFT_MARGIN, 480 - TOP_MARGIN - MENU_SIDE_Y - 44 * 2);
		
		quickloadButton = new Texture(Gdx.files.internal("QuickloadButton.png"));
		quickloadSprite = new Sprite(quickloadButton, 0, 0, 164, 44);
		quickloadSprite.setPosition(MENU_SIDE_X + LEFT_MARGIN, 480 - TOP_MARGIN - MENU_SIDE_Y - 44 * 3);
		
		exitButton = new Texture(Gdx.files.internal("ExitButton.png"));
		exitSprite = new Sprite(exitButton, 0, 0, 164, 44);
		exitSprite.setPosition(MENU_SIDE_X + LEFT_MARGIN, 480 - TOP_MARGIN - MENU_SIDE_Y - 44 * 4);
		
		// End of HUD.
		
		// Initialise game state.
		gameState = new GameState(new WorldGenerator(7, 13, sprites));
		
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
	
	private void processActiveInput() {
		if (Gdx.input.isTouched()) {
			int X = Gdx.input.getX();
			int Y = Gdx.input.getY();
			
			if (Y <= 44 && X > 780 - 164) {
				gameState.pauseGame();
			}
		} else {
			if (Gdx.input.isKeyPressed(Keys.UP)) {
				int dY = (int)(Gdx.graphics.getDeltaTime() * Player.SPEED_IN_PX_PER_SECOND);
				gameState.getWorld().movePlayer(0, dY, 0);
			} else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
				int dY = -(int)(Gdx.graphics.getDeltaTime() * Player.SPEED_IN_PX_PER_SECOND);
				gameState.getWorld().movePlayer(0, dY, 0);
			} else if (Gdx.input.isKeyPressed(Keys.LEFT)) {
				int dX = -(int)(Gdx.graphics.getDeltaTime() * Player.SPEED_IN_PX_PER_SECOND);
				gameState.getWorld().movePlayer(dX, 0, 0);
			} else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
				int dX = (int)(Gdx.graphics.getDeltaTime() * Player.SPEED_IN_PX_PER_SECOND);
				gameState.getWorld().movePlayer(dX, 0, 0);
			}
		}
	}
	
	private void processPausedInput() {
		if (Gdx.input.isTouched()) {
			int X = Gdx.input.getX();
			int Y = Gdx.input.getY();
			
			int MENU_SIDE_X = (780 - 300) / 2;
			int MENU_SIDE_Y = (480 - 250) / 2;
			
			int LEFT_MARGIN = (300 - 164) / 2;
			int TOP_MARGIN = (250 - 44 * 4) / 2;
			
			if (X >= MENU_SIDE_X + LEFT_MARGIN && X < MENU_SIDE_X + LEFT_MARGIN + 164) {
				if (Y >= MENU_SIDE_Y + TOP_MARGIN && Y < MENU_SIDE_Y + TOP_MARGIN + 44) {
					gameState.unpauseGame();
				} else if (Y >= MENU_SIDE_Y + TOP_MARGIN + 44 && Y < MENU_SIDE_Y + TOP_MARGIN + 44 * 2) {
				
					gameState.quicksave();
					gameState.unpauseGame();
				
				} else if (Y >= MENU_SIDE_Y + TOP_MARGIN + 44 * 2 && Y < MENU_SIDE_Y + TOP_MARGIN + 44 * 3) {
				
					gameState.quickload();
					gameState.unpauseGame();
				
				} else if (Y >= MENU_SIDE_Y + TOP_MARGIN + 44 * 3 && Y < MENU_SIDE_Y + TOP_MARGIN + 44 * 4) {
					Gdx.app.exit();
				}
			}
		}
	}
	
	private void processInput() {
		if (gameState.isGamePaused()) {
			processPausedInput();
		} else {
			processActiveInput();
		}
	}
	
	private void processGravity() {
		
	}
	
	public void drawWorld(GameState gameState) {
		batch.begin();
		
		gameState.getWorld().draw(batch);
		
		batch.end();
	}
	
	public void drawHUD(GameState gameState) {
		
		if (gameState.isGamePaused()) {
			menuShapeRenderer.begin(ShapeType.Filled);
			
			menuShapeRenderer.setColor(Color.LIGHT_GRAY);
			menuShapeRenderer.rect((780 - 300) / 2, (480 - 250) / 2, 300, 250);
			
			menuShapeRenderer.end();
			
			menuShapeRenderer.begin(ShapeType.Line);
			
			menuShapeRenderer.setColor(Color.BLACK);
			menuShapeRenderer.rect((780 - 300) / 2, (480 - 250) / 2, 300, 250);
			
			menuShapeRenderer.end();
		}
		
		batch.begin();
		
		if (!gameState.isGamePaused()) {
			menuSprite.draw(batch);
		} else {
			resumeSprite.draw(batch);
			quicksaveSprite.draw(batch);
			quickloadSprite.draw(batch);
			exitSprite.draw(batch);
		}
		
		batch.end();
	}

	@Override
	public void render() {	
		processInput();
		processGravity();
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		drawWorld(gameState);
		
		drawHUD(gameState);
		
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
}
