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
	public static Treasure getFire() {
		return Cave.fire;
	}
	public static boolean getIfGet(){
		return Cave.ifGet;
	}
	public static void setTreasure(Treasure fire){
		Cave.fire = fire;
	}
	public static void setIfGet(boolean ifGet) {
		Cave.ifGet = ifGet;
	}
	@Override
	public Treasure getTreasure(){
		if(Cave.fire==null){
			System.err.println("[The fire treasure == null.]");
			return null;
		} else if(Cave.ifGet){
			System.err.println("[The fire treasure has been captured already.]");
			return null;
		} else {
			Cave.ifGet = true;//the "ifGet" do not need to assign to false again. Because the only possibility for this is the player which has the treasure become dead.
			return Cave.fire;
		}
	}
}
