/** Author: Shelby, Ethan Wang
 * The class used for essentially all the graphics of the game.
 */

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import processing.core.PImage;

import processing.core.PApplet;

public class DrawingSurface extends PApplet {

	public static final int DRAWING_WIDTH = 800;
	public static final int DRAWING_HEIGHT = 600;

	private Rectangle screenRect;
	private Map map;
	
	private double camx, camy;
	private long lastUpdate;
	private Main w;
	
	private Player player;
	
	private ArrayList<Integer> keys;

	public DrawingSurface(Main w) {
		super();
		keys = new ArrayList<Integer>();
		screenRect = new Rectangle(0,0,DRAWING_WIDTH,DRAWING_HEIGHT);
		map = new Map();
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
		
		
		
		
		
		
		fill(100);

		for (Shape s : map.getObstacles()) {
			if (s instanceof Rectangle) {
				Rectangle r = (Rectangle)s;
				rect((int)(r.x - camx),(int)(r.y - camy),r.width,r.height);
			}
		}
		
		
		for (Rectangle s : map.getCheckpoints()) {
			if (s instanceof Rectangle) {
				Rectangle r = (Rectangle)s;
				if (player.lastCheck == map.getCheckpoints().indexOf(s)) {
					fill(250);
				} else {
					fill(50);
				}
				rect((int)(r.x - camx),(int)(r.y - camy),r.width,r.height);
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
				list.set(6, (int) (list.get(6) - (System.nanoTime() - lastUpdate)/100000));
				if (list.get(6) <= 0) {
					map.spawnEnemy(0, list.get(1), list.get(2), list.get(3), list.get(4), i);
					list.set(6,list.get(5));
				}
			} else {
				e.draw(this, camx, camy);
				e.act(map, System.nanoTime() - lastUpdate, player);
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

		if (isPressed(KeyEvent.VK_A))
			player.walk(-1);
		if (isPressed(KeyEvent.VK_D))
			player.walk(1);
		if (isPressed(KeyEvent.VK_W))
			player.jump();
		if (isPressed(KeyEvent.VK_SPACE))
			player.dash();
		if (isPressed(KeyEvent.VK_S))
			player.dive();
		if (isPressed(KeyEvent.VK_Q))
			player.lightAttack(map);
		if (isPressed(KeyEvent.VK_E))
			player.heavyAttack(map);
		

		player.act(map, System.nanoTime() - lastUpdate);
		
		if (player.y >= 800 || player.hp <= 0) {
			player.x = map.getCheckpoints().get(player.lastCheck).getCenterX() -Player.PLAYER_WIDTH/2 ;
			player.y = map.getCheckpoints().get(player.lastCheck).getCenterY() -Player.PLAYER_HEIGHT/2;
			player.hp = 100;
		}
		lastUpdate = System.nanoTime();

	}

	/*
	 * key is pressed
	 */
	public void keyPressed() {
		keys.add(keyCode);
		if (key == 'p') {
			noLoop();
	    	keys.clear();
	    	w.changePanel(2);
		}
	}
	/*
	 * unpause the screen
	 */
	public void unpause() {
		loop();
		lastUpdate = System.nanoTime();
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

	

}

