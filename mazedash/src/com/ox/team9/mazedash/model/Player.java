package com.ox.team9.mazedash.model;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.ox.team9.mazedash.util.*;
import com.ox.team9.mazedash.visitor.*;

public class Player extends Roleplayer {
	
	public static final int SPEED_IN_PX_PER_SECOND = 300;
	public static final int SIZE_IN_PX = 40;
	
	public int speed;
	
	private Texture sprites;
	
	public Player(Texture sprites) {
		super(sprites);
		
		speed = 300;
		
		this.sprites = sprites;
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
	
	@Override
	public int getRadius() {
		return 20;
	}
	
	@Override
	public int getSpeed() {
		return speed;
	}
	
	// The player cannot be moved by AI.
	@Override
	public Direction aiMove() {
		return null;
	}
	
	@Override
	public WorldElement cloneElement() {
		return new Player(sprites);
	}
	
}
