package dev.newjavagame.entities.creatures.player.abilities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import dev.newjavagame.Handler;
import dev.newjavagame.entities.Entity;
import dev.newjavagame.entities.creatures.player.Player;
import dev.newjavagame.gfx.Animation;
import dev.newjavagame.gfx.Assets;

public class SwordAttack extends PlayerAbility {
	
	//NOT HITTING ENTITIES
	
	private Rectangle hitbox, playerColBox;
	private int damage, critDamage, attackForce;
	private boolean dealtDamage, crit;
	
	public SwordAttack(Handler handler, Player player) {
		super(handler, player);
		
		//Attack Damage and Force (knockback strength)
		damage = player.getAttackDamage();
		critDamage = 0;
		attackForce = 150;
		
		//Animations
		frameAnimSpeed = 100 - (15 * player.getAttackSpeedPerk());
		animLeft = new Animation(frameAnimSpeed, Assets.player_attack_left);
		animRight = new Animation(frameAnimSpeed, Assets.player_attack_right);
		
		//Ability Length and Cooldown
		abilityEndTime = frameAnimSpeed * 4;
		abilityCooldown = 800;
		abilityTimer = abilityCooldown;
		
		//Misc
		crit = false;
		dealtDamage= false;
		hitbox = new Rectangle();
	}
	
	@Override
	public void render(Graphics g) {
		if (handler.getMap().getEntityManager().isShowBounds()) {
			g.setColor(Color.RED);
			g.fillRect(hitbox.x - (int)handler.getGameCamera().getxOffset(), hitbox.y - (int)handler.getGameCamera().getyOffset(), hitbox.width, hitbox.height);
		}
		if (isCrit()) {
			if (!player.isFacingRight())
				g.drawImage(Assets.crit_left, (int)(player.getX() - handler.getGameCamera().getxOffset()),
						(int)(player.getY() - handler.getGameCamera().getyOffset()), player.getWidth(), player.getHeight(), null);
			else
				g.drawImage(Assets.crit_right, (int)(player.getX() - handler.getGameCamera().getxOffset()),
						(int)(player.getY() - handler.getGameCamera().getyOffset()), player.getWidth(), player.getHeight(), null);
		}
	}
	
	@Override
	protected void activationEffects() {
		playerColBox = player.getCollisionBounds(0, 0);
	}
	
	private void setHitbox() {
		hitbox.width = 40;
		hitbox.height = 85;
		
		hitbox.y = (int)(player.getY()) + 22;
		if (player.isFacingRight())
			hitbox.x = playerColBox.x + playerColBox.width;
		else
			hitbox.x = playerColBox.x - hitbox.width;
	}

	@Override
	protected void delayedEffects() {
		if (abilityTimer >= frameAnimSpeed - 5 && !dealtDamage) { //the '-5' accounts for the slight delay before this ticks
			setHitbox();
			critDamage();
			for (Entity e: handler.getMap().getEntityManager().getEntities()) {
				if (e.equals(player))
					continue;
				if (e.getCollisionBounds(0,0).intersects(hitbox)) {
					e.takeDamage(damage + critDamage);
					if (e.isAlive())
						checkKnockback(e);
				}
			}
			dealtDamage = true;
		}
	}
	
	public void checkKnockback(Entity e) {
		if (!player.isFacingRight())
			e.checkKnockback((int)(-attackForce * (1 + 0.2 * player.getKnockbackPerk())));
		else
			e.checkKnockback((int)(attackForce * (1 + 0.2 * player.getKnockbackPerk())));
	}
	
	public void critDamage() {
		if ((int)(Math.random() * 100) < player.getCritChance()) {
			critDamage = (int)(damage * (1 + 0.25 * player.getCritPerk()));
			crit = true;
		}
		critDamage = 0;
	}
	
	protected void endEffects() {
		dealtDamage = false;
		crit = false;
	}

	@Override
	public boolean activationConditions() {
		if (handler.getKeyManager().isAbility1() && player.isCanJump())
			return true;
		return false;
	}
	
	@Override
	public boolean endConditions() {
		return true;
	}

	@Override
	public boolean getCurrentAnimation() {
		return false;
	}

	//Getters and Setters
	
	public boolean isCrit() {
		return crit;
	}

	public void setCrit(boolean crit) {
		this.crit = crit;
	}
	
		
}
