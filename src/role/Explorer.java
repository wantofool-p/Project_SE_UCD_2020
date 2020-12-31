package role;

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
			return false;//not enough AP
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
			return false;//not enough AP
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
}
