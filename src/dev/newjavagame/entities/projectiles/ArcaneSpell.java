package dev.newjavagame.entities.projectiles;

import java.awt.Graphics;

import dev.newjavagame.Handler;
import dev.newjavagame.gfx.Assets;

public class ArcaneSpell extends Projectile {

	public ArcaneSpell(Handler handler, float x, float y, int width, int height, int xVel, int yVel) {
		super(handler, x, y, width, height);
		
		//Animation
		loopFrame = 0;
		endFrame = 5;
		explodeFrame = 6;
		anim = Assets.arcane_spell;
		
		//Movement
		this.xVel = xVel;
		this.yVel = yVel;
	}

	@Override
	public void setxVel() {
		
	}

	@Override
	public void setyVel() {
		
	}

}
