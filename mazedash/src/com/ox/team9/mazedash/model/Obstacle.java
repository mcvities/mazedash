package com.ox.team9.mazedash.model;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.ox.team9.mazedash.visitor.WorldElementVisitor;

public abstract class Obstacle extends WorldElement {
	public Obstacle(Texture sprites) {
		super(sprites);
	}
	
	@Override
	public void accept(GameState gameState, ArrayList<WorldElement> tower, WorldElementVisitor visitor) {
		// TODO Auto-generated method stub
		visitor.visit(gameState, tower, this);
	}
}
