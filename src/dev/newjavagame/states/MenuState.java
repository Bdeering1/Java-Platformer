package dev.newjavagame.states;

import java.awt.Color;
import java.awt.Graphics;

import dev.newjavagame.Game;
import dev.newjavagame.Handler;
import dev.newjavagame.gfx.Assets;
import dev.newjavagame.ui.ClickListener;
import dev.newjavagame.ui.UIImageButton;
import dev.newjavagame.ui.UIManager;

public class MenuState extends State{
	
	private UIManager uiManager;
	
	public MenuState(Handler handler) {
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);
		
		uiManager.addObject(new UIImageButton(handler.getWidth() / 2 - 315, handler.getHeight() / 2 - 187, 630, 375, Assets.btn_play, new ClickListener(){
			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				State.setState(handler.getGame().gameState);
			}
		}));
		uiManager.getObjects().get(0).setBounds(105, 75, 420, 225);
	}
	
	@Override
	public void tick() {
		uiManager.tick();
		
		//Temporarily Skip Menu State
		handler.getMouseManager().setUIManager(null);
		State.setState(handler.getGame().gameState);
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(Color.orange);
		g.fillRect(0, 0, handler.getGame().getWidth(), handler.getGame().getHeight());
		uiManager.render(g);
	}
	
}
