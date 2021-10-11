package dev.newjavagame;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import dev.newjavagame.display.Display;
import dev.newjavagame.gfx.Assets;
import dev.newjavagame.gfx.GameCamera;
import dev.newjavagame.input.KeyManager;
import dev.newjavagame.input.MouseManager;
import dev.newjavagame.states.GameState;
import dev.newjavagame.states.MenuState;
import dev.newjavagame.states.State;

public class Game implements Runnable {
	
	private Display display;
	private int width, height;
	public String title; 
	
	private boolean running = false;
	private Thread thread;
	
	private BufferStrategy bufferStrategy;
	private Graphics g;
	
	//States
	public State menuState;
	public State gameState;
	
	//Input
	private KeyManager keyManager;
	private MouseManager mouseManager;
	
	//Camera
	private GameCamera gameCamera;
	
	//Handler
	private Handler handler;
	
	public Game(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
		keyManager = new KeyManager();
		mouseManager = new MouseManager();
	}
	
	private void init() {
		display = new Display(title, width, height);
		display.getFrame().addKeyListener(keyManager);
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		Assets.init();
		
		handler = new Handler(this);
		gameCamera = new GameCamera(handler, 0, 0);
		
		menuState = new MenuState(handler);
		gameState = new GameState(handler);
		State.setState(menuState);
	}
	
	private void tick() {
		keyManager.tick();
		
		if (State.getState() != null)
			State.getState().tick();
	}
	
	private void render() {
		bufferStrategy = display.getCanvas().getBufferStrategy();
		if (bufferStrategy == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bufferStrategy.getDrawGraphics();
		//Clear Screen
		g.clearRect(0, 0, width, height);
		//Draw Here
		if (State.getState() != null)
			State.getState().render(g);
		
		bufferStrategy.show();
		g.dispose();
	}
	
	public void run() {
		
		init();
		
		int fps = 60;
		int tps = 100;
		double timePerTick = 1000000000 / tps;
		double timePerFrame = 1000000000 / fps;
		double deltaTick = 0;
		double deltaFrame = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;
		int frames = 0;
		
		
		while(running) {
			now = System.nanoTime();
			deltaTick += (now - lastTime) / timePerTick;
			deltaFrame += (now - lastTime) / timePerFrame;
			timer += now - lastTime;
			lastTime = now;
			
			if (deltaTick >= 1) {
				tick();
				ticks++;
				deltaTick--;
			}
			
			if (deltaFrame >= 1) {
				render();
				frames++;
				deltaFrame--;
			}
			
			if (timer >= 1000000000) {
				//System.out.println("Ticks: " + ticks + "\tFrames: " + frames);
				ticks = 0;
				frames = 0;
				timer = 0;
			}
		}
		
		stop();
		
	}
	
	public KeyManager getKeyManager() {
		return keyManager;
	}
	
	public MouseManager getMouseManager() {
		return mouseManager;
	}
	
	public GameCamera getGameCamera() {
		return gameCamera;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public synchronized void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		if (!running)
			return; 
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
