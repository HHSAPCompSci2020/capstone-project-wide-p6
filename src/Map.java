

import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.Arrays;
import processing.core.PApplet;
import processing.core.PImage;
/** Author: Ethan Wang, Justin Zhu
 * The map class is meant to be a huge database of all the enemies and obstacles across the map
 */
public class Map  {

	private ArrayList<Shape> obstacles;
	private ArrayList<Rectangle> checkpoints;
	private ArrayList<Rectangle> lava;
	private ArrayList<Hitbox> hitboxes; //only really applies to player, enemy projectiles go into enemies category.
	//hitboxes are formatted as: x, y, width, height, damage, frames on-screen, launch xvel, launch yvel
	private Enemy[] enemies;
	private ArrayList<Projectile> projectiles;

	private PImage mario, goomba,fly; 
	
	private ArrayList<ArrayList<Integer>> enemyInfo;
	
	/** Map constructor. No parameters because the map will be the same every time. Sets up the entire map.
	 */
	public Map() {
		mario = (new PApplet()).loadImage("imgs/shooterenemy.png");
		goomba = (new PApplet()).loadImage("imgs/enemy.png");
		fly = (new PApplet()).loadImage("imgs/flyenemy.png");
		
		checkpoints = new ArrayList<Rectangle>();
		obstacles = new ArrayList<Shape>();
		projectiles = new ArrayList<Projectile>();
		enemyInfo = new ArrayList<ArrayList<Integer>>();
		hitboxes = new ArrayList<Hitbox>();
		lava = new ArrayList<Rectangle>();
		
		
		
		obstacles.add(new Rectangle(50,0,5400,50));
		obstacles.add(new Rectangle(50,6050,5400,100));
		obstacles.add(new Rectangle(0,0,50,6150));
		obstacles.add(new Rectangle(5400,0,100,6150));
		obstacles.add(new Rectangle(200,2650,200,50));
		obstacles.add(new Rectangle(600,2600,350,50));
		obstacles.add(new Rectangle(50,2800,3100,100));
		obstacles.add(new Rectangle(700,2650,100,150));
		obstacles.add(new Rectangle(2250,2650,200,50));
		obstacles.add(new Rectangle(2500,2500,200,50));
		obstacles.add(new Rectangle(3050,1450,100,1350));
		obstacles.add(new Rectangle(2850,2350,200,50));
		obstacles.add(new Rectangle(2250,2050,550,50));
		obstacles.add(new Rectangle(1650,2050,500,50));
		obstacles.add(new Rectangle(1300,1900,50,50));
		
		obstacles.add(new Rectangle(650,1600,150,50));
		obstacles.add(new Rectangle(450,1300,100,350));
		obstacles.add(new Rectangle(100,1600,150,50));
		obstacles.add(new Rectangle(550,1200,300,100));
		obstacles.add(new Rectangle(850,1100,250,100));
		obstacles.add(new Rectangle(1100,1200,750,100));
		obstacles.add(new Rectangle(1850,1050,100,150));
		obstacles.add(new Rectangle(1200,950,1100,100));
		obstacles.add(new Rectangle(2300,650,100,300));
		obstacles.add(new Rectangle(2400,650,200,100));
		obstacles.add(new Rectangle(2650,500,200,100));
		obstacles.add(new Rectangle(2400,950,100,100));
		obstacles.add(new Rectangle(2500,850,600,100));
		obstacles.add(new Rectangle(3150,1550,1900,100));
		obstacles.add(new Rectangle(3150,1350,200,100));
		obstacles.add(new Rectangle(3450,250,100,1300));
		obstacles.add(new Rectangle(600,150,2950,100));
		obstacles.add(new Rectangle(350,350,150,50));
		obstacles.add(new Rectangle(500,400,2050,100));
		obstacles.add(new Rectangle(3550,650,50,50));
		obstacles.add(new Rectangle(3550,950,50,50));
		obstacles.add(new Rectangle(3550,1250,50,50));
		obstacles.add(new Rectangle(3550,350,50,50));

		obstacles.add(new Rectangle(4800,400,50,50));
		obstacles.add(new Rectangle(4950,150,100,1400));
		obstacles.add(new Rectangle(4700,3050,50,100));
		obstacles.add(new Rectangle(4950,3050,50,100));
		obstacles.add(new Rectangle(3600,3150,1800,100));
		//obstacles.add(new Rectangle(5100,1750,300,1500));
		
		//obstacles.add(new Rectangle(4150,2250,100,100));
		//obstacles.add(new Rectangle(3700,1950,100,100));
		obstacles.add(new Rectangle(3500,1950,100,3100));
		obstacles.add(new Rectangle(3150,1950,250,100));
		
		obstacles.add(new Rectangle(3300,2050,100,3200));
		obstacles.add(new Rectangle(3400,5150,1700,100));
		obstacles.add(new Rectangle(3300,2050,100,3200));
		obstacles.add(new Rectangle(3600,3450,100,100));
		obstacles.add(new Rectangle(3700,3750,100,100));
		obstacles.add(new Rectangle(3600,4050,100,100));
		obstacles.add(new Rectangle(3700,4350,100,100));
		obstacles.add(new Rectangle(3600,4650,100,100));
		obstacles.add(new Rectangle(3700,4950,100,100));

		obstacles.add(new Rectangle(3800,3350,100,1800));
		obstacles.add(new Rectangle(3900,3350,1200,100));
		obstacles.add(new Rectangle(4000,4050,1400,100));
		obstacles.add(new Rectangle(4000,3750,100,300));
		obstacles.add(new Rectangle(3900,4500,1200,100));
		obstacles.add(new Rectangle(4000,4900,1400,100));
		obstacles.add(new Rectangle(3900,5150,1200,100));
		obstacles.add(new Rectangle(3900,5150,1200,100));

		obstacles.add(new Rectangle(5100,5350,300,100));
		obstacles.add(new Rectangle(5000,5450,100,100));
		obstacles.add(new Rectangle(4900,5550,100,100));
		obstacles.add(new Rectangle(4800,5650,100,100));
		obstacles.add(new Rectangle(4700,5750,100,100));
		obstacles.add(new Rectangle(4600,5850,100,100));
		obstacles.add(new Rectangle(4400,5950,200,100));
		obstacles.add(new Rectangle(4300,5750,100,200));
		obstacles.add(new Rectangle(4200,5650,100,100));
		obstacles.add(new Rectangle(4100,5550,100,100));
		obstacles.add(new Rectangle(4000,5450,100,100));
		obstacles.add(new Rectangle(3300,5450,100,100));
		obstacles.add(new Rectangle(2900,5350,200,100));
		obstacles.add(new Rectangle(2550,5150,300,100));
		obstacles.add(new Rectangle(1200,4850,200,100));
		obstacles.add(new Rectangle(1650,5000,200,100));
		obstacles.add(new Rectangle(1700,4650,200,100));
		obstacles.add(new Rectangle(2150,4600,200,100));
		obstacles.add(new Rectangle(1400,4400,200,100));
		obstacles.add(new Rectangle(1100,4200,100,100));
		obstacles.add(new Rectangle(1300,3900,500,100));
		obstacles.add(new Rectangle(850,4100,100,100));
		obstacles.add(new Rectangle(2650,3400,100,100));
		obstacles.add(new Rectangle(2100,3650,100,100));

		obstacles.add(new Rectangle(3200,3150,100,100));
		obstacles.add(new Rectangle(3050,3000,100,100));
		obstacles.add(new Rectangle(3250,2750,50,50));
		obstacles.add(new Rectangle(3150,2500,50,50));
		obstacles.add(new Rectangle(3250,2250,50,50));
		obstacles.add(new Rectangle(5100,4600,100,100));
		
		obstacles.add(new Rectangle(0,7000,1600, 100));
		obstacles.add(new Rectangle(1600,7000,100,1100));
		obstacles.add(new Rectangle(100,8100,1600,100));
		obstacles.add(new Rectangle(0,7100,100,1100));


		
		checkpoints.add(new Rectangle(250,2600,50,50));
		checkpoints.add(new Rectangle(650,1550,50,50));
		checkpoints.add(new Rectangle(350,300,50,50));
		checkpoints.add(new Rectangle(3500,100,50,50));
		checkpoints.add(new Rectangle(4950,100,50,50));
		checkpoints.add(new Rectangle(3500,1900,50,50));
		checkpoints.add(new Rectangle(3600,3400,50,50));
		checkpoints.add(new Rectangle(5150,5300,50,50));
		checkpoints.add(new Rectangle(4000,5400,50,50));
		checkpoints.add(new Rectangle(3900,3750,50,50));
		checkpoints.add(new Rectangle(3950,3750,50,50));
		checkpoints.add(new Rectangle(5150,4550,50,50));
		checkpoints.add(new Rectangle(3250,3100,50,50));


		lava.add(new Rectangle(50,6001,4350,50));
		lava.add(new Rectangle(300,2750,400,50));
		lava.add(new Rectangle(1100,1150,150,50));
		lava.add(new Rectangle(5050,300,350,300));



//		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(0, 450, 350, 50, 50, 45000, 45000))); // enemy type, x, y, width, height, respawn time, respawn time left in thousandths of a second
//		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(1, 750, 200, 50, 50, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(0, 4850, 3100, 100, 100, 0, 0)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(1, 1300, 2700, 50, 50, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(0, 1400, 2700, 50, 50, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(0, 2450, 2000, 50, 50, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(0, 1950, 2000, 50, 50, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(0, 1600, 1150, 50, 50, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(0, 1650, 1100, 100, 100, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(1, 1750, 1100, 100, 100, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(0, 1450, 1150, 100, 100, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(0, 650, 100, 50, 50, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(0, 750, 100, 50, 50, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(0, 850, 100, 50, 50, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(0, 950, 100, 50, 50, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(0, 1050, 100, 50, 50, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(0, 1150, 100, 50, 50, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(0, 1250, 100, 50, 50, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(0, 1350, 100, 50, 50, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(0, 3900, 3300, 50, 50, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(1, 3950, 3300, 50, 50, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(1, 4000, 3300, 50, 50, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(1, 4100, 3300, 50, 50, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(0, 4050, 3300, 50, 50, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(0, 4150, 3300, 50, 50, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(0, 4200, 3300, 50, 50, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(0, 4250, 3300, 50, 50, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(1, 4300, 3300, 50, 50, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(1, 4450, 3250, 100, 100, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(0, 4550, 3250, 50, 50, 45000, 45000)));

		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(2, 4350, 3600, 50, 50, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(2, 4450, 3600, 50, 50, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(2, 4550, 3600, 50, 50, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(2, 4650, 3600, 50, 50, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(2, 4750, 3600, 50, 50, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(2, 4850, 3600, 100, 100, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(1, 4050, 3700, 50, 50, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(1, 4350, 4000, 50, 50, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(1, 4450, 4000, 50, 50, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(1, 4150, 3900, 150, 150, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(1, 4550, 4000, 100, 100, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(0, 4650, 4000, 100, 100, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(0, 4750, 4000, 50, 50, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(0, 4850, 4000, 200, 200, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(1, 4450, 4000, 100, 100, 45000, 45000)));
		
		
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(2, 4450, 4200, 100, 100, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(2, 4650, 4200, 100, 100, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(1, 4450, 4400, 100, 100, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(1, 4550, 4400, 100, 100, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(1, 4750, 4400, 100, 100, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(0, 4850, 4400, 100, 100, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(0, 4150, 4400, 100, 100, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(0, 4250, 4400, 100, 100, 45000, 45000)));

		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(1, 4650, 4700, 150, 150, 45000, 45000)));
		enemyInfo.add(new ArrayList<Integer>(Arrays.asList(0, 4950, 4700, 150, 150, 45000, 45000)));

		
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
			enemies[index] = (new ShooterEnemy(new ArrayList<PImage>(Arrays.asList(mario)), x, y, w, h, index));
		}
		if (type == 2) {
			enemies[index] = (new FlyingShooter(new ArrayList<PImage>(Arrays.asList(fly)), x, y, w, h, index));
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
	/** creates a new projectile
	 * 
	 * @param type  always 0, no other types of projectiles
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param vx x velocity
	 * @param vy y velocity
	 * @param dist maximum distance to travel
	 * @param dam damage it deals
	 */ 
	public void shoot(int type, int x, int y, int w, int h, double vx, double vy, double dist, int dam) {
		if (type == 0) { 
			projectiles.add(new Projectile((new PApplet()).loadImage("imgs/hitboxtemp.png"), x, y, w, h, vx, vy, dist, dam));
		}
	}
	/**
	 * 
	 * @return returns an arraylist of projectiles
	 */
	public ArrayList<Projectile> getProjectiles(){
		return projectiles;
	}
	/**
	 * 
	 * @return returns an arraylist of lava
	 */
	public ArrayList<Rectangle> getLava(){
		return lava;
	}
}

	
