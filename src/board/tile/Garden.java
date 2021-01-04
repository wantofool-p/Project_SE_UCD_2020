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
	public static Treasure getWind() {
		return Garden.wind;
	}
	public static boolean getIfGet(){
		return Garden.ifGet;
	}
	public static void setTreasure(Treasure wind){
		Garden.wind = wind;
	}
	public static void setIfGet(boolean ifGet) {
		Garden.ifGet = ifGet;
	}
	@Override
	public Treasure getTreasure(){
		if(Garden.wind==null){
			System.err.println("[The wind treasure == null.]");
			return null;
		} else if(Garden.ifGet){
			System.err.println("[The wind treasure has been captured already.]");
			return null;
		} else {
			Garden.ifGet = true;//the "ifGet" do not need to assign to false again. Because the only possibility for this is the player which has the treasure become dead.
			return Garden.wind;
		}
	}


}
