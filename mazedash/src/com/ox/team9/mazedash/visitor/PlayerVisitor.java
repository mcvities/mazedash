package com.ox.team9.mazedash.visitor;

import java.util.ArrayList;

import com.ox.team9.mazedash.model.*;

public class PlayerVisitor implements WorldElementVisitor {
	protected boolean visited = false;
	protected boolean canVisit;
	
	@Override
	public void visit(GameState gameState, ArrayList<WorldElement> tower, Key key) {
		if (visited) {
			throw new IllegalStateException();
		}
		
		visited = true;
		
		canVisit = true;
		
		gameState.collectKey();
		
		tower.remove(key);
		
		//System.out.println("Collected key.");
	}
	
	@Override
	public void visit(GameState gameState, ArrayList<WorldElement> tower, Gate gate) {
		if (visited) {
			throw new IllegalStateException();
		}
		
		visited = true;
		
		canVisit = false;
		
		if (gameState.hasKey()) {
			gameState.reset();
		}
	}
	
	@Override
	public void visit(GameState gameState, ArrayList<WorldElement> tower, Block block) {
		if (visited) {
			throw new IllegalStateException();
		}
		
		visited = true;
		
		canVisit = false;
	}
	
	@Override
	public void visit(GameState gameState, ArrayList<WorldElement> tower, Obstacle obstacle) {
		if (visited) {
			throw new IllegalStateException();
		}
		
		visited = true;
		
		canVisit = false;
	}
	
	@Override
	public void visit(GameState gameState, ArrayList<WorldElement> tower, Roleplayer roleplayer) {
		throw new IllegalStateException();
	}
	
	public boolean couldVisit() {
		if (!visited) {
			throw new IllegalStateException();
		}
		
		return canVisit;
	}

}
