package gna;

import java.util.*;

import libpract.PriorityFunc;

public class Solver {

	public BoardState solution = new BoardState();
	public ArrayList<Board> listOfSolutions;

	private class BoardState {
		private Board board;
		private int nbMoves;
		private BoardState prev;

		BoardState() {
			this.board = null;
			this.nbMoves = 0;
			this.prev = null;
		}

		public BoardState(BoardState state) {
			this.board = state.board;
			this.nbMoves = state.nbMoves;
			this.prev = state.prev;
		}
	}

	public class StateComparator implements Comparator<BoardState> {

		// initializes a StateComparator with given priority function
		StateComparator(PriorityFunc priority) {
			super();
			this.priority = priority;
		}

		private PriorityFunc priority;

		@Override
		public int compare(BoardState s1, BoardState s2) {
			if (priority == PriorityFunc.HAMMING)
				return (s1.board.hamming() + s1.nbMoves) - (s2.board.hamming() + s2.nbMoves);
			else if (priority == PriorityFunc.MANHATTAN)
				return (s1.board.manhattan() + s1.nbMoves) - (s2.board.manhattan() + s2.nbMoves);
			else
				throw new IllegalArgumentException("Priority function not supported");
		}
	}

	/**
	 * Finds a solution to the initial board.
	 *
	 * @param priority is either PriorityFunc.HAMMING or PriorityFunc.MANHATTAN
	 */
	public Solver(Board initial, PriorityFunc priority) {
		// initializig the variables to solve the problem
		boolean found = false;
		BoardState temp; // a variable to store the minimum at a given point
		BoardState state = new BoardState();
		state.board = initial;

		if (!initial.isSolvable())
			throw new IllegalArgumentException("Board is not solvable.");

		if (priority == PriorityFunc.HAMMING || priority == PriorityFunc.MANHATTAN) {
			Comparator<BoardState> comparator = new StateComparator(priority);
			PriorityQueue<BoardState> pq = new PriorityQueue<BoardState>(comparator);
			pq.add(state);
			while (!found) {
				temp = pq.poll();
				if (temp.board.equals(solvedBoard(state.board))) {
					solution = temp;
					found = true;
				}
				for (Board nb : temp.board.neighbors()) {
					if (temp.prev == null || !nb.equals(temp.prev.board)) {
						state = new BoardState();
						state.prev = temp;
						state.board = nb;
						state.nbMoves = temp.nbMoves + 1;
						pq.add(state);
					}
				}
			}
		} else {
			throw new IllegalArgumentException("Priority function not supported");
		}
	}

	/**
	 * Returns a List of board positions as the solution. It should contain the initial
	 * Board as well as the solution (if these are equal only one Board is returned).
	 */
	public List<Board> solution() {
		if (listOfSolutions != null) //if list already made, don't do again
			return listOfSolutions;
		listOfSolutions = new ArrayList<Board>();
		BoardState temp = solution;
		for (int i = 0; i < solution.nbMoves + 1; i++) {
			listOfSolutions.add(0, temp.board);
			temp = temp.prev;
		}
		return listOfSolutions;
	}

	public Board solvedBoard(Board board){
		int length = board.getTiles().length;
		int [][] targetTiles = new int [length][length];
		int value = 1;
		for(int i=0; i<length; i++)
			for(int j=0; j<length; j++){
				targetTiles[i][j] = value;
				value++;
			}

		//laatste is de lege
		targetTiles[length-1][length-1] = 0;
		return new Board(targetTiles);
	}
}