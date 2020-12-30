//initiative FunctionalCard
package deck.treasureCard;

import board.tile.StdTile;

public class Sandbags extends TreasureCard{
	public Sandbags(){
		this.name="Sandbags";
	}
	public static boolean use(StdTile destination){
		return destination.storeUp();
	}
}