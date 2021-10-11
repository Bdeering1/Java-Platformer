package dev.newjavagame.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile {
	
	public static Tile[] tiles = new Tile[256];
	public static Tile airTile = new AirTile(0);
	public static Tile wallTile = new WallTile(1);
	public static DirtTile dirtTile = new DirtTile(2);
		
	//Class Components
	
	public static final int TILE_WIDTH = 56,
							TILE_HEIGHT = 56;
	
	protected int colLayer;

	protected BufferedImage texture;
	protected final int id;
	
	public Tile(BufferedImage texture, int id, int colLayer) {
		this.texture = texture;
		this.id = id;
		this.colLayer = colLayer;
		
		tiles[id] = this;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g, int x, int y) {
		if (texture == null)
			return;
		g.drawImage(texture, x, y, TILE_WIDTH, TILE_HEIGHT, null);
	}
	
	public int getId() {
		return id;
	}
	
	public int getColLayer() {
		return colLayer;
	}
	
}
