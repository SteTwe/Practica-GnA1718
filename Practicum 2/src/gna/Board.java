package gna;

import java.util.Collection;
import java.util.Arrays;
import java.util.HashSet;

public class Board
{
	private int[][] tiles;

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
	}
	
	// return number of blocks out of place
	public int hamming() {
		int expected = 1;
		int result = 0;
		//TODO check of i en j loops moeten omgewisseld
		for (int i = 0; i < this.tiles.length; i++){
			for (int j = 0; j < this.tiles[i].length; j++) {
				if (tiles[i][j] != expected && tiles[i][j] != 0) result++;
				expected++;
			}
		}
		return result;
	}
	
	// return sum of Manhattan distances between blocks and goal
	public int manhattan() {
		int count = 0;
		int expected = 1;
		for (int i = 0; i<tiles[0].length; i++)
			for (int j=0; j<tiles[0].length; j++){
				if(tiles[i][j] != expected && tiles[i][j] != 0)
				{
						//expi expj geven de verwacht i en j voor het cijfer dat er staat
						int expi = Math.floorDiv(tiles[i][j] -1, tiles[0].length );
						int expj = Math.floorMod(tiles[i][j] -1, tiles[0].length );
						int dist = Math.abs(expi - i) + Math.abs(expj - j);
						count = count + dist;
				}
				expected++;
			}
		return count;


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
		Collection<Board> neighbors = new HashSet<Board>();

		int[] location = this.emptySpaceLocation();
		// 4 possible neighbors: empty location to above, below, left or right

		if(0 < location[0]) neighbors.add(move(this, new int[]{location[0], location[1]}, new int[]{location[0] - 1, location[1]}));
		if(0 < location[1]) neighbors.add(move(this, new int[]{location[0], location[1]}, new int[]{location[0], location[1] - 1}));

		if(location[0]< tiles[0].length -1) neighbors.add(move(this, new int[]{location[0], location[1]}, new int[]{location[0] + 1, location[1]}));
		if(location[1]< tiles[0].length -1) neighbors.add(move(this, new int[]{location[0], location[1]}, new int[]{location[0], location[1] + 1}));
		return neighbors;
	}
	
	// return a string representation of the board
	public String toString() {
		String b = new String();
		for(int i=0; i<tiles.length; i++) {
			String a = new String();
			for(int j=0; j<tiles[i].length; j++) {
				a += tiles[i][j];
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
		for (int i = location[0]; i < temp.tiles[0].length-1; i++){
			//TODO check of return noodzakelijk is
			temp = move(temp, location, new int[] {location[0], location[1] + 1});
			location[1] += 1;
		}

		//move empty space to correct j position
		for (int j = location[1]; j < temp.tiles[1].length-1; j++){
			//TODO check of return noodzakelijk is
			temp = move(temp, location, new int[] {location[0] + 1, location[1]});
			location[0] +=1;
		}

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
		return (result <= 0);
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

