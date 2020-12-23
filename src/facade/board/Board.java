package facade.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import facade.board.role.StdRole;
import facade.board.tile.Cave;
import facade.board.tile.FoolsLanding;
import facade.board.tile.Garden;
import facade.board.tile.Gate;
import facade.board.tile.NotATile;
import facade.board.tile.Palace;
import facade.board.tile.StdTile;
import facade.board.tile.Temple;
import facade.board.tile.Tile;

public class Board{
	//Create Forbidden Island in sequence
	//1 notATile
	protected NotATile notATile = new NotATile();
	//10 StdTile in tot
	protected StdTile twilightHollow = new StdTile("Twilight Hollow");
	protected StdTile breakersBridge = new StdTile("Breakers Bridge");
	protected StdTile lostLagoon = new StdTile("Lost Lagoon");
	protected StdTile watchtower = new StdTile("Watchtower");
	protected StdTile crimsonForest = new StdTile("Crimson Forest");
	protected StdTile cliffsOfAbandon = new StdTile("Cliffs of Abandon");
	protected StdTile mistyMarsh = new StdTile("Misty Marsh");
	protected StdTile phantomRock = new StdTile("Phantom Rock");
	protected StdTile dunesOfDeception = new StdTile("Dunes of Deception");
	protected StdTile observatory = new StdTile("Observatory");
	//8 TreasureTile in tot
	protected Cave caveOfShadows = new Cave("Cave of Shadows");
	protected Cave caveOfEmbers = new Cave("Cave of Embers");
	protected Garden whisperingGarden = new Garden("Whispering Garden");
	protected Garden howlingGarden = new Garden("Howling Garden");
	protected Palace tidalPalace = new Palace("Tidal Palace");
	protected Palace coralPalace = new Palace("Coral Palace");
	protected Temple templeOfTheMoon = new Temple("Temple of the Moon");
	protected Temple templeOfTheSun = new Temple("Temple of the Sun");
	//6 Gate in tot (including the "Fools' Landing")
	protected FoolsLanding foolsLanding = new FoolsLanding();
	protected Gate goldGate = new Gate("Gold Gate");
	protected Gate ironGate = new Gate("Iron Gate");
	protected Gate silverGate = new Gate("Silver Gate");
	protected Gate bronzeGate = new Gate("Bronze Gate");
	protected Gate copperGate = new Gate("Copper Gate");
	protected Tile[][] tile = {//fill the map as figure1 in the project introduction
		{notATile,         notATile,         twilightHollow, tidalPalace,     notATile,      notATile},
		{notATile,         breakersBridge,   bronzeGate,     caveOfEmbers,    caveOfShadows, notATile},
		{whisperingGarden, cliffsOfAbandon,  copperGate,     coralPalace,     crimsonForest, phantomRock},
		{watchtower,       dunesOfDeception, foolsLanding,   goldGate,        howlingGarden, silverGate},
		{notATile,         observatory,      mistyMarsh,     lostLagoon,      ironGate,      notATile},
		{notATile,         notATile,         templeOfTheSun, templeOfTheMoon, notATile,      notATile},
	};
	protected List<StdRole> players = new ArrayList<StdRole>();
	public Board(){
		this.resequencingTiles(tile);
	}
	public void resequencingTiles(Tile[][] tile){//this method should be run once only
		List<Integer> tempList = new ArrayList<Integer>();
		for(int i=0;i<6*6;i++){
			if((i==0)||(i==1)||(i==4)||(i==5)||(i==6)||(i==11)||(i==24)||(i==29)||(i==30)||(i==31)||(i==34)||(i==35)) {
				continue;
			} else {
				tempList.add(i);
			}
		}
		List<Integer> randomList = new ArrayList<Integer>();
		for(int i=0;i<(6*6)-(3*4);i++){
			randomList.add(tempList.remove(new Random().nextInt(tempList.size())));
		}
		Tile tempTile = new NotATile();
		int i=0;
		//row 0
		tempTile = tile[0][2];
		tile[0][2] = tile[randomList.get(i)/6][randomList.get(i)%6];
		tile[randomList.get(i)/6][randomList.get(i)%6] = tempTile;
		i++;

		tempTile = tile[0][3];
		tile[0][3] = tile[randomList.get(i)/6][randomList.get(i)%6];
		tile[randomList.get(i)/6][randomList.get(i)%6] = tempTile;
		i++;
		//row 1
		tempTile = tile[1][1];
		tile[1][1] = tile[randomList.get(i)/6][randomList.get(i)%6];
		tile[randomList.get(i)/6][randomList.get(i)%6] = tempTile;
		i++;

		tempTile = tile[1][2];
		tile[1][2] = tile[randomList.get(i)/6][randomList.get(i)%6];
		tile[randomList.get(i)/6][randomList.get(i)%6] = tempTile;
		i++;

		tempTile = tile[1][3];
		tile[1][3] = tile[randomList.get(i)/6][randomList.get(i)%6];
		tile[randomList.get(i)/6][randomList.get(i)%6] = tempTile;
		i++;

		tempTile = tile[1][4];
		tile[1][4] = tile[randomList.get(i)/6][randomList.get(i)%6];
		tile[randomList.get(i)/6][randomList.get(i)%6] = tempTile;
		i++;
		//row 2
		tempTile = tile[2][0];
		tile[2][0] = tile[randomList.get(i)/6][randomList.get(i)%6];
		tile[randomList.get(i)/6][randomList.get(i)%6] = tempTile;
		i++;

		tempTile = tile[2][1];
		tile[2][1] = tile[randomList.get(i)/6][randomList.get(i)%6];
		tile[randomList.get(i)/6][randomList.get(i)%6] = tempTile;
		i++;

		tempTile = tile[2][2];
		tile[2][2] = tile[randomList.get(i)/6][randomList.get(i)%6];
		tile[randomList.get(i)/6][randomList.get(i)%6] = tempTile;
		i++;

		tempTile = tile[2][3];
		tile[2][3] = tile[randomList.get(i)/6][randomList.get(i)%6];
		tile[randomList.get(i)/6][randomList.get(i)%6] = tempTile;
		i++;

		tempTile = tile[2][4];
		tile[2][4] = tile[randomList.get(i)/6][randomList.get(i)%6];
		tile[randomList.get(i)/6][randomList.get(i)%6] = tempTile;
		i++;

		tempTile = tile[2][5];
		tile[2][5] = tile[randomList.get(i)/6][randomList.get(i)%6];
		tile[randomList.get(i)/6][randomList.get(i)%6] = tempTile;
		i++;
		//row 3
		tempTile = tile[3][0];
		tile[3][0] = tile[randomList.get(i)/6][randomList.get(i)%6];
		tile[randomList.get(i)/6][randomList.get(i)%6] = tempTile;
		i++;

		tempTile = tile[3][1];
		tile[3][1] = tile[randomList.get(i)/6][randomList.get(i)%6];
		tile[randomList.get(i)/6][randomList.get(i)%6] = tempTile;
		i++;

		tempTile = tile[3][2];
		tile[3][2] = tile[randomList.get(i)/6][randomList.get(i)%6];
		tile[randomList.get(i)/6][randomList.get(i)%6] = tempTile;
		i++;

		tempTile = tile[3][3];
		tile[3][3] = tile[randomList.get(i)/6][randomList.get(i)%6];
		tile[randomList.get(i)/6][randomList.get(i)%6] = tempTile;
		i++;

		tempTile = tile[3][4];
		tile[3][4] = tile[randomList.get(i)/6][randomList.get(i)%6];
		tile[randomList.get(i)/6][randomList.get(i)%6] = tempTile;
		i++;

		tempTile = tile[3][5];
		tile[3][5] = tile[randomList.get(i)/6][randomList.get(i)%6];
		tile[randomList.get(i)/6][randomList.get(i)%6] = tempTile;
		i++;
		//row 4
		tempTile = tile[4][1];
		tile[4][1] = tile[randomList.get(i)/6][randomList.get(i)%6];
		tile[randomList.get(i)/6][randomList.get(i)%6] = tempTile;
		i++;

		tempTile = tile[4][2];
		tile[4][2] = tile[randomList.get(i)/6][randomList.get(i)%6];
		tile[randomList.get(i)/6][randomList.get(i)%6] = tempTile;
		i++;

		tempTile = tile[4][3];
		tile[4][3] = tile[randomList.get(i)/6][randomList.get(i)%6];
		tile[randomList.get(i)/6][randomList.get(i)%6] = tempTile;
		i++;

		tempTile = tile[4][4];
		tile[4][4] = tile[randomList.get(i)/6][randomList.get(i)%6];
		tile[randomList.get(i)/6][randomList.get(i)%6] = tempTile;
		i++;

		//row 5
		tempTile = tile[5][2];
		tile[5][2] = tile[randomList.get(i)/6][randomList.get(i)%6];
		tile[randomList.get(i)/6][randomList.get(i)%6] = tempTile;
		i++;

		tempTile = tile[5][3];
		tile[5][3] = tile[randomList.get(i)/6][randomList.get(i)%6];
		tile[randomList.get(i)/6][randomList.get(i)%6] = tempTile;
	}
	public void printCLI(List<StringBuffer> Lines1To5){
		for(int i=0;i<6;i++){
			for(int j=0;j<6;j++){
				tile[i][j].printCLI(Lines1To5);
			}
			System.out.println(Lines1To5.get(0));
			System.out.println(Lines1To5.get(1));
			System.out.println(Lines1To5.get(2));
			System.out.println(Lines1To5.get(3));
			System.out.println(Lines1To5.get(4));
			Lines1To5.get(0).delete(0, Lines1To5.get(0).capacity());
			Lines1To5.get(1).delete(0, Lines1To5.get(1).capacity());
			Lines1To5.get(2).delete(0, Lines1To5.get(2).capacity());
			Lines1To5.get(3).delete(0, Lines1To5.get(3).capacity());
			Lines1To5.get(4).delete(0, Lines1To5.get(4).capacity());
		}
		this.printPlayersCLI();
	}
	public StdTile getAStdTileDebug(int i){
		switch(i){
			//10 StdTile in tot
			case 0: return twilightHollow;
			case 1: return breakersBridge;
			case 2: return lostLagoon;
			case 3: return watchtower;
			case 4: return crimsonForest;
			case 5: return cliffsOfAbandon;
			case 6: return mistyMarsh;
			case 7: return phantomRock;
			case 8: return dunesOfDeception;
			case 9: return observatory;
			//8 TreasureTile in tot
			case 10: return caveOfShadows;
			case 11: return caveOfEmbers;
			case 12: return whisperingGarden;
			case 13: return howlingGarden;
			case 14: return tidalPalace;
			case 15: return coralPalace;
			case 16: return templeOfTheMoon;
			case 17: return templeOfTheSun;
			//6 Gate in tot (including the "Fools' Landing")
			case 18: return foolsLanding;
			case 19: return goldGate;
			case 20: return ironGate;
			case 21: return silverGate;
			case 22: return bronzeGate;
			case 23: return copperGate;
		}
		System.out.println("func getAStdTileDebug ERROR -- Out of Range");
		return null;//ERROR
	}
	public Tile getATile(int row, int col){
		if((row<0)||(row>6)||(col<0)||(col>6)){
			System.out.println("func getATile ERROR -- Out of Range");
			return null;
		} else {
			return this.tile[row][col];
		}
	}
	public int[] getACoordinate(StdTile tempTile){
		int[] temp = {0, 0};
		for(int row=0;row<6;row++){
			for(int col=0;col<6;col++){
				if(this.tile[row][col]==tempTile) {
					temp[0]=row;
					temp[1]=col;
					return temp;
				}
			}
		}
		System.out.println("func getACoordinate ERROR");
		return temp;//ERROR
	}
	public void addPlayers(List<StdRole> playerList){//this methos should run once only
		this.players = playerList;
	}
	public List<StdRole> getPlayers(){
		return players;
	}
	public void printPlayersCLI(){
		if(players==null)
			return;
		for(StdRole i: players){
			i.printCLI();
		}
	}
}