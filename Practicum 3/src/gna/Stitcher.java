package gna;

import java.net.PortUnreachableException;
import java.util.*;

import libpract.*;

/**
 * Implement the methods stitch, seam and floodfill.
 */
public class Stitcher
{

	private Comparator<Position> comparator= new Comparator<Position>(){
		@Override
		public int compare(Position pos1, Position pos2){
			if (distance[pos1.getX()][pos1.getY()] < distance[pos2.getX()][pos2.getY()])
				return -1;
			else if (distance[pos1.getX()][pos1.getY()] > distance[pos2.getX()][pos2.getY()])
				return 1;
			return 0;
		}
	};
	private PriorityQueue<Position> prioQ = new PriorityQueue<Position>(comparator);
	private double distance[][];
	private int dimensionX,dimensionY;
	private Position[][] previous;

	public void setDimensions(int x, int y) {
		this.dimensionX = x;
		this.dimensionY = y;
		}

	/**
	 * Prepare for dijkstra algorithm: set node distance to infinity
	 */
	private void dijkstraPrepare(){
		distance = new double[dimensionX][dimensionY];
		previous = new Position[dimensionX][dimensionY];
		for (int i =0; i < dimensionX; i++){
			for (int j = 0; j < dimensionY; j++){
				distance[i][j] = Double.POSITIVE_INFINITY;
			}
		}
		distance[0][0] = 0;
	}

	/**
	 * Return the sequence of positions on the seam. The first position in the
	 * sequence is (0, 0) and the last is (width - 1, height - 1). Each position
	 * on the seam must be adjacent to its predecessor and successor (if any).
	 * Positions that are diagonally adjacent are considered adjacent.
	 * 
	 * image1 and image2 are both non-null and have equal dimensions.
	 *
	 * Remark: Here we use the default computer graphics coordinate system,
	 *   illustrated in the following image:
	 * 
	 *        +-------------> X
	 *        |  +---+---+
	 *        |  | A | B |
	 *        |  +---+---+
	 *        |  | C | D |
	 *        |  +---+---+
	 *      Y v 
	 * 
	 *   The historical reasons behind using this layout is explained on the following
	 *   website: http://programarcadegames.com/index.php?chapter=introduction_to_graphics
	 * 
	 *   Position (y, x) corresponds to the pixels image1[y][x] and image2[y][x]. This
	 *   convention also means that, when an automated test mentioned that it used the array
	 *   {{A,B},{C,D}} as a test image, this corresponds to the image layout as shown in
	 *   the illustration above.
	 */
	public List<Position> seam(int[][] image1, int[][] image2) {
		setDimensions(image1.length, image1[0].length);
		 Position wanted = new Position(dimensionX - 1, dimensionY - 1);
		 prioQ.clear();
		 dijkstraPrepare();
		 prioQ.add(new Position(0,0));
		 while (!prioQ.isEmpty()){
		 	Position pos = prioQ.poll();
		 	if (pos == wanted) break;
		 	for (Position pos2 : neighbors(pos)){
		 		try {
		 			double c = distance[pos.getX()][pos.getY()] + ImageCompositor.pixelSqDistance(image1[pos2.getX()][pos2.getY()], image2[pos2.getX()][pos2.getY()]);
					if (c < distance[pos2.getX()][pos2.getY()]){
						distance[pos2.getX()][pos2.getY()] = c;
						previous[pos2.getX()][pos2.getY()] = pos;
						if (prioQ.contains(pos2)){
							prioQ.remove(pos2);
						}
						prioQ.add(pos2);
					}
				}
				catch (ArrayIndexOutOfBoundsException except){
		 			continue;
				}
			}
		 }
		 return dijkstra(wanted);
	}

	private List<Position> neighbors(Position p){
		List<Position> neighbors = new ArrayList<Position>();
		neighbors.add(new Position(p.getX() + 1,p.getY()));
		neighbors.add(new Position(p.getX() - 1,p.getY()));
		neighbors.add(new Position(p.getX(),p.getY() + 1));
		neighbors.add(new Position(p.getX(),p.getY() - 1));
		neighbors.add(new Position(p.getX() + 1,p.getY() + 1));
		neighbors.add(new Position(p.getX() + 1,p.getY() - 1));
		neighbors.add(new Position(p.getX() - 1,p.getY() + 1));
		neighbors.add(new Position(p.getX() - 1,p.getY() - 1));
		return neighbors;
	}

	public List<Position> dijkstra(Position target){
		ArrayList<Position> solution = new ArrayList<Position>();
		Position pos = new Position(target.getX(),target.getY());
		solution.add(0,pos);
		while (previous[pos.getX()][pos.getY()] != null){
			solution.add(0, previous[pos.getX()][pos.getY()]);
			pos = previous[pos.getX()][pos.getY()];
		}
		return solution;
	}

	/**
	 * Apply the floodfill algorithm described in the assignment to mask. You can assume the mask
	 * contains a seam from the upper left corner to the bottom right corner. The seam is represented
	 * using Stitch.SEAM and all other positions contain the default value Stitch.EMPTY. So your
	 * algorithm must replace all Stitch.EMPTY values with either Stitch.IMAGE1 or Stitch.IMAGE2.
	 *
	 * Positions left to the seam should contain Stitch.IMAGE1, and those right to the seam
	 * should contain Stitch.IMAGE2. You can run `ant test` for a basic (but not complete) test
	 * to check whether your implementation does this properly.
	 */
	public void floodfill(Stitch[][] mask) {
		flood(mask, new Position(mask.length - 1, 0), Stitch.IMAGE1);
		flood(mask, new Position(0, mask[0].length - 1), Stitch.IMAGE2);
		}

	private void flood(Stitch[][] mask, Position pos, Stitch image){
		Stack<Position> stack = new Stack<Position>();
		Stitch checker;
		stack.clear();
		stack.push(new Position(pos.getX(),pos.getY()));
		while (!stack.isEmpty()){
			pos = stack.pop();
			if (mask[pos.getX()][pos.getY()] != Stitch.EMPTY)
				continue;
			mask[pos.getX()][pos.getY()] = image;
			for (Position n: nb(pos)){
				try {
					checker = mask[n.getX()][n.getY()];
					stack.push(n);
				} catch (ArrayIndexOutOfBoundsException e){}
			}
		}
	}

	private List<Position> nb(Position pos){
		List<Position> neighbors = new ArrayList<Position>();
		neighbors.add(new Position(pos.getX() + 1,pos.getY()));
		neighbors.add(new Position(pos.getX() - 1,pos.getY()));
		neighbors.add(new Position(pos.getX(),pos.getY() + 1));
		neighbors.add(new Position(pos.getX(),pos.getY() - 1));
		return neighbors;
	}




	/**
	 * Return the mask to stitch two images together. The seam runs from the upper
	 * left to the lower right corner, where in general the rightmost part comes from
	 * the second image (but remember that the seam can be complex, see the spiral example
	 * in the assignment). A pixel in the mask is Stitch.IMAGE1 on the places where
	 * image1 should be used, and Stitch.IMAGE2 where image2 should be used. On the seam
	 * record a value of Stitch.SEAM.
	 * 
	 * ImageCompositor will only call this method (not seam and floodfill) to
	 * stitch two images.
	 * 
	 * image1 and image2 are both non-null and have equal dimensions.
	 */
	public Stitch[][] stitch(int[][] image1, int[][] image2) {
		setDimensions(image1.length, image1[0].length);
		Stitch[][] result = new Stitch[dimensionX][dimensionY];
		for (int i = 0; i < dimensionX; i++)	// Create an empty stitch array.
			for (int j = 0; j < dimensionY; j++)
				result[i][j] = Stitch.EMPTY;

		for (Position seamPosition: seam(image1, image2)) // Create the seam.
			result[seamPosition.getX()][seamPosition.getY()] = Stitch.SEAM;

		floodfill(result);	// Floodfill the stitch array.
		return result;
	}
}


