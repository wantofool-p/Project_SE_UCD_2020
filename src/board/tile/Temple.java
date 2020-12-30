package board.tile;

import treasure.Treasure;
import treasure.TreasureType;

public class Temple extends TreasureTile{
	protected static Treasure stone;
	protected static boolean ifGet=false;
	public Temple(String name){
		super(name);
		this.treasureType=TreasureType.STONE;
		this.nameCLI=" STONE ";
	}
	public boolean getIfGet(){
		return Temple.ifGet;
	}
	public void setTreasure(Treasure stone){
		Temple.stone = stone;
	}
	public Treasure getTreasure(){
		if(Temple.ifGet){
			return null;
		} else {
			Temple.ifGet = true;//the "ifGet" do not need to assign to false again. Because the only possibility for this is the player which has the treasure become dead.
			return Temple.stone;
		}
	}
}

