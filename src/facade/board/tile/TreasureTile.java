package facade.board.tile;

import java.util.List;

public abstract class TreasureTile extends StdTile{
	protected TreasureType treasureType;//which type of treasure would this island provide
	public TreasureTile(String name){
		super(name);
	}
	//TODO provide treasure
}
