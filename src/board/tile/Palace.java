package board.tile;

import treasure.Treasure;
import treasure.TreasureType;

public class Palace extends TreasureTile{
	protected static Treasure chalice;
	protected static boolean ifGet=false;
	public Palace(String name){
		super(name);
		this.treasureType=TreasureType.CHALICE;
		this.nameCLI="CHALICE";
	}
	public static boolean getIfGet(){
		return Palace.ifGet;
	}
	public void setTreasure(Treasure chalice){
		Palace.chalice = chalice;
	}
	@Override
	public Treasure getTreasure(){
		if(Palace.chalice==null){
			System.err.println("[The chalice treasure == null.]");
			return null;
		} else if(Palace.ifGet){
			System.err.println("[The Chalice treasure has been captured already.]");
			return null;
		} else {
			Palace.ifGet = true;//the "ifGet" do not need to assign to false again. Because the only possibility for this is the player which has the treasure become dead.
			return Palace.chalice;
		}
	}
}
