package com.ox.team9.mazedash.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.ox.team9.mazedash.util.DisjointForest;


public class WorldGenerator {

	private World world;
	
	private final int rows, columns;
	private final Texture sprites;
	private GameState gameState;
	
	Random rand = new Random();
	
	public WorldGenerator(int rows, int columns, Texture sprites) {
		this.rows = rows;
		this.columns = columns;
		this.sprites = sprites;
		gameState = new GameState(this);
		
		world = newWorld(gameState);
	}
	
	public World newWorld(GameState gameState) {
		
		this.gameState = gameState;
	
		world = new World(rows, columns, sprites, gameState);
	
		
		// Create a "flat" world
		for (int row = 0; row < rows; row += 1)
			for (int column = 0; column < columns; column += 1) {
				world.addElement(row, column, new HighStoneBlock(sprites));
			}
				

		// Add walls
		for (int i = 1; i < rows; i += 2)
			for (int j = 0; j < columns; j++)
				world.addElement(i, j, new Tree(sprites));
		for (int j = 1; j < columns; j+=2)
			for (int i = 0; i < rows; i += 2)
				world.addElement(i, j, new Tree(sprites));

		
		// Each block is represented by the integer (row + column * rows)
		DisjointForest<Integer> forest = new DisjointForest<Integer>();
		

		for (int j = 0; j < (columns-3)*rows; j+=2*rows) {
						
			// Add rest of the column
			for (int i = 0; i < rows; i+=2)
				if (forest.find(i+j) == null) {
					forest.makeSet(i+j);
				}
			

			// Randomly make vertical connections
			for (int i = 0; i < rows-1; i+=2) 
				if (rand.nextBoolean()) {
					forest.union(i+j, i+j+2);
					world.removeElement(i+1,j/rows);										
				}
			
			
			// Make horizontal connections, one per set.
			// First calculate the disjoint elements of the column;
			
			List<List<Integer>> disjointLists = new ArrayList<List<Integer>>();
			List<Integer> column = new ArrayList<Integer>();
			
			for (int i = 0; i < rows; i+=2)		
				column.add(i+j);
			
			forest.disjointElements(column, disjointLists);
			
			
			// Then pick a random element from each list and join it to the next column.
			for (List<Integer> list : disjointLists) {
				Integer join = list.get(rand.nextInt(list.size()));
				forest.makeSet(join + 2*rows);				
				forest.union(join, join + 2*rows);
				world.removeElement(join - j, j/rows + 1);				
			}
		}
		
		
		// For second to last column, do the same but adjusted because
		// of the Gate location
		
		int j = (columns - 3)*rows;
		
		for (int i = 0; i < rows; i+=2)
			if (forest.find(i+j) == null) {
				forest.makeSet(i+j);
			}
		
		for (int i = 0; i < rows-3; i+=2) 
			if (rand.nextBoolean()) {
				forest.union(i+j, i+j+2);
				world.removeElement(i+1,j/rows);										
			}
			
		// Ensure a vertical connection next to gate
		forest.union(rows-3+j, rows-1+j);
		world.removeElement(rows-2,j/rows);		
		
		List<List<Integer>> disjointLists = new ArrayList<List<Integer>>();
		List<Integer> column = new ArrayList<Integer>();
		
		for (int i = 0; i < rows; i+=2)		
			column.add(i+j);
		
		forest.disjointElements(column, disjointLists);
		
		for (List<Integer> list : disjointLists) {
			// No horizontal connection where the gate is
			if (list.contains(rows-1+j)) list.remove(new Integer(rows-1+j));
			Integer join = list.get(rand.nextInt(list.size()));
			forest.makeSet(join + 2*rows);				
			forest.union(join, join + 2*rows);
			world.removeElement(join - j, j/rows + 1);				
		}
		

		// For final column, connect all adjacent disjoint spaces
		
		j = (columns-1)*rows;
		
		for (int i = 0; i < rows; i+=2)
			if (forest.find(i+j) == null) {
				forest.makeSet(i+j);
			}
		
		for (int i = 0; i < rows-1; i+=2)
			if (forest.find(i+j) != forest.find(i+j+2)) {
				forest.union(i+j,i+j+2);
				world.removeElement(i+1, columns-1);
			}
		
		
		// Place key at (rows - 1, 0).
		world.addElement(rows - 1, 0, new Key(sprites));
		
		// Place gate at (0, columns - 1).
		world.addElement(rows-1, columns - 1, new Gate(sprites));
		
		// Place player at (0, 0).
		world.setPlayer(0, 0);
		
		return world;
	}
	
	public World getWorld() {
		return world;
	}
	
}