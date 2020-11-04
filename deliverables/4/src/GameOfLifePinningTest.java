import org.junit.*;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GameOfLifePinningTest {
	/*
	 * READ ME: You may need to write pinning tests for methods from multiple
	 * classes, if you decide to refactor methods from multiple classes.
	 * 
	 * In general, a pinning test doesn't necessarily have to be a unit test; it can
	 * be an end-to-end test that spans multiple classes that you slap on quickly
	 * for the purposes of refactoring. The end-to-end pinning test is gradually
	 * refined into more high quality unit tests. Sometimes this is necessary
	 * because writing unit tests itself requires refactoring to make the code more
	 * testable (e.g. dependency injection), and you need a temporary end-to-end
	 * pinning test to protect the code base meanwhile.
	 * 
	 * For this deliverable, there is no reason you cannot write unit tests for
	 * pinning tests as the dependency injection(s) has already been done for you.
	 * You are required to localize each pinning unit test within the tested class
	 * as we did for Deliverable 2 (meaning it should not exercise any code from
	 * external classes). You will have to use Mockito mock objects to achieve this.
	 * 
	 * Also, you may have to use behavior verification instead of state verification
	 * to test some methods because the state change happens within a mocked
	 * external object. Remember that you can use behavior verification only on
	 * mocked objects (technically, you can use Mockito.verify on real objects too
	 * using something called a Spy, but you wouldn't need to go to that length for
	 * this deliverable).
	 */

	/* TODO: Declare all variables required for the test fixture. */
	MainPanel panel;
	Cell[][] table;
	Cell cell1;
	Cell cell2;
	Cell cell3;
	@Before
	public void setUp() {
		/*
		 * TODO: initialize the text fixture. For the initial pattern, use the "blinker"
		 * pattern shown in:
		 * https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life#Examples_of_patterns
		 * The actual pattern GIF is at:
		 * https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life#/media/File:Game_of_life_blinker.gif
		 * Start from the vertical bar on a 5X5 matrix as shown in the GIF.
		 */
		table = new Cell[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				table[i][j] = Mockito.mock(Cell.class);
			}
		}
		cell1 = Mockito.mock(Cell.class);
		Mockito.when(cell1.getAlive()).thenReturn(true);
		cell2 = Mockito.mock(Cell.class);
		Mockito.when(cell2.getAlive()).thenReturn(true);
		cell3 = Mockito.mock(Cell.class);
		Mockito.when(cell3.getAlive()).thenReturn(true);
		table[1][2] = cell1;
		table[2][2] = cell2;
		table[3][2] = cell3;
		panel = new MainPanel(5);
		panel.setCells(table);
	}
	/* TODO: Write the three pinning unit tests for the three optimized methods */
	@Test
	public void testCellToString() {
		Cell newCell = new Cell();
		newCell.setAlive(false);
		assertEquals(".", newCell.toString());
		newCell.setAlive(true);
		assertEquals("X", newCell.toString());
	}
	
	@Test
	public void testIterateCell() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				boolean truthValue = panel.iterateCell(i, j);
				if (i==2 && (j==1 || j==2 || j==3)) {
					assertTrue(truthValue);
				} else  {
					assertFalse(truthValue);
				}
			}
		}
	}
	
	@Test
	public void testCalculateNextIteration() {
		panel.calculateNextIteration();
		Cell[][] newTable = panel.getCells();
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (i==2 && (j==1 || j==2 || j==3)) {
					Mockito.verify(newTable[i][j], times(1)).setAlive(true);
				} else  {
					Mockito.verify(newTable[i][j], times(1)).setAlive(false);
				}
			}
		}
	}
}
