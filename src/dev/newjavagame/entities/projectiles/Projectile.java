package dev.newjavagame.entities.projectiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.newjavagame.Handler;
import dev.newjavagame.entities.Entity;
import dev.newjavagame.gfx.Animation;
import dev.newjavagame.gfx.Assets;
import dev.newjavagame.tiles.Tile;

public abstract class Projectile extends Entity {

	public int damage = 20;
	public float gravity = 0.4f;
	
	//Motion
	protected boolean hoverX, hoverY, goingLeft, goingUp;
	protected boolean isGravity;
	
	//Animation
	protected int curFrame, endFrame, loopFrame, explodeFrame;
	protected BufferedImage[] anim;
	
	//Properties
	protected int lifeTimer, despawnTime;
	private boolean dealtDamage;
	protected boolean piercing;
	protected boolean explosive;
	
	public Projectile(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		
		//Motion
		hoverX = true;
		hoverY = true;
		goingLeft = false;
		goingUp = false;
		isGravity = false;
		
		//Properties
		dealtDamage = false;
		piercing = false;
		explosive = false;
		
		//Animation
		curFrame = 0;
	}
	
	public void tick() {
		lifeTimer++;
		if (lifeTimer >= despawnTime) {
			alive = false;
		}
		if (lifeTimer % 100 == 0) {
			if (curFrame == endFrame)
				curFrame = loopFrame;
			else
				curFrame++;
		}
		
		setxVel();
		setyVel();
		move();
	}
	
	public void render(Graphics g) {
		g.drawImage(anim[curFrame], (int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), 100, 100, null);
	}
	
	public abstract void setxVel();
	
	public abstract void setyVel();
	
	public void collision() {
		if (!dealtDamage) {
			for (Entity e: handler.getMap().getEntityManager().getEntities()) {
				if (e.getCollisionBounds(0, 0).intersects(getCollisionBounds(0, 0))) {
					e.takeDamage(damage); //change so that an entity can only take damage from this once
					if (!piercing) {
						die();
					}
				}
			}
		}
	}
	
	public void move() {
		xMove = xVel;
		yMove = yVel;
		
		if (isGravity)
			yVel -= gravity;
		
		if (!hoverX)
			moveX();
		if (!hoverY)
			moveY();
	}
	
	public void die() {
		dealtDamage = true;
		if (explosive) {
			curFrame = explodeFrame;
			despawnTime = lifeTimer + 100;
		}
		else
			despawnTime = lifeTimer;
	}
	
	public void moveX() {
		//Moving Left
		if (xMove < 0) {
			int tx = (int)(x + xMove + bounds.x) / Tile.TILE_WIDTH;
			
			if (!collisionWithTile(tx, (int)(y + bounds.y) / Tile.TILE_HEIGHT) &&
					!collisionWithTile(tx, (int)(y + bounds.y + bounds.height) / Tile.TILE_HEIGHT))
				x += xMove;
			else
				die();
		}
		//Moving Right
		else if (xMove > 0) {
			int tx = (int)(x + xMove + bounds.x + bounds.width) / Tile.TILE_WIDTH;
			
			if (!collisionWithTile(tx, (int)(y + bounds.y) / Tile.TILE_HEIGHT) &&
					!collisionWithTile(tx, (int)(y + bounds.y + bounds.height) / Tile.TILE_HEIGHT))
				x += xMove;
			else
				die();
		}
	}
	
	public void moveY() {
		//Moving Down
		if (yMove > 0) {
			int ty = (int)(y + yMove + bounds.y + bounds.height) / Tile.TILE_HEIGHT;
			
			if (!collisionWithTile((int)(x + bounds.x) / Tile.TILE_WIDTH, ty) &&
					!collisionWithTile((int)(x + bounds.x + bounds.width) / Tile.TILE_WIDTH, ty))
				y += yMove;
			else
				die();
		}
		//Moving Up
		else if (yMove < 0) {
			int ty = (int)(y + yMove + bounds.y) / Tile.TILE_HEIGHT;
			
			if (!collisionWithTile((int)(x + bounds.x) / Tile.TILE_WIDTH, ty) &&
					!collisionWithTile((int)(x + bounds.x + bounds.width) / Tile.TILE_WIDTH, ty))
				y += yMove;
			else
				die();
		}
	}
	
	protected boolean collisionWithTile(int x, int y) {
		if (handler.getMap().getTile(x, y).getColLayer() == 1)
			return true;
		return false;
	}
	
	@Override
	public void takeDamage(int amt) {
		
	}

}
