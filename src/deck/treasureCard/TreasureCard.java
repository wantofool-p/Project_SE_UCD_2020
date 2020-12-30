package deck.treasureCard;
import treasure.TreasureType;
public class TreasureCard{
	protected String name;//this element is debug only
	protected TreasureType treasureType;//which type of treasure would this island provide
	public TreasureCard(){
		;
	}
	public void printCLI(){//this method is debug only
		System.out.println(this.name);
	}
	public String getName(){//this method is debug only
		return name;
	}
	public TreasureType getTreasureType(){//this method is debug only
		return treasureType;
	}
}
//TODO functional