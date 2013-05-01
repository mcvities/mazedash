package com.ox.team9.mazedash.visitor;

import java.util.ArrayList;

import com.ox.team9.mazedash.model.*;

public class PlayerVisitor implements WorldElementVisitor {
	protected boolean visited = false;
	protected boolean canVisit;
	
	@Override
	public void visit(ArrayList<WorldElement> tower, Key key) {
		if (visited) {
			throw new IllegalStateException();
		}
		
		visited = true;
		
		canVisit = true;
		
		tower.remove(key);
	}
	
	@Override
	public void visit(ArrayList<WorldElement> tower, Gate gate) {
		if (visited) {
			throw new IllegalStateException();
		}
		
		visited = true;
		
		canVisit = false;
	}
	
	@Override
	public void visit(ArrayList<WorldElement> tower, Block block) {
		if (visited) {
			throw new IllegalStateException();
		}
		
		visited = true;
		
		canVisit = false;
	}
	
	@Override
	public void visit(ArrayList<WorldElement> tower, Player player) {
		
	}
	
	public boolean couldVisit() {
		if (!visited) {
			throw new IllegalStateException();
		}
		
		return canVisit;
	}

}
