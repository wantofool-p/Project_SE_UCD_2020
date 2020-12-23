package facade.board.tile;

public class Garden extends TreasureTile{
	protected static int numOFGarden=0;//how many Gardens in tot
	public Garden(String name){
		super(name);
		this.treasureType=TreasureType.WIND;
		this.AddOneToNumOFGarden();
		this.nameCLI=" WIND  ";
	}
	@SuppressWarnings("static-access")
	public void AddOneToNumOFGarden(){
		this.numOFGarden++;
	}
}
