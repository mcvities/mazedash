package com.ox.team9.mazedash;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.ox.team9.mazedash.model.Player;
import com.ox.team9.mazedash.model.World;
import com.ox.team9.mazedash.model.WorldGenerator;

public class MazeDash implements ApplicationListener {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture sprites;
	
	private World world;
	
	private WorldGenerator worldGenerator;
	
	@Override
	public void create() {		
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 780, 480);
		
		batch = new SpriteBatch();
		
		sprites = new Texture(Gdx.files.internal("sprites.png"));
		
		worldGenerator = new WorldGenerator(9, 13, sprites);
		
		world = worldGenerator.getWorld();
		
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void render() {	
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			//System.out.println("UP IS PRESSED");
			int dY = (int)(Gdx.graphics.getDeltaTime() * Player.SPEED_IN_PX_PER_SECOND);
			world.movePlayer(0, dY, 0);
			
		} else if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			int dY = -(int)(Gdx.graphics.getDeltaTime() * Player.SPEED_IN_PX_PER_SECOND);
			world.movePlayer(0, dY, 0);
		} else if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			int dX = -(int)(Gdx.graphics.getDeltaTime() * Player.SPEED_IN_PX_PER_SECOND);
			world.movePlayer(dX, 0, 0);
		} else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			int dX = (int)(Gdx.graphics.getDeltaTime() * Player.SPEED_IN_PX_PER_SECOND);
			world.movePlayer(dX, 0, 0);
		}
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		world.draw(batch);
		
		batch.end();
		
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
