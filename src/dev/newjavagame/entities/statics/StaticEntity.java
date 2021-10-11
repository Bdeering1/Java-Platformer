package dev.newjavagame.entities.statics;

import dev.newjavagame.Handler;
import dev.newjavagame.entities.Entity;

public abstract class StaticEntity extends Entity {

	public StaticEntity(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
	}
	
}
