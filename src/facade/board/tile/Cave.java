package facade.board.tile;

public class Cave extends TreasureTile{
	protected static int numOFCave=0;//how many Caves in tot
	public Cave(String name){
		super(name);
		this.treasureType=TreasureType.FIRE;
		this.AddOneToNumOFCave();
		this.nameCLI=" FIRE  ";
	}
	@SuppressWarnings("static-access")
	public void AddOneToNumOFCave(){
		this.numOFCave++;
	}
}
