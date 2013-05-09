package com.ox.team9.mazedash.model;

import java.util.ArrayList;

import com.ox.team9.mazedash.visitor.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ox.team9.mazedash.visitor.WorldElementVisitor;

public class Key extends WorldElement {
	private Texture sprites;
	
	public Key(Texture sprites) {
		super(sprites);
		
		this.sprites = sprites;
	}
	
	@Override
	public int spriteColumn() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int spriteRow() {
		// TODO Auto-generated method stub
		return 6;
	}
	
	@Override
	public int getHeight() {
		return 20;
	}
	
	@Override
	public void accept(GameState gameState, ArrayList<WorldElement> tower, WorldElementVisitor visitor) {
		visitor.visit(gameState, tower, this);
	}
	
	@Override
	public WorldElement cloneElement() {
		return new Key(sprites);
	}

}
