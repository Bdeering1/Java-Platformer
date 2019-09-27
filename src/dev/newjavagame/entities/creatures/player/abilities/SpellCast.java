package dev.newjavagame.entities.creatures.player.abilities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import dev.newjavagame.Handler;
import dev.newjavagame.entities.creatures.player.Player;
import dev.newjavagame.entities.projectiles.ArcaneSpell;
import dev.newjavagame.gfx.Animation;
import dev.newjavagame.gfx.Assets;

public class SpellCast extends PlayerAbility {

	public SpellCast(Handler handler, Player player) {
		super(handler, player);

		//Animations
		frameAnimSpeed = 100;
		animLeft = new Animation(frameAnimSpeed, Assets.player_cast_left);
		animRight = new Animation(frameAnimSpeed, Assets.player_cast_right);
		
		//Ability Length and Cooldown
		abilityEndTime = frameAnimSpeed * 8;
		abilityCooldown = 1200;
		abilityTimer = abilityCooldown;
		
	}

	@Override
	public void render(Graphics g) {
		
	}

	@Override
	protected void activationEffects() {
		ArcaneSpell spell;
		
		if (!player.isFacingRight())
			spell = new ArcaneSpell(handler, (int)(player.getCollisionBounds(0,0).getX() + player.getCollisionBounds(0, 0).width - handler.getGameCamera().getxOffset()),
					(int)(player.getCollisionBounds(0,0).getY() + player.getCollisionBounds(0, 0).height - handler.getGameCamera().getyOffset()),
					100, 100, 10, 0);
		else
			spell = new ArcaneSpell(handler, (int)(player.getCollisionBounds(0,0).getX() - handler.getGameCamera().getxOffset()),
					(int)(player.getCollisionBounds(0,0).getY()- handler.getGameCamera().getyOffset()), 100, 100, -10, 0);
		
		//handler.getMap().getEntityManager().addEntity(spell);
		//causes error in entity manager
	}

	@Override
	protected void delayedEffects() {
		
	}

	@Override
	protected void endEffects() {
		
	}

	@Override
	public boolean activationConditions() {
		if (handler.getKeyManager().isAbility3() && player.isCanJump())
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
