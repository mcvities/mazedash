package com.ox.team9.mazedash.model;

import java.util.ArrayList;

import com.ox.team9.mazedash.visitor.WorldElementVisitor;

import com.badlogic.gdx.graphics.Texture;

public class Tree extends Obstacle {
	private Texture sprites;
	
	public Tree(Texture sprites) {
		super(sprites);
		
		this.sprites = sprites;
	}
	
	@Override
	public int spriteColumn() {
		return 0;
	}

	@Override
	public int spriteRow() {
		return 16;
	}

	@Override
	public int getHeight() {
		return 20;
	}
	
	@Override
	public WorldElement cloneElement() {
		return new Tree(sprites);
	}

}
