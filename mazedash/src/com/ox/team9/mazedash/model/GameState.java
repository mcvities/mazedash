package com.ox.team9.mazedash.model;

public class GameState {
	protected World world;
	protected World savedWorld;
	
	protected boolean keyCollected;
	
	protected boolean paused;
	
	protected final WorldGenerator worldGenerator;
	
	
	public GameState(WorldGenerator worldGenerator) {
		this.worldGenerator = worldGenerator;
		
		reset();
	}
	
	public void reset() {
		world = worldGenerator.newWorld(this);
		savedWorld = null;
		keyCollected = false;
		
		paused = false;
	}
	
	public void pauseGame() {
		paused = true;
	}
	
	public void unpauseGame() {
		paused = false;
	}
	
	public boolean isGamePaused() {
		return paused;
	}
	
	public World getWorld() {
		return world;
	}
	
	public void collectKey() {
		keyCollected = true;
	}
	
	public boolean hasKey() {
		return keyCollected;
	}
	
	public void quicksave() {
		savedWorld = world.clone();
	}
	
	public void quickload() {
		if (savedWorld != null) {
			world = savedWorld;
			savedWorld = world.clone();
		}
	}
}
