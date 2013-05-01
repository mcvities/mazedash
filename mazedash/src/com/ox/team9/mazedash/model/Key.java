package com.ox.team9.mazedash.model;

import java.util.ArrayList;

import com.ox.team9.mazedash.visitor.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ox.team9.mazedash.visitor.WorldElementVisitor;

public class Key extends WorldElement {

	public Key(Texture sprites) {
		super(sprites);
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
	
	public void accept(ArrayList<WorldElement> tower, WorldElementVisitor visitor) {
		visitor.visit(tower, this);
	}

}
