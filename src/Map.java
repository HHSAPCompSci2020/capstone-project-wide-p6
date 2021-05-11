/** Author: Ethan Wang
 * The map class is meant to be a huge database of all the enemies and obstacles across the map
 */

import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;

public class Map {

	private ArrayList<Shape> obstacles;
	private ArrayList<ArrayList<Integer>> hitboxes; //only really applies to player, enemy projectiles go into enemies category.
	private ArrayList<Enemy> enemies;
	private ArrayList<ArrayList<Integer>> enemyInfo;
	
	/** Map constructor. No parameters because the map will be the same every time. Sets up the entire map.
	 */
	public Map() {
		obstacles = new ArrayList<Shape>();
		hitboxes = new ArrayList<ArrayList<Integer>>();
		obstacles.add(new Rectangle(200,400,400,50));
		obstacles.add(new Rectangle(0,250,100,50));
		obstacles.add(new Rectangle(700,250,100,50));
		obstacles.add(new Rectangle(375,300,50,100));
		obstacles.add(new Rectangle(300,250,200,50));
	}
	
	
	/**
	 * @return returns an arraylists of all obstacles on the map.
	 */
	public ArrayList<Shape> getObstacles(){
		return obstacles;
	}
	public ArrayList<ArrayList<Integer>> getHitboxes(){
		return hitboxes;
	}
	public void addHitbox(ArrayList<Integer> hitbox){
		hitboxes.add(hitbox);
	}
	public ArrayList<Enemy> getEnemies(){
		return enemies;
	}
	public ArrayList<ArrayList<Integer>> getEnemyInfo(){
		return enemyInfo;
	}

}
