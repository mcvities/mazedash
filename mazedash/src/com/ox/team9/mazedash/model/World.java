package com.ox.team9.mazedash.model;

import com.ox.team9.mazedash.util.Array2D;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

import com.ox.team9.mazedash.visitor.*;

public class World {
	public static final int CELL_SIDE_IN_PX = 60;
	public static final int CELL_3D_SIDE_IN_PX = 48;
	
	
	private final int rows, columns;
	private final Array2D<ArrayList<WorldElement>> environment;
	
	// Player element.
	private final WorldElement player;
	
	// Player position.
	private int playerX, playerY, playerH;
	
	// Game state.
	private final GameState gameState;
	
	// Sprites.
	private final Texture sprites;
	
	
	public World(int rows, int columns, Texture sprites, GameState gameState) {
		this.rows = rows;
		this.columns = columns;
		this.gameState = gameState;
		this.sprites = sprites;
		
		player = new Player(sprites);
		
		// Initialize 2D environment.
		environment = new Array2D<ArrayList<WorldElement>>(rows, columns);
		
		// Initialize the element stack for every cell.
		for (int row = 0; row < rows; row += 1)
			for (int column = 0; column < columns; column += 1)
				environment.putElement(new ArrayList<WorldElement>(), row, column);
	}
	
	
	// Stack element up at (row, column).
	public void addElement(int row, int column, WorldElement element) {
		environment.getElement(row, column).add(element);
	}
	
	// Remove element from top of stack at (row, column)
	public void removeElement(int row, int column) {
		environment.getElement(row, column).remove(environment.getElement(row, column).size()-1);
	}

	
	// Get height of cell (row, column).
	public int getHeightAt(int row, int column) {
		int totalHeight = 0;
		
		for (WorldElement worldElement : environment.getElement(row, column)) {
			totalHeight += worldElement.getHeight();
		}
		
		return totalHeight;
	}
	
	
	// Place player on top of the tower at (playerRow, playerColumn).
	public void setPlayer(int playerRow, int playerColumn) {
		playerY = playerRow * CELL_SIDE_IN_PX + CELL_SIDE_IN_PX / 2;
		playerX = playerColumn * CELL_SIDE_IN_PX + CELL_SIDE_IN_PX / 2;
		playerH = getHeightAt(playerRow, playerColumn);
	}
	
	public void movePlayer(int dX, int dY, int dH) {
		int newX = playerX + dX;
		int newY = playerY + dY;
		int newH = playerH + dH;
		
		if (validPlayerCoordinates(newX, newY, newH)) {
			playerX = newX;
			playerY = newY;
			playerH = newH;
		}
	}
	
	private int min(int x, int y) {
		return x < y ? x : y;
	}
	
	private int max(int x, int y) {
		return x > y ? x : y;
	}
	
	private boolean overlap(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
		int minMaxX = min(x2, x4);
		int maxMinX = max(x1, x3);
		
		int minMaxY = min(y2, y4);
		int maxMinY = max(y1, y3);
		
		if (minMaxX >= maxMinX && minMaxY >= maxMinY) {
			return true;
		} else {
			return false;
		}
		
	}
	
	private boolean validPlayerCoordinates(int X, int Y, int H) {
		if (X < Player.SIZE_IN_PX / 2 || X > columns * CELL_SIDE_IN_PX - Player.SIZE_IN_PX / 2 ||
				Y < Player.SIZE_IN_PX / 2 || Y > rows * CELL_SIDE_IN_PX - Player.SIZE_IN_PX / 2) {
			return false;
		}
		
		for (int row = 0; row < rows; row += 1)
			for (int column = 0; column < columns; column += 1) {
				int playerMinX = X - Player.SIZE_IN_PX / 2;
				int playerMaxX = X + Player.SIZE_IN_PX / 2 -1;
				
				int playerMinY = Y - Player.SIZE_IN_PX / 2;
				int playerMaxY = Y + Player.SIZE_IN_PX / 2 - 1;
				
				int totalHeight = 0;
				
				for (WorldElement element : environment.getElement(row, column)) {
					int minX = column * CELL_SIDE_IN_PX;
					int minY = row * CELL_SIDE_IN_PX;
					
					int maxX = (column + 1) * CELL_SIDE_IN_PX - 1;
					int maxY = (row + 1) * CELL_SIDE_IN_PX - 1;
					
					if (H >= totalHeight && H < totalHeight + element.getHeight()) {
						if (overlap(playerMinX, playerMinY, playerMaxX, playerMaxY,
									minX, minY, maxX, maxY)) {
							
							PlayerVisitor playerVisitor = new PlayerVisitor();
							
							element.accept(gameState, environment.getElement(row, column), playerVisitor);
							
							if (!playerVisitor.couldVisit()) {
								return false;
							} else {
								return true;
							}
						}
					}
					
					totalHeight += element.getHeight();
				}
			}
		
		return true;
	}
	
	// Draw the world.
	public void draw(SpriteBatch spriteBatch) {
		//System.out.println("Drawing the world!");
		
		for (int row = rows - 1; row >= 0; row -= 1) {
			for (int column = 0; column < columns; column += 1) {
				int height = 0;
				for (WorldElement worldElement : environment.getElement(row, column)) {
					worldElement.draw(column * World.CELL_SIDE_IN_PX, row * World.CELL_SIDE_IN_PX,
							height, spriteBatch);
					
					height += worldElement.getHeight();
				}
			}
			
			if (playerY - Player.SIZE_IN_PX / 2 >= row * CELL_SIDE_IN_PX &&
				playerY - Player.SIZE_IN_PX / 2 < (row + 1) * CELL_SIDE_IN_PX) {
				player.draw(playerX - World.CELL_SIDE_IN_PX / 2, playerY - World.CELL_SIDE_IN_PX / 2,
						playerH, spriteBatch);
			}
		}
	}
	
	protected void setPlayerCoords(int playerX, int playerY, int playerH) {
		this.playerX = playerX;
		this.playerY = playerY;
		this.playerH = playerH;
	}
	
	public World clone() {
		World newWorld = new World(rows, columns, sprites, gameState);
		
		newWorld.setPlayerCoords(playerX, playerY, playerH);
		
		for (int row = 0; row < rows; row += 1) {
			for (int column = 0; column < columns; column += 1) {
				for (WorldElement element : environment.getElement(row, column)) {
					newWorld.addElement(row, column, element.cloneElement());
				}
			}
		}
		
		return newWorld;
	}
}
