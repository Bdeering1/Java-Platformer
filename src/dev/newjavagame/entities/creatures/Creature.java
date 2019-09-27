package dev.newjavagame.entities.creatures;

import dev.newjavagame.Game;
import dev.newjavagame.Handler;
import dev.newjavagame.entities.Entity;
import dev.newjavagame.entities.creatures.player.Player;
import dev.newjavagame.tiles.Tile;

public abstract class Creature extends Entity {
	
	public static final float DEFAULT_SPEED = 1;
	public static final int DEFAULT_CREATURE_WIDTH = 64,
							DEFAULT_CREATURE_HEIGHT = 64;
	public static final float DEFAULT_GRAVITY = 0.4f;
	public static final float SQRT2 = 1.414213f;
	
	protected Player player;
	protected float speed, relativeSpeed;
	protected float gravity;
	protected float xSlow, ySlow;
	protected boolean xRoot, yRoot;
	protected boolean collidingLeft, collidingRight;
	protected boolean collidingUp, collidingDown;
	protected boolean canJump;
	protected boolean facingRight;
	
	//Entity Collision
	protected Entity e;
	
	//Wall Jump
	protected long colXTimer, lastColXTimer;
	
	public Creature(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		speed = DEFAULT_SPEED;
		xMove = 0; yMove = 0;
		xVel = 0; yVel = 0;
		xSlow = 0; ySlow = 0;
		gravity = DEFAULT_GRAVITY;
		xRoot = false; yRoot = false;
		collidingLeft = false; collidingRight = false;
		collidingUp = false; collidingDown = false;
		canJump = false;
		facingRight = true;
	}

	public void move() {
		e = checkEntityCollisions(xMove, yMove);
		if (e != null)
			collideWithEntity(e);
			
		//Horizontal Movement
		if (!xRoot) {
			if (xMove < 0)
				facingRight = false;
			else if (xMove > 0)
				facingRight = true;
			
			moveX();
		}
		else
			xVel = 0;
		
		//Vertical Movement
		if (!yRoot)
			moveY();
		else
			yVel = 0;
		
		if (this != handler.getMap().getEntityManager().getPlayer()) {
			xMove = 0;
			yMove = 0;
		}
	}
	
	public void moveX() {
		//X Collision (for wall jump)
		colXTimer += System.currentTimeMillis() - lastColXTimer;
		lastColXTimer = System.currentTimeMillis();
		if (colXTimer > 70) {
			collidingLeft = false;
			collidingRight = false;
		}
		
		//X Velocity
		if (xVel > 0) {
			if (xVel < 1)
				xVel = 0;
			else
				xVel--;
		}
		else if (xVel < 0) {
			if (xVel > -1)
				xVel = 0;
			else
				xVel++;
		}
		xMove += xVel;
		//Slow
		if (xSlow != 0)
			xMove *= 1 / xSlow;
		
		//Moving Left
		if (xMove < 0) {
			int tx = (int)(x + xMove + bounds.x) / Tile.TILE_WIDTH;
			
			if (!collisionWithTile(tx, (int)(y + bounds.y) / Tile.TILE_HEIGHT) &&
					!collisionWithTile(tx, (int)(y + bounds.y + bounds.height / 2) / Tile.TILE_HEIGHT) &&
					!collisionWithTile(tx, (int)(y + bounds.y + bounds.height) / Tile.TILE_HEIGHT)) {
				x += xMove;
			}
			else {
				collidingLeft = true;
				colXTimer = 0;
				if (!canJump) {
					xSlow = 2;
					if (yVel > 0.4)
						ySlow = 3;
				}
				x = (tx + 1) * Tile.TILE_WIDTH - bounds.x;
			}
		}
		//Moving Right
		else if (xMove > 0) {
			int tx = (int)(x + xMove + bounds.x + bounds.width) / Tile.TILE_WIDTH;
			
			if (!collisionWithTile(tx, (int)(y + bounds.y) / Tile.TILE_HEIGHT) &&
					!collisionWithTile(tx, (int)(y + bounds.y + bounds.height / 2) / Tile.TILE_HEIGHT) &&
					!collisionWithTile(tx, (int)(y + bounds.y + bounds.height) / Tile.TILE_HEIGHT)) {
				x += xMove;
			}
			else {
				collidingRight = true;
				colXTimer = 0;
				if (!canJump) {
					xSlow = 2;
					if (yVel > 0.4)
						ySlow = 3;
				}
				x = tx * Tile.TILE_WIDTH - bounds.x - bounds.width - 1;
			}
			
		}
	}
	
	public void moveY() {
		canJump = false;
		yVel += gravity;
		yMove = yVel;
		
		//Slow
		if (ySlow != 0) {
			yMove *= 1 / ySlow;
			ySlow = 0;
		}
		if (yMove > 0 && yMove < 0.4)
			yMove = 0.4f;
		
		collidingDown = false;
		collidingUp = false;
		
		//Moving Down
		if (yMove > 0) {
			int ty = (int)(y + yMove + bounds.y + bounds.height) / Tile.TILE_HEIGHT;
			
			if (!collisionWithTile((int)(x + bounds.x) / Tile.TILE_WIDTH, ty) &&
					!collisionWithTile((int)(x + bounds.x + bounds.width) / Tile.TILE_WIDTH, ty)) {
				y += yMove;
			}
			//Downwards Collision
			else {
				collidingDown = true;
				y = ty * Tile.TILE_HEIGHT - bounds.y - bounds.height - gravity;
				canJump = true;
				xSlow = 0;
				yVel = 0;
				yMove = 0;
			}
		}
		//Moving Up
		else if (yMove < 0) {
			int ty = (int)(y + yMove + bounds.y) / Tile.TILE_HEIGHT;
			
			if (!collisionWithTile((int)(x + bounds.x) / Tile.TILE_WIDTH, ty) &&
					!collisionWithTile((int)(x + bounds.x + bounds.width) / Tile.TILE_WIDTH, ty)) {
				y += yMove;
			}
			//Upwards Collision
			else {
				collidingUp = true;
				y = (ty + 1) * Tile.TILE_HEIGHT - bounds.y;
				if (yVel < -4)
					yVel = -4;
			}
		}
	}
	
	protected boolean collisionWithTile(int x, int y) {
		if (handler.getMap().getTile(x, y).getColLayer() == 1)
			return true;
		return false;
	}
	
	protected abstract void collideWithEntity(Entity e);
	
	//Getters and Setters
	public float getxMove() {
		return xMove;
	}

	public void setxMove(float xMove) {
		this.xMove = xMove;
	}

	public float getyMove() {
		return yMove;
	}

	public void setyMove(float yMove) {
		this.yMove = yMove;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getxVel() {
		return xVel;
	}

	public void setxVel(float xVel) {
		this.xVel = xVel;
	}

	public float getyVel() {
		return yVel;
	}

	public void setyVel(float yVel) {
		this.yVel = yVel;
	}

	public boolean isCanJump() {
		return canJump;
	}

	public void setCanJump(boolean canJump) {
		this.canJump = canJump;
	}

	public void setGravity(float gravity) {
		this.gravity = gravity;
	}

	public boolean isxRoot() {
		return xRoot;
	}

	public void setxRoot(boolean xRoot) {
		this.xRoot = xRoot;
	}

	public boolean isyRoot() {
		return yRoot;
	}

	public void setyRoot(boolean yRoot) {
		this.yRoot = yRoot;
	}
	
	public boolean isFacingRight() {
		return facingRight;
	}

	public void setFacingRight(boolean playerRight) {
		this.facingRight = playerRight;
	}

	public float getxSlow() {
		return xSlow;
	}

	public void setxSlow(float xSlow) {
		this.xSlow = xSlow;
	}

	public float getySlow() {
		return ySlow;
	}

	public void setySlow(float ySlow) {
		this.ySlow = ySlow;
	}
	
}
