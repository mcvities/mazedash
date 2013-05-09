package com.ox.team9.mazedash.model;

import java.util.ArrayList;
import java.util.Random;

import com.ox.team9.mazedash.util.*;
import com.ox.team9.mazedash.visitor.*;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.Gdx;

public class Ladybug extends Roleplayer {

	protected Direction lastDirection;
	protected int distanceMovedInLastDirection;
	
	private Texture sprites;
	
	public Ladybug(Texture sprites) {
		super(sprites);
		
		lastDirection = null;
		distanceMovedInLastDirection = World.CELL_SIDE_IN_PX;
		
		this.sprites = sprites;
	}
	
	@Override
	public int getRadius() {
		return 30;
	}

	@Override
	public int getSpeed() {
		return 100;
	}

	
	// Ladybug's AI is random.
	@Override
	public Direction aiMove() {
		
		if (distanceMovedInLastDirection >= World.CELL_SIDE_IN_PX) {
			Random randomGen = new Random();
			
			final int randomInt = randomGen.nextInt(4);
			
			switch(randomInt) {
				case 0: lastDirection = Direction.NORTH;
						break;
				case 1: lastDirection = Direction.SOUTH;
						break;
				case 2: lastDirection = Direction.EAST;
						break;
				case 3: lastDirection = Direction.WEST;
						break;
				default: lastDirection = null;
						 break;
			}
		}
		
		if (lastDirection == null) {
			throw new IllegalStateException();
		}
		
		distanceMovedInLastDirection += getSpeed() * Gdx.graphics.getDeltaTime();
		
		return lastDirection;
	}

	@Override
	public int spriteColumn() {
		return 0;
	}

	@Override
	public int spriteRow() {
		return 4;
	}

	@Override
	public int getHeight() {
		return 34;
	}

	@Override
	public WorldElement cloneElement() {
		return new Ladybug(sprites);
	}
}
