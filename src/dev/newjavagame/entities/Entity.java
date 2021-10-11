package dev.newjavagame.entities;

import java.awt.Graphics;
import java.awt.Rectangle;

import dev.newjavagame.Game;
import dev.newjavagame.Handler;

public abstract class Entity {

	public static final int DEFAULT_HEALTH = 100, DEFAULT_WEIGHT = 10;
	
	protected Handler handler;
	protected float x, y;
	protected int width, height;
	protected int health, weight;
	protected boolean alive = true;
	protected Rectangle bounds;
	
	protected float xMove, yMove;
	protected float xVel, yVel;

	public Entity(Handler handler,float x, float y, int width, int height) {
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		health = DEFAULT_HEALTH;
		weight = DEFAULT_WEIGHT;
		
		bounds = new Rectangle(0, 0);
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	public abstract void die();
	
	public void takeDamage(int amt) {
		//System.out.println(amt);
		health -= amt;
		if (health <= 0) {
			alive = false;
			die();
		}
	}
	
	public void checkKnockback(int attackForce) {
		if (attackForce != 0)
			xVel = attackForce / weight;
	}
	
	public Entity checkEntityCollisions(float xOffset, float yOffset) {
		for (Entity e: handler.getMap().getEntityManager().getEntities()) {
			if (e.equals(this))
				continue;
			if (e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset)))
				return e;
		}
		return null;
	}
	
	public Rectangle getCollisionBounds(float xOffset, float yOffset) {
		return new Rectangle((int)(x + bounds.x + xOffset), (int)(y + bounds.y + yOffset), bounds.width, bounds.height);
	}
	
	//Getters and Setters
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
}
