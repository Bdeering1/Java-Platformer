package dev.newjavagame.entities.creatures.player.abilities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import dev.newjavagame.Handler;
import dev.newjavagame.entities.Entity;
import dev.newjavagame.entities.creatures.Creature;
import dev.newjavagame.entities.creatures.player.Player;
import dev.newjavagame.entities.creatures.player.PlayerAbilityManager;
import dev.newjavagame.gfx.Animation;
import dev.newjavagame.gfx.Assets;

public class AirAttack extends PlayerAbility{
	
	private Rectangle hitbox, playerColBox;
	private int damage, critDamage;
	private boolean dealtDamage, crit;
	
	private Creature c;
	private ArrayList<Creature> creaturesHit;

	public AirAttack(Handler handler, Player player) { //FINISH, damage, animations, airdash perk, crit, animations in playerabilitymanager
		super(handler, player);

		//Attack Damage and Force (knockback strength)
		damage = (int)(player.getAttackDamage() * (2 / 3));
		critDamage = 0;
		
		//Animations
		frameAnimSpeed = 100;
		animLeft = new Animation(frameAnimSpeed, Assets.player_attack_left_air);
		animRight = new Animation(frameAnimSpeed, Assets.player_attack_right_air);
		
		//Ability Length and Cooldown
		abilityEndTime = frameAnimSpeed * 4;
		abilityCooldown = 1000;
		abilityTimer = abilityCooldown;
		
		//Misc
		crit = false;
		damage = player.getAttackDamage() / 3 * 2;
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
		setHitbox();
		
		if (player.getAirDashPerk() != 0) {
			if (!player.isFacingRight())
				player.setxVel(-8 - 3.5f * player.getAirDashPerk());
			else
				player.setxVel(8 + 3.5f * player.getAirDashPerk());
		}
	}
	
	private void setHitbox() {
		playerColBox = player.getCollisionBounds(0, 0);
		
		hitbox.width = 40;
		hitbox.height = 50;
		
		hitbox.y = (int)(player.getY()) + 22;
		if (player.isFacingRight())
			hitbox.x = playerColBox.x + playerColBox.width;
		else
			hitbox.x = playerColBox.x - hitbox.width;
	}

	@Override
	protected void delayedEffects() {
		setHitbox();
		
		if (abilityTimer >= frameAnimSpeed && abilityTimer <= frameAnimSpeed * 2)
		for (Entity e: handler.getMap().getEntityManager().getEntities()) {
			if (e.equals(player))
				continue;
			if (e.getCollisionBounds(0,0).intersects(hitbox) && e instanceof Creature) {
				for (Creature cr: creaturesHit) {
					if (cr == c)
						return;
				}
				c = (Creature) e;
				creaturesHit.add(c);
				c.takeDamage(damage);
			}
		}
	}

	@Override
	protected void endEffects() {
		creaturesHit.clear();
	}

	@Override
	public boolean activationConditions() {
		if (handler.getGame().getKeyManager().keyJustPressed(KeyEvent.VK_Q) && !player.isCanJump())
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

}
