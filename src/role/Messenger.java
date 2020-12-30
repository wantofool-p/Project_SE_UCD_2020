package role;

public class Messenger extends StdRole{
	public Messenger(){
		this.name="Messenger";
	}
	public boolean passCard(StdRole targetRole, int cardIndex){//be aware that this method would not drop card if the size of cards == 6
		if(this.AP==0){
			return false;//not enough AP
		} else {
			this.freePassCard(targetRole, cardIndex);
			this.AP--;
			return true;//success
		}
	}
}
