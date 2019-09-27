package dev.newjavagame.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import dev.newjavagame.Handler;
import dev.newjavagame.entities.creatures.player.Player;

public class EntityManager {

	private Handler handler;
	private Player player;
	private boolean showBounds, showAI;
	private ArrayList<Entity> entities;
	private Comparator<Entity> renderSorter = new Comparator<Entity>() {
		@Override
		public int compare(Entity a, Entity b) {
			
			return (int)(a.getCollisionBounds(0, 0).y + a.getCollisionBounds(0, 0).height)
					- ((int)(b.getCollisionBounds(0, 0).y + b.getCollisionBounds(0, 0).height));
		}
		
	};
	
	public EntityManager(Handler handler, Player player) {
		this.handler = handler;
		this.player = player;
		entities = new ArrayList<Entity>();
		addEntity(player);
		
		showBounds = false;
		showAI = false;
	}
	
	public void tick() {
		Iterator<Entity> it = entities.iterator();
		while(it.hasNext()) {
			Entity e = it.next();
			e.tick();
			if (!e.isAlive())
				it.remove();
		}
		entities.sort(renderSorter);
	}
	
	public void render(Graphics g) {
		for (Entity e : entities) {
			//Collision Bounds
			if (showBounds) {
				Rectangle cb = e.getCollisionBounds(0, 0);
				g.setColor(Color.red);
				g.fillRect((int)(cb.x - handler.getGameCamera().getxOffset()),
						(int)(cb.y - handler.getGameCamera().getyOffset()), cb.width, cb.height);
			}
			//Entity Render
			e.render(g);
		}
	}
	
	public void addEntity(Entity e) {
		entities.add(e);
	}
	
	//Getters and Setters
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}

	public void setEntities(ArrayList<Entity> entities) {
		this.entities = entities;
	}

	public boolean isShowBounds() {
		return showBounds;
	}

	public void setShowBounds(boolean showBounds) {
		this.showBounds = showBounds;
	}

	public boolean isShowAI() {
		return showAI;
	}

	public void setShowAI(boolean showAI) {
		this.showAI = showAI;
	}
	
}
