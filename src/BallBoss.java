import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import processing.core.PImage;
import jay.jaysound.JayLayer;
import jay.jaysound.JayLayerListener;
public class BallBoss extends Enemy implements JayLayerListener{

	private double xVelocity, yVelocity;
	private boolean onASurface;
	private double gravity;
	private double hp;
	private double speed;
	private double senseRadius = 300;
	private double antiMulti;
	private int index;
	private int phase = 1;
	private int shootDelay = 2000000;
	private int attackDelay = 2000000;
	private ArrayList<PImage> images;
	private JayLayer sound;

	private ArrayList<Drone> drones;
	private Drone[] mains ;
	
	public BallBoss(ArrayList<PImage> img, int x, int y, int w, int h, int index) {
		
		super(img, x, y, w, h, index);
		xVelocity = 0;
		yVelocity = 0;
		gravity = 0.5;
		hp = 1000;
		speed = 1.5;
		antiMulti = 5;
		images = img;
		this.index = index;
		String[] soundEffects = new String[]{"title1.mp3","title2.mp3","title3.mp3","title4.mp3","title5.mp3"};
		sound=new JayLayer("audio/","audio/",false);
		sound.addPlayList();
		sound.addSoundEffects(soundEffects);
		sound.changePlayList(0);
		sound.addJayLayerListener(this);
		// TODO Auto-generated constructor stub
	}

	public void act(Map map, long timeElapsed, Player p) {
		ArrayList<Shape> obstacles = map.getObstacles();
		
		if (phase == 0) {
			shootDelay -= timeElapsed/1000;
			antiMulti -= 1;
			
			if (shootDelay <= 0) {
				map.shoot(0, (int)x, (int)y, 10, 10, (p.x - x)/Math.sqrt((p.x - x)*(p.x - x) + (p.y - y)*(p.y - y))*5, (p.y - y)/Math.sqrt((p.x - x)*(p.x - x) + (p.y - y)*(p.y - y))*5, 400, 10);
				sound.playSoundEffect(0);

				shootDelay = 1500000;
			}
			
			
			double xCoord = getX();
			double yCoord = getY();
			double width = getWidth();
			double height = getHeight();
	
			// *********** Movement ***********
		
			
			//-------------------Physics---------------------
			
			
			
			// -------------------- collision checking ---------------------
			
			
			
			
			for(int i = 0; i < map.getHitboxes().size(); i++) {
				Hitbox list =  map.getHitboxes().get(i);
				if (new Ellipse2D.Double(x, y, width, height).intersects((int)list.x - 7,(int)list.y - 7,list.w + 15,list.h + 15)) {
					if ((antiMulti <= 0)) {
						antiMulti = 6;
						
						if(p.combo >= 30) {
							hp -= list.dam * 2;
							p.hp+= list.dam/5; 
						} else if (p.combo >= 20) {
							hp -= list.dam * 1.5;
						} else if (p.combo >= 10) {
							hp -= list.dam * 1.3;
						} else {
							hp -= list.dam;
						}
						
						p.combo++;
						if(p.getDive() == 1) {
							p.diveHop();
						}
						if(p.getDash() >= 0) {
							p.stamina += 60;
						}
					}
				}
			}
			
			
			if(hp <=0) {
				phase = 1;
			}
			
		
		} else if (phase <= 16){
			hp = 1500;
			super.setImage(images.get((int)phase/4));
			phase++;
		} else {
			
		}
		
	}
	
	public ArrayList<Drone> getDrones(){
		ArrayList<Drone> returner = new ArrayList<Drone>();
		returner.addAll(drones);
		returner.addAll(Arrays.asList(mains));
		return returner;
	}

	@Override
	public void musicStarted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void musicStopped() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playlistEnded() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void songEnded() {
		// TODO Auto-generated method stub
		
	}
	
	
}
