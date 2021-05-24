/** Author: Shelby, Ethan Wang
 * The class used for essentially all the graphics of the game.
 */

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import processing.core.PImage;

import processing.core.PApplet;

public class DrawingSurface extends PApplet implements MouseListener{

	/**
	 * 
	 */
	public static final int DRAWING_WIDTH = 800;
	public static final int DRAWING_HEIGHT = 600;

	private Rectangle screenRect;
	private Map map;
	
	private double camx, camy;
	private long lastUpdate;
	private long currTime;
	private Main w;
	
	private Player player;
	
	private ArrayList<Integer> keys;
	private Boolean[] checksPassed;
	private boolean bossFight = false;
	private BallBoss boss;
	
	
	public DrawingSurface(Main w) {
		super();
		keys = new ArrayList<Integer>();
		screenRect = new Rectangle(0,0,DRAWING_WIDTH,DRAWING_HEIGHT);
		map = new Map();
		checksPassed = new Boolean[map.getCheckpoints().size()];
		for (int j = 0; j < map.getEnemies().length; j++) {
			ArrayList<Integer>list = map.getEnemyInfo().get(j);
			map.spawnEnemy(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), j);
			list.set(6,list.get(5));
		}
		for (int i = 0; i < checksPassed.length; i++) {
			checksPassed[i] = false;
		}
		checksPassed[0] = true;
		player = new Player(new ArrayList<PImage>(Arrays.asList(loadImage("imgs/mario.png"), loadImage("imgs/marioflip.png"))), (int)(map.getCheckpoints().get(0).getCenterX() -Player.PLAYER_WIDTH/2) ,(int)(map.getCheckpoints().get(0).getCenterY() -Player.PLAYER_HEIGHT/2));
		camx = player.x;
		camy = player.y;
		lastUpdate = System.nanoTime();
		
		this.w = w;
	}



	// The statements in the setup() function 
	// execute once when the program begins
	public void setup() {
		//size(0,0,PApplet.P3D);
	}

	// The statements in draw() are executed until the 
	// program is stopped. Each statement is executed in 
	// sequence and after the last line is read, the first 
	// line is executed again.
	public void draw() {
		
		// drawing stuff
		background(0,255,255);   

		pushMatrix();

		int width = getWidth();
		int height = getHeight();

		float ratioX = (float)width/DRAWING_WIDTH;
		float ratioY = (float)height/DRAWING_HEIGHT;

		scale(ratioX, ratioY);
		
		
		//camera movement
		camx = player.x - DRAWING_WIDTH + player.width;
		if (player.y - DRAWING_HEIGHT + player.height> camy + 100) {
			camy = player.y - DRAWING_HEIGHT + player.height- 100;
		} else if (player.y - DRAWING_HEIGHT + player.height > camy - 100) {
			camy = camy;
		} else {
			camy = player.y - DRAWING_HEIGHT + player.height + 100;
		}
		
		
		

		for (Shape s : map.getObstacles()) {
			if (s instanceof Rectangle) {
				stroke(100);
				fill(100);
				Rectangle r = (Rectangle)s;
				if(Math.sqrt((player.x - r.x)*(player.x - r.x) + (player.y - r.y)*(player.y - r.y)) < Math.sqrt((r.width)*(r.width) + (r.height)*(r.height)) +  Math.sqrt((w.getWidth())*(w.getWidth())*4 + (w.getHeight())*(w.getHeight())*4) + 1000) {
					rect((int)(r.x - camx)/2,(int)(r.y - camy)/2,r.width/2,r.height/2);
				}
				stroke(0);
			}
		}
		
		for (Rectangle r : map.getLava()) {
			noStroke();
			fill(250, 0, 0);
			if(Math.sqrt((player.x - r.x)*(player.x - r.x) + (player.y - r.y)*(player.y - r.y)) < Math.sqrt((r.width)*(r.width) + (r.height)*(r.height)) +  Math.sqrt((w.getWidth())*(w.getWidth())*4 + (w.getHeight())*(w.getHeight())*4) + 1000) {
				rect((int)(r.x - camx)/2,(int)(r.y - camy)/2,r.width/2,r.height/2);
			}
			stroke(0);
		}
		
		for (int i = 0 ; i < map.getProjectiles().size(); i++) {
			Projectile p = map.getProjectiles().get(i);
			p.act(map, currTime, player);
			if (p.die(map)) {
				map.getProjectiles().remove(p);
				i--;
			}
			p.draw(this, camx, camy);
		}
		
		boolean allCheck = true;
		for (Rectangle s : map.getCheckpoints()) {
			
			if (s instanceof Rectangle) {
				Rectangle r = (Rectangle)s;
				if (player.lastCheck == map.getCheckpoints().indexOf(s)) {
					checksPassed[map.getCheckpoints().indexOf(s)] = true;
					fill(250);
				} else if (checksPassed[map.getCheckpoints().indexOf(s)]){
					fill(200);
				} else {
					fill(50);
					allCheck = false;
				}
				if(Math.sqrt((player.x - r.x)*(player.x - r.x) + (player.y - r.y)*(player.y - r.y)) < Math.sqrt((r.width)*(r.width) + (r.height)*(r.height)) +  Math.sqrt((w.getWidth())*4*(w.getWidth()) + (w.getHeight())*4*(w.getHeight())) + 1000) {
					rect((int)(r.x - camx)/2,(int)(r.y - camy)/2,r.width/2,r.height/2);
				}
			}
		}
		
		fill(150, 50, 100);
		if (allCheck) {
			rect((int)(3150 - camx)/2,(int)(2050 - camy)/2,150/2,150/2);
			if ((new Rectangle(3150, 2050, 150, 150).intersects(player.x, player.y, player.width, player.height))) {
				bossFight = true;
				boss = new BallBoss(new ArrayList<PImage> (Arrays.asList(loadImage("imgs/Boss1.png"), loadImage("imgs/Boss2.png"), loadImage("imgs/Boss3.png"), loadImage("imgs/Boss4.png"), loadImage("imgs/Boss5.png"))), 850, 7800, 300, 300);
				player.x = 600;
				player.y = 7950;
			}
		}
		
		fill(100);
		for (int i = 0; i < map.getHitboxes().size(); i++) {
			Hitbox list =  map.getHitboxes().get(i);
			list.draw(this, camx, camy);
			list.dur -= 1;
			if (list.dur == 0) {
				map.getHitboxes().remove(list);
			}
			list.setImage(list.dur);
		}
		
		for (int i = 0; i < map.getEnemies().length; i++) {
			Enemy e = map.getEnemies()[i];
			if (e == null) {
				ArrayList<Integer>list = map.getEnemyInfo().get(i);
				list.set(6, (int) (list.get(6) - (currTime)/1000000));
				if (list.get(6) <= 0 && (Math.sqrt((player.x - list.get(0))*(player.x - list.get(0)) + (player.y - list.get(1))*(player.y - list.get(1))) > Math.sqrt((w.getWidth())*4*(w.getWidth()) + (w.getHeight())*4*(w.getHeight()) + 500))) {
					map.spawnEnemy(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), i);
					list.set(6,list.get(5));
				}
			} else {
				if(Math.sqrt((player.x - e.x)*(player.x - e.x) + (player.y - e.y)*(player.y - e.y)) < Math.sqrt((w.getWidth())*4*(w.getWidth()) + (w.getHeight())*4*(w.getHeight()) + 500)) {
					e.draw(this, camx, camy);
					e.act(map, currTime, player);
				}
			}
		}

		
		if(bossFight) {
			
			
			
			
			ArrayList<Drone> drones = boss.getDrones();
			for(int i = 0; i < drones.size();i++) {
				Drone drone = drones.get(i);
				drone.draw(this, camx, camy);
				
			}
			boss.act(map, currTime, player);
			boss.draw(this, camx, camy);
			
			ArrayList<ArrayList<Integer>>  lasers= boss.getLasers();
			for(int i = 0; i < lasers.size();i++) {
				ArrayList<Integer> list = lasers.get(i);
				noStroke();
				rect(list.get(0), list.get(1), list.get(2), list.get(3));
				if ((new Rectangle(list.get(0), list.get(1), list.get(2), list.get(3)).intersects(player.x, player.y, player.width, player.height))) {
					player.hit(15);
				}
				
				stroke(0);
			}
			
			fill(255);
			rect(100,25, 600, 25);
			fill(255, 0, 0);
			rect(100,25, (int)(600 * boss.gethp()/1000), 25);
		}
		
		player.draw(this, camx, camy);
		
		if (player.hp > 100){
			player.hp = 100;
		}
		text("" + player.hp, 50, 50);
		text("" + (int)player.stamina, 50, 100);
		text("" + (int)player.combo, 50, 150);
		
		popMatrix();

		
		// modifying stuff

		if (isPressed(w.left))
			player.walk(-1);
		if (isPressed(w.right))
			player.walk(1);
		if (isPressed(w.jump))
			player.jump();
		if (isPressed(w.dash))
			player.dash();
		if (isPressed(w.dive))
			player.dive();
		if (isPressed(w.light))
			player.lightAttack(map);
		if (isPressed(w.heavy))
			player.heavyAttack(map);
	
		

		player.act(map, currTime);
		
		if (player.hp <= 0) {
			player.x = map.getCheckpoints().get(player.lastCheck).getCenterX() -Player.PLAYER_WIDTH/2 ;
			player.y = map.getCheckpoints().get(player.lastCheck).getCenterY() -Player.PLAYER_HEIGHT/2;
			player.hp = 100;
			bossFight = false;
			boss = null;
			for (int i = 0; i < map.getEnemies().length; i++) {
				ArrayList<Integer>list = map.getEnemyInfo().get(i);
				map.spawnEnemy(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), i);
				list.set(6,list.get(5));
			}
		}
		currTime =  System.nanoTime()  - lastUpdate;
		lastUpdate = System.nanoTime();

	}

	/*
	 * key is pressed
	 */
	public void keyPressed() {
		keys.add(keyCode);
		if (key == 'p') {
			pause();
			pause();
		}
	}
	/*
	 * unpause the screen
	 */
	public void unpause() {
		lastUpdate = System.nanoTime();
		loop();
		
	}
	
	public void pause() {
		fill(237);
		rect(-1, -1, w.getWidth()+1, w.getHeight()+1);
		double scale = (double)1.0/11;
		for (Shape s : map.getObstacles()) {
			if (s instanceof Rectangle) {
				fill(150);
				Rectangle r = (Rectangle)s;
				rect((int)(r.x*scale),(int)(r.y * scale),(int)(r.width * scale),(int)(r.height* scale));
			}
		}
		
		for (Rectangle r : map.getLava()) {
			noStroke();
			fill(250, 0, 0);
			rect((int)(r.x*scale),(int)(r.y*scale),(int)(r.width*scale),(int)(r.height*scale));
		}
		
		boolean allCheck = true;
		for (Rectangle s : map.getCheckpoints()) {
			if (s instanceof Rectangle) {
				Rectangle r = (Rectangle)s;
				if (player.lastCheck == map.getCheckpoints().indexOf(s)) {
					checksPassed[map.getCheckpoints().indexOf(s)] = true;
					fill(250);
				} else if (checksPassed[map.getCheckpoints().indexOf(s)]){
					fill(200);
				} else {
					allCheck = false;
					fill(50);
				}
				rect((int)(r.x*scale),(int)(r.y * scale),(int)(r.width * scale),(int)(r.height* scale));
			}
		}
		
		fill(150, 50, 100);
		if (allCheck) {
			rect((int)(3150/11),(int)(2050/11),150/11,150/11);
		}
		
		
		
		
		fill(0, 255, 0);
		rect((int)(player.x*scale),(int)(player.y * scale),(int)(player.width * scale),(int)(player.height* scale));
		
		
		noLoop();
    	keys.clear();
    	w.changePanel(2);
	}
	/*
	 * key is realeased
	 */

	public void keyReleased() {
		while(keys.contains(keyCode))
			keys.remove(new Integer(keyCode));
	}

	/*
	 * button is pressed
	 */
	public boolean isPressed(Integer code) {
		return keys.contains(code);
	}
	
	public void mouseClicked(MouseEvent e) {
		boolean onCheck = false;
		for(int i = 0; i < map.getCheckpoints().size(); i++) {
			if (map.getCheckpoints().get(i).intersects(new Rectangle2D.Double((int)player.x, (int)player.y , player.PLAYER_WIDTH, player.PLAYER_HEIGHT))) {
				onCheck = true;
			}
		}
		if (onCheck) {
			bossFight = false;
			for(int i = 0; i < map.getCheckpoints().size(); i++) {
				if (checksPassed[i]) {
					if (new Rectangle((int)map.getCheckpoints().get(i).getX()/11 - 5, (int)map.getCheckpoints().get(i).getY()/11 - 5, (int)map.getCheckpoints().get(i).getWidth()/11 + 10, (int)map.getCheckpoints().get(i).getHeight()/11 + 10).contains(e.getX(), e.getY())) {
						player.x = map.getCheckpoints().get(i).getX();
						player.y = map.getCheckpoints().get(i).getY();
						for (int j = 0; j < map.getEnemies().length; j++) {
							ArrayList<Integer>list = map.getEnemyInfo().get(j);
							map.spawnEnemy(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), j);
							list.set(6,list.get(5));
						}
						w.changePanel(1);
						draw();
					}
				}
			}
		}
		
		
	}
}

