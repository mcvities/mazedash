package com.ox.team9.mazedash.visitor;

import java.util.ArrayList;

import com.ox.team9.mazedash.model.*;


public interface WorldElementVisitor {
	
	void visit(GameState gameState, ArrayList<WorldElement> tower, Key key);
	
	void visit(GameState gameState, ArrayList<WorldElement> tower, Gate gate);
	
	void visit(GameState gameState, ArrayList<WorldElement> tower, Block block);

	void visit(GameState gameState, ArrayList<WorldElement> tower, Roleplayer roleplayer);
	
	void visit(GameState gameState, ArrayList<WorldElement> tower, Obstacle obstacle);
}
