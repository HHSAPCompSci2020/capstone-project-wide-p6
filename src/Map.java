import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;

public class Map {

	private ArrayList<Shape> obstacles;
	
	public Map() {
		obstacles = new ArrayList<Shape>();
		obstacles.add(new Rectangle(200,400,400,50));
		obstacles.add(new Rectangle(0,250,100,50));
		obstacles.add(new Rectangle(700,250,100,50));
		obstacles.add(new Rectangle(375,300,50,100));
		obstacles.add(new Rectangle(300,250,200,50));
	}
	
	public ArrayList<Shape> getObstacles(){
		return obstacles;
	}

}
