package com.ox.team9.mazedash.model;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ox.team9.mazedash.visitor.WorldElementVisitor;

import com.ox.team9.mazedash.visitor.*;

public class Player extends Character {
	
	public static final int SPEED_IN_PX_PER_SECOND = 300;
	public static final int SIZE_IN_PX = 40;
	
	public Player(Texture sprites) {
		super(sprites);
	}
	
	@Override
	public int spriteColumn() {
		return 1;
	}
	
	@Override
	public int spriteRow() {
		return 0;
	}
	
	@Override
	public int getHeight() {
		return 0;
	}
	
	public void accept(ArrayList<WorldElement> tower, WorldElementVisitor visitor) {
		visitor.visit(tower, this);
	}
	
}
