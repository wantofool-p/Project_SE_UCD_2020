//this interface would handle the number from input
package facade;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import board.Board;
import board.tile.Cave;
import board.tile.Garden;
import board.tile.Palace;
import board.tile.Status;
import board.tile.StdTile;
import board.tile.Temple;
import deck.FloodDeck;
import deck.TreasureDeck;
import deck.treasureCard.HelicopterLift;
import deck.treasureCard.Sandbags;
import deck.treasureCard.TreasureCard;
import deck.treasureCard.WaterRise;
import role.CheatCharacter;
import role.DirectionType;
import role.Diver;
import role.Engineer;
import role.Explorer;
import role.Messenger;
import role.Navigator;
import role.Pilot;
import role.StdRole;
import setting.Options;
import treasure.Chalice;
import treasure.Fire;
import treasure.Stone;
import treasure.Treasure;
import treasure.Wind;
import waterMeter.WaterMeter;
//this is a kind of sub facade
public class MyGameManager implements MyInput, MyOutput{//the interface for the "Facade" class
	protected Board board = new Board();
	protected FloodDeck floodDeck = new FloodDeck();
	protected FloodDeck usedFloodDeck = new FloodDeck();
	protected TreasureDeck treasureDeck = new TreasureDeck();
	protected TreasureDeck usedTreasureDeck = new TreasureDeck();
	protected ArrayList<Integer> coord = new ArrayList<Integer>();//should be 2 elements only //[0]=row, [1]=col
	protected WaterMeter waterMeter = new WaterMeter();
	protected Treasure chalice = new Chalice();
	protected Treasure fire = new Fire();
	protected Treasure store = new Stone();
	protected Treasure wind = new Wind();
	protected StdRole selectedPlayer = null;//which player should be focus on at this moment
	protected StdRole currPlayer = null;//which player should be focus on at the beginning of next turn, means that which player will get 3 APs in this at the beginning of this turn.
	protected int currPlayerInt = 0;
	protected String[] feedbackOperationString = {
		"The operation has been selected: end this turn",
		"The operation has been selected: move",
		"The operation has been selected: shore up a flood tile",
		"The operation has been selected: pass a treasure card",
		"The operation has been selected: capture a treasure",
		"The operation has been selected: use a functional treasure card",
		"The operation has been selected: switch to another player",
		"The operation has been selected: use ability"
	};
	protected int [] forMoveInputIntArray = {8, 2, 4, 6, 7, 3, 1, 9};
	protected int [] forShoreUpInputIntArray = {8, 2, 4, 6, 5, 7, 3, 1, 9};
	HashMap<Integer, DirectionType> int2direction = new HashMap<Integer, DirectionType>();
	public MyGameManager(){
		this.floodDeck.init(board);
		this.treasureDeck.init();
		int2direction.put(8, DirectionType.UP);
		int2direction.put(2, DirectionType.DOWN);
		int2direction.put(4, DirectionType.LEFT);
		int2direction.put(6, DirectionType.RIGHT);
		int2direction.put(5, DirectionType.MIDDLE);
		int2direction.put(7, DirectionType.TOPLEFT);
		int2direction.put(3, DirectionType.BOTTOMRIGHT);
		int2direction.put(1, DirectionType.BOTTOMLEFT);
		int2direction.put(9, DirectionType.TOPRIGHT);
	}
	public void init(){
		this.board.init();
		for (int i=0; i<6; i++) {
			this.useFloodCard();
		}
		//init player
		//Assign a role to each player
		//The number of players should range from 2 to 4.
		//Each Player can have a role: Engineer, Explorer, Diver, Pilot, Messenger, Navigator.
		int tempInt=0;
		try {
			tempInt = MyInput.inputOneDigitNumber("input number of players (2~4):", 2, 4);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//init all roles
		//Assign a role to each player:
		//Randomly assign to each player one of the following roles: Explorer, Diver, Pilot, Engineer, Messenger and Navigator.
		ArrayList<StdRole> playerList = new ArrayList<StdRole>();
		if(Options.ifCheat){
			for(int i=0;i<tempInt;i++){
				playerList.add(new CheatCharacter());
			}
		} else {
			ArrayList<StdRole> tempPlayerList = new ArrayList<StdRole>();
			tempPlayerList.add(new Diver());
			tempPlayerList.add(new Engineer());
			tempPlayerList.add(new Explorer());
			tempPlayerList.add(new Messenger());
			tempPlayerList.add(new Navigator());
			tempPlayerList.add(new Pilot());
			StdRole tempRole;
			int tempRandom;
			for(int i=0; i<tempPlayerList.size(); i++){//random
				tempRandom = new Random().nextInt(tempPlayerList.size());
				tempRole = tempPlayerList.get(i);
				tempPlayerList.set(i, tempPlayerList.get(tempRandom));
				tempPlayerList.set(tempRandom, tempRole);
			}
			for(int i=0;i<tempInt;i++){
				if((Options.ifDebug)&&(i==1)){
					playerList.add(new CheatCharacter());
				} else {
					playerList.add(tempPlayerList.get(i));
				}
			}
		}
		//init players
		//Depending on his/her role, a player’s pawn will be placed initially on a specific Island tile.
		//The Engineer will be placed on Bronze Gate,
		//the Explorer will be placed on Copper Gate,
		//the Diver will be placed on Iron Gate,
		//the Pilot will be placed on Fools’ Landing,
		//the Messenger will be placed on Silver Gate,
		//and the Navigator will be placed on Gold Gate.
		for(int i=0; i<playerList.size(); i++){
			@SuppressWarnings("rawtypes")
			Class tempClass=(playerList.get(i)).getClass();
			if (tempClass==CheatCharacter.class){
				(playerList.get(i)).setCurrStdTile(board.getStdTile(10+2*i+new Random().nextInt(2)));
				board.getStdTile(10+2*i+new Random().nextInt(2)).playerComes(playerList.get(i));
			}else if(tempClass==Diver.class){
				(playerList.get(i)).setCurrStdTile(board.getStdTile(20));
				board.getStdTile(20).playerComes(playerList.get(i));
			}else if(tempClass==Engineer.class){
				(playerList.get(i)).setCurrStdTile(board.getStdTile(22));
				board.getStdTile(22).playerComes(playerList.get(i));
			}else if(tempClass==Explorer.class){
				(playerList.get(i)).setCurrStdTile(board.getStdTile(23));
				board.getStdTile(23).playerComes(playerList.get(i));
			}else if(tempClass==Messenger.class){
				(playerList.get(i)).setCurrStdTile(board.getStdTile(21));
				board.getStdTile(21).playerComes(playerList.get(i));
			}else if(tempClass==Navigator.class){
				(playerList.get(i)).setCurrStdTile(board.getStdTile(19));
				board.getStdTile(19).playerComes(playerList.get(i));
			}else if(tempClass==Pilot.class){
				(playerList.get(i)).setCurrStdTile(board.getStdTile(18));
				board.getStdTile(18).playerComes(playerList.get(i));
			}else{
				System.err.println("init players ERROR");
			}
		}
		this.board.setPlayerList(playerList);
		//Hand Out Two Treasure Cards to Each player
		//Hand Out Treasure Cards: Shuffle the Treasure Deck and assign 2 cards to each player.
		//If anyone gets the Waters Rise cards, you should put it back in the Treasure Deck and assign another card.
		for(StdRole i: board.getPlayerList()){
			for(int j=0; j<2; j++){
				if(treasureDeck.getStack().peek().getClass()!=WaterRise.class){
					i.addTreasureCard(treasureDeck.popCard());
				} else {
					usedTreasureDeck.pushCard(treasureDeck.popCard());
					j--;
				}
			}
		}
		while(!usedTreasureDeck.isStackEmpty()){
			treasureDeck.getStack().add(new Random().nextInt(treasureDeck.getStack().size()), usedTreasureDeck.popCard());
		}
		//Set the Water Meter
		System.out.println("difficulty (1~4):");
		System.out.println("1: waterMeter=1 Novice");
		System.out.println("2: waterMeter=2 Normal");
		System.out.println("3: waterMeter=3 Elite");
		System.out.println("4: waterMeter=4 Legendary");
		try {
			tempInt = MyInput.inputOneDigitNumber("choose difficulty (1~4):", 1, 4);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.waterMeter.setWaterMeter(tempInt);
	}
	private void useFloodCard(){
		this.floodDeck.useCard(this.usedFloodDeck);
	}
	private boolean ifLose(){// Losing the game: There are 4 possible ways to lose the game:
		if(this.ifLose1() || this.ifLose2() || this.ifLose3() || this.ifLose4()){
			System.out.println("Your team lose the game.");
			System.exit(0);
			return true;
		} else {
			return false;
		}
	}
	private boolean ifLose1(){// 1. If both Temples, Caves, Palaces, or Garden tiles, where the treasures can be collected, sink before the treasures are collected.
		if((this.board.getStdTile(14).getStatus()==Status.SUNK)&&(this.board.getStdTile(15).getStatus()==Status.SUNK)&&!(Palace.getIfGet())){//Palace
			System.out.println("Both Palaces sink before the Chalice treasure is collected.");
			return true;
		} else if((this.board.getStdTile(10).getStatus()==Status.SUNK)&&(this.board.getStdTile(11).getStatus()==Status.SUNK)&&!(Cave.getIfGet())){//Cave
			System.out.println("Both Caves sink before the Fire treasure is collected.");
			return true;
		} else if((this.board.getStdTile(16).getStatus()==Status.SUNK)&&(this.board.getStdTile(17).getStatus()==Status.SUNK)&&!(Temple.getIfGet())){//Temple
			System.out.println("Both Temples sink before the Stone treasure is collected.");
			return true;
		} else if((this.board.getStdTile(12).getStatus()==Status.SUNK)&&(this.board.getStdTile(13).getStatus()==Status.SUNK)&&!(Garden.getIfGet())){//Garden
			System.out.println("Both Gardens sink before the Wind treasure is collected.");
			return true;
		} else {
			return false;
		}
	}
	private boolean ifLose2(){// 2. If Fools’ Landing tile sinks.
		if(board.getStdTile(18).getStatus()==Status.SUNK){
			System.out.println("The Fools’ Landing tile sinks.");
			return true;
		} else {
			return false;
		}
	}
	private boolean ifLose3(){// 3. If any player is on an Island tile that sinks and cannot move to another tile.
		for(StdRole player:this.board.getPlayerList()){
			if(!player.getIsAlive()){
				System.out.println("The player" + player.getName() + "sinks.");
				return true;
			}
		}
		return false;
	}
	private boolean ifLose4(){// 4. If the water level reaches 5
		if((this.waterMeter.getLevel() == 6) || (this.waterMeter.getLevel() == -1)){
			System.out.println("The current water level reaches the skull and crossbones.");
			return true;
		} else {
			return false;
		}
	}
	private void useWaterRise(TreasureCard card){
		System.out.println("useWaterRise: ");
		System.out.println("Water Rise !!");
		WaterRise.use(this.floodDeck, this.usedFloodDeck, this.waterMeter);
		this.dropCard(card);
	}
	private boolean useSandbags(TreasureCard card, int cardIndex, StdRole player) throws IOException{
		boolean result;
		System.out.println("useSandbags: ");
		System.out.println("destination coordinate?");
		int [] tempInt = MyInput.inputDestinationCoord(this.board);
		if(tempInt==null){
			result = false;
		} else {
			StdTile tempTile = this.board.getStdTile(tempInt);
			result = Sandbags.use(tempTile);
			if(result){
				this.dropPlayerCard(player, cardIndex, card);
			}
		}
		return result;
	}
	private boolean useHelicopterLift(TreasureCard card, int cardIndex, StdRole player) throws IOException{
		int tempInt=-1;
		System.out.println("useHelicopterLift: ");
		if(this.ifWin()){//if win
			System.out.println("Your team has captured all the treasures.");
			System.out.println("All team members called Helicopter at the Fools’ Landing tile.");
			System.out.println("You win the game!!");
			System.exit(0);
			return true;//return whatever //this is some kind of success
		} else {
			this.board.printWithAllMeaningfulCoord();
			MyOutput.printPlayerListForChoose(board);
			if(Options.ifHelicopterLiftPlayerShouldAtTheSameTile){
				System.out.println("start at which player's tile?");
				tempInt = MyInput.inputOneDigitNumber(("index for the player (1~" + this.board.getPlayerList().size()), 0, this.board.getPlayerList().size());
				switch (tempInt) {
					case -1:
						System.err.println("func useHelicopterLift ERR");
						return false;//ERR
					case 0:
						System.out.println("This operation has been canceled by user");
						return false;//cancel
					case 1:
					case 2:
					case 3:
					case 4:
						System.out.println("destination coordinate?");
						int [] tempInt2 = MyInput.inputDestinationCoord(this.board);
						if(tempInt2==null){
							System.out.println("This operation has been canceled by user");
							return false;//cancel
						} else {
							StdTile destination = this.board.getStdTile(tempInt2);
							HelicopterLift.use(null, this.board.getPlayerList().get(tempInt-1).getCurrStdTile(), destination);
							this.dropPlayerCard(player, cardIndex, card);
							return true;//success
						}
					default:
						return false;//ERR
				}
			} else {
				System.out.println("choose player(s)?");
				HashSet<Integer> tempIntHashSet = MyInput.chooseMultiPlayer(("number for the player (1~" + this.board.getPlayerList().size()), this.board);
				if(tempIntHashSet==null){
					System.out.println("This operation has been canceled by user");
					return false;//cancel
				}
				HashSet<StdRole> players = new HashSet<StdRole>();
				System.out.println("Player selected:");
				for(int i:tempIntHashSet){
					if(i==0){
						System.out.println("This operation has been canceled by user");
						return false;//cancel
					} else if(i>this.board.getPlayerList().size()){
						System.out.println("invalid input -- player index out of range");
						return false;//invalid input
					} else {
						players.add(this.board.getPlayerList().get(i-1));
					}
				}
				for(StdRole i:players){
					System.out.println(i.getName());
				}
				if(players.size()==0){
					System.err.println("func useHelicopterLift ERROR -- HashSet<StdRole> players size == 0");
					return false;//ERR
				}
				System.out.println("destination coordinate?");
				int [] tempInt2 = MyInput.inputDestinationCoord(this.board);
				if(tempInt2==null){
					System.out.println("This operation has been canceled by user");
					return false;//cancel
				} else {
					StdTile destination = this.board.getStdTile(tempInt2);
					HelicopterLift.use(players, null, destination);
					this.dropPlayerCard(player, cardIndex, card);
					return true;//success
				}
			}
		}
	}
	private boolean useFlyAbility(StdRole player) throws IOException{
		System.out.println("useFlyAbility: ");
		System.out.println("destination coordinate?");
		int [] tempInt = MyInput.inputDestinationCoord(this.board);
		if(tempInt==null){
			return false;
		} else {
			return ((Pilot) player).flyAbility(this.board, tempInt);
		}
	}
	//Winning the game:
	//Once you have collected all four treasures,
	//everyone must move their pawns to the Fools’ Landing tile.
	//Then one player must discard a Helicopter lift card to lift the whole team off Fools’ Landing for the win.
	//You can take a Helicopter lift even if Fools’ landing is flooded.
	private boolean ifWin(){//assume the Helicopter lift card has been used
		if(!this.ifLose2()){
			if(this.board.getStdTile(18).getPlayers().size()==4){
				if(Palace.getIfGet()){
					if(Cave.getIfGet()){
						if(Temple.getIfGet()){
							if(Garden.getIfGet()){
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}
	public void begin() throws IOException{
		this.currPlayer=board.getPlayerList().get(this.currPlayerInt);
		this.selectedPlayer=board.getPlayerList().get(this.currPlayerInt);//set the selectedPlayer to the "curr player" as default
		this.currPlayer.begin();//AP = 3
		TreasureCard tempCard;
		for(int i=0; i<2; i++){//pick two treasure cards
			tempCard = treasureDeck.popCard();
			if(tempCard.getClass()==WaterRise.class){//water rise
				this.useWaterRise(tempCard);
			} else {
				currPlayer.addTreasureCard(tempCard);
			}
			if(this.currPlayer.getCards().size()==6){//drop card
				this.forceToChooseCardToDrop(currPlayer);
			}
			if(treasureDeck.isStackEmpty()){//check if a shuffle is needed
				treasureDeck.shuffle(this.usedTreasureDeck);
			}
		}
	}
	public void end() throws IOException{
		for (int i=0; i<this.waterMeter.getLevel(); i++) {//applied flood card
			this.useFloodCard();
			ArrayList<StdTile> tempNearestTile;
			for(StdRole j: this.board.getPlayerList()){
				tempNearestTile = j.end(this.board);//check if anyone sinks after a flood card has been applied
				if(tempNearestTile!=null){
					this.playerSink(j, tempNearestTile);
				}
			}
		}
		if(this.currPlayerInt==board.getPlayerList().size()-1){//switch to the next player
			this.currPlayerInt=0;
		} else {
			this.currPlayerInt++;
		}
	}
	private void playerSink(StdRole player, ArrayList<StdTile> nearestTile) throws IOException{
		boolean flag = true;
		while(flag){
			System.out.println(player.getName() + " sinks!!");
			if(nearestTile.size()==0){
				System.out.println(player.getName() + " no place to swim!!");
				player.setIsAlive(false);
				System.out.println(player.getName() + " leave from us.");
			} else {
				this.board.printWithCoordA(nearestTile);
				System.out.println(player.getName() + " must swim to a tile");
				ArrayList<int[]> tempCoordList = new ArrayList<int[]>();
				for(StdTile i: nearestTile){
					tempCoordList.add(i.getCoord());
				}
				int [] chosenCoord;
				chosenCoord = MyInput.inputCoord("choose a tile to swim", tempCoordList);
				if(chosenCoord!=null){
					flag=false;
					player.getCurrStdTile().playerLeaves(player);
					this.board.getStdTile(chosenCoord).playerComes(player);
				}
			}
		}
		this.printCLI();
	}
	private void forceToChooseCardToDrop(StdRole player) throws IOException{
		while(true){
			if(this.chooseCardToDrop(player)){
				return;
			} else {
				System.out.println("You are be forced to drop a card, this operation can not be canceled.");
			}
		}
	}
	private boolean chooseCardToDrop(StdRole player) throws IOException{
		System.out.println(player.getName() + ":");
		System.out.println("choose a card to drop:");
		MyOutput.printCardListForDrop(player);
		int tempCardIndex = MyInput.inputOneDigitNumber(("input index of cards (1~" + player.getCards().size() + "):"), 0, player.getCards().size());
		if(tempCardIndex==0){
			System.out.println("This operation has been canceled by user");
			return false; //cancel // usually this would be refuse
		} else {
			if(player.getCards().get(tempCardIndex-1).getClass()==HelicopterLift.class){
				if(this.useHelicopterLift(player.getCards().get(tempCardIndex-1), tempCardIndex-1, player)){
					return true;
				}
			} else if(player.getCards().get(tempCardIndex-1).getClass()==Sandbags.class){
				if(this.useSandbags(player.getCards().get(tempCardIndex-1), tempCardIndex-1, player)){
					return true;
				}
			} else {
				dropPlayerCard(player, tempCardIndex-1);
				return true;
			}
		}
		return false;
	}
	private void dropCard(TreasureCard card){//this method would NOT drop the card which is owned by the player!
		this.usedTreasureDeck.pushCard(card);
	}
	private void dropPlayerCard(StdRole currPlayer, int cardIndex){
		TreasureCard tempCard = currPlayer.getCards().get(cardIndex);
		this.dropPlayerCard(currPlayer, cardIndex, tempCard);
	}
	private void dropPlayerCard(StdRole currPlayer, int cardIndex, TreasureCard card){
		this.usedTreasureDeck.pushCard(card);
		currPlayer.removeTreasureCard(cardIndex);
	}
	public void takeAction() throws IOException{
		while(this.currPlayer.getAP()>0){
			this.printCLI();
			if(!this.ifLose()){
				ArrayList<ArrayList<Boolean>> hintFlag = MyOutput.printMenu(board, this.selectedPlayer, this.currPlayer);
				int tempInt = 6;
				if((selectedPlayer.getClass()==Pilot.class)||(selectedPlayer.getClass()==CheatCharacter.class)){
					tempInt = 7;
				} else if((selectedPlayer.getClass()==Navigator.class)&&(Options.ifNavigatorHasOwnAbility==true)){
					tempInt = 7;
				} else {
					tempInt = 6;
				}
				int tempChoose = MyInput.inputOneDigitNumber(("input index of operation (0~" + tempInt + "):"), 0, tempInt);
				System.out.println("The player has been selected: " + this.selectedPlayer.getName());
				System.out.println(this.feedbackOperationString[tempChoose]);
				int tempChoose2=0, tempChoose3=0;
				boolean tempFlag;
				switch(tempChoose){
					case 1://move
					case 2://shoreUp
					case 3://give
					case 4://capture
					case 7://ability
						if(this.selectedPlayer.getAP()<=0){
							System.out.println(" No enough AP");
							break;
						} else {
							switch(tempChoose){
								case 1://move
									tempFlag = true;
									for(boolean i: hintFlag.get(0)){
										if(i==true){
											tempFlag = false;
											break;
										}
									}
									if(tempFlag){
										System.out.println("No valid tile nearby.");
									} else {
										MyOutput.printMoveMenu(this.board, hintFlag.get(0), this.selectedPlayer);
										ArrayList<Integer> validNumList = new ArrayList<Integer>();
										for(int i=0; i<8; i++){
											if(hintFlag.get(0).get(i)){
												validNumList.add(forMoveInputIntArray[i]);
											}
										}
										validNumList.add(0);
										StringBuilder hint = new StringBuilder("input index of operation (");
										for(int i=0; i<validNumList.size(); i++){
											hint.append(validNumList.get(i));
											hint.append(" or ");
										}
										hint.delete(hint.length()-4, hint.length());
										hint.append("):");
										tempChoose2 = MyInput.inputOneDigitNumber(hint.toString(), validNumList);
										if(tempChoose2==0){
											;
										} else {
											this.selectedPlayer.move(board, int2direction.get(tempChoose2));
										}
									}
									break;
								case 2://shoreUp
									tempFlag = true;
									for(boolean i: hintFlag.get(1)){
										if(i==true){
											tempFlag = false;
											break;
										}
									}
									if(tempFlag){
										System.out.println("No flooded tile nearby.");
									} else {
										MyOutput.printStoreUpMenu(this.board, hintFlag.get(1), this.selectedPlayer);
										ArrayList<Integer> validNumList = new ArrayList<Integer>();
										for(int i=0; i<9; i++){
											if(hintFlag.get(1).get(i)){
												validNumList.add(forShoreUpInputIntArray[i]);
											}
										}
										validNumList.add(0);
										StringBuilder hint = new StringBuilder("input index of operation (");
										for(int i=0; i<validNumList.size(); i++){
											hint.append(validNumList.get(i));
											hint.append(" or ");
										}
										hint.delete(hint.length()-4, hint.length());
										hint.append("):");
										if(this.selectedPlayer.getClass()==Engineer.class){
											System.out.println(" [Engineer] could input two different tile (eg. 2,4) or a single tile input (eg. 2):");
											ArrayList<Integer> tempMultipleChoose = MyInput.inputMultipleOneDigitNumber(hint.toString(), validNumList);
											if(tempMultipleChoose.get(0)==0){
												;
											} else if(tempMultipleChoose.size()==1){
												this.selectedPlayer.shoreUp(board, int2direction.get(tempMultipleChoose.get(0)));
											} else if((tempMultipleChoose.get(1)==0)){
												;
											} else if(tempMultipleChoose.get(0)==tempMultipleChoose.get(1)){
												System.out.println("Store up twice on a single tile is not allowed.");
											} else {
												((Engineer) this.selectedPlayer).shoreUp(board, int2direction.get(tempMultipleChoose.get(0)), int2direction.get(tempMultipleChoose.get(1)));
											}
										} else {
											tempChoose2 = MyInput.inputOneDigitNumber(hint.toString(), validNumList);
											if(tempChoose2==0){
												;
											} else {
												this.selectedPlayer.shoreUp(board, int2direction.get(tempChoose2));
											}
										}
									}
									break;
								case 3://give
									if((hintFlag.get(2).get(0))&&(this.selectedPlayer.getClass()!=Messenger.class)){
										System.out.println("No other player.");
									} else if(hintFlag.get(2).get(1)){
										System.out.println("No cards.");
									} else {
										ArrayList<StdRole> tempPlayerList;
										if(this.selectedPlayer.getClass()==Messenger.class){
											tempPlayerList = this.board.getPlayerList();
										} else {
											tempPlayerList = this.selectedPlayer.getCurrStdTile().getPlayers();
										}
										if(tempPlayerList.size()==2){
											tempChoose3 = 1 - tempPlayerList.lastIndexOf(this.selectedPlayer) + 1;
											System.out.println("Pass card to player -- " + tempPlayerList.get(tempChoose3-1).getName());
										}
										MyOutput.printCardListForDrop(this.selectedPlayer);
										tempChoose2 = MyInput.inputOneDigitNumber(("input index for the card (0~" + this.selectedPlayer.getCards().size() + "):"), 0, this.selectedPlayer.getCards().size());
										if(tempChoose2 == 0){
											System.out.println("This operation has been canceled by user");
										} else{
											if(tempPlayerList.size()==2){
												;//there is only one player in range
											} else {
												MyOutput.printPlayerListForChoose(tempPlayerList);
												if(this.selectedPlayer.getClass()==CheatCharacter.class){
													tempChoose3 = MyInput.inputOneNumber(("input index for the player (0~" + tempPlayerList.size() + "):"), 0, tempPlayerList.size());
												} else {
													tempChoose3 = MyInput.inputOneDigitNumber(("input index for the player (0~" + tempPlayerList.size() + "):"), 0, tempPlayerList.size());
												}
											}
											if(tempChoose3 == 0){
												System.out.println("This operation has been canceled by user");
											} else if (this.selectedPlayer==tempPlayerList.get(tempChoose3-1)){
												System.out.println("You passed a card to yourself without any AP cost.");
											} else {
												this.selectedPlayer.passCard(tempPlayerList.get(tempChoose3-1), tempChoose2-1);
												if(tempPlayerList.get(tempChoose3-1).getCards().size()==6){//drop card
													this.forceToChooseCardToDrop(tempPlayerList.get(tempChoose3-1));
												}
											}
										}
									}
									break;
								case 4://capture
									if(hintFlag.get(3).get(0)){
										System.out.println("No enough any same type treasure cards");
									} else if(hintFlag.get(3).get(1)){
										System.out.println("Not at treasure tile");
									} else if(hintFlag.get(3).get(2)){
										System.out.println("No enough required treasure cards");
									} else {
										if(this.selectedPlayer.captureTreasure(this.usedTreasureDeck)){
											System.out.println("Capture...Success");
										} else {
											System.out.println("Capture...Failed");
										}
									}
									break;
								case 7://ability
									if(this.selectedPlayer.getClass()==CheatCharacter.class){
										System.out.println("1: get 20 Treasure cards (drop WaterRise)");
										System.out.println("2: get 10 Treasure cards (applied WaterRise)");
										System.out.println("3: recover all tiles");
										System.out.println("4: flood all tiles");
										System.out.println("5: sink all tiles");
										System.out.println("6: reset water meter to 1");
										System.out.println("7: reset water meter to 9");
										System.out.println("8: get all treasures");
										System.out.println("9: get all treasures and separates to every one");
										System.out.println("10: move all players to the Fools' Landing");
										System.out.println("11: set a player's AP to 99");
										System.out.println("12: set a diagonally island for explorer");
										System.out.println("13: force the diver into water and puts 1*1 cross-shaped guaranteed tiles surround him");
										System.out.println("14: force the diver into water and puts 2*2 cross-shaped guaranteed tiles surround him");
										System.out.println("15: force the diver into water and puts 3*3 cross-shaped guaranteed tiles surround him");
										System.out.println("16: force the diver into water and puts 4*4 cross-shaped guaranteed tiles surround him");
										System.out.println("17: force the diver into water and puts 5*5 cross-shaped guaranteed tiles surround him");
										System.out.println("18: force the diver into water and puts 6*6 cross-shaped guaranteed tiles surround him");
										System.out.println("19: add 10 HelicopterLift into your hand of cards");
										System.out.println("20: add 10 SandBags into your hand of cards");
										System.out.println("0: cancel");
										tempChoose2 = MyInput.inputOneNumber(("input:"), 0, 20);
										TreasureCard tempCard;
										Treasure tempTreasure;
										int guaranteedArea=0;
										switch(tempChoose2){
											case 1:
												for(int i=0; i<20; i++){
													tempCard = treasureDeck.popCard();
													if(tempCard.getClass()==WaterRise.class){//water rise
														this.usedTreasureDeck.pushCard(tempCard);
													} else {
														this.selectedPlayer.addTreasureCard(tempCard);
													}
													if(treasureDeck.isStackEmpty()){//check if a shuffle is needed
														treasureDeck.shuffle(this.usedTreasureDeck);
													}
												}
												break;
											case 2:
												for(int i=0; i<10; i++){
													tempCard = treasureDeck.popCard();
													if(tempCard.getClass()==WaterRise.class){//water rise
														this.useWaterRise(tempCard);
													} else {
														this.selectedPlayer.addTreasureCard(tempCard);
													}
													if(treasureDeck.isStackEmpty()){//check if a shuffle is needed
														treasureDeck.shuffle(this.usedTreasureDeck);
													}
												}
												break;
											case 3:
												for(int id=0; id<24; id++){
													this.board.getStdTile(id).setStatus(Status.NORMAL);
												}
												break;
											case 4:
												for(int id=0; id<24; id++){
													this.board.getStdTile(id).flood();
												}
												break;
											case 5:
												for(int id=0; id<24; id++){
													this.board.getStdTile(id).setStatus(Status.SUNK);
												}
												break;
											case 6:
												this.waterMeter.setWaterMeter(1);
												break;
											case 7:
												this.waterMeter.setWaterMeter(9);
												break;
											case 8:
												for(int i=10; i<18; i+=2){
													tempTreasure = this.board.getStdTile(i).getTreasure();
													if(tempTreasure!=null){
														this.selectedPlayer.getTreasures().add(tempTreasure);
													}
												}
												break;
											case 9:
												for(int i=10, j=0; i<18 && j<4; i+=2, j++){
													tempTreasure = this.board.getStdTile(i).getTreasure();
													if(tempTreasure!=null){
														this.board.getPlayerList().get(j%this.board.getPlayerList().size()).getTreasures().add(tempTreasure);
													}
												}
												break;
											case 10:
												for(StdRole i:this.board.getPlayerList()){
													i.getCurrStdTile().playerLeaves(i);
													this.board.getStdTile(18).playerComes(i);
												}
												break;
											case 11:
												System.out.println("choose player:");
												tempChoose2 = MyInput.inputOneDigitNumber(("input index for the player (0~" + this.board.getPlayerList().size() + "):"), 0, this.board.getPlayerList().size());
												this.board.getPlayerList().get(tempChoose2-1).setAP(99);
												break;
											case 12:
												boolean ifNoExplorer=true;
												for(StdRole i: this.board.getPlayerList()){
													if(i.getClass()==Explorer.class){
														ifNoExplorer=false;
														int[] tempCoord = i.getCoord();
														StdTile tempTile;
														int row=tempCoord[0]%2, col=tempCoord[1]%2;
														int tempCoord2;
														if(row==col){
															tempCoord2=0;
														} else {
															tempCoord2=1;
														}
														for(;tempCoord2<36;tempCoord2+=2){
															tempTile = this.board.getStdTile(tempCoord2/6, tempCoord2%6);
															if(tempTile!=null){
																tempTile.setStatus(Status.SUNK);
															}
														}
														break;
													}
												}
												if(ifNoExplorer){
													System.out.println("no explorer.");
												}
												break;
											case 18:
												guaranteedArea++;
											case 17:
												guaranteedArea++;
											case 16:
												guaranteedArea++;
											case 15:
												guaranteedArea++;
											case 14:
												guaranteedArea++;
											case 13:
												guaranteedArea++;
												boolean ifNoDiver=true;
												for(StdRole i: this.board.getPlayerList()){
													if(i.getClass()==Diver.class){
														ifNoDiver=false;
														int[] tempCoord = i.getCoord();
														StdTile tempTile;
														for(int row=0;row<6;row++){
															for(int col=0;col<6;col++){
																if(((Math.abs(row-tempCoord[0]))+(Math.abs(col-tempCoord[1])))<=guaranteedArea){
																	tempTile = this.board.getStdTile(row, col);
																	if(tempTile!=null){
																		tempTile.setStatus(Status.SUNK);
																	}
																}
															}
														}
														break;
													}
												}
												if(ifNoDiver){
													System.out.println("no diver.");
												}
												break;
											case 19:
												for(int i=0; i<10; i++){
													this.selectedPlayer.getCards().add(new HelicopterLift());
												}
												break;
											case 20:
												for(int i=0; i<10; i++){
													this.selectedPlayer.getCards().add(new Sandbags());
												}
												break;
											case 0:
												System.out.println("This operation has been canceled by user");
												break;
											default:
												break;
										}
									} else {
										if(hintFlag.get(5).get(0)){
											System.out.println("the ability is limited to use for now");
										} else if(selectedPlayer.getClass()==Pilot.class){
											this.useFlyAbility(this.selectedPlayer);
										} else if(selectedPlayer.getClass()==Navigator.class){
											;//TODO
										} else {
											;//ERR
										}
									}
									break;
								default:
									break;//ERR
							}
						}
						break;
					case 5://use
						if(hintFlag.get(4).get(0)){
							System.out.println("no functional card");
						} else {
							MyOutput.printCardListForUse(this.selectedPlayer);
							if(selectedPlayer.getClass()==CheatCharacter.class){
								tempChoose2 = MyInput.inputOneNumber(("input index for the card (0~" + this.selectedPlayer.getCards().size() + "):"), 0, this.selectedPlayer.getCards().size());
							} else {
								tempChoose2 = MyInput.inputOneDigitNumber(("input index for the card (0~" + this.selectedPlayer.getCards().size() + "):"), 0, this.selectedPlayer.getCards().size());
							}
							if(tempChoose2==0){
								System.out.println("This operation has been canceled by user");
							} else if(selectedPlayer.getCards().get(tempChoose2-1).getClass()==HelicopterLift.class){
								this.useHelicopterLift(selectedPlayer.getCards().get(tempChoose2-1), tempChoose2-1, this.selectedPlayer);
							} else if(selectedPlayer.getCards().get(tempChoose2-1).getClass()==Sandbags.class){
								this.useSandbags(selectedPlayer.getCards().get(tempChoose2-1), tempChoose2-1, this.selectedPlayer);
							} else {
								System.out.println("not allowed");
							}
						}
						break;
					case 6://switch
						MyOutput.printPlayerListForChoose(board);
						tempChoose2 = MyInput.inputOneDigitNumber(("input index for the player (0~" + this.board.getPlayerList().size() + "):"), 0, this.board.getPlayerList().size());
						if(tempChoose2!=0){
							this.selectedPlayer=board.getPlayerList().get(tempChoose2-1);
						} else {
							System.out.println("This operation has been canceled by user");
						}
						break;
					case 0://end
						if(hintFlag.get(6).get(0)){
							System.out.println(" Not your turn");//end
						} else {
							System.out.println("0: cancel");
							System.out.println("1: proceed");
							tempChoose2 = MyInput.inputOneDigitNumber("Are you sure to end this turn? (0 or 1):", 0, 1);
							if(tempChoose2==1){
								this.selectedPlayer.end(board);
							} else {
								System.out.println("This operation has been canceled by user");
							}
						}
						break;
					default:
						System.err.println("tempChoose ERROR");
						break;
				}
			}
		}
	}
	public void printCLI(){
		this.board.printCLILite();
		this.waterMeter.printCLI();
	}
	//DEBUG
	public void printBoardWithAllCoordCLI(){
		this.board.printWithAllCoord();
	}
	public void printFDeckCLI(){
		this.floodDeck.printCLI();
	}
	public void printUsedFDeckCLI(){
		this.usedFloodDeck.printCLI();
	}
	public void printTreasureDeckCLI(){
		this.treasureDeck.printCLI();
	}
	public void printUsedTreasureDeckCLI(){
		this.usedTreasureDeck.printCLI();
	}
	public void printWaterMeterCLI(){
		this.waterMeter.printCLI();
	}
	public void printPlayersCLI(){
		this.board.printPlayersCLI();
	}
}
