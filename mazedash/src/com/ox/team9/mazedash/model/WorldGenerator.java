package com.ox.team9.mazedash.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.ox.team9.mazedash.util.DisjointForest;


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
				world.addElement(i, j, new Key(sprites));
		for (int j = 1; j < columns; j+=2)
			for (int i = 0; i < rows; i += 2)
				world.addElement(i, j, new Key(sprites));

		DisjointForest<Integer> forest = new DisjointForest<Integer>();

		// Add first column to forest	
		for (int i = 0; i < rows; i+=2) {
			forest.makeSet(i);
			
			System.out.println(i+" added to first column");
		}

		for (int j = 0; j < (columns-2)*rows; j+=2*rows) {
			
			System.out.println("Column "+j/rows);

			// Randomly make vertical connections
			for (int i = 0; i < rows-2; i+=2) {
				if (rand.nextBoolean()) {
					forest.union(i+j, i+j+2);
					world.removeElement(i+1,j/rows);
										
					System.out.println("Connect: "+i+ ","+(i+2));
				}
				
		
				
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
				
				System.out.println(list);
				
				System.out.println("Horizontal break at: "+(join-j));
			}
				
			
				
			// Add rest of the next column
			for (int i = 0; i < rows; i+=2)
				if (forest.find(i+j) == null) {
					forest.makeSet(i+j);
					
					System.out.println(i+" added to "+j/rows+"th column");
				}
		}

		// For final column, connect all adjacent disjoint spaces
		for (int i = 0; i < rows-2; i+=2)
			if (forest.find(i+(columns-1)*rows) != forest.find(i+(columns-1)*rows+2)) {
				forest.union(i+(columns-1)*rows,i+(columns-1)*rows+2);
				world.removeElement(i+1, columns-1);
			}
		
		
		
		
		
		
		// ENDS HERE
		
		
		
		
		
		
		
		
		
		
		// Place key at (rows - 1, 0).
		//world.addElement(rows - 1, 0, new Key(sprites));
		
		// Place gate at (0, columns - 1).
		//world.addElement(0, columns - 1, new Gate(sprites));
		
		// Place player at (0, 0).
		world.setPlayer(0, 0);
	}
	
	public World getWorld() {
		return world;
	}
	
}
