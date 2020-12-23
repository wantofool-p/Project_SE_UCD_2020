package facade.board.tile;

import java.util.List;

public class NotATile implements Tile{
	public NotATile(){
		;
	}
	public void printCLI(List<StringBuffer> Lines1To5){
		Lines1To5.set(0, Lines1To5.get(0).append("         "));//line 1
		Lines1To5.set(1, Lines1To5.get(1).append("         "));//line 2
		Lines1To5.set(2, Lines1To5.get(2).append("         "));//line 3
		Lines1To5.set(3, Lines1To5.get(3).append("         "));//line 4
		Lines1To5.set(4, Lines1To5.get(4).append("         "));//line 5
	}
}
