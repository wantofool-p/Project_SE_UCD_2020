package board.tile;

import java.util.ArrayList;

public interface Tile {
	public void printCLI(ArrayList<StringBuilder> lines1To5);
	public int[] getCoord();
	public void setCoord(int[] coord);
	public void setCoord(int row, int col); //it is a setter but not common
}
