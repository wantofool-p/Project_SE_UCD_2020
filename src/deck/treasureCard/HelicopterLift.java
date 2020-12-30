//initiative FunctionalCard
package deck.treasureCard;

import java.util.HashSet;

import board.tile.StdTile;
import role.StdRole;
import setting.Options;

public class HelicopterLift extends TreasureCard{
	public HelicopterLift(){
		this.name="Helicopter Lift";
	}
	public static void use(HashSet<StdRole> players, StdTile startTile, StdTile destination){
		if(Options.ifHelicopterLiftPlayerShouldAtTheSameTile){
			for(StdRole i:startTile.getPlayers()){
				startTile.playerLeaves(i);
				destination.playerComes(i);
			}
		} else {
			for(StdRole i:players){
				i.getCurrStdTile().playerLeaves(i);
				destination.playerComes(i);
			}
		}
	}
}