//passive FunctionalCard
package deck.treasureCard;

import deck.FloodDeck;
import waterMeter.WaterMeter;

public class WaterRise extends TreasureCard{
	public WaterRise(){
		this.name="Water Rise";
	}
	public static void use(FloodDeck floodDeck, FloodDeck usedFloodDeck, WaterMeter waterMeter){//assume a valid operation
		floodDeck.shuffle(usedFloodDeck);
		waterMeter.addMeter();
	}
}
