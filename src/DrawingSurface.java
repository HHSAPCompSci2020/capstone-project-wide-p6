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

	public DrawingSurface(Main w) {
		super();
		keys = new ArrayList<Integer>();
		screenRect = new Rectangle(0,0,DRAWING_WIDTH,DRAWING_HEIGHT);
		map = new Map();
		checksPassed = new Boolean[map.getCheckpoints().size()];
		
		for (int i = 0; i < checksPassed.length; i++) {
			checksPassed[i] = false;
		}
		checksPassed[0] = true;
		player = new Player(new ArrayList<PImage>(Arrays.asList(loadImage("mario.png"), loadImage("marioflip.png"))), (int)(map.getCheckpoints().get(0).getCenterX() -Player.PLAYER_WIDTH/2) ,(int)(map.getCheckpoints().get(0).getCenterY() -Player.PLAYER_HEIGHT/2));
		camx = player.x;
		camy = player.y;
		lastUpdate = System.nanoTime();
		noLoop();
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
		camx = player.x - DRAWING_WIDTH/2 + player.width/2;
		if (player.y - DRAWING_HEIGHT/2 + player.height/2> camy + 100) {
			camy = player.y - DRAWING_HEIGHT/2 + player.height/2- 100;
		} else if (player.y - DRAWING_HEIGHT/2 + player.height/2 > camy - 100) {
			camy = camy;
		} else {
			camy = player.y - DRAWING_HEIGHT/2 + player.height/2 + 100;
		}
		
		
		

		for (Shape s : map.getObstacles()) {
			if (s instanceof Rectangle) {
				noStroke();
				fill(100);
				Rectangle r = (Rectangle)s;
				if(Math.sqrt((player.x - r.x)*(player.x - r.x) + (player.y - r.y)*(player.y - r.y)) < Math.sqrt((r.width)*(r.width) + (r.height)*(r.height)) +  Math.sqrt((w.getWidth())*(w.getWidth()) + (w.getHeight())*(w.getHeight())) + 1000) {
					rect((int)(r.x - camx),(int)(r.y - camy),r.width,r.height);
				}
				stroke(0);
			}
		}
		
		for (Rectangle r : map.getLava()) {
			noStroke();
			fill(250, 0, 0);
			if(Math.sqrt((player.x - r.x)*(player.x - r.x) + (player.y - r.y)*(player.y - r.y)) < Math.sqrt((r.width)*(r.width) + (r.height)*(r.height)) +  Math.sqrt((w.getWidth())*(w.getWidth()) + (w.getHeight())*(w.getHeight())) + 1000) {
				rect((int)(r.x - camx),(int)(r.y - camy),r.width,r.height);
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
				}
				if(Math.sqrt((player.x - r.x)*(player.x - r.x) + (player.y - r.y)*(player.y - r.y)) < Math.sqrt((r.width)*(r.width) + (r.height)*(r.height)) +  Math.sqrt((w.getWidth())*(w.getWidth()) + (w.getHeight())*(w.getHeight())) + 1000) {
					rect((int)(r.x - camx),(int)(r.y - camy),r.width,r.height);
				}
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
				if (list.get(6) <= 0 && (Math.sqrt((list.get(1)-player.x)*(list.get(1)-player.x) + (list.get(2)-player.y)*(list.get(2)-player.y)) >= 500)) {
					map.spawnEnemy(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), i);
					list.set(6,list.get(5));
				}
			} else {
				if(Math.sqrt((player.x - e.x)*(player.x - e.x) + (player.y - e.y)*(player.y - e.y)) < Math.sqrt((w.getWidth())*(w.getWidth()) + (w.getHeight())*(w.getHeight()) + 500)) {
					e.draw(this, camx, camy);
					e.act(map, currTime, player);
				}
			}
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
		loop();
		lastUpdate = System.nanoTime();
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
				}
				rect((int)(r.x*scale),(int)(r.y * scale),(int)(r.width * scale),(int)(r.height* scale));
			}
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

