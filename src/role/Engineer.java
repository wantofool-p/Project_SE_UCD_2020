package role;

import board.Board;
import board.tile.Status;
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
						StdTile destination1 = getDestination(board, directionType1);
						if(destination1==null){
							return false;//failure
						} else {
							if(destination1.getStatus()==Status.FLOODED){
								if(directionType2==null){
									return false;//if any invalid input
								} else {
									switch (directionType2) {
										case UP://↑
										case DOWN://↓
										case LEFT://←
										case RIGHT://→
										case MIDDLE://•
											StdTile destination2 = getDestination(board, directionType2);
											if(destination2==null){
												return false;//failure
											} else {
												if(destination2.getStatus()==Status.FLOODED){
													this.freeShoreUp(destination1);
													this.freeShoreUp(destination2);
													this.AP--;//true
													return true;
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
		}
	}
}
