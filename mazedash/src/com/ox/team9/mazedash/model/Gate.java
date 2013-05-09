package com.ox.team9.mazedash.model;

import java.util.ArrayList;

import com.ox.team9.mazedash.visitor.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ox.team9.mazedash.visitor.WorldElementVisitor;

public class Gate extends WorldElement {
	private Texture sprites;
	
	public Gate(Texture sprites) {
		super(sprites);
		
		this.sprites = sprites;
	}
	
	@Override
	public int spriteColumn() {
		return 1;
	}

	@Override
	public int spriteRow() {
		return 3;
	}
	
	@Override
	public int getHeight() {
		return 30;
	}
	
	@Override
	public void accept(GameState gameState, ArrayList<WorldElement> tower, WorldElementVisitor visitor) {
		visitor.visit(gameState, tower, this);
	}
	
	@Override
	public WorldElement cloneElement() {
		return new Gate(sprites);
	}

}
