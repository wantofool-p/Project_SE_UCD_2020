package board.tile;

import java.util.ArrayList;

public class NullTile implements Tile{
	protected int[] coord = {-1, -1};//should be 2 elements only //[0]=row, [1]=col
	public NullTile(){
		;
	}
	public int[] getCoord(){
		return coord;
	}
	public void setCoord(int[] coord){// run once only
		this.coord = coord;
	}
	public void setCoord(int row, int col){
		int[] temp = {row, col};
		this.coord = temp;
	}
	public void printCLI(ArrayList<StringBuilder> lines1To5){
		lines1To5.set(0, lines1To5.get(0).append("         "));//line 1
		lines1To5.set(1, lines1To5.get(1).append("         "));//line 2
		lines1To5.set(2, lines1To5.get(2).append("         "));//line 3
		lines1To5.set(3, lines1To5.get(3).append("         "));//line 4
		lines1To5.set(4, lines1To5.get(4).append("         "));//line 5
	}
}
