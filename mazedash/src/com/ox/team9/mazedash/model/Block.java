package com.ox.team9.mazedash.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.ox.team9.mazedash.visitor.*;

import java.util.ArrayList;

public abstract class Block extends WorldElement {
	
	protected Block(Texture sprites) {
		super(sprites);
	}
	
	public void accept(ArrayList<WorldElement> tower, WorldElementVisitor visitor) {
		visitor.visit(tower, this);
	}
}
