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

  int[][] tiles1 = new int[][]{{7,5,3},{4,2,6},{1,8,0}};

  @Test
  public void test() {
    Board board = new Board(tiles1);
    System.out.println(board.toString());
  }
}
