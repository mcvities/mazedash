package com.ox.team9.mazedash.model;

import java.util.ArrayList;

import com.ox.team9.mazedash.visitor.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ox.team9.mazedash.visitor.WorldElementVisitor;

public class Gate extends WorldElement {

	public Gate(Texture sprites) {
		super(sprites);
	}
	
	@Override
	public int spriteColumn() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int spriteRow() {
		// TODO Auto-generated method stub
		return 3;
	}
	
	@Override
	public int getHeight() {
		return 30;
	}
	
	public void accept(ArrayList<WorldElement> tower, WorldElementVisitor visitor) {
		visitor.visit(tower, this);
	}

}
