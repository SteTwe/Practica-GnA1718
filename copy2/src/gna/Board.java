package gna;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Arrays;
import java.util.HashSet;

public class Board
{
	private int[][] tiles;
	private final int N;

	public int[][] getTiles(){
		return this.tiles;
	}

	// construct a board from an N-by-N array of tiles
	public Board( int[][] tiles ) {
		int[][] copy = new int[tiles[0].length][tiles[1].length];
		for (int i = 0; i < tiles[0].length; i++){
			for (int j = 0; j < tiles[1].length; j++)
				copy[i][j] = tiles[i][j];
		}
		this.tiles = copy;
		this.N = tiles.length;
	}
	
	// return number of blocks out of place
	public int hamming() {
		int pos = 0, res = 0;
		for (int row = 0; row < N; row++) {
			for (int column = 0; column < N; column++) {
				pos += 1;
				if (tiles[row][column] != pos && tiles[row][column] !=0)
					res += 1;
			}
		}
		return res;
	}
	
	// return sum of Manhattan distances between blocks and goal
	public int manhattan() {
		int res = 0;
		for (int row = 0; row < N; row++) {
			for (int col = 0; col < N; col++) {
				// calculates the the right position of this tile
				if (tiles[row][col] != 0) {
					int defRow = defaultRow(tiles[row][col]), defCol = defaultCol(tiles[row][col]);
					res += Math.abs(defRow - row) + Math.abs(defCol - col);
				}
			}
		}
		return res;
	}

	private int defaultRow(int pos) {
		return ((int) Math.ceil((double) pos / (double) N) - 1);
	}

	private int defaultCol(int pos) {
		int col = pos % N;
		if (col == 0)
			return N - 1;
		return col - 1;
	}
	
	// Does this board equal y. Two boards are equal when they both were constructed
	// using tiles[][] arrays that contained the same values.
	@Override
	public boolean equals(Object y)	{
		if ( !(y instanceof Board) )
			return false;

		Board other = (Board)y;
		return Arrays.deepEquals(tiles, other.tiles);
	}

	// Since we override equals(), we must also override hashCode(). When two objects are
	// equal according to equals() they must return the same hashCode. More info:
	// - http://stackoverflow.com/questions/27581/what-issues-should-be-considered-when-overriding-equals-and-hashcode-in-java/27609#27609
	// - http://www.ibm.com/developerworks/library/j-jtp05273/
    @Override
    public int hashCode() {
		return Arrays.deepHashCode(tiles);
	}
	
	// return a Collection of all neighboring board positions
	public Collection<Board> neighbors() {
		String[] directions = new String[] { "left", "right", "up", "down" };
		ArrayList<Board> neighbors = new ArrayList<Board>();
		for (String direction : directions) {
			try {
				neighbors.add(new Board(moveZero(tiles, direction)));
			} catch (IndexOutOfBoundsException e) {
				continue;
			}
		}
		return neighbors;
	}

	public boolean isSolution(){
		int counter = 1;
		for (int i = 0; i < N; i++){
			for (int j = 0; j < N; j++){
				if (counter != N*N && tiles[i][j] != counter++)
					return false;
			}
		}
		return true;
	}

	private Integer[] linearize(int[][] tiles) {
		Integer[] linearised = new Integer[N * N];
		int counter = 0;
		for (int[] row : tiles) {
			for (int element : row) {
				linearised[counter++] = element;
			}
		}
		return linearised;
	}

	private int[][] moveZero(int[][] tiles, String direction) throws IllegalArgumentException {
		int[][] newTiles = deepCopy(tiles);
		int[] zeroPos = getTilePos(newTiles, 0);
		direction = direction.toLowerCase();
		if (direction == "left") {
			newTiles[zeroPos[0]][zeroPos[1]] = newTiles[zeroPos[0]][zeroPos[1] - 1];
			newTiles[zeroPos[0]][zeroPos[1] - 1] = 0;
		} else if (direction == "right") {
			newTiles[zeroPos[0]][zeroPos[1]] = newTiles[zeroPos[0]][zeroPos[1] + 1];
			newTiles[zeroPos[0]][zeroPos[1] + 1] = 0;
		} else if (direction == "up") {
			newTiles[zeroPos[0]][zeroPos[1]] = newTiles[zeroPos[0] - 1][zeroPos[1]];
			newTiles[zeroPos[0] - 1][zeroPos[1]] = 0;
		} else if (direction == "down") {
			newTiles[zeroPos[0]][zeroPos[1]] = newTiles[zeroPos[0] + 1][zeroPos[1]];
			newTiles[zeroPos[0] + 1][zeroPos[1]] = 0;
		} else {
			throw new IllegalArgumentException("allowed moves are: left, right, up, down");
		}
		return newTiles;
	}

	private int[] getTilePos(int[][] tiles, int value) {
		// X-Y coordinates
		int res[] = new int[] { -1, -1 };
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (tiles[i][j] == value) {
					res[0] = i; res[1] = j;
				}
			}
		}
		return res;
	}


	public static int[][] deepCopy(int[][] a) {
		int[][] b = new int[a.length][a[0].length];
		for (int row = 0; row < a.length; row++) {
			for (int column = 0; column < a[row].length; column++) {
				b[row][column] = a[row][column];
			}
		}
		return b;
	}
	// return a string representation of the board
	public String toString() {
		String b = new String();
		for(int i=0; i<tiles.length; i++) {
			String a = new String();
			for(int j=0; j<tiles[i].length; j++) {
				a += tiles[i][j];
				a += " ";
				if (tiles[i][j] < 10) a += " "; 
			}
			b += a + '\n';
		}
		return b;
	}

	// is the initial board solvable? Note that the empty tile must
	// first be moved to its correct position.
	public boolean isSolvable() {
		Board temp = new Board(this.tiles);

		//locate empty space and move to correct position
		int[] location = temp.emptySpaceLocation();

		//correct location in NxN board == [N-1][N-1]
		//move empty space to correct i position
		for (int i = location[1]; i < temp.tiles[0].length-1; i++){
			//TODO check of return noodzakelijk is
			temp = move(temp, location, new int[] {location[0], location[1] + 1});
			location[1] += 1;
		}

		//move empty space to correct j position
		for (int j = location[0]; j < temp.tiles[1].length-1; j++){
			//TODO check of return noodzakelijk is
			temp = move(temp, location, new int[] {location[0] + 1, location[1]});
			location[0] +=1;
		}

		System.out.println(temp.toString());
	
		//calculate formula
		double numerator;
		double denominator;
		double result = 1;
		double elements = Math.pow(this.tiles[0].length,2) - 1;
		for (int j = 1; j <= elements; j++){
			for (int i = 1; i < j; i++){
				numerator = p(temp, j) - p(temp, i);
				denominator = j - i;
				result = result * numerator/denominator;
			}
		}

		//result must be smaller or equal to 0
		return (result >= 0);
	}

	private Board move(Board board, int[] location1, int[] location2){
		Board newBoard = new Board(board.tiles);
		newBoard.tiles[location1[0]][location1[1]] = board.tiles[location2[0]][location2[1]];
		newBoard.tiles[location2[0]][location2[1]] = board.tiles[location1[0]][location1[1]];

		return newBoard;
	}

	private int p (Board board, int value) throws IllegalArgumentException{
		for (int i = 0; i < board.tiles[0].length; i++){
			for (int j = 0; j < board.tiles[0].length; j++){
				if (board.tiles[i][j] == value) return i * board.tiles[0].length + j +1;
			}
		}
		throw new IllegalArgumentException("Given value was not found");
	}

	private int[] emptySpaceLocation(){
		int locationI = 0;
		int locationJ = 0;
		for (int i = 0; i < this.tiles[0].length; i++){
			for (int j = 0; j < this.tiles[1].length; j++){
				if (tiles[i][j] == 0){
					locationI = i;
					locationJ = j;
				}
			}
		}
		return new int[] {locationI, locationJ};
	}
}

