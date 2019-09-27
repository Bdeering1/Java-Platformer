package dev.newjavagame.entities.creatures.player.abilities;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import dev.newjavagame.Handler;
import dev.newjavagame.entities.creatures.player.Player;

public class Jump extends PlayerAbility {
	
	private boolean bufferedJump;

	public Jump(Handler handler, Player player) {
		super(handler, player);
		
		bufferedJump = false;
	}

	@Override
	public void render(Graphics g) {
		
	}

	@Override
	protected void activationEffects() {
		if (player.isCanJump()) {
			player.setyVel(-14);
			player.getAbilityManager().getAirAttack().setAbilityTimer(1000);
		}
		else if (player.hasWallJumpLeft() || player.hasWallJumpRight()){
			//player.setHasWallJump(false);
			player.setyVel(-12);
			player.setxSlow(1.5f);
			
			if (player.hasWallJumpLeft()) {
				player.setxVel(-18);
			}
			else {
				player.setxVel(18);
			}
			
			//if (player.getAirDashPerk() >= 3)
			//	player.getAbilityManager().getAirAttack().setAbilityTimer(1000);  //allows infinite climbing against walls
		}
		else {
			player.setyVel(-8 - 2 * player.getDoubleJumpPerk());
			player.setHasDoubleJump(false);
		}
	}

	@Override
	protected void delayedEffects() {

	}

	@Override
	protected void endEffects() {
		
	}

	@Override
	public boolean activationConditions() {
		if (!player.isFlightMode() && player.isCanJump() || player.hasDoubleJump() || player.hasWallJumpLeft() || player.hasWallJumpRight()) {
			if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP)) {
				if (!player.getAbilityManager().getSwordAttack().isActive() && !player.getAbilityManager().getAirAttack().isActive()
						&& !player.getAbilityManager().getSpellCast().isActive())
					return true;
				else {
					bufferedJump = true;
					return false;
				}
			}
			else if (bufferedJump && !player.getAbilityManager().getSwordAttack().isActive() && !player.getAbilityManager().getAirAttack().isActive()
						&& !player.getAbilityManager().getSpellCast().isActive()) {
				bufferedJump = false;
				return true;
			}
		}
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
