package facade.deck.treasureCard;
public class TreasureCard{
	protected String name;//this element is debug only
	public TreasureCard(){
		;
	}
	public void printCLI(){//this method is debug only
		System.out.println(this.name);
	}
	public String getName(){//this method is debug only
		return name;
	}
}
//TODO functional