package role;

import java.util.ArrayList;

import board.Board;
import board.tile.Status;
import board.tile.StdTile;

public class Explorer extends StdRole{
	public Explorer(){
		this.name="Explorer";
	}
	@Override
	public boolean move(Board board, DirectionType directionType){
		if(this.AP==0){
			return false;//no enough AP
		} else {
			switch (directionType) {
				case UP://↑
				case DOWN://↓
				case LEFT://←
				case RIGHT://→
				case TOPLEFT://↖
				case BOTTOMRIGHT://↘
				case BOTTOMLEFT://↙
				case TOPRIGHT://↗
					StdTile destination = getDestination(board, directionType);
					if(destination==null){
						return false;//failure
					} else if(destination.getStatus()==Status.SUNK){
						return false;//failure
					} else {
						this.freeMove(destination);
						this.AP--;
						return true;//success
					}
				case MIDDLE://•//cancel
				default://ERR
					return false;//failure
			}
		}
	}
	@Override
	public boolean shoreUp(Board board, DirectionType directionType){
		if(this.AP==0){
			return false;//no enough AP
		} else {
			switch (directionType) {
				case UP://↑
				case DOWN://↓
				case LEFT://←
				case RIGHT://→
				case TOPLEFT://↖//not allowed
				case BOTTOMRIGHT://↘//not allowed
				case BOTTOMLEFT://↙//not allowed
				case TOPRIGHT://↗//not allowed
					StdTile destination = getDestination(board, directionType);
					if(destination==null){
						return false;//failure
					} else {
						if(this.freeShoreUp(destination)){
							this.AP--;
							return true;//success
						} else {
							return false;//failure
						}
					}
				case MIDDLE://•
				default://ERR
					return false;//failure
			}
		}
	}
	@Override
	public ArrayList<StdTile> sink(Board board){//return valid Tile List
		if(this.currStdTile.getStatus()!=Status.SUNK){
			System.err.println("func sink ERR -- mistaken call");
			return null;//mistaken call
		} else {
			ArrayList<StdTile> tempList = new ArrayList<StdTile>();
			StdTile temp;
			ArrayList<DirectionType> eightDirectionTypes = new ArrayList<DirectionType>();
			eightDirectionTypes.add(DirectionType.UP);
			eightDirectionTypes.add(DirectionType.DOWN);
			eightDirectionTypes.add(DirectionType.LEFT);
			eightDirectionTypes.add(DirectionType.RIGHT);
			eightDirectionTypes.add(DirectionType.TOPLEFT);
			eightDirectionTypes.add(DirectionType.BOTTOMRIGHT);
			eightDirectionTypes.add(DirectionType.BOTTOMLEFT);
			eightDirectionTypes.add(DirectionType.TOPRIGHT);
			for(DirectionType i:eightDirectionTypes){
				temp=this.getDestination(board, i);
				if(temp==null){
					;
				} else if(temp.getStatus()==Status.SUNK){
					;
				} else {
					tempList.add(temp);
				}
			}
			if (tempList.size()==0){
				this.isAlive=false;
			}
			return tempList;
		}
	}
}
