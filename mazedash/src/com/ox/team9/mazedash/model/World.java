package com.ox.team9.mazedash.model;

import com.ox.team9.mazedash.util.Array2D;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class World {
	public static final int CELL_SIDE_IN_PX = 60;
	public static final int CELL_3D_SIDE_IN_PX = 48;
	
	
	private final int rows, columns;
	private final Array2D<ArrayList<WorldElement>> environment;
	
	// Player element.
	private final WorldElement player;
	
	// Player position.
	private int playerX, playerY, playerH;
	
	
	
	public World(int rows, int columns, Texture sprites) {
		this.rows = rows;
		this.columns = columns;
		
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
		playerX += dX;
		playerY += dY;
		playerH += dH;
	}
	
	// Draw the world.
	public void draw(SpriteBatch spriteBatch) {
		System.out.println("Drawing the world!");
		
		for (int row = rows - 1; row >= 0; row -= 1)
			for (int column = 0; column < columns; column += 1) {
				int height = 0;
				for (WorldElement worldElement : environment.getElement(row, column)) {
					worldElement.draw(column * World.CELL_SIDE_IN_PX, row * World.CELL_SIDE_IN_PX,
							height, spriteBatch);
					
					height += worldElement.getHeight();
				}
			}
		
		player.draw(playerX - World.CELL_SIDE_IN_PX / 2, playerY - World.CELL_SIDE_IN_PX / 2,
				playerH, spriteBatch);
	}
}
