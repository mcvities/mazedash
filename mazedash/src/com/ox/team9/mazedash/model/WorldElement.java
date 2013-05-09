package com.ox.team9.mazedash.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.ox.team9.mazedash.visitor.*;

import java.util.ArrayList;

public abstract class WorldElement {
	/*
	 * Sprite object that will handle drawing the element.
	 */
	protected Sprite sprite;
	
	
	/*
	 * Constructors.
	 */
	public WorldElement(Texture sprites) {
		sprite = new Sprite(sprites, spriteColumn() * 60, spriteRow() * 104, 60, 104);
	}
	
	
	/*
	 * The position of the graphics in the sprite grid.
	 */
	public abstract int spriteColumn();
	
	public abstract int spriteRow();
	
	
	/*
	 * The height of the element.
	 */
	public abstract int getHeight();
	
	
	/*
	 * Draw the element to spriteBatch.
	 */
	public void draw(int X, int Y, int H, SpriteBatch spriteBatch) {
		sprite.setPosition(X, Y * World.CELL_3D_SIDE_IN_PX / World.CELL_SIDE_IN_PX + H);
		sprite.draw(spriteBatch);
	}
	
	
	public abstract void accept(GameState gameState, ArrayList<WorldElement> tower, WorldElementVisitor visitor);
	
	public abstract WorldElement cloneElement();
}
