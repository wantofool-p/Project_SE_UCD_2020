package role;

import board.Board;
import board.tile.StdTile;

public class Engineer extends StdRole{
	public Engineer(){
		this.name="Engineer";
	}
	public boolean shoreUp(Board board, DirectionType directionType1, DirectionType directionType2){
		if(this.AP==0){
			return false;//not enough AP
		} else {
			if(directionType1==directionType2){
				return false;//mistaken call
			} else if(directionType1==null){
				return false;//mistaken call
			} else {
				switch (directionType1) {
					case UP://↑
					case DOWN://↓
					case LEFT://←
					case RIGHT://→
					case MIDDLE://•
						StdTile destination = getDestination(board, directionType1);
						if(destination==null){
							return false;//failure
						} else {
							if(this.freeShoreUp(destination)){
								break;
							} else {
								return false;//failure
							}
						}
					case TOPLEFT://↖//not allowed
					case BOTTOMRIGHT://↘//not allowed
					case BOTTOMLEFT://↙//not allowed
					case TOPRIGHT://↗//not allowed
					default://ERR
						return false;//failure
				}
			}
			if(directionType2==null){
				return true;//success //it is allowed to store up ONE tile
			} else {
				switch (directionType2) {
					case UP://↑
					case DOWN://↓
					case LEFT://←
					case RIGHT://→
					case MIDDLE://•
						StdTile destination = getDestination(board, directionType2);
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
					case TOPLEFT://↖//not allowed
					case BOTTOMRIGHT://↘//not allowed
					case BOTTOMLEFT://↙//not allowed
					case TOPRIGHT://↗//not allowed
					default://ERR
						return false;//it is allowed to store up ONE tile
				}
			}
		}
	}
}
