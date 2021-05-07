

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
		player = new Player(new ArrayList<PImage>(Arrays.asList(loadImage("mario.png"), loadImage("marioflip.png"))), DRAWING_WIDTH/2-Player.PLAYER_WIDTH/2,50);
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
		camx = player.x - DRAWING_WIDTH/2 + player.width/2;
		if (player.y - DRAWING_HEIGHT/2 + player.height/2> camy + 100) {
			camy = player.y - DRAWING_HEIGHT/2 + player.height/2- 100;
		} else if (player.y - DRAWING_HEIGHT/2 + player.height/2 > camy - 100) {
			camy = camy;
		} else {
			camy = player.y - DRAWING_HEIGHT/2 + player.height/2 + 100;
		}
		
		
		
		
		player.draw(this, camx, camy);
		
		fill(100);

		for (Shape s : map.getObstacles()) {
			if (s instanceof Rectangle) {
				Rectangle r = (Rectangle)s;
				rect((int)(r.x - camx),(int)(r.y - camy),r.width,r.height);
			}
		}


		

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

		player.act(map.getObstacles(), System.nanoTime() - lastUpdate);
		
		if (player.y >= 800) {
			player.x =DRAWING_WIDTH/2-Player.PLAYER_WIDTH/2 ;
			player.y = 50;
		}
		lastUpdate = System.nanoTime();

	}


	public void keyPressed() {
		keys.add(keyCode);
	}

	public void keyReleased() {
		while(keys.contains(keyCode))
			keys.remove(new Integer(keyCode));
	}

	public boolean isPressed(Integer code) {
		return keys.contains(code);
	}


}

