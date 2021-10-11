package dev.newjavagame.entities.creatures.player;

import java.awt.Graphics;
import java.util.ArrayList;

import dev.newjavagame.Handler;
import dev.newjavagame.entities.creatures.player.abilities.AirAttack;
import dev.newjavagame.entities.creatures.player.abilities.Jump;
import dev.newjavagame.entities.creatures.player.abilities.PlayerAbility;
import dev.newjavagame.entities.creatures.player.abilities.SpecialAttack;
import dev.newjavagame.entities.creatures.player.abilities.SpellCast;
import dev.newjavagame.entities.creatures.player.abilities.SwordAttack;
import dev.newjavagame.gfx.Animation;

public class PlayerAbilityManager {
	
	private Handler handler;
	private Player player;
	
	private boolean crit;
	
	//Abilities Array
	private ArrayList<PlayerAbility> playerAbilities;
	
	//Abilities
	private PlayerAbility jump;
	private PlayerAbility swordAttack;
	private PlayerAbility airAttack;
	private PlayerAbility specialAttack;
	private PlayerAbility spellCast;

	public PlayerAbilityManager(Handler handler, Player player) {
		this.handler = handler;
		this.player = player;
		
		crit = false;
		
		jump = new Jump(handler, player);
		swordAttack = new SwordAttack(handler, player);
		airAttack = new AirAttack(handler, player);
		specialAttack = new SpecialAttack(handler, player);
		spellCast = new SpellCast(handler, player);
		
		playerAbilities = new ArrayList<PlayerAbility>();
		playerAbilities.add(jump);
		playerAbilities.add(swordAttack);
		playerAbilities.add(airAttack);
		playerAbilities.add(specialAttack);
		playerAbilities.add(spellCast);
	}
	
	public void tick() {
		for (PlayerAbility pa: playerAbilities) {
			if (!specialAttack.isActive())
				pa.tick();
			else
				specialAttack.tick();
		}
	}
	
	public void render(Graphics g) {
		for (PlayerAbility pa: playerAbilities) {
			pa.render(g);
		}
	}
	
	public Animation getCurrentAnimationLeft() {
		if (swordAttack.isActive())
			return swordAttack.getAnimLeft();
		else if (spellCast.isActive())
			return spellCast.getAnimLeft();
		else if (specialAttack.isActive())
			return specialAttack.getAnimLeft();
		else if (airAttack.isActive())
			return airAttack.getAnimLeft();
		else
			return null;
	}
	
	public Animation getCurrentAnimationRight() {
		if (swordAttack.isActive())
			return swordAttack.getAnimRight();
		else if (spellCast.isActive())
			return spellCast.getAnimRight();
		else if (specialAttack.isActive())
			return specialAttack.getAnimRight();
		else if (airAttack.isActive())
			return airAttack.getAnimRight();
		else
			return null;
	}
	
	//Getters and Setters
	
	public PlayerAbility getSwordAttack() {
		return swordAttack;
	}
	
	public PlayerAbility getAirAttack() {
		return airAttack;
	}

	public PlayerAbility getSpecialAttack() {
		return specialAttack;
	}
	
	public PlayerAbility getSpellCast() {
		return spellCast;
	}

	public boolean isCrit() {
		return crit;
	}

	public void setCrit(boolean crit) {
		this.crit = crit;
	}
	
}
