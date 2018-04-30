package gna;

import java.util.Collection;
import java.util.List;
import libpract.PriorityFunc;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * A number of JUnit tests for Solver.
 *
 * Feel free to modify these to automatically test puzzles or other functionality
 */
public class UnitTests {

  int[][] tiles1 = new int[][]{{0,1,3},{4,2,5},{7,8,6}};

  @Test
  public void test() {
    Board board = new Board(tiles1);
    Solver solver = new Solver(board, PriorityFunc.HAMMING);
    System.out.println(board.toString());
    //System.out.println(solver.solvedBoard(board).toString());
  }

  @Test
  public void test22(){
    int[][] puzzle22 = new int[][] {{1,2,3,4,5},{12,6,8,9,10},{0,7,13,19,14},{11,16,17,18,15},{21,22,23,24,20}};
    Board board = new Board(puzzle22);
    Solver solver = new Solver(board, PriorityFunc.HAMMING);
    for (Board b : solver.solution())
      System.out.println(b);
  }
}
