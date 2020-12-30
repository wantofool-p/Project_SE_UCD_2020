package role;

import board.tile.Status;
import board.tile.StdTile;
import setting.Options;

public class Navigator extends StdRole{
	public Navigator(){
		this.name="Navigator";
	}
	public boolean guideAbility(StdRole targetRole, StdTile destination){//many times per turn // not used //TODO fix
		if(Options.ifNavigatorHasOwnAbility){
			if(this.AP==0){
				return false;//no enough AP
			} else if(destination==null){
				return false;//ERR
			} else {
				int[] destCoord = destination.getCoord();
				int destRow=destCoord[0], destCol=destCoord[1];
				int[] currCoord = targetRole.getCoord();
				int currRow=currCoord[0], currCol=currCoord[1];
				int deltaRow=Math.abs(destRow-currRow), deltaCol=Math.abs(destCol-currCol);
				if((destRow==currRow)&&(destCol==currCol)){
					return false;//cancel
				} else if ((deltaRow<=2)&&(deltaCol<=2)){
					if((deltaRow+deltaCol>2)&&(targetRole.getClass()!=Explorer.class)){
						return false;//failure
					} else if((destination.getStatus()==Status.SUNK)&&(targetRole.getClass()!=Diver.class)){
						return false;//failure
					} else {
						targetRole.freeMove(destination);
						this.AP--;
						return true;
					}
				} else {
					return false;//failure
				}
			}
		} else {
			return false;
		}
	}
}
