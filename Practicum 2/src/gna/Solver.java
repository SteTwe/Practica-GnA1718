package gna;

import java.beans.Statement;
import java.util.*;

import libpract.PriorityFunc;

public class Solver{

	PriorityQueue<BoardState> prioQ = null;

	PriorityFunc priorityFunc;

	int[][] _initial = null;
	private class BoardState{

		private BoardState previousState;
		private Board board;
		private int steps;

		public BoardState(){
			this.previousState = null;
			this.board = null;
			this.steps = 0;
		}

		public BoardState(BoardState state, int[][] board, int steps){
			this.previousState = state;
			this.board = new Board(board);
			this.steps = steps;
		}
	}

	public class hammingComparator implements Comparator<BoardState>{
		public hammingComparator(){
			super();
		}

		@Override
		public int compare(BoardState state1, BoardState state2) {
				return (state1.board.hamming() + state1.steps) - (state2.board.hamming() - state2.steps);

		}
	}


	public class manhattanComparator implements Comparator<BoardState>{
		public manhattanComparator(){
			super();
		}

		@Override
		public int compare(BoardState state1, BoardState state2) {
			return (state1.board.manhattan() + state1.steps) - (state2.board.manhattan() - state2.steps);

		}
	}

	public Board solvedBoard(){
		int [][] targetTiles = new int [this._initial.length][this._initial.length];
		int value = 1;
		for(int i=0; i<this._initial.length; i++)
			for(int j=0; j<this._initial.length; j++){
				targetTiles[i][j] = value;
				value++;
			}

		//laatste is de lege
		targetTiles[this._initial.length-1][this._initial.length-1] = 0;
		return new Board(targetTiles);
	}

	/**
	 * Finds a solution to the initial board.
	 *
	 * @param priority is either PriorityFunc.HAMMING or PriorityFunc.MANHATTAN
	 */
	public Solver(Board initial, PriorityFunc priority) {
		this.priorityFunc = priority;

		//if(!initial.isSolvable()) throw new IllegalArgumentException("Board is not solvable");
		// Use the given priority function (either PriorityFunc.HAMMING
		// or PriorityFunc.MANHATTAN) to solve the puzzle.
		if (priority == PriorityFunc.HAMMING) {
			hammingComparator hammingComp = new hammingComparator();
			prioQ = new PriorityQueue<BoardState>(hammingComp);
			_initial = initial.getTiles();
		} else if (priority == PriorityFunc.MANHATTAN) {
			manhattanComparator manhattanComp = new manhattanComparator();
			prioQ = new PriorityQueue<BoardState>(manhattanComp);
			_initial = initial.getTiles();
		} else {
			throw new IllegalArgumentException("Priority function not supported");
		}
	}
	

	/**
	 * Returns a List of board positions as the solution. It should contain the initial
	 * Board as well as the solution (if these are equal only one Board is returned).
	 */
	public List<Board> solution() {
		System.out.println("test");
		List<Board> solution = new ArrayList<Board>();

		prioQ.clear();

		Board target = solvedBoard();

		// add initial board to prioQ
		BoardState initialState = new BoardState(null, _initial, 0);
		prioQ.add(initialState);

		Collection<Board> neighbor = null;
		BoardState currentState = null;
		int count =0;
		while (true){
			count++;
			System.out.println(count);
			if (count >50) break;
			BoardState state = null;
			Board board = null;
			Board next = null;

			currentState = prioQ.remove();
			board = new Board(currentState.board.getTiles());
			if (board.equals(target)) break;

			neighbor = board.neighbors();

			Board[] array = new Board[neighbor.size()];
			neighbor.toArray(array);
			for ( int i = 0;i<array.length;i++) {
				next = array[i];
				// volgend gelijk aan vorige overslaan
				if (currentState.previousState != null){
					if (!next.equals(new Board(currentState.previousState.board.getTiles()))) {
						state = new BoardState(currentState, next.getTiles(), currentState.steps + 1 );
						prioQ.add(state);
					}
				}
				//de eerst buren toevoegen
				else {
					state = new BoardState(currentState, next.getTiles(), currentState.steps + 1 );
					prioQ.add(state);
				}

			}
		}


		if (currentState != null){
			BoardState b = currentState;

			solution.add(new Board(b.board.getTiles()));
			//teruglopen per bord

			while (b.previousState != null) {
				b= b.previousState;
				solution.add(new Board(b.board.getTiles()));
			}
		}

		Collections.reverse(solution);
		printQ(prioQ);
		return solution;
	}

	public void printQ(PriorityQueue<BoardState> Q){

		//create a new Q with the copy constructor
		PriorityQueue<BoardState> Qbs1 = new PriorityQueue<BoardState>(Q);
		BoardState bs=null;

		System.out.println("********************Priority Queue*****************************");
		while (!Qbs1.isEmpty()){
			bs = Qbs1.remove();
			Board b = new Board(bs.board.getTiles());
			printBoard (b, bs.steps,bs.board.hamming()+bs.steps,bs.board.manhattan()+bs.steps);
			//break;
		}
		System.out.println("*************************************************");

	}
	public void printBoard(Board b, int steps, int hamming, int manhattan){

		if (priorityFunc == PriorityFunc.HAMMING){
			System.out.println(b.toString()) ;

		}
		if (priorityFunc == PriorityFunc.MANHATTAN) {
			System.out.println(b.toString()) ;

		}
		System.out.println("");

	}
}


