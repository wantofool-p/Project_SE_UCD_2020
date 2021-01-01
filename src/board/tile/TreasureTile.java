package board.tile;

import java.util.ArrayList;

import deck.treasureCard.TreasureCard;
import treasure.Treasure;
import treasure.TreasureType;

public abstract class TreasureTile extends StdTile{
	protected TreasureType treasureType;//which type of treasure would this island provide
	public TreasureTile(String name){
		super(name);
	}
	@Override
	public Treasure getTreasure(){
		System.err.println("func getTreasure ERR -- class TreasureTile mistaken call");
		return null;
	}
	@Override
	public ArrayList<Integer> callTreasure(ArrayList<TreasureCard> cards){//this method would be called when player is trying to get a treasure //called by StdRole.java
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for(int i=cards.size()-1; i>-1; i--){
			if(cards.get(i).getTreasureType()==this.treasureType){
				temp.add(i);
				if(temp.size()==4){
					return temp;
				}
			}
		}
		return temp;
	}
	//TODO provide treasure
}
