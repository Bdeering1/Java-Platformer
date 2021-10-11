package dev.newjavagame.gfx;

import java.awt.image.BufferedImage;

public class Assets {
	
	private static final int width = 32, height = 32;
	private static final int spriteWidth = 50, spriteHeight = 37;
	private static final int golemWidth = 225, golemHeight = 225;

	public static BufferedImage tree, block, grass, dirt, water, goblin, wood_item, back_drop_1;
	//Test Dummy
	public static BufferedImage test_dummy_left, test_dummy_right;
	
	//Player
	public static BufferedImage[] player_idle_left, player_idle_right;
	public static BufferedImage[] player_left, player_right;
	public static BufferedImage[] player_jump_left, player_jump_right;
	public static BufferedImage[] player_fall_left, player_fall_right;
	public static BufferedImage[] player_attack_left, player_attack_right, player_attack_left_air, player_attack_right_air;
	public static BufferedImage[] player_air_combo_left, player_air_combo_right;
	public static BufferedImage[] player_cast_left, player_cast_right;
	//Golem
	public static BufferedImage[] golem_idle_left, golem_idle_right, golem_left, golem_right, golem_attack_left, golem_attack_right;
	
	//Effects
	public static BufferedImage crit_left, crit_right, air_crit_left, air_crit_right;
	public static BufferedImage[] whirlwind;
	
	//Projectiles
	public static BufferedImage[] arcane_spell;
	
	public static BufferedImage[] btn_play;
	
	public static void init() {
		SpriteSheet tilesheet1 = new SpriteSheet(ImageLoader.loadImage("/textures/sheet.png"));
		SpriteSheet tilesheet2 = new SpriteSheet(ImageLoader.loadImage("/textures/forestground06dv5.png"));
		SpriteSheet advsheetleft = new SpriteSheet(ImageLoader.loadImage("/textures/adventurersheetleft.png"));
		SpriteSheet advsheetright = new SpriteSheet(ImageLoader.loadImage("/textures/adventurersheetright.png"));
		SpriteSheet golemsheetleft = new SpriteSheet(ImageLoader.loadImage("/textures/golemsheetleft.png"));
		SpriteSheet golemsheetright = new SpriteSheet(ImageLoader.loadImage("/textures/golemsheetright.png"));
		
		back_drop_1 = ImageLoader.loadImage("/textures/backdrop1.png");
		
		btn_play = new BufferedImage[2];
		btn_play[0] = ImageLoader.loadImage("/textures/playbutton1.png");
		btn_play[1] = ImageLoader.loadImage("/textures/playbutton2.png");
		
		test_dummy_left = ImageLoader.loadImage("/textures/trainingDummyLeft.png");
		test_dummy_right = ImageLoader.loadImage("/textures/trainingDummyRight.png");
		
		//PLAYER
		player_idle_left = new BufferedImage[4]; //Idle
		player_idle_right = new BufferedImage[4];
		player_left = new BufferedImage[5]; //Run
		player_right = new BufferedImage[5];
		player_jump_left = new BufferedImage[2]; //Jump
		player_jump_right = new BufferedImage[2];
		player_fall_left = new BufferedImage[2]; //Fall
		player_fall_right = new BufferedImage[2];
		player_attack_left = new BufferedImage[4]; //Ground Attack
		player_attack_right = new BufferedImage[4];
		player_attack_left_air = new BufferedImage[4]; //Air Attack
		player_attack_right_air = new BufferedImage[4];
		player_air_combo_left = new BufferedImage[13]; //Air Combo Attack
		player_air_combo_right = new BufferedImage[13];
		player_cast_left = new BufferedImage[8]; //Spell Cast
		player_cast_right = new BufferedImage[8];
		
		//Player Idle Left
		player_idle_left[0] = advsheetleft.crop(spriteWidth * 3, 0, spriteWidth, spriteHeight);
		player_idle_left[1] = advsheetleft.crop(spriteWidth * 4, 0, spriteWidth, spriteHeight);
		player_idle_left[2] = advsheetleft.crop(spriteWidth * 5, 0, spriteWidth, spriteHeight);
		player_idle_left[3] = advsheetleft.crop(spriteWidth * 6, 0, spriteWidth, spriteHeight);
		//PlayerIdle Right
		player_idle_right[0] = advsheetright.crop(0, 0, spriteWidth, spriteHeight);
		player_idle_right[1] = advsheetright.crop(spriteWidth, 0, spriteWidth, spriteHeight);
		player_idle_right[2] = advsheetright.crop(spriteWidth * 2, 0, spriteWidth, spriteHeight);
		player_idle_right[3] = advsheetright.crop(spriteWidth * 3, 0, spriteWidth, spriteHeight);
		//PlayerRun Left
		player_left[0] = advsheetleft.crop(0, spriteHeight, spriteWidth - 8, spriteHeight);
		player_left[1] = advsheetleft.crop(spriteWidth, spriteHeight, spriteWidth - 8, spriteHeight);
		player_left[2] = advsheetleft.crop(spriteWidth * 2, spriteHeight, spriteWidth - 8, spriteHeight);
		player_left[3] = advsheetleft.crop(spriteWidth * 3, spriteHeight, spriteWidth - 8, spriteHeight);
		player_left[4] = advsheetleft.crop(spriteWidth * 4, spriteHeight, spriteWidth - 8, spriteHeight);
		//PlayerRun Right
		player_right[0] = advsheetright.crop(spriteWidth + 8, spriteHeight, spriteWidth - 8, spriteHeight);
		player_right[1] = advsheetright.crop(spriteWidth * 2 + 8, spriteHeight, spriteWidth - 8, spriteHeight);
		player_right[2] = advsheetright.crop(spriteWidth * 3 + 8, spriteHeight, spriteWidth - 8, spriteHeight);
		player_right[3] = advsheetright.crop(spriteWidth * 4 + 8, spriteHeight, spriteWidth - 8, spriteHeight);
		player_right[4] = advsheetright.crop(spriteWidth * 5 + 8, spriteHeight, spriteWidth - 8, spriteHeight);
		//Player Attack Left
		player_attack_left[0] = advsheetleft.crop(spriteWidth * 6, spriteHeight * 7, spriteWidth, spriteHeight);
		player_attack_left[1] = advsheetleft.crop(spriteWidth * 5, spriteHeight * 7, spriteWidth, spriteHeight);
		player_attack_left[2] = advsheetleft.crop(spriteWidth * 4, spriteHeight * 7, spriteWidth, spriteHeight);
		player_attack_left[3] = advsheetleft.crop(spriteWidth * 3, spriteHeight * 7, spriteWidth, spriteHeight);
		//Player Attack Right
		player_attack_right[0] = advsheetright.crop(0, spriteHeight * 7, spriteWidth, spriteHeight);
		player_attack_right[1] = advsheetright.crop(spriteWidth, spriteHeight * 7, spriteWidth, spriteHeight);
		player_attack_right[2] = advsheetright.crop(spriteWidth * 2, spriteHeight * 7, spriteWidth, spriteHeight);
		player_attack_right[3] = advsheetright.crop(spriteWidth * 3, spriteHeight * 7, spriteWidth, spriteHeight);
		//Player Air Attack Left
		player_attack_left_air[0] = advsheetleft.crop(spriteWidth, spriteHeight * 13, spriteWidth, spriteHeight);
		player_attack_left_air[1] = advsheetleft.crop(0, spriteHeight * 13, spriteWidth, spriteHeight);
		player_attack_left_air[2] = advsheetleft.crop(spriteWidth * 6, spriteHeight * 14, spriteWidth, spriteHeight);
		player_attack_left_air[3] = advsheetleft.crop(spriteWidth * 5, spriteHeight * 14, spriteWidth, spriteHeight);
		//Player Air Attack Right
		player_attack_right_air[0] = advsheetright.crop(spriteWidth * 5, spriteHeight * 13, spriteWidth, spriteHeight);
		player_attack_right_air[1] = advsheetright.crop(spriteWidth * 6, spriteHeight * 13, spriteWidth, spriteHeight);
		player_attack_right_air[2] = advsheetright.crop(0, spriteHeight * 14, spriteWidth, spriteHeight);
		player_attack_right_air[3] = advsheetright.crop(spriteWidth, spriteHeight * 14, spriteWidth, spriteHeight);
		//Player Air Combo Left
		player_air_combo_left[0] = advsheetleft.crop(spriteWidth, spriteHeight * 13, spriteWidth, spriteHeight);
		player_air_combo_left[1] = advsheetleft.crop(0, spriteHeight * 13, spriteWidth, spriteHeight);
		player_air_combo_left[2] = advsheetleft.crop(spriteWidth * 6, spriteHeight * 14, spriteWidth, spriteHeight);
		player_air_combo_left[3] = advsheetleft.crop(spriteWidth * 5, spriteHeight * 14, spriteWidth, spriteHeight);
		player_air_combo_left[4] = advsheetleft.crop(spriteWidth * 4, spriteHeight * 14, spriteWidth, spriteHeight);
		player_air_combo_left[5] = advsheetleft.crop(spriteWidth * 3, spriteHeight * 14, spriteWidth, spriteHeight);
		player_air_combo_left[6] = advsheetleft.crop(spriteWidth * 2, spriteHeight * 14, spriteWidth, spriteHeight);
		player_air_combo_left[7] = advsheetleft.crop(spriteWidth, spriteHeight * 14, spriteWidth, spriteHeight);
		player_air_combo_left[8] = advsheetleft.crop(0, spriteHeight * 14, spriteWidth, spriteHeight);
		player_air_combo_left[9] = advsheetleft.crop(spriteWidth * 6, spriteHeight * 15, spriteWidth, spriteHeight);
		player_air_combo_left[10] = advsheetleft.crop(spriteWidth * 5, spriteHeight * 15, spriteWidth, spriteHeight);
		player_air_combo_left[11] = advsheetleft.crop(spriteWidth * 4, spriteHeight * 15, spriteWidth, spriteHeight);
		player_air_combo_left[12] = advsheetleft.crop(spriteWidth * 3, spriteHeight * 15, spriteWidth, spriteHeight);
		//Player Air Combo Right
		player_air_combo_right[0] = advsheetright.crop(spriteWidth * 5, spriteHeight * 13, spriteWidth, spriteHeight);
		player_air_combo_right[1] = advsheetright.crop(spriteWidth * 6, spriteHeight * 13, spriteWidth, spriteHeight);
		player_air_combo_right[2] = advsheetright.crop(0, spriteHeight * 14, spriteWidth, spriteHeight);
		player_air_combo_right[3] = advsheetright.crop(spriteWidth, spriteHeight * 14, spriteWidth, spriteHeight);
		player_air_combo_right[4] = advsheetright.crop(spriteWidth * 2, spriteHeight * 14, spriteWidth, spriteHeight);
		player_air_combo_right[5] = advsheetright.crop(spriteWidth * 3, spriteHeight * 14, spriteWidth, spriteHeight);
		player_air_combo_right[6] = advsheetright.crop(spriteWidth * 4, spriteHeight * 14, spriteWidth, spriteHeight);
		player_air_combo_right[7] = advsheetright.crop(spriteWidth * 5, spriteHeight * 14, spriteWidth, spriteHeight);
		player_air_combo_right[8] = advsheetright.crop(spriteWidth * 6, spriteHeight * 14, spriteWidth, spriteHeight);
		player_air_combo_right[9] = advsheetright.crop(0, spriteHeight * 15, spriteWidth, spriteHeight);
		player_air_combo_right[10] = advsheetright.crop(spriteWidth, spriteHeight * 15, spriteWidth, spriteHeight);
		player_air_combo_right[11] = advsheetright.crop(spriteWidth * 2, spriteHeight * 15, spriteWidth, spriteHeight);
		player_air_combo_right[12] = advsheetright.crop(spriteWidth * 3, spriteHeight * 15, spriteWidth, spriteHeight);
		//Player Jump/Fall Left
		player_jump_left[0] = advsheetleft.crop(spriteWidth * 4, spriteHeight * 2, spriteWidth - 5, spriteHeight);
		player_jump_left[1] = advsheetleft.crop(spriteWidth * 3, spriteHeight * 2, spriteWidth - 5, spriteHeight);
		player_fall_left[0] = advsheetleft.crop(spriteWidth * 5, spriteHeight * 3, spriteWidth - 5, spriteHeight);
		player_fall_left[1] = advsheetleft.crop(spriteWidth * 4, spriteHeight * 3, spriteWidth - 5, spriteHeight);
		//Player Jump/Fall Right
		player_jump_right[0] = advsheetright.crop(spriteWidth * 2 + 5, spriteHeight * 2, spriteWidth - 5, spriteHeight);
		player_jump_right[1] = advsheetright.crop(spriteWidth * 3 + 5, spriteHeight * 2, spriteWidth - 5, spriteHeight);
		player_fall_right[0] = advsheetright.crop(spriteWidth + 5, spriteHeight * 3, spriteWidth - 5, spriteHeight);
		player_fall_right[1] = advsheetright.crop(spriteWidth * 2 + 5, spriteHeight * 3, spriteWidth - 5, spriteHeight);
		//Player Spell Cast Left
		player_cast_left[0] = advsheetleft.crop(spriteWidth * 4, spriteHeight * 12, spriteWidth, spriteHeight);
		player_cast_left[1] = advsheetleft.crop(spriteWidth * 4, spriteHeight * 12, spriteWidth, spriteHeight);
		player_cast_left[2] = advsheetleft.crop(spriteWidth * 3, spriteHeight * 12, spriteWidth, spriteHeight);
		player_cast_left[3] = advsheetleft.crop(spriteWidth * 2, spriteHeight * 12, spriteWidth, spriteHeight);
		player_cast_left[4] = advsheetleft.crop(spriteWidth, spriteHeight * 12, spriteWidth, spriteHeight);
		player_cast_left[5] = advsheetleft.crop(0, spriteHeight * 12, spriteWidth, spriteHeight);
		player_cast_left[6] = advsheetleft.crop(spriteWidth * 6, spriteHeight * 13, spriteWidth, spriteHeight);
		player_cast_left[7] = advsheetleft.crop(spriteWidth * 5, spriteHeight * 13, spriteWidth, spriteHeight);
		//Player Spell Cast Right
		player_cast_right[0] = advsheetright.crop(spriteWidth, spriteHeight * 12, spriteWidth, spriteHeight);
		player_cast_right[1] = advsheetright.crop(spriteWidth * 2, spriteHeight * 12, spriteWidth, spriteHeight);
		player_cast_right[2] = advsheetright.crop(spriteWidth * 3, spriteHeight * 12, spriteWidth, spriteHeight);
		player_cast_right[3] = advsheetright.crop(spriteWidth * 4, spriteHeight * 12, spriteWidth, spriteHeight);
		player_cast_right[4] = advsheetright.crop(spriteWidth * 5, spriteHeight * 12, spriteWidth, spriteHeight);
		player_cast_right[5] = advsheetright.crop(spriteWidth * 6, spriteHeight * 12, spriteWidth, spriteHeight);
		player_cast_right[6] = advsheetright.crop(0, spriteHeight * 13, spriteWidth, spriteHeight);
		player_cast_right[7] = advsheetright.crop(spriteWidth, spriteHeight * 13, spriteWidth, spriteHeight);
		
		//GOLEM
		golem_idle_left = new BufferedImage[9];
		golem_idle_right = new BufferedImage[9];
		golem_left = new BufferedImage[12];
		golem_right = new BufferedImage[12];
		golem_attack_left = new BufferedImage[6];
		golem_attack_right = new BufferedImage[6];
		
		//Golem Idle Left
		golem_idle_left[0] = golemsheetleft.crop(golemWidth, 0, golemWidth, golemHeight);
		golem_idle_left[1] = golemsheetleft.crop(golemWidth * 2, 0, golemWidth, golemHeight);
		golem_idle_left[2] = golemsheetleft.crop(golemWidth, golemHeight, golemWidth, golemHeight);
		golem_idle_left[3] = golemsheetleft.crop(golemWidth * 2, golemHeight, golemWidth, golemHeight);
		golem_idle_left[4] = golemsheetleft.crop(golemWidth * 3, golemHeight, golemWidth, golemHeight);
		golem_idle_left[5] = golemsheetleft.crop(golemWidth * 4, golemHeight, golemWidth, golemHeight);
		golem_idle_left[6] = golemsheetleft.crop(golemWidth * 5, golemHeight, golemWidth, golemHeight);
		golem_idle_left[7] = golemsheetleft.crop(golemWidth * 4, golemHeight * 2, golemWidth, golemHeight);
		golem_idle_left[8] = golemsheetleft.crop(golemWidth * 5, golemHeight * 2, golemWidth, golemHeight);
		//Golem Idle Right
		golem_idle_right[0] = golemsheetright.crop(golemWidth * 3, 0, golemWidth, golemHeight);
		golem_idle_right[1] = golemsheetright.crop(golemWidth * 4, 0, golemWidth, golemHeight);
		golem_idle_right[2] = golemsheetright.crop(0, golemHeight, golemWidth, golemHeight);
		golem_idle_right[3] = golemsheetright.crop(golemWidth, golemHeight, golemWidth, golemHeight);
		golem_idle_right[4] = golemsheetright.crop(golemWidth * 2, golemHeight, golemWidth, golemHeight);
		golem_idle_right[5] = golemsheetright.crop(golemWidth * 3, golemHeight, golemWidth, golemHeight);
		golem_idle_right[6] = golemsheetright.crop(golemWidth * 4, golemHeight, golemWidth, golemHeight);
		golem_idle_right[7] = golemsheetright.crop(0, golemHeight * 2, golemWidth, golemHeight);
		golem_idle_right[8] = golemsheetright.crop(golemWidth, golemHeight * 2, golemWidth, golemHeight);
		//Golem Left
		golem_left[0] = golemsheetleft.crop(golemWidth * 4, golemHeight * 4, golemWidth, golemHeight);
		golem_left[1] = golemsheetleft.crop(golemWidth * 3, golemHeight * 4, golemWidth, golemHeight);
		golem_left[2] = golemsheetleft.crop(golemWidth * 2, golemHeight * 4, golemWidth, golemHeight);
		golem_left[3] = golemsheetleft.crop(golemWidth, golemHeight * 4, golemWidth, golemHeight);
		golem_left[4] = golemsheetleft.crop(0, 0, golemWidth, golemHeight);
		golem_left[5] = golemsheetleft.crop(0, golemHeight, golemWidth, golemHeight);
		golem_left[6] = golemsheetleft.crop(0, golemHeight * 2, golemWidth, golemHeight);
		golem_left[7] = golemsheetleft.crop(0, golemHeight * 3, golemWidth, golemHeight);
		golem_left[8] = golemsheetleft.crop(0, golemHeight * 4, golemWidth, golemHeight);
		golem_left[9] = golemsheetleft.crop(golemWidth * 5, golemHeight * 5, golemWidth, golemHeight);
		golem_left[10] = golemsheetleft.crop(golemWidth * 4, golemHeight * 5, golemWidth, golemHeight);
		golem_left[11] = golemsheetleft.crop(golemWidth * 3, golemHeight * 5, golemWidth, golemHeight);
		//Golem Right
		golem_right[0] = golemsheetright.crop(golemWidth, golemHeight * 4, golemWidth, golemHeight);
		golem_right[1] = golemsheetright.crop(golemWidth * 2, golemHeight * 4, golemWidth, golemHeight);
		golem_right[2] = golemsheetright.crop(golemWidth * 3, golemHeight * 4, golemWidth, golemHeight);
		golem_right[3] = golemsheetright.crop(golemWidth * 4, golemHeight * 4, golemWidth, golemHeight);
		golem_right[4] = golemsheetright.crop(golemWidth * 5, 0, golemWidth, golemHeight);
		golem_right[5] = golemsheetright.crop(golemWidth * 5, golemHeight, golemWidth, golemHeight);
		golem_right[6] = golemsheetright.crop(golemWidth * 5, golemHeight * 2, golemWidth, golemHeight);
		golem_right[7] = golemsheetright.crop(golemWidth * 5, golemHeight * 3, golemWidth, golemHeight);
		golem_right[8] = golemsheetright.crop(golemWidth * 5, golemHeight * 4, golemWidth, golemHeight);
		golem_right[9] = golemsheetright.crop(0, golemHeight * 5, golemWidth, golemHeight);
		golem_right[10] = golemsheetright.crop(golemWidth, golemHeight * 5, golemWidth, golemHeight);
		golem_right[11] = golemsheetright.crop(golemWidth * 2, golemHeight * 5, golemWidth, golemHeight);
		//Golem Attack Left
		//Golem Attack Right
		
		//EFFECTS
		whirlwind = new BufferedImage[4];
		
		whirlwind[0] = ImageLoader.loadImage("/textures/whirl1.png");
		whirlwind[1] = ImageLoader.loadImage("/textures/whirl2.png");
		whirlwind[2] = ImageLoader.loadImage("/textures/whirl3.png");
		whirlwind[3] = ImageLoader.loadImage("/textures/whirl4.png");
		
		crit_left = ImageLoader.loadImage("/textures/critLeft.png");
		crit_right = ImageLoader.loadImage("/textures/critRight.png");
		air_crit_left = ImageLoader.loadImage("/textures/airCritLeft.png");
		air_crit_right = ImageLoader.loadImage("/textures/airCritRight.png");
		
		//PROJECTILES
		arcane_spell = new BufferedImage[7];
		
		arcane_spell[0] = ImageLoader.loadImage("/textures/04/Arcane_Effect_1.png");
		arcane_spell[1] = ImageLoader.loadImage("/textures/04/Arcane_Effect_2.png");
		arcane_spell[2] = ImageLoader.loadImage("/textures/04/Arcane_Effect_3.png");
		arcane_spell[3] = ImageLoader.loadImage("/textures/04/Arcane_Effect_4.png");
		arcane_spell[4] = ImageLoader.loadImage("/textures/04/Arcane_Effect_5.png");
		arcane_spell[5] = ImageLoader.loadImage("/textures/04/Arcane_Effect_6.png");
		arcane_spell[6] = ImageLoader.loadImage("/textures/04/Arcane_Effect_7.png");
		
		//ENVIRONMENT
		tree = ImageLoader.loadImage("/textures/spruce.png");
		wood_item = ImageLoader.loadImage("/textures/wooditem.png");
		
		block = tilesheet1.crop(0, 0, width, height);
		grass = tilesheet1.crop(width, 0, width, height);
		dirt = tilesheet1.crop(0, height, width, height);
		water = tilesheet1.crop(width, height, width, height);
		goblin = ImageLoader.loadImage("/textures/goblin3.png");
	}
	
}
