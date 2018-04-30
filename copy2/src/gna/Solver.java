package gna;

import java.beans.Statement;
import java.util.*;

import libpract.PriorityFunc;

public class Solver {

	public State solution = new State();
	public ArrayList<Board> trace;

	private class State {
		private Board board;
		private int nbMoves;
		private State prev;

		public State() {
			this.board = null;
			this.nbMoves = 0;
			this.prev = null;
		}

		public State(State state) {
			this.board = state.board;
			this.nbMoves = state.nbMoves;
			this.prev = state.prev;
		}
	}

	public class StateComparator implements Comparator<State> {

		// initializes a StateComparator with given priorityfunction
		public StateComparator(PriorityFunc priority) {
			super();
			this.priority = priority;
		}

		private PriorityFunc priority;

		@Override
		public int compare(State s1, State s2) {
			if (priority == PriorityFunc.HAMMING)
				return (s1.board.hamming() + s1.nbMoves) - (s2.board.hamming() + s2.nbMoves);
			else if (priority == PriorityFunc.MANHATTAN)
				return (s1.board.manhattan() + s1.nbMoves) - (s2.board.manhattan() + s2.nbMoves);
			else
				throw new IllegalArgumentException("No implementation for this priorityfunction");
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
		State tmp; // a variable to store the minimum at a given point
		State state = new State();
		state.board = initial;

		if (!initial.isSolvable())
			throw new IllegalArgumentException("Board is not solvable.");

		if (priority == PriorityFunc.HAMMING || priority == PriorityFunc.MANHATTAN) {
			Comparator<State> comparator = new StateComparator(priority);
			PriorityQueue<State> pq = new PriorityQueue<State>(comparator);
			pq.add(state);
			while (!found) {
				tmp = pq.poll();
				if (tmp.board.isSolution()) {
					solution = tmp;
					found = true;
					break;
				}
				for (Board neighbor : tmp.board.neighbors()) {
					if (tmp.prev == null || !neighbor.equals(tmp.prev.board)) {
						state = new State();
						state.prev = tmp;
						state.board = neighbor;
						state.nbMoves = tmp.nbMoves + 1;
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
		if (trace != null) // don't build the solution trace if its already there
			return trace;
		trace = new ArrayList<Board>();
		State tmp = solution;
		for (int i = 0; i < solution.nbMoves + 1; i++) {
			trace.add(0, tmp.board);
			tmp = tmp.prev;
		}
		return trace;
	}
}