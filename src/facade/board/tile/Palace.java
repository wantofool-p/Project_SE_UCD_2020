package facade.board.tile;

public class Palace extends TreasureTile{
	protected static int numOFPalace=0;//how many Palaces in tot
	public Palace(String name){
		super(name);
		this.treasureType=TreasureType.CHALICE;
		this.AddOneToNumOFPalace();
		this.nameCLI="CHALICE";
	}
	@SuppressWarnings("static-access")
	public void AddOneToNumOFPalace(){
		this.numOFPalace++;
	}
}
