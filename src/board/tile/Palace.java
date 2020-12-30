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
	public boolean getIfGet(){
		return Palace.ifGet;
	}
	public void setTreasure(Treasure chalice){
		Palace.chalice = chalice;
	}
	public Treasure getTreasure(){
		if(Palace.ifGet){
			return null;
		} else {
			Palace.ifGet = true;//the "ifGet" do not need to assign to false again. Because the only possibility for this is the player which has the treasure become dead.
			return Palace.chalice;
		}
	}
}
