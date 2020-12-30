package deck.floodCard;

import board.tile.Status;
import board.tile.StdTile;

public class FloodCard{
	protected String name;//this element is debug only
	protected StdTile stdTile;//linked to the specific island tile
	public FloodCard(String name, StdTile stdTile){
		this.name=name;
		this.stdTile=stdTile;
	}
	public void useCard(){
		System.out.print(this.stdTile.getName());
		this.stdTile.flood();
		if(this.stdTile.getStatus()==Status.FLOODED){//FLOODED, SUNK
			System.out.println(" flood more...");
		} else if(this.stdTile.getStatus()==Status.SUNK){
			System.out.println(" has sunk!!");
		}
	}
	public void printCLI(){//this method is debug only
		System.out.println(this.name);
	}
}
