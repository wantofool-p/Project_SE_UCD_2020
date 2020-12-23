package facade.board.tile;

import java.util.ArrayList;
import java.util.List;

import facade.board.role.CheatCharacter;
import facade.board.role.Diver;
import facade.board.role.Engineer;
import facade.board.role.Explorer;
import facade.board.role.Messenger;
import facade.board.role.Navigator;
import facade.board.role.Pilot;
import facade.board.role.StdRole;
public class StdTile implements Tile{
	protected String name;//island name
	protected Stage stage=Stage.NORMAL;//island is flooded or sunk or completely normal as new
	protected List<StdRole> players=new ArrayList<StdRole>();//should up to 4 players
	protected String nameCLI="       ";//7 digit String
	public StdTile(){//should not use
		;
	}
	public StdTile(String name){
		this.name = name;
	}
	public void flood(){
		if(this.stage==Stage.NORMAL){
			this.stage=Stage.FLOODED;
		} else if (this.stage==Stage.FLOODED){
			this.stage=Stage.SUNK;
		} else {
			;
		}
	}
	public boolean storeUp(){
		if(this.stage==Stage.FLOODED){
			this.stage=Stage.NORMAL;
			System.out.println("storeUp success");
			return true;
		} else {
			if (this.stage==Stage.NORMAL){
				System.out.println("this.stage==Stage.NORMAL");
				System.out.println("storeUp failure");
				return false;
			} else if(this.stage==Stage.SUNK){
				System.out.println("this.stage==Stage.SUNK");
				System.out.println("storeUp failure");
				return false;
			} else {
				System.out.println("this.stage ERROR");
				System.out.println("storeUp failure");
				return false;
			}
		}
	}
	public void printCLI(List<StringBuffer> Lines1To5){
		if(this.stage==Stage.NORMAL){
			Lines1To5.set(0, Lines1To5.get(0).append("╔═══════╗"));//line 1
			Lines1To5.set(1, Lines1To5.get(1).append("║"));//line 2
			printOneCharacterCLI(Lines1To5, 1, 4);
			Lines1To5.set(1, Lines1To5.get(1).append(" "));//line 2
			printOneCharacterCLI(Lines1To5, 1, 3);
			Lines1To5.set(1, Lines1To5.get(1).append("║"));//line 2
			Lines1To5.set(2, Lines1To5.get(2).append("║"));//line 3
			printTileNameCLI(Lines1To5);
			Lines1To5.set(2, Lines1To5.get(2).append("║"));//line 3
			Lines1To5.set(3, Lines1To5.get(3).append("║"));//line 4
			printOneCharacterCLI(Lines1To5, 3, 2);
			Lines1To5.set(3, Lines1To5.get(3).append(" "));//line 4
			printOneCharacterCLI(Lines1To5, 3, 1);
			Lines1To5.set(3, Lines1To5.get(3).append("║"));//line 4
			Lines1To5.set(4, Lines1To5.get(4).append("╚═══════╝"));//line 5
		} else if(this.stage==Stage.FLOODED){
			Lines1To5.set(0, Lines1To5.get(0).append("┌───────┐"));//line 1
			Lines1To5.set(1, Lines1To5.get(1).append("│"));//line 2
			printOneCharacterCLI(Lines1To5, 1, 4);
			Lines1To5.set(1, Lines1To5.get(1).append(" "));//line 2
			printOneCharacterCLI(Lines1To5, 1, 3);
			Lines1To5.set(1, Lines1To5.get(1).append("│"));//line 2
			Lines1To5.set(2, Lines1To5.get(2).append("│"));//line 3
			printTileNameCLI(Lines1To5);
			Lines1To5.set(2, Lines1To5.get(2).append("│"));//line 3
			Lines1To5.set(3, Lines1To5.get(3).append("│"));//line 4
			printOneCharacterCLI(Lines1To5, 3, 2);
			Lines1To5.set(3, Lines1To5.get(3).append(" "));//line 4
			printOneCharacterCLI(Lines1To5, 3, 1);
			Lines1To5.set(3, Lines1To5.get(3).append("│"));//line 4
			Lines1To5.set(4, Lines1To5.get(4).append("└───────┘"));//line 5
		} else if(this.stage==Stage.SUNK){
			Lines1To5.set(0, Lines1To5.get(0).append("         "));//line 1
			Lines1To5.set(1, Lines1To5.get(1).append(" "));//line 2
			printOneCharacterCLI(Lines1To5, 1, 4);
			Lines1To5.set(1, Lines1To5.get(1).append(" "));//line 2
			printOneCharacterCLI(Lines1To5, 1, 3);
			Lines1To5.set(1, Lines1To5.get(1).append(" "));//line 2
			Lines1To5.set(2, Lines1To5.get(2).append(" "));//line 3
			printTileNameCLI(Lines1To5);
			Lines1To5.set(2, Lines1To5.get(2).append(" "));//line 3
			Lines1To5.set(3, Lines1To5.get(3).append(" "));//line 4
			printOneCharacterCLI(Lines1To5, 3, 2);
			Lines1To5.set(3, Lines1To5.get(3).append(" "));//line 4
			printOneCharacterCLI(Lines1To5, 3, 1);
			Lines1To5.set(3, Lines1To5.get(3).append(" "));//line 4
			Lines1To5.set(4, Lines1To5.get(4).append("         "));//line 5
		} else {
			Lines1To5.set(0, Lines1To5.get(0).append("pCLIERROR"));//line 1
			Lines1To5.set(1, Lines1To5.get(1).append("pCLIERROR"));//line 2
			Lines1To5.set(2, Lines1To5.get(2).append("pCLIERROR"));//line 3
			Lines1To5.set(3, Lines1To5.get(3).append("pCLIERROR"));//line 4
			Lines1To5.set(4, Lines1To5.get(4).append("pCLIERROR"));//line 5
		}
	}
	public void printOneCharacterCLI(List<StringBuffer> Lines1To5, int lineNum, int playerNum){
		if(players.size()==playerNum){
			@SuppressWarnings("rawtypes")
			Class temp = (players.get(playerNum-1)).getClass();
			if (temp==CheatCharacter.class){
				Lines1To5.set(lineNum, Lines1To5.get(lineNum).append("Dad"));
			}else if(temp==Diver.class){
				Lines1To5.set(lineNum, Lines1To5.get(lineNum).append("DIV"));
			}else if(temp==Engineer.class){
				Lines1To5.set(lineNum, Lines1To5.get(lineNum).append("ENG"));
			}else if(temp==Explorer.class){
				Lines1To5.set(lineNum, Lines1To5.get(lineNum).append("EXP"));
			}else if(temp==Messenger.class){
				Lines1To5.set(lineNum, Lines1To5.get(lineNum).append("MSG"));
			}else if(temp==Navigator.class){
				Lines1To5.set(lineNum, Lines1To5.get(lineNum).append("NAV"));
			}else if(temp==Pilot.class){
				Lines1To5.set(lineNum, Lines1To5.get(lineNum).append("PIL"));
			}else{
				Lines1To5.set(lineNum, Lines1To5.get(lineNum).append("ERR"));
			}
		} else {
			Lines1To5.set(lineNum, Lines1To5.get(lineNum).append("   "));
		}
	}
	public void printTileNameCLI(List<StringBuffer> Lines1To5){
		Lines1To5.set(2, Lines1To5.get(2).append(nameCLI));
	}
	public String getName(){
		return this.name;
	}
	public List<StdRole> getPlayers(){
		return players;
	}
	public void addPlayer(StdRole player){//assume a vaild operation
		players.add(player);
	}
	public void removePlayer(StdRole player){//assume a vaild operation
		players.remove(player);
	}
	public Stage getStage(){
		return this.stage;
	}
}
