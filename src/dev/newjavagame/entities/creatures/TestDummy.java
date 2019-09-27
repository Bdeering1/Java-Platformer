package dev.newjavagame.entities.creatures;

import java.awt.Graphics;

import dev.newjavagame.Handler;
import dev.newjavagame.entities.Entity;
import dev.newjavagame.entities.creatures.player.Player;
import dev.newjavagame.gfx.Assets;

public class TestDummy extends Creature {
	
	public boolean isZombieDummy = true;

	public TestDummy(Handler handler, float x, float y, int weight, Player player) {
		super(handler, x, y, 81, 111);
		health = 500;
		this.weight = weight;
		this.player = player;
		
		bounds.x = 0;
		bounds.y = 0;
		bounds.width = width;
		bounds.height = height;
	}

	@Override
	public void tick() {
		move();
		if (isZombieDummy && Math.abs(x + width / 2 - (player.getX() + player.getWidth() / 2)) >= 2) {
			if (x + width / 2 > (player.getX() + player.getWidth() / 2))
				xMove = -speed;
			else
				xMove = speed;
		}
		if ((int)(Math.random() * 1000) < 4 && canJump)
			setyVel(-18f);
	}

	@Override
	public void render(Graphics g) {
		if (x + width / 2 > (handler.getMap().getEntityManager().getPlayer().getX() + handler.getMap().getEntityManager().getPlayer().getWidth() / 2))
			g.drawImage(Assets.test_dummy_left, (int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
		else
			g.drawImage(Assets.test_dummy_right, (int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
	}
	
	@Override
	protected void collideWithEntity(Entity e) {
		
	}

	@Override
	public void die() {
		
	}
	
	//Getters and Setters

	public boolean isZombieDummy() {
		return isZombieDummy;
	}

	public void setZombieDummy(boolean isZombieDummy) {
		this.isZombieDummy = isZombieDummy;
	}
	
}
