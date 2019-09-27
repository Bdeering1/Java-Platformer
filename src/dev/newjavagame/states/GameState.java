package dev.newjavagame.states;

import java.awt.Graphics;

import dev.newjavagame.Game;
import dev.newjavagame.Handler;
import dev.newjavagame.entities.creatures.player.Player;
import dev.newjavagame.entities.statics.Tree;
import dev.newjavagame.gfx.Assets;
import dev.newjavagame.maps.Map;
import dev.newjavagame.tiles.Tile;

public class GameState extends State {

	private Map map;
	
	public GameState(Handler handler) {
		super(handler);
		map = new Map(handler, Assets.back_drop_1, "resources/maps/platformer1.map");
		handler.setMap(map);
	}
	
	@Override
	public void tick() {
		map.tick();
	}
	
	@Override
	public void render(Graphics g) {
		map.render(g);
	}
	
}