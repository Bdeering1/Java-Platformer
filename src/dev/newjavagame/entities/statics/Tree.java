package dev.newjavagame.entities.statics;

import java.awt.Color;
import java.awt.Graphics;

import dev.newjavagame.Handler;
import dev.newjavagame.gfx.Assets;
import dev.newjavagame.items.Item;
import dev.newjavagame.tiles.Tile;

public class Tree extends StaticEntity {

	public Tree(Handler handler, float x, float y) {
		super(handler, x, y, Tile.TILE_WIDTH * 2, Tile.TILE_HEIGHT * 3);
		
		bounds.x = 46;
		bounds.y = 150;
		bounds.width = 24;
		bounds.height = 10;
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.tree, (int)(x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
	}
	
	@Override
	public void die() {
		handler.getMap().getItemManager().addItem(Item.woodItem.createNew((int)x + bounds.x + bounds.width / 2 - Item.ITEM_WIDTH / 2,
																			(int)y + bounds.y + bounds.height / 2 - Item.ITEM_HEIGHT / 2));
	}

}