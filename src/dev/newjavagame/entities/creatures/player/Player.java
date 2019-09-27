package dev.newjavagame.entities.creatures.player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import dev.newjavagame.Game;
import dev.newjavagame.Handler;
import dev.newjavagame.entities.Entity;
import dev.newjavagame.entities.creatures.Creature;
import dev.newjavagame.gfx.Animation;
import dev.newjavagame.gfx.Assets;
import dev.newjavagame.inventory.Inventory;

public class Player extends Creature { //hitboxes need to be corrected
	
	//Player Ability Managaer
	private PlayerAbilityManager abilityManager;
	
	//Animations
	private Animation animIdleLeft, animIdleRight;
	private Animation animLeft, animRight;
	private Animation animJumpLeft, animJumpRight;
	private Animation animFallLeft, animFallRight;
	private Animation shieldAnim;
	
	private int idleAnimTime = 200, runAnimTime = 150, attackAnimTime = 100, jumpAnimTime = 334;
	private int shieldAnimTime = 70;
	
	//Moves
	private int attackDamage = 30;
	private int critChance = 10; 

	//Jump
	private boolean hasDoubleJump = false;
	private boolean hasWallJump = false;
	private boolean hasWallJumpLeft = false;
	private boolean hasWallJumpRight = false;
	
	//Inventory
	private Inventory inventory;
	
	//Perks
	//Common
	private int wallJumpPerk;
	private int attackSpeedPerk;
	private int knockbackPerk;
	private int speedPerk;
	//Rare
	private int doubleJumpPerk;
	private int airDashPerk;
	private int critPerk;
	//Epic
	
	//Shield
	private boolean shieldActive;
	
	//Cheats
	private boolean flightMode;
	private float flightSpeed;

	public Player(Handler handler, float x, float y, int width, int height, int health) {
		super(handler, x, y, width, height);
		this.speed = 1.8f;
		
		bounds.x = 46;
		bounds.y = 18;
		bounds.width = 45 - 1;
		bounds.height = 90 - 1;
		
		//Animations
		animIdleLeft = new Animation(idleAnimTime, Assets.player_idle_left);
		animIdleRight = new Animation(idleAnimTime, Assets.player_idle_right);
		animLeft = new Animation(runAnimTime, Assets.player_left);
		animRight = new Animation(runAnimTime, Assets.player_right);
		
		animJumpLeft = new Animation(jumpAnimTime, Assets.player_jump_left);
		animJumpRight = new Animation(jumpAnimTime, Assets.player_jump_right);
		animFallLeft = new Animation(jumpAnimTime, Assets.player_fall_left);
		animFallRight = new Animation(jumpAnimTime, Assets.player_fall_right);
		
		shieldAnim = new Animation(shieldAnimTime, Assets.whirlwind);
		
		inventory = new Inventory(handler);
		
		//Perks
		doubleJumpPerk = 2;
		wallJumpPerk = 1;
		airDashPerk = 3;
		attackSpeedPerk = 2;
		knockbackPerk = 1;
		critPerk = 2;
		speedPerk = 1;
		
		//Ability Manager
		abilityManager = new PlayerAbilityManager(handler, this);
		
		//Shield
		shieldActive = false;
		
		//Cheats
		flightMode = false;
	}

	@Override
	public void tick() {
		//Check Perks
		//if (perksUpdated)
		updatePerks();
		
		checkDoubleJump();
		checkWallJump();
		
		//Abilities
		abilityManager.tick();
		
		//Movement
		moveInput();
		move();
		handler.getGameCamera().centerOnEntity(this);
		
		//Animations
		animIdleLeft.tick();
		animIdleRight.tick();
		animLeft.tick();
		animRight.tick();
		if (!canJump) {
			if (yVel < 0) {
				animJumpLeft.tick();
				animJumpRight.tick();
			}
			else {
				animFallLeft.tick();
				animFallRight.tick();
			}
		}
		
		//Inventory
		inventory.tick();
	}
	
	private void updatePerks() {
		speed = 1.8f + 0.2f * speedPerk;
	}
	
	private void checkWallJump() {
		if (wallJumpPerk != 0) {
			if (collidingDown)
				hasWallJump = true;
			
			hasWallJumpLeft = false;
			hasWallJumpRight = false;
			
			if (hasWallJump) {
				if (collidingLeft)
					hasWallJumpRight = true;
				else if (collidingRight)
					hasWallJumpLeft = true;
			}
		}
	}

	@Override
	public void render(Graphics g) {
		//Player
		g.drawImage(getCurrentAnimationFrame(), (int)(x - handler.getGameCamera().getxOffset()),
								(int)(y - handler.getGameCamera().getyOffset()), getCurrentAnimationFrame().getWidth() * 3, height, null);
		
		//Ability Manager
		abilityManager.render(g);
		
		//Shield
		if (shieldActive) {
			shieldAnim.tick();
			g.drawImage(shieldAnim.getCurrentFrame(), (int)(x + bounds.x - handler.getGameCamera().getxOffset()) - 85,
					(int)(y + bounds.y - handler.getGameCamera().getyOffset()) - 70, 220, 220, null);
		}
		
		//Inventory
		inventory.render(g);
	}
	
	private void moveInput() {
		xMove = 0;
		
		if (!abilityManager.getSwordAttack().isActive() && !abilityManager.getAirAttack().isActive() && !abilityManager.getSpellCast().isActive()) {
			
			//Flight Mode (cheat)
			if (flightMode) {
				setGravity(0);
				flightSpeed = 4;
				
				if (handler.getKeyManager().isLeft())
					xMove -= flightSpeed;
				if (handler.getKeyManager().isRight())
					xMove += flightSpeed;
				if (handler.getKeyManager().isUp())
					yVel = -flightSpeed;
				else if (handler.getKeyManager().isDown())
					yVel = flightSpeed;
				else
					yVel = 0;
			}
			//Regular Movement
			else {
				if (handler.getKeyManager().isLeft())
					xMove -= speed;
				if (handler.getKeyManager().isRight())
					xMove += speed;
			}
		}
	}
	
	private BufferedImage getCurrentAnimationFrame() {
		return getCurrentAnimation().getCurrentFrame();
	}
	
	private Animation getCurrentAnimation() {
		if (!facingRight) {
			if (abilityManager.getCurrentAnimationLeft() != null)
				return abilityManager.getCurrentAnimationLeft();
			else if (!canJump) {
				if (yVel < 0)
					return animJumpLeft;
				else
					return animFallLeft;
			}
			else {
				if (xMove < 0)
					return animLeft;
				else
					return animIdleLeft;
			}
		}
		else {
			if (abilityManager.getCurrentAnimationRight() != null)
				return abilityManager.getCurrentAnimationRight();
			else if (!canJump) {
				if (yVel < 0)
					return animJumpRight;
				else
					return animFallRight;
			}
			else {
				if (xMove > 0)
					return animRight;
				else
					return animIdleRight;
			}
		}
	}
	
	public void checkDoubleJump() {
		if (!hasDoubleJump && canJump && doubleJumpPerk != 0)
			hasDoubleJump = true;
	}
	
	@Override
	protected void collideWithEntity(Entity e) {
		
	}
	
	@Override
	public void die() {
		
	}

	//Getters and Setters
	
	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public int getAttackAnimTime() {
		return attackAnimTime;
	}

	public void setAttackAnimTime(int attackAnimTime) {
		this.attackAnimTime = attackAnimTime;
	}

	public int getCritChance() {
		if (!canJump)
			return critChance * 2;
		return critChance;
	}

	public void setCritChance(int critChance) {
		this.critChance = critChance;
	}

	public int getAttackDamage() {
		return attackDamage;
	}
	
	public boolean hasDoubleJump() {
		return hasDoubleJump;
	}
	
	public void setHasDoubleJump(boolean hasDoubleJump) {
		this.hasDoubleJump = hasDoubleJump;
	}

	public int getDoubleJumpPerk() {
		return doubleJumpPerk;
	}

	public void setDoubleJumpPerk(int doubleJumpPerk) {
		this.doubleJumpPerk = doubleJumpPerk;
	}

	public int getAirDashPerk() {
		return airDashPerk;
	}

	public void setAirDashPerk(int airDashPerk) {
		this.airDashPerk = airDashPerk;
	}

	public int getAttackSpeedPerk() {
		return attackSpeedPerk;
	}

	public void setAttackSpeedPerk(int attackSpeedPerk) {
		this.attackSpeedPerk = attackSpeedPerk;
	}

	public int getKnockbackPerk() {
		return knockbackPerk;
	}

	public void setKnockbackPerk(int knockbackPerk) {
		this.knockbackPerk = knockbackPerk;
	}

	public int getCritPerk() {
		return critPerk;
	}

	public void setCritPerk(int critPerk) {
		this.critPerk = critPerk;
	}

	public PlayerAbilityManager getAbilityManager() {
		return abilityManager;
	}

	public boolean hasWallJumpLeft() {
		return hasWallJumpLeft;
	}

	public boolean hasWallJumpRight() {
		return hasWallJumpRight;
	}

	public boolean hasWallJump() {
		return hasWallJump;
	}

	public void setHasWallJump(boolean hasWallJump) {
		this.hasWallJump = hasWallJump;
	}

	public boolean isFlightMode() {
		return flightMode;
	}

	public void setFlightMode(boolean flightMode) {
		this.flightMode = flightMode;
	}
	
	
	
}
