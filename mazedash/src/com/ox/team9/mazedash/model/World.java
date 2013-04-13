package com.ox.team9.mazedash.model;

import com.ox.team9.mazedash.util.Array2D;

import java.util.LinkedList;

public class World {
	private final int rows, columns;
	private final Array2D<LinkedList<WorldElement>> world;
	
	public World(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		
		world = new Array2D<LinkedList<WorldElement>>(rows, columns);
	}
	
	
}
