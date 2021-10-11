package dev.newjavagame.entities.creatures.player.abilities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import dev.newjavagame.Handler;
import dev.newjavagame.entities.Entity;
import dev.newjavagame.entities.creatures.Creature;
import dev.newjavagame.entities.creatures.player.Player;
import dev.newjavagame.gfx.Animation;
import dev.newjavagame.gfx.Assets;

public class SpecialAttack extends PlayerAbility {
	
	private Rectangle hitbox, playerColBox;
	private int damage;
	private boolean dealtDamage1, dealtDamage2, dealtDamage3, dealtDamage4;
	
	private Creature c;
	private ArrayList<Creature> creaturesHit;
	
	public SpecialAttack(Handler handler, Player player) { //fix damage so that other creatures take splash on the ground form fourth attack
		super(handler, player);
		
		//Animations
		frameAnimSpeed = 80;
		animLeft = new Animation(frameAnimSpeed, Assets.player_air_combo_left);
		animRight = new Animation(frameAnimSpeed, Assets.player_air_combo_right);
		
		abilityEndTime = frameAnimSpeed * 3;
		abilityCooldown = 1600;
		abilityTimer = abilityCooldown;
		
		damage = 30;
		dealtDamage1 = false;
		dealtDamage2 = false;
		dealtDamage3 = false;
		dealtDamage4 = false;
		hitbox = new Rectangle();
		
		creaturesHit = new ArrayList<Creature>();
	}
	
	@Override
	public void render(Graphics g) {
		if (handler.getMap().getEntityManager().isShowBounds()) {
			g.setColor(Color.RED);
			g.fillRect(hitbox.x - (int)handler.getGameCamera().getxOffset(), hitbox.y - (int)handler.getGameCamera().getyOffset(), hitbox.width, hitbox.height);
		}
	}

	@Override
	protected void activationEffects() {
		playerColBox = player.getCollisionBounds(0, 0);
		setHitbox();
		player.setxRoot(true);
		player.setyRoot(true);
		
		for (Entity e: handler.getMap().getEntityManager().getEntities()) {
			if (e.equals(player))
				continue;
			if (e.getCollisionBounds(0,0).intersects(hitbox) && e instanceof Creature) {
				c = (Creature) e;
				creaturesHit.add(c);
				c.setxRoot(true);
				c.setyRoot(true);
				//c.setY(player.getY() - 50);
			}
		}
	}
	
	private void setHitbox() {
		hitbox.width = 60;
		hitbox.height = 85;
		
		hitbox.y = (int)player.getY() + 22;
		if (player.isFacingRight())
			hitbox.x = playerColBox.x + playerColBox.width;
		else
			hitbox.x = playerColBox.x - hitbox.width;
	}

	@Override
	protected void delayedEffects() {
		//First Hit
		if (abilityTimer >= frameAnimSpeed && !dealtDamage1) {
			for (Creature c: creaturesHit)
				c.takeDamage(damage);
			dealtDamage1 = true;
		}
		//Second Hit
		else if (abilityTimer >= frameAnimSpeed * 4 && !dealtDamage2) {
			for (Creature c: creaturesHit)
				c.takeDamage(damage);
			dealtDamage2 = true;
		}
		//Third Hit / Falling
		else if (abilityTimer >= frameAnimSpeed * 7 && !dealtDamage3) {
			for (Creature c: creaturesHit) {
				c.takeDamage(damage);
				c.setyRoot(false);
			}
			player.setyRoot(false);
			dealtDamage3 = true;
		}
		//Last Hit (On Ground)
		else if (player.isCanJump() && !dealtDamage4) {
			for (Creature c: creaturesHit) {
				c.takeDamage(damage);
				c.setxRoot(false);
			}
			animLeft.setPaused(false);
			animRight.setPaused(false);
			dealtDamage4 = true;
			abilityTimer = 0;
		}
		//Falling Animation
		else if (abilityTimer >= frameAnimSpeed * 9) {
			animLeft.setPaused(true);
			animRight.setPaused(true);
		}
	}
	
	protected void endEffects() {
		dealtDamage1 = false;
		dealtDamage2 = false;
		dealtDamage3 = false;
		dealtDamage4 = false;
		
		player.setxRoot(false);
		creaturesHit.clear();
	}

	@Override
	public boolean activationConditions() {
		if (handler.getKeyManager().isAbility2() && !player.isCanJump()) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean endConditions() {
		if (player.isCanJump() && !animLeft.isPaused())
			return true;
		return false;
	}

	@Override
	public boolean getCurrentAnimation() {
		return false;
	}

	
	
}
