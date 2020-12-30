package board.tile;

import treasure.Treasure;
import treasure.TreasureType;

public class Garden extends TreasureTile{
	protected static Treasure wind;
	protected static boolean ifGet=false;
	public Garden(String name){
		super(name);
		this.treasureType=TreasureType.WIND;
		this.nameCLI=" WIND  ";
	}
	public boolean getIfGet(){
		return Garden.ifGet;
	}
	public void setTreasure(Treasure wind){
		Garden.wind = wind;
	}
	public Treasure getTreasure(){
		if(Garden.ifGet){
			return null;
		} else {
			Garden.ifGet = true;//the "ifGet" do not need to assign to false again. Because the only possibility for this is the player which has the treasure become dead.
			return Garden.wind;
		}
	}
}
