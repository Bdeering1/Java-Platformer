package dev.newjavagame.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import dev.newjavagame.Handler;
import dev.newjavagame.entities.Entity;
import dev.newjavagame.entities.creatures.player.Player;
import dev.newjavagame.gfx.Animation;
import dev.newjavagame.gfx.Assets;
import dev.newjavagame.tiles.Tile;

public class Golem extends Creature {
	
	private Animation animIdleLeft, animIdleRight, animLeft, animRight;
	private int idleAnimTime, walkAnimTime, attackAnimTime;
	private int followLength;
	
	private int airSpaceTileX, airSpaceTileY, blockLocationTileX, blockLocationTileY;
	private Point airSpace;
	private boolean jumpUp, jumpDown;
	

	public Golem(Handler handler, float x, float y, Player player) {
		super(handler, x, y, 150, 150);
		this.player = player;
		
		weight = 7 + (int)(Math.random() * 3);
		
		idleAnimTime = 50;
		walkAnimTime = 50;
		//attackAnimTime = 70;
		
		animIdleLeft = new Animation(idleAnimTime, Assets.golem_idle_left);
		animIdleRight = new Animation(idleAnimTime, Assets.golem_idle_right);
		animLeft = new Animation(walkAnimTime, Assets.golem_left);
		animRight = new Animation(walkAnimTime, Assets.golem_right);
		//add attack animations here
		
		bounds.x = 35;
		bounds.y = 42;
		bounds.width = width - 70;
		bounds.height = height - 62;
		
		followLength = 40;
	}

	@Override
	public void tick() {
		move();
		
		animIdleLeft.tick();
		animIdleRight.tick();
		animLeft.tick();
		animRight.tick();
		//attack animations tick here
		
		//AI
		tickAI();
	}
	
	private void tickAI() {
		
		//Horizontal Movement
		chasePlayer();
		
		//Jumping
		checkJump();
	}
	
	private void chasePlayer() {
		if (!canJump) {
			if (!facingRight)
				xMove = -speed;
			else
				xMove = speed;
		}
		else if (x + bounds.x > (player.getCollisionBounds(0,0).getX() + player.getCollisionBounds(0, 0).getWidth() + followLength)) {
			if (!(!player.isCanJump() && isLedgeLeft()))
				xMove = -speed;
		}
		else if (x + bounds.x + bounds.width + followLength < player.getCollisionBounds(0, 0).getX()) {
			if (!(!player.isCanJump() && isLedgeRight()))
			xMove = speed;
		}
	}
	
	private boolean isLedgeLeft() {
		airSpace = new Point((int)(x + bounds.x) + 30, (int)(y + bounds.y + bounds.height));
		if (!collisionWithTile((int)(airSpace.x / Tile.TILE_WIDTH), (int)(airSpace.y / Tile.TILE_HEIGHT)) 
				&& !collisionWithTile((int)(airSpace.x / Tile.TILE_WIDTH), (int)(airSpace.y / Tile.TILE_HEIGHT) + 1))
			return true;
		return false;
	}
	
	private boolean isLedgeRight() {
		airSpace = new Point((int)(x + bounds.x + bounds.width) - 30, (int)(y + bounds.y + bounds.height));
		airSpaceTileX = (int)(airSpace.x / Tile.TILE_WIDTH);
		airSpaceTileY = (int)(airSpace.y / Tile.TILE_HEIGHT);
		if (!collisionWithTile((int)(airSpace.x / Tile.TILE_WIDTH), (int)(airSpace.y / Tile.TILE_HEIGHT)) 
				&& !collisionWithTile((int)(airSpace.x / Tile.TILE_WIDTH), (int)(airSpace.y / Tile.TILE_HEIGHT) + 1))
			return true;
		return false;
	}
	
	private void checkJump() { //adjust jumpLocation and blockLocation, also add check for airspace above head
		if (canJump) {
			jumpUp = false;
			jumpDown = false;
		}
		
		if (canJump && player.isCanJump()) {
			//Jumping Left
			if (!facingRight) {
				airSpace = new Point((int)(x + bounds.x) - 45, (int)(y + bounds.y));
				airSpaceTileX = (int)(airSpace.x / Tile.TILE_WIDTH);
				airSpaceTileY = (int)(airSpace.y / Tile.TILE_HEIGHT);
				
				if (y + bounds.y + Tile.TILE_HEIGHT > (player.getCollisionBounds(0,0).getY() + player.getCollisionBounds(0, 0).getHeight())) {
					for (int i = -1; i < 3; i++) {
						if (canJumpToLeft(airSpaceTileX, airSpaceTileY - i))
							jumpUp = true;
					}
					if (!jumpUp) {
						for (int i = 2; i <= 4; i++) {
							if (canJumpToLeft(airSpaceTileX, airSpaceTileY + i))
								jumpDown = true;
						}
					}
				}
				else if (y + bounds.y + bounds.height - Tile.TILE_HEIGHT < (player.getCollisionBounds(0,0).getY() + player.getCollisionBounds(0, 0).getHeight())
						&& y + bounds.y + bounds.height + Tile.TILE_HEIGHT * 5 > (player.getCollisionBounds(0,0).getY() + player.getCollisionBounds(0,0).getHeight())) {
					for (int i = 2; i <= 4; i++) {
						if (canJumpToLeft(airSpaceTileX, airSpaceTileY + i))
							jumpDown = true;
					}
				}
				
				if (jumpUp) {
					yVel = -15;
					xVel = -7;
				}
				else if (jumpDown) {
					yVel = -8;
					xVel = -7;
				}
			}
			//Jumping Right
			else {
				airSpace = new Point((int)(x + bounds.x + bounds.width) + 45, (int)(y + bounds.y));
				airSpaceTileX = (int)(airSpace.x / Tile.TILE_WIDTH);
				airSpaceTileY = (int)(airSpace.y / Tile.TILE_HEIGHT);
				
				if (y + bounds.y + Tile.TILE_HEIGHT > (player.getCollisionBounds(0,0).getY() + player.getCollisionBounds(0, 0).getHeight())) {
					for (int i = -1; i < 3; i++) {
						if (canJumpToRight(airSpaceTileX, airSpaceTileY - i))
							jumpUp = true;
					}
					if (!jumpUp) {
						for (int i = 2; i <= 4; i++) {
							if (canJumpToRight(airSpaceTileX, airSpaceTileY + i))
								jumpDown = true;
						}
					}
				}
				else if (y + bounds.y + bounds.height - Tile.TILE_HEIGHT < (player.getCollisionBounds(0,0).getY() + player.getCollisionBounds(0, 0).getHeight())
						&& y + bounds.y + bounds.height + Tile.TILE_HEIGHT * 5 > (player.getCollisionBounds(0,0).getY() + player.getCollisionBounds(0,0).getHeight())) {
					for (int i = 2; i <= 4; i++) {
						if (canJumpToRight(airSpaceTileX, airSpaceTileY + i))
							jumpDown = true;
					}
				}
				
				if (jumpUp) {
					yVel = -15;
					xVel = 7;
				}
				else if (jumpDown) {
					yVel = -8;
					xVel = 7;
				}
			}
		}
	}
	
	private boolean canJumpToLeft (int tileX, int tileY) {
		if (!collisionWithTile(tileX, tileY) && !collisionWithTile(tileX, tileY - 1) 
				&& !collisionWithTile(tileX + 1, tileY) && !collisionWithTile(tileX + 1, tileY + 1)
				&& !collisionWithTile(tileX + 1, tileY - 1) && collisionWithTile(tileX, tileY + 1)) {
			return true;
		}
		return false;
	}
	
	private boolean canJumpToRight (int tileX, int tileY) {
		if (!collisionWithTile(tileX, tileY) && !collisionWithTile(tileX, tileY - 1) 
				&& !collisionWithTile(tileX - 1, tileY) && !collisionWithTile(tileX - 1, tileY + 1)
				&& !collisionWithTile(tileX - 1, tileY - 1) && collisionWithTile(tileX, tileY + 1)) {
			return true;
		}
		return false;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(getCurrentAnimationFrame(), (int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
		if (airSpace != null && handler.getMap().getEntityManager().isShowAI()) {
			g.setColor(Color.BLUE);
			for (int i = 1; i > -3; i--)
			g.fillRect(airSpaceTileX * Tile.TILE_WIDTH - (int)handler.getGameCamera().getxOffset() + 5, 
					(airSpaceTileY - i) * Tile.TILE_HEIGHT - (int)handler.getGameCamera().getyOffset() + 5, Tile.TILE_WIDTH - 10, Tile.TILE_HEIGHT - 10);
			g.setColor(Color.GRAY);
			for (int i = 3; i <= 5; i++)
				g.fillRect(airSpaceTileX * Tile.TILE_WIDTH - (int)handler.getGameCamera().getxOffset() + 5, 
						(airSpaceTileY + i) * Tile.TILE_HEIGHT - (int)handler.getGameCamera().getyOffset() + 5, Tile.TILE_WIDTH - 10, Tile.TILE_HEIGHT - 10);
			
			g.setColor(Color.GREEN);
			g.fillRect((int)(x + bounds.x + bounds.width / 2 - 20 - handler.getGameCamera().getxOffset()),
					(int)(y + bounds.y + bounds.height - handler.getGameCamera().getyOffset()) + Tile.TILE_HEIGHT * 5, 40, 10);
		}
	}
	
	private BufferedImage getCurrentAnimationFrame() {
		return getCurrentAnimation().getCurrentFrame();
	}
	
	private Animation getCurrentAnimation() {
		if (!facingRight) {
			if (xMove < 0)
				return animLeft;
			else
				return animIdleLeft;
		}
		else {
			if (xMove > 0)
				return animRight;
			else
				return animIdleRight;
		}
	}
	
	@Override
	protected void collideWithEntity(Entity e) { //non stacking (not working)
		if (e instanceof Golem) {
			if (e.getX() == x && e.getY() == y) {
				//followLength -= (int)(Math.random() * 3) * 10;
			}
		}
	}

	@Override
	public void die() {
		
	}

}
