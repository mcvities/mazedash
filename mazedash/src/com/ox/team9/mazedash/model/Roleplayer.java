package com.ox.team9.mazedash.model;

import com.badlogic.gdx.graphics.Texture;

import com.ox.team9.mazedash.util.*;
import com.ox.team9.mazedash.visitor.*;

import java.util.ArrayList;

public abstract class Roleplayer extends WorldElement {
	public Roleplayer(Texture sprites) {
		super(sprites);
	}
	
	public abstract int getRadius();
	
	public abstract int getSpeed();
	
	public abstract Direction aiMove();
	
	@Override
	public void accept(GameState gameState, ArrayList<WorldElement> tower, WorldElementVisitor visitor) {
		visitor.visit(gameState, tower, this);
	}
}
