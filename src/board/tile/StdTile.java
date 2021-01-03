package board.tile;

import java.util.ArrayList;

import deck.treasureCard.TreasureCard;
import role.CheatCharacter;
import role.Diver;
import role.Engineer;
import role.Explorer;
import role.Messenger;
import role.Navigator;
import role.Pilot;
import role.StdRole;
import treasure.Treasure;
public class StdTile implements Tile{
	protected String nameCLI="       ";//7 digit String //for print //should not be changed
	protected String name;//island name
	protected Status status=Status.NORMAL;//island is flooded or sunk or completely normal as new
	protected ArrayList<StdRole> players = new ArrayList<StdRole>();//should up to 4 players
	protected int[] coord = {-1, -1};//should be 2 elements only //[0]=row, [1]=col
	public StdTile(){
		;
	}
	public StdTile(String name){
		this.name = name;
	}
	public String getName(){
		return this.name;
	}
	public Status getStatus(){
		return this.status;
	}
	public ArrayList<StdRole> getPlayers(){
		return this.players;
	}
	public int[] getCoord(){
		return this.coord;
	}
	public void setName(String name){
		this.name = name;
	}
	public void setStatus(Status status){
		this.status = status;
	}
	public void setPlayers(ArrayList<StdRole> players){
		this.players = players;
	}
	public void setCoord(int[] coord){
		this.coord = coord;
	}
	public void setCoord(int row, int col){
		int[] temp = {row, col};
		this.coord = temp;
	}
	public void flood(){
		if(this.status==Status.NORMAL){
			this.status=Status.FLOODED;
		} else if (this.status==Status.FLOODED){
			this.status=Status.SUNK;
		} else {
			;
		}
	}
	public boolean storeUp(){
		if(this.status==Status.FLOODED){
			this.status=Status.NORMAL;
			System.out.println("[Store Up success]");
			return true;
		} else {
			if (this.status==Status.NORMAL){
				System.err.println("[Store Up failure -- This tile is completely normal.]");
				return false;
			} else if(this.status==Status.SUNK){
				System.err.println("[Store Up failure -- This tile is already sunk.]");
				return false;
			} else {
				System.err.println("[Store Up failure -- this.status ERROR]");
				return false;
			}
		}
	}
	public ArrayList<Integer> callTreasure(ArrayList<TreasureCard> cards){//this method would be called when player is trying to get a treasure //called by StdRole.java
		System.err.println("func callTreasure ERR -- class StdTile mistaken call");
		return null;
	}
	public Treasure getTreasure(){
		System.err.println("func getTreasure ERR -- class StdTile mistaken call");
		return null;
	}
	public void printCLI(ArrayList<StringBuilder> lines1To5){
		if(this.status==Status.NORMAL){
			lines1To5.set(0, lines1To5.get(0).append("╔═══════╗"));//line 1
			lines1To5.set(1, lines1To5.get(1).append("║"));//line 2
			printCharacterCLI(lines1To5, 1, 4);
			lines1To5.set(1, lines1To5.get(1).append(" "));//line 2
			printCharacterCLI(lines1To5, 1, 3);
			lines1To5.set(1, lines1To5.get(1).append("║"));//line 2
			lines1To5.set(2, lines1To5.get(2).append("║"));//line 3
			printTileNameCLI(lines1To5);
			lines1To5.set(2, lines1To5.get(2).append("║"));//line 3
			lines1To5.set(3, lines1To5.get(3).append("║"));//line 4
			printCharacterCLI(lines1To5, 3, 2);
			lines1To5.set(3, lines1To5.get(3).append(" "));//line 4
			printCharacterCLI(lines1To5, 3, 1);
			lines1To5.set(3, lines1To5.get(3).append("║"));//line 4
			lines1To5.set(4, lines1To5.get(4).append("╚═══════╝"));//line 5
		} else if(this.status==Status.FLOODED){
			lines1To5.set(0, lines1To5.get(0).append("┌───────┐"));//line 1
			lines1To5.set(1, lines1To5.get(1).append("│"));//line 2
			printCharacterCLI(lines1To5, 1, 4);
			lines1To5.set(1, lines1To5.get(1).append(" "));//line 2
			printCharacterCLI(lines1To5, 1, 3);
			lines1To5.set(1, lines1To5.get(1).append("│"));//line 2
			lines1To5.set(2, lines1To5.get(2).append("│"));//line 3
			printTileNameCLI(lines1To5);
			lines1To5.set(2, lines1To5.get(2).append("│"));//line 3
			lines1To5.set(3, lines1To5.get(3).append("│"));//line 4
			printCharacterCLI(lines1To5, 3, 2);
			lines1To5.set(3, lines1To5.get(3).append(" "));//line 4
			printCharacterCLI(lines1To5, 3, 1);
			lines1To5.set(3, lines1To5.get(3).append("│"));//line 4
			lines1To5.set(4, lines1To5.get(4).append("└───────┘"));//line 5
		} else if(this.status==Status.SUNK){
			lines1To5.set(0, lines1To5.get(0).append("         "));//line 1
			lines1To5.set(1, lines1To5.get(1).append(" "));//line 2
			printCharacterCLI(lines1To5, 1, 4);
			lines1To5.set(1, lines1To5.get(1).append(" "));//line 2
			printCharacterCLI(lines1To5, 1, 3);
			lines1To5.set(1, lines1To5.get(1).append(" "));//line 2
			// lines1To5.set(2, lines1To5.get(2).append(" "));//line 3//old style
			// printTileNameCLI(lines1To5);//old style
			// lines1To5.set(2, lines1To5.get(2).append(" "));//line 3//old style
			lines1To5.set(2, lines1To5.get(2).append("         "));//line 3//new style
			lines1To5.set(3, lines1To5.get(3).append(" "));//line 4
			printCharacterCLI(lines1To5, 3, 2);
			lines1To5.set(3, lines1To5.get(3).append(" "));//line 4
			printCharacterCLI(lines1To5, 3, 1);
			lines1To5.set(3, lines1To5.get(3).append(" "));//line 4
			lines1To5.set(4, lines1To5.get(4).append("         "));//line 5
		} else {
			lines1To5.set(0, lines1To5.get(0).append("priCLIerr"));//line 1
			lines1To5.set(1, lines1To5.get(1).append("priCLIerr"));//line 2
			lines1To5.set(2, lines1To5.get(2).append("priCLIerr"));//line 3
			lines1To5.set(3, lines1To5.get(3).append("priCLIerr"));//line 4
			lines1To5.set(4, lines1To5.get(4).append("priCLIerr"));//line 5
		}
	}
	public void printCharacterCLI(ArrayList<StringBuilder> lines1To5, int lineNum, int playerNum){
		if(players.size()>=playerNum){
			@SuppressWarnings("rawtypes")
			Class temp = (players.get(playerNum-1)).getClass();
			if (temp==CheatCharacter.class){
				lines1To5.set(lineNum, lines1To5.get(lineNum).append("DAD"));
			}else if(temp==Diver.class){
				lines1To5.set(lineNum, lines1To5.get(lineNum).append("DIV"));
			}else if(temp==Engineer.class){
				lines1To5.set(lineNum, lines1To5.get(lineNum).append("ENG"));
			}else if(temp==Explorer.class){
				lines1To5.set(lineNum, lines1To5.get(lineNum).append("EXP"));
			}else if(temp==Messenger.class){
				lines1To5.set(lineNum, lines1To5.get(lineNum).append("MSG"));
			}else if(temp==Navigator.class){
				lines1To5.set(lineNum, lines1To5.get(lineNum).append("NAV"));
			}else if(temp==Pilot.class){
				lines1To5.set(lineNum, lines1To5.get(lineNum).append("PIL"));
			}else{
				lines1To5.set(lineNum, lines1To5.get(lineNum).append("ERR"));
			}
		} else {
			lines1To5.set(lineNum, lines1To5.get(lineNum).append("   "));
		}
	}
	public void printTileNameCLI(ArrayList<StringBuilder> lines1To5){
		lines1To5.set(2, lines1To5.get(2).append(nameCLI));
	}
	public void playerComes(StdRole player){//assume a valid operation
		players.add(player);
		player.setCurrStdTile(this);
	}
	public void playerLeaves(StdRole player){//assume a valid operation
		players.remove(player);
		player.setCurrStdTile(null);
	}
	public void playerAllLeaves(){//assume a valid operation
		players.clear();
	}
}
