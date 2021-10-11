 package dev.newjavagame.maps;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.newjavagame.Game;
import dev.newjavagame.Handler;
import dev.newjavagame.entities.EntityManager;
import dev.newjavagame.entities.creatures.Golem;
import dev.newjavagame.entities.creatures.TestDummy;
import dev.newjavagame.entities.creatures.player.Player;
import dev.newjavagame.entities.statics.Tree;
import dev.newjavagame.gfx.BackDrop;
import dev.newjavagame.items.ItemManager;
import dev.newjavagame.tiles.Tile;
import dev.newjavagame.utils.Utils;

public class Map {

	private Handler handler;
	private int width, height;
	private int spawnX, spawnY;
	private int[][] tiles;
	
	//Player
	private Player player;
	//Backdrop
	private BackDrop backDrop;
	//Entities
	private EntityManager entityManager;
	//Items
	private ItemManager itemManager;
	
	public Map(Handler handler, BufferedImage image, String path) {
		player = new Player(handler, 300, 300, 150, 111, 100);
		
		this.handler = handler;
		backDrop = new BackDrop(handler, image);
		entityManager = new EntityManager(handler, player);
		itemManager = new ItemManager(handler);
		
		//entityManager.addEntity(new TestDummy(handler, Tile.TILE_WIDTH * 20, Tile.TILE_HEIGHT * 13, 10, player));
		//entityManager.addEntity(new TestDummy(handler, Tile.TILE_WIDTH * 7, Tile.TILE_HEIGHT * 15, 8, player));
		
		entityManager.addEntity(new Golem(handler, Tile.TILE_WIDTH * 20, Tile.TILE_HEIGHT * 13, player));
		//entityManager.addEntity(new Golem(handler, Tile.TILE_WIDTH * 24, Tile.TILE_HEIGHT * 15, player));
		//entityManager.addEntity(new Golem(handler, Tile.TILE_WIDTH * 26, Tile.TILE_HEIGHT * 15, player));
		
		loadMap(path);
		
		entityManager.getPlayer().setX(Tile.TILE_WIDTH * spawnX);
		entityManager.getPlayer().setY(Tile.TILE_HEIGHT * spawnY);
	}

	public void tick() {
		backDrop.tick();
		entityManager.tick();
		itemManager.tick();
	}
	
	public void render(Graphics g) {
		backDrop.render(g);;
		
		int xStart = (int)Math.max(0, handler.getGameCamera().getxOffset() / Tile.TILE_WIDTH);
		int xEnd = (int)Math.min(width, (handler.getGameCamera().getxOffset() + handler.getWidth()) / Tile.TILE_WIDTH + 1);
		int yStart = (int)Math.max(0, handler.getGameCamera().getyOffset() / Tile.TILE_HEIGHT);
		int yEnd = (int)Math.min(height, (handler.getGameCamera().getyOffset() + handler.getHeight()) / Tile.TILE_HEIGHT + 1);
		
		for (int y = yStart; y < yEnd; y++) {
			for (int x = xStart; x < xEnd; x++) {
				getTile(x, y).render(g, (int)(x * Tile.TILE_WIDTH - handler.getGameCamera().getxOffset()),
										(int)(y * Tile.TILE_HEIGHT - handler.getGameCamera().getyOffset()));
			}
		}
		//Items
		itemManager.render(g);
		//Entities
		entityManager.render(g);
	}
	
	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height)
			return Tile.airTile;
		
		Tile t = Tile.tiles[tiles[x][y]];
		if (t == null) 
			return Tile.airTile;
		return t;
	}
	
	private void loadMap(String path) {
		String file = Utils.loadFileAsString(path);
		String[] tokens = file.split("\\s+");
		width = Utils.parseInt(tokens[0]);
		height = Utils.parseInt(tokens[1]);
		spawnX = Utils.parseInt(tokens[2]);
		spawnY = Utils.parseInt(tokens[3]);
		
		tiles = new int[width][height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tiles[x][y] = Utils.parseInt(tokens[(x + y * width) + 4]);
			}
		}
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public ItemManager getItemManager() {
		return itemManager;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}
	
}
