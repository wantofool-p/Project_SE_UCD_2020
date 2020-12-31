//CheatCharacter
//should not available in the normal game
package role;

public class CheatCharacter extends StdRole{
	public CheatCharacter(){
		this.name="Daddy";
	}
	@Override
	public void begin(){
		this.AP=99;
	}
}//TODO cheat character
