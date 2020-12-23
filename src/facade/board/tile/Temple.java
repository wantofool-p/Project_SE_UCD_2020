package facade.board.tile;

public class Temple extends TreasureTile{
	protected static int numOFTemple=0;//how many Temples in tot
	public Temple(String name){
		super(name);
		this.treasureType=TreasureType.STONE;
		this.AddOneToNumOFTemple();
		this.nameCLI=" STONE ";
	}
	@SuppressWarnings("static-access")
	public void AddOneToNumOFTemple(){
		this.numOFTemple++;
	}
}
