package dev.newjavagame.gfx;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.newjavagame.Handler;

public class BackDrop {
	
	private Handler handler;
	private BufferedImage image;

	public BackDrop(Handler handler, BufferedImage image) {
		this.handler = handler;
		this.image = image;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.drawImage(image, 0, 0, handler.getWidth(), handler.getHeight(), null);
	}
	
}
