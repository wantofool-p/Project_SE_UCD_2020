package role;

import java.util.ArrayList;

import board.Board;
import board.tile.Status;
import board.tile.StdTile;
import setting.Options;

public class Diver extends StdRole{//Diver must end on a Tile (not SUNK)
	public Diver(){
		this.name="Diver";
	}
	public ArrayList<StdTile> end(Board board){
		this.AP=0;
		if(this.currStdTile.getStatus()==Status.SUNK){
			if(Options.ifDiverAllowedStopAtSunkTile){
				return this.sink(board);
			} else {
				this.isAlive=false;
				return null;
			}
		} else {
			return null;
		}
	}
	public boolean move(Board board, DirectionType directionType){
		if(this.AP==0){
			return false;//not enough AP
		} else {
			switch (directionType) {
				case UP://↑
				case DOWN://↓
				case LEFT://←
				case RIGHT://→
					StdTile destination = getDestination(board, directionType);
					if(destination==null){
						return false;//failure
					} else {
						this.freeMove(destination);
						this.AP--;
						return true;//success
					}
				case MIDDLE://•//cancel
				case TOPLEFT://↖//not allowed
				case BOTTOMRIGHT://↘//not allowed
				case BOTTOMLEFT://↙//not allowed
				case TOPRIGHT://↗//not allowed
				default://ERR
					return false;//failure
			}
		}
	}
	public ArrayList<StdTile> sink(Board board){//return nearest valid StdTile List
		int[] currCoord = this.currStdTile.getCoord();
		int currRow=currCoord[0], currCol=currCoord[1];
		ArrayList<StdTile> tempList = new ArrayList<StdTile>();
		StdTile temp;
		int distance=1;
		do {// ╱↖ // ↗╲ // ↘╱ // ╲↙
			for(int i=0, y=0, x=-distance; i<distance; i++, y--, x++){// ╱↖
				temp = board.getStdTile(currRow+y, currCol+x);
				if(temp==null){
					;
				} else if(temp.getStatus()==Status.SUNK){
					;
				} else {
					tempList.add(temp);
				}
			}
			for(int i=0, y=-distance, x=0; i<distance; i++, y++, x++){// ↗╲
				temp = board.getStdTile(currRow+y, currCol+x);
				if(temp==null){
					;
				} else if(temp.getStatus()==Status.SUNK){
					;
				} else {
					tempList.add(temp);
				}
			}
			for(int i=0, y=0, x=distance; i<distance; i++, y++, x--){// ↘╱
				temp = board.getStdTile(currRow+y, currCol+x);
				if(temp==null){
					;
				} else if(temp.getStatus()==Status.SUNK){
					;
				} else {
					tempList.add(temp);
				}
			}
			for(int i=0, y=distance, x=0; i<distance; i++, y--, x--){// ╲↙
				temp = board.getStdTile(currRow+y, currCol+x);
				if(temp==null){
					;
				} else if(temp.getStatus()==Status.SUNK){
					;
				} else {
					tempList.add(temp);
				}
			}
		} while ((tempList.size()==0)&&(distance++!=7));
		if (tempList.size()==0){
			this.isAlive=false;
		}
		return tempList;
	}
}
