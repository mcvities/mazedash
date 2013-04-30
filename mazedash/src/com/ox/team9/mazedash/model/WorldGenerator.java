package com.ox.team9.mazedash.model;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.ox.team9.mazedash.util.DisjointForest;
import com.ox.team9.mazedash.util.Tuple;


public class WorldGenerator {

	private final World world;
	
	private final int rows, columns;
	
	public WorldGenerator(int rows, int columns, Texture sprites) {
		this.rows = rows;
		this.columns = columns;
		
		world = new World(rows, columns, sprites);
		
		
		// Create a "flat" world
		for (int row = 0; row < rows; row += 1)
			for (int column = 0; column < columns; column += 1) {
				world.addElement(row, column, new HighStoneBlock(sprites));
			}
		
		// Generate graph here.
		

		// STARTS HERE

		
		
		
		

		// Generate graph here.
		Random rand = new Random();

		// Add walls
		for (int i = 1; i < rows; i += 2)
			for (int j = 0; j < columns; j++)
				world.addElement(i, j, new HighStoneBlock(sprites));
		for (int j = 1; j < columns; j+=2)
			for (int i = 0; i < rows; i += 2)
				world.addElement(i, j, new HighStoneBlock(sprites));

		DisjointForest<Tuple<Integer, Integer>> forest = new DisjointForest<Tuple<Integer, Integer>>();

		// Add first column to forest	
		for (int i = 0; i < rows; i+=2)
			forest.makeSet(new Tuple<Integer, Integer>(i,0));

		for (int j = 0; j < columns-2; j+=2) {

			// Randomly make vertical connections
			for (int i = 0; i < rows; i+=2) {
				if (rand.nextBoolean() && i < rows-1) {
					forest.union(new Tuple<Integer, Integer>(i,j), new Tuple<Integer, Integer>(i+1,j));
					world.removeElement(i+1, j);
				}
				// Make horizontal connection, not currently random
				else {
					forest.union(new Tuple<Integer, Integer>(i,j), new Tuple<Integer, Integer>(i,j+2));
					world.removeElement(i, j+1);
				}
			}
			// Add rest of the next column
			for (int i = 0; i < rows; i+=2)
				if (forest.find(new Tuple<Integer, Integer>(i,j)) == null)
					forest.makeSet(new Tuple<Integer, Integer>(i,j));
		}

		// For final column, connect all adjacent disjoint spaces
		for (int i = 0; i < rows-2; i+=2)
			if (forest.find(new Tuple<Integer, Integer>(i,columns-1)) != 
			forest.find(new Tuple<Integer, Integer>(i+2,columns-1)))
				forest.union(new Tuple<Integer, Integer>(i,columns-1), new Tuple<Integer, Integer>(i+2,columns-1));

		
		
		
		
		
		
		// ENDS HERE
		
		
		
		
		
		
		
		
		
		
		// Place key at (rows - 1, 0).
		world.addElement(rows - 1, 0, new Key(sprites));
		
		// Place gate at (0, columns - 1).
		world.addElement(0, columns - 1, new Gate(sprites));
		
		// Place player at (0, 0).
		world.setPlayer(0, 0);
	}
	
	public World getWorld() {
		return world;
	}
	
}
