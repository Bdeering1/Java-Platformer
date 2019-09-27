package dev.newjavagame.entities.creatures.player.abilities;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import dev.newjavagame.Handler;
import dev.newjavagame.entities.creatures.player.Player;
import dev.newjavagame.gfx.Animation;

public abstract class PlayerAbility {
	
	protected Handler handler;
	protected Player player;
	
	protected long abilityTimer, lastAbilityTimer, abilityEndTime, abilityCooldown;
	protected int frameAnimSpeed;
	protected boolean active;
	
	//Animation
	protected Animation animLeft, animRight;
	
	public PlayerAbility(Handler handler, Player player) {
		this.handler = handler;
		this.player = player;
	}

	
	public void tick() {
		abilityTimer += System.currentTimeMillis() - lastAbilityTimer;
		lastAbilityTimer = System.currentTimeMillis();
		
		if (active) {
			//End of Ability Effects
			if (abilityTimer > abilityEndTime && endConditions()) {
				endEffects();
				active = false;
				if (animLeft != null && animRight != null) { //maybe there's a better way to do this??
					animLeft.setIndex(0);
					animRight.setIndex(0);
				}
			}
			//Ability Currently Active
			else {
				if (animLeft != null && animRight != null)
				animLeft.tick();
				animRight.tick();
				delayedEffects();
			}
		}
		//Ability Just Activated
		if (abilityTimer > abilityCooldown && activationConditions()) { //change so that ability cooldown starts after abilty ends ( + abilityEndTime)
			abilityTimer = 0;
			active = true;
			activationEffects();
		}
	}
	
	public abstract void render(Graphics g);
	
	
	//Ability Effects
	
	protected abstract void activationEffects();
	
	protected abstract void delayedEffects();
	
	protected abstract void endEffects();
	
	
	//Ability Conditions
	
	public abstract boolean activationConditions();
	
	public abstract boolean endConditions();
	
	
	//Getters and Setters
	
	public abstract boolean getCurrentAnimation(); //???
	
	public boolean isActive() {
		return active;
	}

	public Animation getAnimLeft() {
		return animLeft;
	}

	public Animation getAnimRight() {
		return animRight;
	}


	public long getAbilityTimer() {
		return abilityTimer;
	}

	public void setAbilityTimer(long abilityTimer) {
		this.abilityTimer = abilityTimer;
	}
	
}
