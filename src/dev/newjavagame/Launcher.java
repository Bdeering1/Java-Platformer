package dev.newjavagame;

import dev.newjavagame.display.Display;

public class Launcher {
	
	public static Game game;

	public static void main(String[] args) {
		game = new Game("Bryn Deering Java Platformer", 1664, 960);
		game.start();
	}
	
}
