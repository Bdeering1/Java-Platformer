package dev.newjavagame;

import dev.newjavagame.gfx.GameCamera;
import dev.newjavagame.input.KeyManager;
import dev.newjavagame.input.MouseManager;
import dev.newjavagame.maps.Map;

public class Handler {

	private Game game;
	private Map map;
	
	public Handler(Game game) {
		this.game = game;
	}
	
	//Getters and Setters
	public GameCamera getGameCamera() {
		return game.getGameCamera();
	}
	
	public KeyManager getKeyManager() {
		return game.getKeyManager();
	}
	
	public MouseManager getMouseManager() {
		return game.getMouseManager();
	}
	
	public int getWidth() {
		return game.getWidth();
	}
	
	public int getHeight() {
		return game.getHeight();
	}
	
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}
	
}
