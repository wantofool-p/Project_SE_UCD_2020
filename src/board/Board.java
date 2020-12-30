package board;

import java.util.ArrayList;
import java.util.Random;

import board.tile.Cave;
import board.tile.FoolsLanding;
import board.tile.Garden;
import board.tile.Gate;
import board.tile.NullTile;
import board.tile.Palace;
import board.tile.Status;
import board.tile.StdTile;
import board.tile.Temple;
import board.tile.Tile;
import role.DirectionType;
import role.StdRole;
import treasure.Chalice;
import treasure.Fire;
import treasure.Stone;
import treasure.Wind;

public class Board{
	//reusing 1 NullTile
	protected NullTile NullTile = new NullTile();
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
	protected Palace tidalPalace = new Palace("Tidal Palace");
	protected Palace coralPalace = new Palace("Coral Palace");
	protected Cave caveOfShadows = new Cave("Cave of Shadows");
	protected Cave caveOfEmbers = new Cave("Cave of Embers");
	protected Temple templeOfTheMoon = new Temple("Temple of the Moon");
	protected Temple templeOfTheSun = new Temple("Temple of the Sun");
	protected Garden whisperingGarden = new Garden("Whispering Garden");
	protected Garden howlingGarden = new Garden("Howling Garden");
	//6 Gate in tot (including the "Fools' Landing")
	protected FoolsLanding foolsLanding = new FoolsLanding();
	protected Gate goldGate = new Gate("Gold Gate");
	protected Gate ironGate = new Gate("Iron Gate");
	protected Gate silverGate = new Gate("Silver Gate");
	protected Gate bronzeGate = new Gate("Bronze Gate");
	protected Gate copperGate = new Gate("Copper Gate");
	protected Tile[][] tile = {//Create Forbidden Island in sequence //fill the map as the figure1 in the project introduction
		{NullTile,         NullTile,         twilightHollow, tidalPalace,     NullTile,      NullTile},
		{NullTile,         breakersBridge,   bronzeGate,     caveOfEmbers,    caveOfShadows, NullTile},
		{whisperingGarden, cliffsOfAbandon,  copperGate,     coralPalace,     crimsonForest, phantomRock},
		{watchtower,       dunesOfDeception, foolsLanding,   goldGate,        howlingGarden, silverGate},
		{NullTile,         observatory,      mistyMarsh,     lostLagoon,      ironGate,      NullTile},
		{NullTile,         NullTile,         templeOfTheSun, templeOfTheMoon, NullTile,      NullTile},
	};
	// Player ArrayList
	protected ArrayList<StdRole> playerList = new ArrayList<StdRole>();
	// Flag
	private boolean ifInit = false;
	public Board(){
		this.initCoord();
		this.tidalPalace.setTreasure(new Chalice());
		this.caveOfShadows.setTreasure(new Fire());
		this.templeOfTheMoon.setTreasure(new Stone());
		this.whisperingGarden.setTreasure(new Wind());
	}
	public ArrayList<StdRole> getPlayerList(){
		return this.playerList;
	}
	public void setPlayerList(ArrayList<StdRole> playerList){//this method must be call once
		this.playerList = playerList;
	}
	public void init(){//this method must be call once
		if(this.ifInit){
			System.err.println("func init ERR -- has been called already");
		} else {
			this.rearrange(this.tile);
			this.ifInit=true;
		}
	}
	private void rearrange(Tile[][] tile){
		ArrayList<Integer> tempList = new ArrayList<Integer>();
		ArrayList<Integer> tempList2 = new ArrayList<Integer>();
		for(int i=0;i<6*6;i++){
			if((i==0)||(i==1)||(i==4)||(i==5)||(i==6)||(i==11)||(i==24)||(i==29)||(i==30)||(i==31)||(i==34)||(i==35)){//ignore the NullTiles
				continue;
			} else {
				tempList.add(i);
				tempList2.add(i);
			}
		}
		ArrayList<Integer> randomList = new ArrayList<Integer>();
		for(int i=0;i<(6*6)-(3*4);i++){
			randomList.add(tempList.remove(new Random().nextInt(tempList.size())));
		}
		Tile tempTile;
		for(int i=0; i<tempList2.size(); i++){//random
			tempTile = this.tile[tempList2.get(i)/6][tempList2.get(i)%6];
			this.tile[tempList2.get(i)/6][tempList2.get(i)%6] = this.tile[randomList.get(i)/6][randomList.get(i)%6];
			this.tile[randomList.get(i)/6][randomList.get(i)%6] = tempTile;
		}
		this.initCoord();
	}
	private void initCoord(){
		for(int i=0; i<6; i++){
			for(int j=0; j<6; j++){
				this.tile[i][j].setCoord(i,j);
			}
		}
	}
	public StdTile getStdTile(int id){
		switch(id){
			//10 StdTile in tot
			case 0: return this.twilightHollow;
			case 1: return this.breakersBridge;
			case 2: return this.lostLagoon;
			case 3: return this.watchtower;
			case 4: return this.crimsonForest;
			case 5: return this.cliffsOfAbandon;
			case 6: return this.mistyMarsh;
			case 7: return this.phantomRock;
			case 8: return this.dunesOfDeception;
			case 9: return this.observatory;
			//8 TreasureTile in tot
			case 10: return this.caveOfShadows;
			case 11: return this.caveOfEmbers;
			case 12: return this.whisperingGarden;
			case 13: return this.howlingGarden;
			case 14: return this.tidalPalace;
			case 15: return this.coralPalace;
			case 16: return this.templeOfTheMoon;
			case 17: return this.templeOfTheSun;
			//6 Gate in tot (including the "Fools' Landing")
			case 18: return this.foolsLanding;
			case 19: return this.goldGate;
			case 20: return this.ironGate;
			case 21: return this.silverGate;
			case 22: return this.bronzeGate;
			case 23: return this.copperGate;
		}
		System.err.println("func getAStdTileDebug ERROR -- Out of Range");
		return null;//ERROR
	}
	public StdTile getStdTile(int[] coord){
		return this.getStdTile(coord[0], coord[1]);
	}
	public StdTile getStdTile(int row, int col){
		if((row<0)||(row>5)||(col<0)||(col>5)){
			return null;//Out of Range
		} else {
			int temp = row*6+col;
			if((temp==0)||(temp==1)||(temp==4)||(temp==5)||(temp==6)||(temp==11)||(temp==24)||(temp==29)||(temp==30)||(temp==31)||(temp==34)||(temp==35)){//ignore the NullTiles
				return null;//NotTile found
			} else {
				return (StdTile) this.tile[row][col];
			}
		}
	}
	public ArrayList<StdTile> getAllNotSinkStdTile(){
		ArrayList<StdTile> tempTileList= new ArrayList<StdTile>();
		StdTile tempTile;
		for(int id=0; id<24; id++){
			tempTile = this.getStdTile(id);
			if(tempTile.getStatus()!=Status.SUNK)
				tempTileList.add(tempTile);
		}
		return tempTileList;
	}
	public int[] getCoord(StdTile tempTile){
		return tempTile.getCoord();
	}
	public void printCLILite(){
		ArrayList<StringBuilder> lines1To5 = new ArrayList<StringBuilder>();//should be 5 elements only
		for (int i=0; i<5; i++) {
			lines1To5.add(new StringBuilder(""));
		}
		for(int i=0;i<6;i++){
			for(int j=0;j<6;j++){
				this.tile[i][j].printCLI(lines1To5);
			}
			for(int j=0;j<5;j++){
				System.out.println(lines1To5.get(j));
				lines1To5.get(j).delete(0, lines1To5.get(j).capacity());
			}
		}
		this.printPlayersCLI();
	}
	public void printWithAllCoord(){
		ArrayList<StringBuilder> lines1To5 = new ArrayList<StringBuilder>();//should be 5 elements only
		for (int i=0; i<5; i++) {
			lines1To5.add(new StringBuilder(""));
		}
		for(int i=0;i<6;i++){
			for(int j=0;j<6;j++){
				this.tile[i][j].printCLI(lines1To5);
				lines1To5.get(0).replace(3+j*9, 6+j*9, (i + "," + j));
			}
			for(int j=0;j<5;j++){
				System.out.println(lines1To5.get(j));
				lines1To5.get(j).delete(0, lines1To5.get(j).capacity());
			}
		}
		this.printPlayersCLI();
	}
	public void printWithAllMeaningfulCoord(){
		this.printWithCoordA(this.getAllNotSinkStdTile());
	}
	public void printWithCoordA(ArrayList<StdTile> tempTileList){
		if(tempTileList==null){
			System.err.println("func printCLIWithCoordA ERR");
			return;
		}
		ArrayList<int[]> tempCoordList = new ArrayList<int[]>();
		for(StdTile i:tempTileList){
			tempCoordList.add(i.getCoord());
		}
		this.printWithCoordC(tempCoordList);
	}
	public void printWithCoordB(ArrayList<DirectionType> directionList, StdRole player){
		ArrayList<int[]> tempCoordList = new ArrayList<int[]>();
		StdTile tempTile;
		for(DirectionType i: directionList){
			tempTile = player.getDestination(this, i);
			if(tempTile!=null){
				tempCoordList.add(tempTile.getCoord());
			}
		}
		this.printWithCoordC(tempCoordList);
	}
	public void printWithCoordC(ArrayList<int[]> tempCoordList){
		if(tempCoordList==null){
			System.err.println("func printCLIWithCoordC ERR");
			return;
		}
		ArrayList<StringBuilder> lines1To5 = new ArrayList<StringBuilder>();//should be 5 elements only
		for (int i=0; i<5; i++) {
			lines1To5.add(new StringBuilder(""));
		}
		// int[] tempCoord = {-1, -1};
		for(int i=0;i<6;i++){
			for(int j=0;j<6;j++){
				this.tile[i][j].printCLI(lines1To5);
				// tempCoord[0] = i; tempCoord[1] = j;
				for(int[] k:tempCoordList){
					if((k[0]==i)&&(k[1]==j))
						lines1To5.get(0).replace(3+j*9, 6+j*9, (i + "," + j));
				}
				// if(tempCoordList.contains(tempCoord)){
				// 	lines1To5.get(0).replace(3+j*9, 6+j*9, (i + "," + j));
				// }
			}
			for(int j=0;j<5;j++){
				System.out.println(lines1To5.get(j));
				lines1To5.get(j).delete(0, lines1To5.get(j).capacity());
			}
		}
	}
	public void printPlayersCLI(){
		if(this.playerList.size()==0)
			return;//no player
		for(StdRole player: this.playerList){
			player.printCLI();
		}
	}
}