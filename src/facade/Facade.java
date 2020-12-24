package facade;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import facade.board.Board;
import facade.board.role.CheatCharacter;
import facade.board.role.Diver;
import facade.board.role.Engineer;
import facade.board.role.Explorer;
import facade.board.role.Messenger;
import facade.board.role.Navigator;
import facade.board.role.Pilot;
import facade.board.role.StdRole;
import facade.board.tile.Cave;
import facade.board.tile.Garden;
import facade.board.tile.NotATile;
import facade.board.tile.Palace;
import facade.board.tile.Stage;
import facade.board.tile.StdTile;
import facade.board.tile.Temple;
import facade.board.tile.Tile;
import facade.board.tile.TreasureTile;
import facade.deck.FloodDeck;
import facade.deck.TreasureDeck;
import facade.deck.treasureCard.ChaliceCard;
import facade.deck.treasureCard.FireCard;
import facade.deck.treasureCard.FunctionalCard;
import facade.deck.treasureCard.HelicopterLift;
import facade.deck.treasureCard.Sandbags;
import facade.deck.treasureCard.StoneCard;
import facade.deck.treasureCard.TreasureCard;
import facade.deck.treasureCard.WaterRise;
import facade.deck.treasureCard.WindCard;
import facade.myNumManager.myNumManager;
import facade.treasure.Chalice;
import facade.treasure.Fire;
import facade.treasure.Store;
import facade.treasure.Treasure;
import facade.treasure.Wind;
import facade.waterMeter.WaterMeter;
public class Facade{
	protected Scanner scanner = new Scanner(System.in);
	//This scanner would never be closed. Because once it has been closed, the scanner could be never correctly open again.
	//This is a little bit waste. But we still doing it, for secure the functionality.
	protected Board board = new Board();
	protected FloodDeck FDeck = new FloodDeck();
	protected FloodDeck UsedFDeck = new FloodDeck();
	protected TreasureDeck TDeck = new TreasureDeck();
	protected TreasureDeck UsedTDeck = new TreasureDeck();
	protected List<StringBuffer> Lines1To5 = new ArrayList<StringBuffer>();
	protected WaterMeter waterMeter = new WaterMeter();
	protected Treasure chalice = new Chalice();
	protected Treasure fire = new Fire();
	protected Treasure store = new Store();
	protected Treasure wind = new Wind();
	protected StdRole selectedPlayer = null;//which player should be focus on at this moment
	protected StdRole currPlayer = null;//which player should be focus on at the beginning of next turn, means that which player will get 3 APs in this at the beginning of this turn.
	protected int currPlayerInt = 0;
	public Facade(){
		for (int i=0; i<5; i++) {
			Lines1To5.add(new StringBuffer(""));
		}
		FDeck.load(board);
		TDeck.load();
		for (int i=0; i<6; i++) {
			this.useFloodCard();
		}
	}
	public void printBoardCLI(){
		this.board.printCLI(Lines1To5);
	}
	public void printFDeckCLI(){
		this.FDeck.printCLI();
	}
	public void printUsedFDeckCLI(){
		this.UsedFDeck.printCLI();
	}
	public void printTDeckCLI(){
		this.TDeck.printCLI();
	}
	public void printUsedTDeckCLI(){
		this.UsedTDeck.printCLI();
	}
	public void printWaterMeterCLI(){
		this.waterMeter.printCLI();
	}
	public void printPlayersCLI(){
		this.board.printPlayersCLI();
	}
	public void init(){
		//init player
		//Assign a role to each player
		//The number of players should range from 2 to 4.
		//Each Player can have a role: Engineer, Explorer, Diver, Pilot, Messenger, Navigator.
		int tempInt=0;
		boolean tempFlag=true;
		while(tempFlag){
			System.out.print("input number of players (2~4):");
			if (scanner.hasNextInt()) {
				tempInt = scanner.nextInt();
				if(tempInt<=0){
					System.out.println("not >0");
				} else if ((tempInt==1)||(tempInt>4)){
					System.out.println("not in range of 2~4");
				} else {
					System.out.println("number of players: " + tempInt);
					tempFlag=false;
				}
			} else {
				System.out.println("not int");
			}
		}
		//init all roles
		//Assign a role to each player:
		//Randomly assign to each player one of the following roles: Explorer, Diver, Pilot, Engineer, Messenger and Navigator.
		List<Integer> tempList4 = new ArrayList<Integer>();
		for(int i=0;i<6;i++){
			tempList4.add(i);
		}
		List<StdRole> tempPlayerList = new ArrayList<StdRole>();
		tempPlayerList.add(new Diver());
		tempPlayerList.add(new Engineer());
		tempPlayerList.add(new Explorer());
		tempPlayerList.add(new Messenger());
		tempPlayerList.add(new Navigator());
		tempPlayerList.add(new Pilot());
		List<StdRole> playerList = new ArrayList<StdRole>();
		for(int i=0;i<tempInt;i++){
			playerList.add(tempPlayerList.get(tempList4.remove(new Random().nextInt(tempList4.size()))));
		}
		while(!tempList4.isEmpty()){
			tempList4.remove(0);
		}
		while(!tempPlayerList.isEmpty()){
			tempPlayerList.remove(0);
		}
		//init players
		//Depending on his/her role, a player’s pawn will be placed initially on a specific Island tile.
		//The Engineer will be placed on Bronze Gate,
		//the Explorer will be placed on Copper Gate,
		//the Diver will be placed on Iron Gate,
		//the Pilot will be placed on Fools’ Landing,
		//the Messenger will be placed on Silver Gate,
		//and the Navigator will be placed on Gold Gate.
		for(int i=0;i<playerList.size();i++){
			@SuppressWarnings("rawtypes")
			Class tempClass = (playerList.get(i)).getClass();
			if (tempClass==CheatCharacter.class){
				(playerList.get(i)).setCurr(board.getAStdTileDebug(10+2*i+new Random().nextInt(2)));
				board.getAStdTileDebug(10+2*i+new Random().nextInt(2)).addPlayer(playerList.get(i));
			}else if(tempClass==Diver.class){
				(playerList.get(i)).setCurr(board.getAStdTileDebug(20));
				board.getAStdTileDebug(20).addPlayer(playerList.get(i));
			}else if(tempClass==Engineer.class){
				(playerList.get(i)).setCurr(board.getAStdTileDebug(22));
				board.getAStdTileDebug(22).addPlayer(playerList.get(i));
			}else if(tempClass==Explorer.class){
				(playerList.get(i)).setCurr(board.getAStdTileDebug(23));
				board.getAStdTileDebug(23).addPlayer(playerList.get(i));
			}else if(tempClass==Messenger.class){
				(playerList.get(i)).setCurr(board.getAStdTileDebug(21));
				board.getAStdTileDebug(21).addPlayer(playerList.get(i));
			}else if(tempClass==Navigator.class){
				(playerList.get(i)).setCurr(board.getAStdTileDebug(19));
				board.getAStdTileDebug(19).addPlayer(playerList.get(i));
			}else if(tempClass==Pilot.class){
				(playerList.get(i)).setCurr(board.getAStdTileDebug(18));
				board.getAStdTileDebug(18).addPlayer(playerList.get(i));
			}else{
				System.out.println("init players ERROR");
			}
		}
		this.board.addPlayers(playerList);
		//Hand Out Two Treasure Cards to Each player
		//Hand Out Treasure Cards: Shuffle the Treasure Deck and assign 2 cards to each player.
		//If anyone gets the Waters Rise cards, you should put it back in the Treasure Deck and assign another card.
		for(StdRole i: board.getPlayers()){
			for(int j=0; j<2; j++){
				if(TDeck.getStack().peek().getClass()!=WaterRise.class){
					i.addTreasureCard(TDeck.getStack().pop());
				} else {
					UsedTDeck.getStack().push(TDeck.getStack().pop());
					j--;
				}
			}
		}
		while(!UsedTDeck.getStack().empty()){
			TDeck.getStack().add(new Random().nextInt(TDeck.getStack().size()), UsedTDeck.getStack().pop());
		}
		//Set the Water Level
		tempInt=0;
		tempFlag=true;
		System.out.println("difficulty (1~4):");
		System.out.println("1: waterLevel=1 Novice");
		System.out.println("2: waterLevel=2 Normal");
		System.out.println("3: waterLevel=3 Elite");
		System.out.println("4: waterLevel=4 Legendary");
		tempInt = myNumManager.inputNumberInRange("difficulty", 1, 4);
		while(tempFlag){
			System.out.print("choose difficulty (1~4):");
			if (scanner.hasNextInt()) {
				tempInt = scanner.nextInt();
				if ((tempInt<1)||(tempInt>4)){
					System.out.println("not in range of 1~4");
				} else {
					tempFlag=false;
					switch (tempInt) {
						case 1:
							System.out.println("difficulty: 1: waterLevel=1 Novice");
							break;
						case 2:
							System.out.println("difficulty: 2: waterLevel=2 Normal");
							break;
						case 3:
							System.out.println("difficulty: 3: waterLevel=3 Elite");
							break;
						case 4:
							System.out.println("difficulty: 4: waterLevel=4 Legendary");
							break;
						default:
							System.out.println("Set the Water Level ERROR switch (tempInt)");
							break;
					}
					waterMeter.setWaterLevel(tempInt);
				}
			} else {
				System.out.println("not int");
			}
		}
	}
	public boolean ifLose(){
		// Losing the game: There are 4 possible ways to lose the game:
		// 1.	If both Temples, Caves, Palaces, or Garden tiles, where the treasures can be collected, sink before the treasures are collected.
		int tempInt=0;
		//Temples
		if((board.getAStdTileDebug(16).getStage()==Stage.SUNK)&&(board.getAStdTileDebug(17).getStage()==Stage.SUNK)){
			for(StdRole i: board.getPlayers()){
				for(TreasureCard j: i.getCards()){
					if (j.getClass() == StoneCard.class){
						tempInt++;
					}
				}
			}
			if(tempInt<4){
				return true;
			}
		}
		tempInt=0;
		//Caves
		if((board.getAStdTileDebug(10).getStage()==Stage.SUNK)&&(board.getAStdTileDebug(11).getStage()==Stage.SUNK)){
			for(StdRole i: board.getPlayers()){
				for(TreasureCard j: i.getCards()){
					if (j.getClass() == FireCard.class){
						tempInt++;
					}
				}
			}
			if(tempInt<4){
				return true;
			}
		}
		tempInt=0;
		//Palaces
		if((board.getAStdTileDebug(14).getStage()==Stage.SUNK)&&(board.getAStdTileDebug(15).getStage()==Stage.SUNK)){
			for(StdRole i: board.getPlayers()){
				for(TreasureCard j: i.getCards()){
					if (j.getClass() == ChaliceCard.class){
						tempInt++;
					}
				}
			}
			if(tempInt<4){
				return true;
			}
		}
		tempInt=0;
		//Gardens
		if((board.getAStdTileDebug(12).getStage()==Stage.SUNK)&&(board.getAStdTileDebug(13).getStage()==Stage.SUNK)){
			for(StdRole i: board.getPlayers()){
				for(TreasureCard j: i.getCards()){
					if (j.getClass() == WindCard.class){
						tempInt++;
					}
				}
			}
			if(tempInt<4){
				return true;
			}
		}
		tempInt=0;
		// 2.	If Fools’ Landing tile sinks.
		if(board.getAStdTileDebug(18).getStage()==Stage.SUNK){
			return true;
		}
		// 3.	If any player is on an Island tile that sinks and cannot move to another tile.
		for(StdRole i: board.getPlayers()){
			if (i.getCurr().getStage() == Stage.SUNK){
				return true;
			}
		}
		// 4.	If the water level reaches 5
		if(this.waterMeter.getLevel()==10){
			return true;
		}
		return false;
	}
	public boolean ifWin(){//assume the Helicopter lift card has been used
	//Winning the game:
	//Once you have collected all four treasures,
	//everyone must move their pawns to the Fools’ Landing tile.
	//Then one player must discard a Helicopter lift card to lift the whole team off Fools’ Landing for the win.
	//You can take a Helicopter lift even if Fools’ landing is flooded.
		if(ifLose()==false){//if ifLose==true, means the Fools’ landing is SUNK
			boolean storeFlag=false;
			boolean fireFlag=false;
			boolean chaliceFlag=false;
			boolean windFlag=false;
			for(StdRole i: board.getPlayers()){
				if (i.getTreasures().contains(this.store)){
					storeFlag=true;
					break;
				}
			}
			for(StdRole i: board.getPlayers()){
				if (i.getTreasures().contains(this.fire)){
					fireFlag=true;
					break;
				}
			}
			for(StdRole i: board.getPlayers()){
				if (i.getTreasures().contains(this.chalice)){
					chaliceFlag=true;
					break;
				}
			}
			for(StdRole i: board.getPlayers()){
				if (i.getTreasures().contains(this.wind)){
					windFlag=true;
					break;
				}
			}
			if((storeFlag)&&(fireFlag)&&(chaliceFlag)&&(windFlag)){
				if(board.getAStdTileDebug(18).getPlayers().size()==board.getPlayers().size()){
					return true;
				}
			}
		}
		return false;
	}
	public void begin(){
		this.currPlayer=board.getPlayers().get(this.currPlayerInt);
		this.selectedPlayer=board.getPlayers().get(this.currPlayerInt);//set the selectedPlayer to the "curr player" as default
		this.currPlayer.begin();//AP = 3
		for(int i=0; i<2; i++){//pick two treasure cards
			if(TDeck.getStack().peek().getClass()!=WaterRise.class){
				currPlayer.addTreasureCard(TDeck.getStack().pop());
			} else {
				this.waterMeter.addLevel();
				this.FDeck.shuffle(this.UsedFDeck);
				this.UsedTDeck.getStack().push(TDeck.getStack().pop());
			}
			if(TDeck.getStack().empty()){//check if a shuffle is needed
				TDeck.shuffle(this.UsedTDeck);
			}
			//TODO drop card if num of card >5
		}
	}
	public void action(){
		int[] tempCoordinate=null;
		int row=0, col=0;
		boolean ops0flag1=false;
		boolean ops1flag1=false;
		boolean ops1flag2=false;
		boolean ops1flag3=false;
		boolean ops1flag4=false;
		boolean ops1flag5=false;
		boolean ops2flag1=false;
		boolean ops2flag2=false;
		boolean ops2flag3=false;
		boolean ops2flag4=false;
		boolean ops2flag5=false;
		boolean ops3flag1=false;
		boolean ops3flag2=false;
		boolean ops3flag3=false;
		boolean ops3flag4=false;
		boolean ops3flag5=false;
		boolean ops3flag6=false;
		boolean ops3flag7=false;
		boolean ops4flag1=false;
		boolean ops6flag1=false;
		boolean upFlag=false;
		boolean downFlag=false;
		boolean leftFlag=false;
		boolean rightFlag=false;
		boolean midFlag=false;
		int tempTipForOperation=31;
		Tile tempTile=null;
		StdTile tempStdTile=null;
		int countFire=0;
		int countWind=0;
		int countStore=0;
		int countChalice=0;
		boolean ifNoFuntionalCard=true;
		while(this.currPlayer.getAP()>0){
			tempCoordinate=null;
			row=0;
			col=0;
			ops0flag1=false;
			ops1flag1=false;
			ops1flag2=false;
			ops1flag3=false;
			ops1flag4=false;
			ops1flag5=false;
			ops2flag1=false;
			ops2flag2=false;
			ops2flag3=false;
			ops2flag4=false;
			ops2flag5=false;
			ops3flag1=false;
			ops3flag2=false;
			ops3flag3=false;
			ops3flag4=false;
			ops3flag5=false;
			ops3flag6=false;
			ops3flag7=false;
			ops4flag1=false;
			ops6flag1=false;
			upFlag=false;
			downFlag=false;
			leftFlag=false;
			rightFlag=false;
			midFlag=false;
			tempTipForOperation=31;
			tempTile=null;
			tempStdTile=null;
			countFire=0;
			countWind=0;
			countStore=0;
			countChalice=0;
			ifNoFuntionalCard=true;
			this.printBoardCLI();
			System.out.println("This is "+ this.currPlayer.getName() + "'s turn:");
			System.out.println("The player has been selected: " + this.selectedPlayer.getName() + "  AP:" + this.selectedPlayer.getAP());
			System.out.print("0: move");//move
			if(this.selectedPlayer.getAP()<=0){
				System.out.println(" [No enough AP]");
				ops0flag1=true;
			} else {
				System.out.println();
			}
			System.out.print("1: shore up a flood tile");//shoreUp
			if(this.selectedPlayer.getAP()<=0){
				System.out.print(" [No enough AP]");
			}
			tempCoordinate=this.board.getACoordinate(this.selectedPlayer.getCurr());
			row=tempCoordinate[0];
			col=tempCoordinate[1];
			tempStdTile=this.selectedPlayer.getCurr();
			if(tempStdTile.getStage()!=Stage.FLOODED){
				ops1flag1=true;
				tempTipForOperation-=16;
			}
			if(row==0){//↑
				ops1flag2=true;
				tempTipForOperation-=8;
			} else {
				tempTile=this.board.getATile(row-1, col);//↑
				if(tempTile.getClass()==NotATile.class){
					ops1flag2=true;
					tempTipForOperation-=8;
				} else if(((StdTile)tempTile).getStage()!=Stage.FLOODED){
					ops1flag2=true;
					tempTipForOperation-=8;
				}
			}
			if(row==5){//↓
				ops1flag3=true;
				tempTipForOperation-=4;
			} else {
				tempTile=this.board.getATile(row+1, col);//↓
				if(tempTile.getClass()==NotATile.class){
					ops1flag3=true;
					tempTipForOperation-=4;
				} else if(((StdTile)tempTile).getStage()!=Stage.FLOODED){
					ops1flag3=true;
					tempTipForOperation-=4;
				}
			}
			if(col==0){//←
				ops1flag4=true;
				tempTipForOperation-=2;
			} else {
				tempTile=this.board.getATile(row, col-1);//←
				if(tempTile.getClass()==NotATile.class){
					ops1flag4=true;
					tempTipForOperation-=2;
				} else if(((StdTile)tempTile).getStage()!=Stage.FLOODED){
					ops1flag4=true;
					tempTipForOperation-=2;
				}
			}
			if(col==5){//→
				ops1flag5=true;
				tempTipForOperation-=1;
			} else {
				tempTile=this.board.getATile(row, col+1);//→
				if(tempTile.getClass()==NotATile.class){
					ops1flag5=true;
					tempTipForOperation-=1;
				} else if(((StdTile)tempTile).getStage()!=Stage.FLOODED){
					ops1flag5=true;
					tempTipForOperation-=1;
				}
			}
			if(tempTipForOperation==0){
				System.out.print(" [No flooded tile nearby]");
			}
			System.out.println();
			System.out.print("2: pass a treasure card to another player");//giveATreasureCard
			if(this.selectedPlayer.getAP()<=0){
				System.out.print(" [No enough AP]");
			}
			tempTipForOperation=31;
			tempStdTile=this.selectedPlayer.getCurr();
			if(tempStdTile.getPlayers().size()==0){
				ops2flag1=true;
				tempTipForOperation-=16;
			}
			if(row==0){//↑
				ops2flag2=true;
				tempTipForOperation-=8;
			} else {
				tempTile=this.board.getATile(row-1, col);//↑
				if(tempTile.getClass()==NotATile.class){
					ops2flag2=true;
					tempTipForOperation-=8;
				} else if(((StdTile)tempTile).getPlayers().size()==0){
					ops2flag2=true;
					tempTipForOperation-=8;
				}
			}
			if(row==5){//↓
				ops2flag3=true;
				tempTipForOperation-=4;
			} else {
				tempTile=this.board.getATile(row+1, col);//↓
				if(tempTile.getClass()==NotATile.class){
					ops2flag3=true;
					tempTipForOperation-=4;
				} else if(((StdTile)tempTile).getPlayers().size()==0){
					ops2flag3=true;
					tempTipForOperation-=4;
				}
			}
			if(col==0){//←
				ops2flag4=true;
				tempTipForOperation-=2;
			} else {
				tempTile=this.board.getATile(row, col-1);//←
				if(tempTile.getClass()==NotATile.class){
					ops2flag4=true;
					tempTipForOperation-=2;
				} else if(((StdTile)tempTile).getPlayers().size()==0){
					ops2flag4=true;
					tempTipForOperation-=2;
				}
			}
			if(col==5){//→
				ops2flag5=true;
				tempTipForOperation-=1;
			} else {
				tempTile=this.board.getATile(row, col+1);//→
				if(tempTile.getClass()==NotATile.class){
					ops2flag5=true;
					tempTipForOperation-=1;
				} else if(((StdTile)tempTile).getPlayers().size()==0){
					ops2flag5=true;
					tempTipForOperation-=1;
				}
			}
			if(tempTipForOperation==0){
				System.out.print(" [No player nearby]");
			}
			if(this.selectedPlayer.getCards().size()==0){
				System.out.print(" [No cards]");
			}
			System.out.println();
			System.out.print("3: capture the treasure if you can");//captureATreasure
			if(this.selectedPlayer.getAP()<=0){
				ops3flag1=true;
				System.out.print(" [No enough AP]");
			}
			for(TreasureCard i: this.selectedPlayer.getCards()){
				if (i.getClass()==FireCard.class){
					countFire++;
				}
				if (i.getClass()==WindCard.class){
					countWind++;
				}
				if (i.getClass()==StoneCard.class){
					countStore++;
				}
				if (i.getClass()==ChaliceCard.class){
					countChalice++;
				}
			}
			if((countFire>=4)||(countWind>=4)||(countStore>=4)||(countChalice>=4)){
				;
			} else {
				ops3flag2=true;
				System.out.print(" [No enough correlate treasure card]");
			}
			if(!(this.selectedPlayer.getCurr() instanceof TreasureTile)){
				ops3flag3=true;
				System.out.print(" [Not at treasure tile]");
			} else {
				if((this.selectedPlayer.getCurr().getClass() == Cave.class)&&(countFire<4)){
					ops3flag4=true;
					System.out.print(" [No enough Firecard]");
				}
				if((this.selectedPlayer.getCurr().getClass() == Garden.class)&&(countWind<4)){
					ops3flag5=true;
					System.out.print(" [No enough Windcard]");
				}
				if((this.selectedPlayer.getCurr().getClass() == Palace.class)&&(countChalice<4)){
					ops3flag6=true;
					System.out.print(" [No enough Chalice card]");
				}
				if((this.selectedPlayer.getCurr().getClass() == Temple.class)&&(countStore<4)){
					ops3flag7=true;
					System.out.print(" [No enough Store card]");
				}
			}
			System.out.println();
			System.out.print("4: use a functional treasure card");//use
			for(TreasureCard i: this.selectedPlayer.getCards()){
				if (i instanceof FunctionalCard){
					ifNoFuntionalCard=false;
					break;
				}
			}
			if(ifNoFuntionalCard){
				ops4flag1=true;
				System.out.print(" [No Funtional Card]");
			}
			System.out.println();
			System.out.println("5: select another player");//switch the selected player
			if(this.selectedPlayer.getAP()<=0){
				System.out.print("6: end this turn");//end
			} else {
				System.out.print("6: drop all remaining APs to end this turn");//end
			}
			if(currPlayer!=selectedPlayer){
				ops6flag1=true;
				System.out.print(" [not your turn]");//end
			}
			System.out.println();
			int tempInt = 0;
			boolean tempFlag=true;
			while(tempFlag){
				System.out.print("input number of operation (0~6):");
				if (scanner.hasNextInt()) {
					tempInt = scanner.nextInt();
					if ((tempInt<0)||(tempInt>6)){
						System.out.println("not in range of (0~6)");
					} else {
						tempFlag=false;
					}
				} else {
					System.out.println("not int");
				}
			}
			int tempInt2;
			switch(tempInt){
				case 0://move
					tempCoordinate[0]=-1;
					tempCoordinate[1]=-1;
					row=-1;
					col=-1;
					upFlag=false;
					downFlag=false;
					leftFlag=false;
					rightFlag=false;
					System.out.println("The player has been selected: " + this.selectedPlayer.getName());
					System.out.println("The operation has been selected: move");
					if(this.selectedPlayer.getAP()<=0){
						System.out.println(" No enough AP");
						break;
					}
					tempCoordinate=this.board.getACoordinate(this.selectedPlayer.getCurr());
					row=tempCoordinate[0];
					col=tempCoordinate[1];
					tempTile=null;
					System.out.print("8: ↑");//↑
					if(row!=0){
						tempTile=this.board.getATile(row-1, col);
						if(tempTile.getClass()!=NotATile.class){
							if(((StdTile)this.board.getATile(row-1, col)).getStage()!=Stage.SUNK){
								upFlag=true;
							}
						}
					}
					if(upFlag==false){
						System.out.println(" [Not Allowed]");
					} else {
						System.out.println();
					}
					System.out.print("2: ↓");//↓
					if(row!=5){
						tempTile=this.board.getATile(row+1, col);
						if(tempTile.getClass()!=NotATile.class){
							if(((StdTile)this.board.getATile(row+1, col)).getStage()!=Stage.SUNK){
								downFlag=true;
							}
						}
					}
					if(downFlag==false){
						System.out.println(" [Not Allowed]");
					} else {
						System.out.println();
					}
					System.out.print("4: ←");//←
					if(col!=0){
						tempTile=this.board.getATile(row, col-1);
						if(tempTile.getClass()!=NotATile.class){
							if(((StdTile)this.board.getATile(row, col-1)).getStage()!=Stage.SUNK){
								leftFlag=true;
							}
						}
					}
					if(leftFlag==false){
						System.out.println(" [Not Allowed]");
					} else {
						System.out.println();
					}
					System.out.print("6: →");//→
					if(col!=5){
						tempTile=this.board.getATile(row, col+1);
						if(tempTile.getClass()!=NotATile.class){
							if(((StdTile)this.board.getATile(row, col+1)).getStage()!=Stage.SUNK){
								rightFlag=true;
							}
						}
					}
					if(rightFlag==false){
						System.out.println(" [Not Allowed]");
					} else {
						System.out.println();
					}
					System.out.println("5 or 0: cancel");//cancel
					tempInt2=0;
					tempFlag=true;
					while(tempFlag){
						System.out.print("input number of operation (8 or 2 or 4 or 6 or 5 or 0):");
						if (scanner.hasNextInt()) {
							tempInt2 = scanner.nextInt();
							if ((tempInt2!=8)&&(tempInt2!=2)&&(tempInt2!=4)&&(tempInt2!=6)&&(tempInt2!=5)&&(tempInt2!=0)){
								System.out.println("not in range");
							} else if(((tempInt2==8)&&(!upFlag))||((tempInt2==2)&&(!downFlag))||((tempInt2==4)&&(!leftFlag))||((tempInt2==6)&&(!rightFlag))){
								System.out.println("not allowed -- invaild destination");
							} else {
								tempFlag=false;
							}
						} else {
							System.out.println("not int");
						}
					}
					switch(tempInt2){
						case 8:
							this.selectedPlayer.move((StdTile)this.board.getATile(row-1, col));
							break;
						case 2:
							this.selectedPlayer.move((StdTile)this.board.getATile(row+1, col));
							break;
						case 4:
							this.selectedPlayer.move((StdTile)this.board.getATile(row, col-1));
							break;
						case 6:
							this.selectedPlayer.move((StdTile)this.board.getATile(row, col+1));
							break;
						case 5:
						case 0:
							break;
						default:
							System.out.println("tempInt2 ERROR");
							break;
					}
					break;
				case 1://shoreUp
					System.out.println("The player has been selected: " + this.selectedPlayer.getName());
					System.out.println("The operation has been selected: shore up a flood tile");
					if(this.selectedPlayer.getAP()<=0){
						System.out.println(" No enough AP");
						break;
					}
					System.out.print("5: curr position");//curr position
					if(ops1flag1){
						System.out.println(" [Not Allowed]");
					} else {
						System.out.println();
					}
					System.out.print("8: ↑");//↑
					if(ops1flag2){
						System.out.println(" [Not Allowed]");
					} else {
						System.out.println();
					}
					System.out.print("2: ↓");//↓
					if(ops1flag3){
						System.out.println(" [Not Allowed]");
					} else {
						System.out.println();
					}
					System.out.print("4: ←");//←
					if(ops1flag4){
						System.out.println(" [Not Allowed]");
					} else {
						System.out.println();
					}
					System.out.print("6: →");//→
					if(ops1flag5){
						System.out.println(" [Not Allowed]");
					} else {
						System.out.println();
					}
					System.out.println("0: cancel");//cancel
					tempInt2=0;
					tempFlag=true;
					while(tempFlag){
						System.out.print("input number of operation (8 or 2 or 4 or 6 or 5 or 0):");
						if (scanner.hasNextInt()) {
							tempInt2 = scanner.nextInt();
							if ((tempInt2!=8)&&(tempInt2!=2)&&(tempInt2!=4)&&(tempInt2!=6)&&(tempInt2!=5)&&(tempInt2!=0)){
								System.out.println("not in range");
							} else if(((tempInt2==5)&&(ops1flag1))||((tempInt2==8)&&(ops1flag2))||((tempInt2==2)&&(ops1flag3))||((tempInt2==4)&&(ops1flag4))||((tempInt2==6)&&(ops1flag5))){
								System.out.println("not allowed -- invaild destination");
							} else {
								tempFlag=false;
							}
						} else {
							System.out.println("not int");
						}
					}
					switch(tempInt2){
						case 5:
							this.selectedPlayer.shoreUp(this.selectedPlayer.getCurr());//storeUp();
							break;
						case 8:
							this.selectedPlayer.shoreUp((StdTile) this.board.getATile(row-1, col));
							break;
						case 2:
							this.selectedPlayer.shoreUp((StdTile) this.board.getATile(row+1, col));//storeUp();
							break;
						case 4:
							this.selectedPlayer.shoreUp((StdTile) this.board.getATile(row, col-1));//storeUp();
							break;
						case 6:
							this.selectedPlayer.shoreUp((StdTile) this.board.getATile(row, col+1));//storeUp();
							break;
						case 0:
							break;
						default:
							System.out.println("tempInt2 ERROR");
							break;
					}
					break;
				case 2://give

					break;
				case 3://capture

					break;
				case 4://use
					boolean card0Flag=false;
					boolean card1Flag=false;
					boolean card2Flag=false;
					boolean card3Flag=false;
					boolean card4Flag=false;
					System.out.println("The player has been selected: " + this.selectedPlayer.getName());
					System.out.println("The operation has been selected: use a functional treasure card");
					System.out.println("treasure cards in tot: " + this.selectedPlayer.getCards().size());
					if(this.selectedPlayer.getCards().size()>0){
						System.out.print("1: " + this.selectedPlayer.getCards().get(0).getName());
						if(!(this.selectedPlayer.getCards().get(0) instanceof FunctionalCard)){
							card0Flag=true;
							System.out.print(" [Not Allowed]");
						}
					}
					System.out.println();
					if(this.selectedPlayer.getCards().size()>1){
						System.out.print("2: " + this.selectedPlayer.getCards().get(1).getName());
						if(!(this.selectedPlayer.getCards().get(1) instanceof FunctionalCard)){
							card1Flag=true;
							System.out.print(" [Not Allowed]");
						}
					}
					System.out.println();
					if(this.selectedPlayer.getCards().size()>2){
						System.out.print("3: " + this.selectedPlayer.getCards().get(2).getName());
						if(!(this.selectedPlayer.getCards().get(2) instanceof FunctionalCard)){
							card2Flag=true;
							System.out.print(" [Not Allowed]");
						}
					}
					System.out.println();
					if(this.selectedPlayer.getCards().size()>3){
						System.out.print("4: " + this.selectedPlayer.getCards().get(3).getName());
						if(!(this.selectedPlayer.getCards().get(3) instanceof FunctionalCard)){
							card3Flag=true;
							System.out.print(" [Not Allowed]");
						}
					}
					System.out.println();
					if(this.selectedPlayer.getCards().size()>4){
						System.out.print("5: " + this.selectedPlayer.getCards().get(4).getName());
						if(!(this.selectedPlayer.getCards().get(4) instanceof FunctionalCard)){
							card4Flag=true;
							System.out.print(" [Not Allowed]");
						}
					}
					System.out.println();
					System.out.println("0: cancel");
					tempInt2=0;
					tempFlag=true;
					while(tempFlag){
						System.out.print("input number for the card (0~" + (this.selectedPlayer.getCards().size()));
						System.out.print("):");
						if (scanner.hasNextInt()) {
							tempInt2 = scanner.nextInt();
							if ((tempInt2<0)||(tempInt2>this.selectedPlayer.getCards().size())){
								System.out.print("not in range (0~" + (this.selectedPlayer.getCards().size()));
								System.out.print("):");
							} else if(((tempInt2==1)&&(card0Flag))||((tempInt2==2)&&(card1Flag))||((tempInt2==3)&&(card2Flag))||((tempInt2==4)&&(card3Flag))||((tempInt2==5)&&(card4Flag))){
								System.out.println("not allowed -- invaild destination");
							} else {
								tempFlag=false;
							}
						} else {
							System.out.println("not int");
						}
					}
					switch(tempInt2){
						case 0:
							break;
						case 1:
							if(this.selectedPlayer.getCards().get(0).getClass()==HelicopterLift.class){
								// this.selectedPlayer.removeTreasureCard(this.selectedPlayer.getCards().get(0));
								this.useHelicopterLift(scanner);
								this.UsedTDeck.getStack().push(this.selectedPlayer.getCards().remove(0));
							} else if(this.selectedPlayer.getCards().get(0).getClass()==Sandbags.class){
								// this.selectedPlayer.removeTreasureCard(this.selectedPlayer.getCards().get(0));
								this.useSandbags(scanner);
								this.UsedTDeck.getStack().push(this.selectedPlayer.getCards().remove(0));
							} else {
								;//ERROR
							}
							break;
						case 2:
							if(this.selectedPlayer.getCards().get(1).getClass()==HelicopterLift.class){
								// this.selectedPlayer.removeTreasureCard(this.selectedPlayer.getCards().get(1));
								this.useHelicopterLift(scanner);
								this.UsedTDeck.getStack().push(this.selectedPlayer.getCards().remove(1));
							} else if(this.selectedPlayer.getCards().get(1).getClass()==Sandbags.class){
								// this.selectedPlayer.removeTreasureCard(this.selectedPlayer.getCards().get(1));
								this.useSandbags(scanner);
								this.UsedTDeck.getStack().push(this.selectedPlayer.getCards().remove(1));
							} else {
								;//ERROR
							}
							break;
						case 3:
							if(this.selectedPlayer.getCards().get(2).getClass()==HelicopterLift.class){
								// this.selectedPlayer.removeTreasureCard(this.selectedPlayer.getCards().get(2));
								this.useHelicopterLift(scanner);
								this.UsedTDeck.getStack().push(this.selectedPlayer.getCards().remove(2));
							} else if(this.selectedPlayer.getCards().get(2).getClass()==Sandbags.class){
								// this.selectedPlayer.removeTreasureCard(this.selectedPlayer.getCards().get(2));
								this.useSandbags(scanner);
								this.UsedTDeck.getStack().push(this.selectedPlayer.getCards().remove(2));
							} else {
								;//ERROR
							}
							break;
						case 4:
							if(this.selectedPlayer.getCards().get(3).getClass()==HelicopterLift.class){
								// this.selectedPlayer.removeTreasureCard(this.selectedPlayer.getCards().get(3));
								this.useHelicopterLift(scanner);
								this.UsedTDeck.getStack().push(this.selectedPlayer.getCards().remove(3));
							} else if(this.selectedPlayer.getCards().get(3).getClass()==Sandbags.class){
								// this.selectedPlayer.removeTreasureCard(this.selectedPlayer.getCards().get(3));
								this.useSandbags(scanner);
								this.UsedTDeck.getStack().push(this.selectedPlayer.getCards().remove(3));
							} else {
								;//ERROR
							}
							break;
						case 5:
							if(this.selectedPlayer.getCards().get(4).getClass()==HelicopterLift.class){
								this.useHelicopterLift(scanner);
								this.UsedTDeck.getStack().push(this.selectedPlayer.getCards().remove(4));
							} else if(this.selectedPlayer.getCards().get(4).getClass()==Sandbags.class){
								this.useSandbags(scanner);
								this.UsedTDeck.getStack().push(this.selectedPlayer.getCards().remove(4));
							} else {
								;//ERROR
							}
							break;
						default:
							System.out.println("tempInt2 ERROR");
							break;
					}
					break;
				case 5://switch
					System.out.println("The player has been selected: " + this.selectedPlayer.getName());
					System.out.println("The operation has been selected: select another player");
					System.out.println("0: " + this.board.getPlayers().get(0).getName());
					System.out.println("1: " + this.board.getPlayers().get(1).getName());
					if(this.board.getPlayers().size()>=3){
						System.out.println("2: " + this.board.getPlayers().get(2).getName());
					}
					if(this.board.getPlayers().size()==4){//should not >4
						System.out.println("3: " + this.board.getPlayers().get(3).getName());
					}
					tempInt2=0;
					tempFlag=true;
					while(tempFlag){
						System.out.print("input number for the player (0~" + (this.board.getPlayers().size()-1));
						System.out.print("):");
						if (scanner.hasNextInt()) {
							tempInt2 = scanner.nextInt();
							if ((tempInt2<0)||(tempInt2>this.board.getPlayers().size()-1)){
								System.out.print("not in range (0~" + (this.board.getPlayers().size()-1));
								System.out.print("):");
							} else {
								tempFlag=false;
							}
						} else {
							System.out.println("not int");
						}
					}
					this.selectedPlayer=board.getPlayers().get(tempInt2);
					break;
				case 6://end
					System.out.println("The player has been selected: " + this.selectedPlayer.getName());
					System.out.println("The operation has been selected: end this turn");
					if(ops6flag1){
						System.out.println(" not your turn");//end
						break;
					}
					System.out.println("0: cancel");
					System.out.println("1: proceed");
					tempInt2=0;
					tempFlag=true;
					while(tempFlag){
						System.out.print("Are you sure to end this turn? (0 or 1)");
						if (scanner.hasNextInt()) {
							tempInt2 = scanner.nextInt();
							if ((tempInt2!=0)&&(tempInt2!=1)){
								System.out.print("not in range (0 or 1):");
							} else {
								tempFlag=false;
							}
						} else {
							System.out.println("not int");
						}
					}
					if(tempInt2==0){
						break;
					} else {
						this.selectedPlayer.end();
						break;
					}
				default:
					System.out.println("tempInt ERROR");
					break;
			}

		}
	}
	public void end(){
		for (int i=0; i<this.waterMeter.getMeter(); i++) {//applied flood card
			this.useFloodCard();
		}
		if(this.currPlayerInt==board.getPlayers().size()-1){//switch to the next player
			this.currPlayerInt=0;
		} else {
			this.currPlayerInt++;
		}
	}
	public void useFloodCard(){
		this.FDeck.useCard(UsedFDeck);
	}
	public void useHelicopterLift(){
		StdTile tempBeginStdTile=null;
		System.out.println("useHelicopterLift: ");
		System.out.println("start at which player's tile?");
		System.out.println("0: " + this.board.getPlayers().get(0).getName());
		System.out.println("1: " + this.board.getPlayers().get(1).getName());
		if(this.board.getPlayers().size()>=3){
			System.out.println("2: " + this.board.getPlayers().get(2).getName());
		}
		if(this.board.getPlayers().size()==4){//should not >4
			System.out.println("3: " + this.board.getPlayers().get(3).getName());
		}
		int tempInt2=0;
		boolean tempFlag=true;
		while(tempFlag){
			System.out.print("input number for the player (0~" + (this.board.getPlayers().size()-1));
			System.out.print("):");
			if (scanner.hasNextInt()) {
				tempInt2 = scanner.nextInt();
				if ((tempInt2<0)||(tempInt2>this.board.getPlayers().size()-1)){
					System.out.print("not in range (0~" + (this.board.getPlayers().size()-1));
					System.out.print("):");
				} else {
					tempFlag=false;
				}
			} else {
				System.out.println("not int");
			}
		}
		tempBeginStdTile=(board.getPlayers().get(tempInt2).getCurr());
		int tempRow=-1;
		int tempCol=-1;
		System.out.println("destination coordinate?");
		boolean tempFlag2=true;
		while(tempFlag2){
			tempFlag=true;
			while(tempFlag){
				System.out.print("input number of row (1~6):");
				if (scanner.hasNextInt()) {
					tempRow = scanner.nextInt();
					if ((tempRow<1)||(tempRow>6)){
						System.out.print("not in range (1~6):");
					} else {
						tempFlag=false;
					}
				} else {
					System.out.println("not int");
				}
			}
			tempRow--;
			tempFlag=true;
			while(tempFlag){
				System.out.print("input number of col (1~6):");
				if (scanner.hasNextInt()) {
					tempCol = scanner.nextInt();
					if ((tempCol<1)||(tempCol>6)){
						System.out.print("not in range (1~6):");
					} else {
						tempFlag=false;
					}
				} else {
					System.out.println("not int");
				}
			}
			tempCol--;
			if(this.board.getATile(tempRow,tempCol).getClass() != NotATile.class){
				if(((StdTile) this.board.getATile(tempRow,tempCol)).getStage() != Stage.SUNK){
					tempFlag2=false;
				} else {
					System.out.println("the Tile SUNK");
				}
			} else {
				System.out.println("not A Tile");
			}
		}
		for(StdRole i: tempBeginStdTile.getPlayers()){
			i.freeMove((StdTile) this.board.getATile(tempRow,tempCol));
		}
	}
	public void useSandbags(){
		StdTile tempBeginStdTile=null;
		System.out.println("useSandbags: ");
		int tempRow=-1;
		int tempCol=-1;
		System.out.println("destination coordinate?");
		boolean tempFlag=true;
		boolean tempFlag2=true;
		while(tempFlag2){
			tempFlag=true;
			while(tempFlag){
				System.out.print("input number of row (1~6):");
				if (scanner.hasNextInt()) {
					tempRow = scanner.nextInt();
					if ((tempRow<1)||(tempRow>6)){
						System.out.print("not in range (1~6):");
					} else {
						tempFlag=false;
					}
				} else {
					System.out.println("not int");
				}
			}
			tempRow--;
			tempFlag=true;
			while(tempFlag){
				System.out.print("input number of col (1~6):");
				if (scanner.hasNextInt()) {
					tempCol = scanner.nextInt();
					if ((tempCol<1)||(tempCol>6)){
						System.out.print("not in range (1~6):");
					} else {
						tempFlag=false;
					}
				} else {
					System.out.println("not int");
				}
			}
			tempCol--;
			if(this.board.getATile(tempRow,tempCol).getClass() != NotATile.class){
				if(((StdTile) this.board.getATile(tempRow,tempCol)).getStage() == Stage.FLOODED){
					tempFlag2=false;
				} else {
					System.out.println("not FLOODED");
				}
			} else {
				System.out.println("not A Tile");
			}
		}
		tempBeginStdTile=(StdTile) this.board.getATile(tempRow,tempCol);
		tempBeginStdTile.storeUp();
	}
}
