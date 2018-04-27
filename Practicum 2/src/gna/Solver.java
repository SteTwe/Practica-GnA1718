package gna;

import java.beans.Statement;
import java.util.*;

import libpract.PriorityFunc;

public class Solver{

	PriorityQueue<BoardState> prioQ = null;

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
			this.previousState = state.previousState;
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
		if(!initial.isSolvable()) throw new IllegalArgumentException("Board is not solvable");
		// Use the given priority function (either PriorityFunc.HAMMING
		// or PriorityFunc.MANHATTAN) to solve the puzzle.
		if (priority == PriorityFunc.HAMMING) {
			hammingComparator hammingComp = new hammingComparator();
			prioQ = new PriorityQueue<BoardState>(hammingComp);
		} else if (priority == PriorityFunc.MANHATTAN) {
			manhattanComparator manhattanComp = new manhattanComparator();
			prioQ = new PriorityQueue<BoardState>(manhattanComp);
		} else {
			throw new IllegalArgumentException("Priority function not supported");
		}
	}
	

	/**
	 * Returns a List of board positions as the solution. It should contain the initial
	 * Board as well as the solution (if these are equal only one Board is returned).
	 */
	public List<Board> solution() {
		List<Board> solution = new ArrayList<Board>();

		prioQ.clear();

		Board target = solvedBoard();

		// add initial board to prioQ
		BoardState initialState = new BoardState(null, _initial, 0);
		prioQ.add(initialState);

		Collection<Board> neighbor = null;
		BoardState currentState = null;

		while (true){
			BoardState state = null;
			Board board = null;
			Board next = null;

			currentState = prioQ.remove();
			board = new Board(currentState.board.getTiles());
			if (board.equals(target)) break;
			
		}


		return solution;
	}
}


