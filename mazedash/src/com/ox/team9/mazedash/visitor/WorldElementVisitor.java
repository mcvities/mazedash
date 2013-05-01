package com.ox.team9.mazedash.visitor;

import java.util.ArrayList;

import com.ox.team9.mazedash.model.*;


public interface WorldElementVisitor {
	
	void visit(ArrayList<WorldElement> tower, Key key);
	
	void visit(ArrayList<WorldElement> tower, Gate gate);
	
	void visit(ArrayList<WorldElement> tower, Block block);

	void visit(ArrayList<WorldElement> tower, Player player);
}
