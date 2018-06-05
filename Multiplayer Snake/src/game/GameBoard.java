package game;
import java.util.concurrent.ConcurrentHashMap;

public class GameBoard {
	
	private ConcurrentHashMap<Integer, Cell> cells;
	private Integer appleIndex;

	private int rows;
	private int cols;

	public GameBoard(int rows, int cols) {
		
		this.cells = new ConcurrentHashMap<Integer, Cell>();
		
		this.rows = rows;
		this.cols = cols;
		
		initializeGameBoard(rows * cols);
		
	}
	
	public int getRows() {
		return this.rows;
	}
	
	public int getCols() {
		return this.cols;
	}
	
	public Cell getCell(int index) {
		return this.cells.get(index);
	}
	
	public Cell getCell(int x, int y) {
		return cells.get((y * rows) + x);
	}
	
	public ConcurrentHashMap<Integer, Cell> getCells() {
		return cells;
	}
	
	private void initializeGameBoard(int size) {
		
		this.cells.clear();
		
		for (int i = 0; i < size; i++) {
			this.cells.put(i, new Cell(0, i));
		}
		
	}
	
	public void swapCell(Cell a, Cell b) {

		this.cells.put(a.getIndex(), b);
		this.cells.put(b.getIndex(), a);
		int tempIndex = a.getIndex();
		a.setIndex(b.getIndex());
		b.setIndex(tempIndex);
	}

	public int getAppleIndex() {
		return appleIndex;
	}

	public void setAppleIndex(int appleIndex) {
		this.appleIndex = appleIndex;
	}
}