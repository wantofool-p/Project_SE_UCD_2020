package board.tile;

import treasure.Treasure;
import treasure.TreasureType;

public class Cave extends TreasureTile{
	protected static Treasure fire;
	protected static boolean ifGet=false;
	public Cave(String name){
		super(name);
		this.treasureType=TreasureType.FIRE;
		this.nameCLI=" FIRE  ";
	}
	public static boolean getIfGet(){
		return Cave.ifGet;
	}
	public void setTreasure(Treasure fire){
		Cave.fire = fire;
	}
	@Override
	public Treasure getTreasure(){
		if(Cave.ifGet){
			System.err.println("This treasure has been captured already.");
			return null;
		} else {
			Cave.ifGet = true;//the "ifGet" do not need to assign to false again. Because the only possibility for this is the player which has the treasure become dead.
			return Cave.fire;
		}
	}
}
