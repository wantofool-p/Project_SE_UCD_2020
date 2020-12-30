package treasure;
import role.StdRole;
public abstract class Treasure{
	protected String name;
	protected TreasureType treasureType;
	protected StdRole currRole;
	public Treasure(){
		;
	}
	public String getName(){
		return name;
	}
	public TreasureType getTreasureType(){
		return treasureType;
	}
	public StdRole getCurrRole(){
		return currRole;
	}
	public void setCurrRole(StdRole currRole){
		this.currRole=currRole;
	}
}
