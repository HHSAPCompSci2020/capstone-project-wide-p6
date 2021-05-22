/** Author: Ethan Wang
 * The map class is meant to be a huge database of all the enemies and obstacles across the map
 */

import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.Arrays;

import processing.core.PApplet;
import processing.core.PImage;

public class Map {

	private ArrayList<Shape> obstacles;
	private ArrayList<Rectangle> checkpoints;
	private ArrayList<Hitbox> hitboxes; //only really applies to player, enemy projectiles go into enemies category.
	//hitboxes are formatted as: x, y, width, height, damage, frames on-screen, launch xvel, launch yvel
	private Enemy[] enemies;
	private ArrayList<Projectile> projectiles;
	
	private PImage marioflip, goomba; 
	
	private ArrayList<ArrayList<Integer>> enemyInfo;
	
	/** Map constructor. No parameters because the map will be the same every time. Sets up the entire map.
	 */
	public Map() {
		marioflip = (new PApplet()).loadImage("marioflip.png");
		goomba = (new PApplet()).loadImage("goomba.png");
		
		checkpoints = new ArrayList<Rectangle>();
		obstacles = new ArrayList<Shape>();
		projectiles = new ArrayList<Projectile>();
		enemyInfo = new ArrayList<ArrayList<Integer>>();
		hitboxes = new ArrayList<Hitbox>();
		
		
		
		
		
		obstacles.add(new Rectangle(200,400,1000,50));
		obstacles.add(new Rectangle(0,250,100,50));
		obstacles.add(new Rectangle(700,250,100,50));
		obstacles.add(new Rectangle(375,300,50,100));
		obstacles.add(new Rectangle(300,250,200,50));
		
		
		
		
		
		checkpoints.add(new Rectangle(0,200,50,50));
		checkpoints.add(new Rectangle(700,200,50,50));
		
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(0, 450, 350, 50, 50, 45000, 45000))); // enemy type, x, y, width, height, respawn time, respawn time left in thousandths of a second
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(1, 750, 200, 50, 50, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(2, 400, 150, 50, 50, 45000, 45000)));
		
		
		
		enemies = new Enemy[enemyInfo.size()];
		
		for(int i = 0; i < enemyInfo.size(); i++) {
			ArrayList<Integer> list = enemyInfo.get(i);
			spawnEnemy(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), i);
			
		}
	}
	
	public void spawnEnemy(int type, int x, int y, int w, int h, int index) {
		if (type == 0) {
			enemies[index] = (new BasicEnemy(new ArrayList<PImage>(Arrays.asList(goomba)), x, y, w, h, index));
		}
		if (type == 1) {
			enemies[index] = (new ShooterEnemy(new ArrayList<PImage>(Arrays.asList(marioflip)), x, y, w, h, index));
		}
		if (type == 2) {
			enemies[index] = (new FlyingShooter(new ArrayList<PImage>(Arrays.asList(marioflip)), x, y, w, h, index));
		}
	}
	/**
	 * @return returns an arraylists of all obstacles on the map.
	 */
	public ArrayList<Shape> getObstacles(){
		return obstacles;
	}
	/**
	 * @return returns an arraylists of all checkpoints on the map.
	 */
	public ArrayList<Rectangle> getCheckpoints(){
		return checkpoints;
	}
	/**
	 * @return returns an arraylists of all hitboxes on the map.
	 */
	public ArrayList<Hitbox> getHitboxes(){
		return hitboxes;
	}
	/**
	 * adds a hitbox
	 *@param hitbox a list of all the hitboxes in the game
	 */
	public void addHitbox(Hitbox hitbox){
		hitboxes.add(hitbox);
	}
	/**
	 * @return returns an arraylists of all enemies on the map.
	 */
	public Enemy[] getEnemies(){
		return enemies;
	}
	/**
	 * @return returns an arraylists of all enemy info on the map.
	 */
	public ArrayList<ArrayList<Integer>> getEnemyInfo(){
		return enemyInfo;
	}

	public void shoot(int type, int x, int y, int w, int h, double vx, double vy, double dist, int dam) {
		if (type == 0) { 
			projectiles.add(new Projectile((new PApplet()).loadImage("hitboxtemp.png"), x, y, w, h, vx, vy, dist, dam));
		}
	}
	
	public ArrayList<Projectile> getProjectiles(){
		return projectiles;
	}
	
}
