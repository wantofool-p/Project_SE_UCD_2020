package role;
import java.util.ArrayList;

import board.Board;
import board.tile.Status;
import board.tile.StdTile;
import deck.TreasureDeck;
import deck.treasureCard.TreasureCard;
import treasure.Treasure;
import treasure.TreasureType;
public abstract class StdRole{
	protected boolean isAlive=true;
	protected int AP=0;//action points
	protected StdTile currStdTile;//current position
	protected ArrayList<TreasureCard> cards = new ArrayList<TreasureCard>();//should up to 5
	protected ArrayList<Treasure> treasures = new ArrayList<Treasure>();//should up to 4
	protected String name;
	public StdRole(){
		;
	}
	public void begin(){
		this.AP=3;
	}
	public ArrayList<StdTile> end(Board board){
		this.AP=0;
		if(this.currStdTile.getStatus()==Status.SUNK){
			return this.sink(board);
		}
		return null;
	}
	public boolean getIsAlive(){
		return this.isAlive;
	}
	public int getAP(){
		return this.AP;
	}
	public StdTile getCurrStdTile(){
		return currStdTile;
	}
	public ArrayList<TreasureCard> getCards(){
		return this.cards;
	}
	public ArrayList<Treasure> getTreasures(){
		return this.treasures;
	}
	public String getName(){
		return this.name;
	}
	public void setIsAlive(boolean isAlive){
		this.isAlive=isAlive;
	}
	public void setAP(int AP){
		this.AP=AP;
	}
	public void setCurrStdTile(StdTile currStdTile){
		this.currStdTile=currStdTile;
	}
	public void setCards(ArrayList<TreasureCard> cards){
		this.cards=cards;
	}
	public void setTreasures(ArrayList<Treasure> treasures){
		this.treasures=treasures;
	}
	public void setName(String name){
		this.name=name;
	}
	public int[] getCoord(){
		return this.currStdTile.getCoord();
	}
	public StdTile getDestination(Board board, DirectionType directionType){//if the target StdTile is valid to move, return this StdTile
		int[] temp = currStdTile.getCoord();
		int row=temp[0], col=temp[1];
		if((row==-1)&&(col==-1)){
			System.err.print("func getDestination ERR -- coordinate not initialize");
			return null;
		} else if((row<0)||(row>5)||(col<0)||(col>5)){
			System.err.print("func getDestination ERR -- coordinate out of range");
			return null;
		} else {
			switch (directionType) {
				case UP://↑
					if(row==0){
						return null;//not allowed
					} else {
						return board.getStdTile(row-1, col);
					}
				case DOWN://↓
					if(row==5){
						return null;//not allowed
					} else {
						return board.getStdTile(row+1, col);
					}
				case LEFT://←
					if(col==0){
						return null;//not allowed
					} else {
						return board.getStdTile(row, col-1);
					}
				case RIGHT://→
					if(col==5){
						return null;//not allowed
					} else {
						return board.getStdTile(row, col+1);
					}
				case MIDDLE://•
					return this.currStdTile;
				case TOPLEFT://↖
					if((row+col==2)||(row+col==3)){
						return null;//not allowed
					} else {
						return board.getStdTile(row-1, col-1);
					}
				case BOTTOMRIGHT://↘
					if((row+col==7)||(row+col==8)){
						return null;//not allowed
					} else {
						return board.getStdTile(row+1, col+1);
					}
				case BOTTOMLEFT://↙
					int tempRow = 5-row;
					if((tempRow+col==2)||(tempRow+col==3)){
						return null;//not allowed
					} else {
						return board.getStdTile(row+1, col-1);
					}
				case TOPRIGHT://↗
					int tempCol = 5-col;
					if((row+tempCol==2)||(row+tempCol==3)){
						return null;//not allowed
					} else {
						return board.getStdTile(row-1, col+1);
					}
				default://ERR
					return null;
			}
		}
	}
	public boolean move(Board board, DirectionType directionType){
		if(this.AP==0){
			return false;//not enough AP
		} else {
			switch (directionType) {
				case UP://↑
				case DOWN://↓
				case LEFT://←
				case RIGHT://→
					StdTile destination = getDestination(board, directionType);
					if(destination==null){
						return false;//failure
					} else if(destination.getStatus()==Status.SUNK){
						return false;//failure
					} else {
						this.freeMove(destination);
						this.AP--;
						return true;//success
					}
				case MIDDLE://•//cancel
				case TOPLEFT://↖//not allowed
				case BOTTOMRIGHT://↘//not allowed
				case BOTTOMLEFT://↙//not allowed
				case TOPRIGHT://↗//not allowed
				default://ERR
					return false;//failure
			}
		}
	}
	protected void freeMove(StdTile destination){//assume a valid operation
		destination.getPlayers().add(this);
		this.currStdTile.getPlayers().remove(this);
		this.currStdTile=destination;
	}
	public boolean shoreUp(Board board, DirectionType directionType){
		if(this.AP==0){
			return false;//not enough AP
		} else {
			switch (directionType) {
				case UP://↑
				case DOWN://↓
				case LEFT://←
				case RIGHT://→
				case MIDDLE://•
					StdTile destination = getDestination(board, directionType);
					if(destination==null){
						return false;//failure
					} else {
						if(this.freeShoreUp(destination)){
							this.AP--;
							return true;//success
						} else {
							return false;//failure
						}
					}
				case TOPLEFT://↖//not allowed
				case BOTTOMRIGHT://↘//not allowed
				case BOTTOMLEFT://↙//not allowed
				case TOPRIGHT://↗//not allowed
				default://ERR
					return false;//failure
			}
		}
	}
	protected boolean freeShoreUp(StdTile destination){//assume a valid operation
		return destination.storeUp();
	}
	public ArrayList<StdTile> sink(Board board){//return valid Tile List
		if(this.currStdTile.getStatus()!=Status.SUNK){
			System.err.println("func sink ERR -- mistaken call");
			return null;//mistaken call
		} else {
			ArrayList<StdTile> tempList = new ArrayList<StdTile>();
			StdTile temp;
			ArrayList<DirectionType> fourDirectionTypes = new ArrayList<DirectionType>();
			fourDirectionTypes.add(DirectionType.UP);
			fourDirectionTypes.add(DirectionType.DOWN);
			fourDirectionTypes.add(DirectionType.LEFT);
			fourDirectionTypes.add(DirectionType.RIGHT);
			for(DirectionType i:fourDirectionTypes){
				temp=this.getDestination(board, i);
				if(temp==null){
					;
				} else if(temp.getStatus()==Status.SUNK){
					;
				} else {
					tempList.add(temp);
				}
			}
			if (tempList.size()==0){
				this.isAlive=false;
			}
			return tempList;
		}
	}
	public boolean addTreasureCard(TreasureCard card){//return false if sum of cards == 6//must make sure this is a valid operation
		this.cards.add(card);
		if(cards.size()==6){
			return false;//if the num of cards == 6
		} else {
			return true;
		}
	}
	public boolean dropTreasureCard(int cardIndex, TreasureDeck usedTreasureDeck){//card would not be pushed into any card deck
		if(cards.size()==0){
			return false;//if this player has no card
		} else if(cards.size()<cardIndex+1){
			return false;//if card index > the sum of curr player's cards
		} else {
			usedTreasureDeck.getStack().push(this.cards.remove(cardIndex));
			return true;
		}
	}
	public boolean removeTreasureCard(int cardIndex){//card would not be pushed into any card deck
		if(cards.size()==0){
			return false;//if this player has no card
		} else if(cards.size()<cardIndex+1){
			return false;//if card index > the sum of curr player's cards
		} else {
			this.cards.remove(cardIndex);
			return true;
		}
	}
	public boolean passCard(StdRole targetRole, int cardIndex){//be aware that this method would not drop card if the size of cards == 6
		if(this.AP==0){
			return false;//not enough AP
		} else {
			int[] destCoord = targetRole.getCoord();
			int destRow=destCoord[0], destCol=destCoord[1];
			int[] currCoord = this.getCoord();
			int currRow=currCoord[0], currCol=currCoord[1];
			if((destRow==currRow)&&(destCol==currCol)){
				this.freePassCard(targetRole, cardIndex);
				this.AP--;
				return true;//success
			} else {
				return false;//failure
			}
		}
	}
	protected void freePassCard(StdRole targetRole, int cardIndex){//assume a valid operation
		targetRole.addTreasureCard(targetRole.getCards().get(cardIndex));
		this.removeTreasureCard(cardIndex);
	}
	public boolean captureTreasure(TreasureDeck usedTreasureDeck){//assume a valid operation
		if(this.AP==0){
			return false;//not enough AP
		} else {
			ArrayList<Integer> temp = this.currStdTile.callTreasure(this.cards);
			if(temp == null){
				System.err.print(" func captureTreasure ERR -- temp == null");
				return false;
			} else if(temp.size()==4) {
				Treasure tempTreasure = this.currStdTile.getTreasure();
				if(tempTreasure==null){
					return false;//failure
				} else {
					this.treasures.add(tempTreasure);
					for(int i:temp){
						this.dropTreasureCard(i, usedTreasureDeck);
					}
					this.AP--;
					return true;//success
				}
			} else {
				return false;//not allowed
			}
		}
	}
	public void printCLI(){
		System.out.print(this.name + "  AP: " + this.AP);
		if(this.treasures.size()>0){
			for(Treasure i: this.treasures){
				System.out.print("  [Treasure ");
				if(i==null){
					System.out.print("null");
				} else {
					System.out.print(i.getName());
				}
				System.out.print("]");
			}
		}
		TreasureType tempTreasureType;
		int countFire=0, countWind=0, countStore=0, countChalice=0;
		for(TreasureCard i:this.getCards()){
			tempTreasureType = i.getTreasureType();
			if (tempTreasureType==TreasureType.FIRE){
				countFire++;
			} else if (tempTreasureType==TreasureType.WIND){
				countWind++;
			} else if (tempTreasureType==TreasureType.STONE){
				countStore++;
			} else if (tempTreasureType==TreasureType.CHALICE){
				countChalice++;
			} else {
				;
			}
		}
		if(countWind>=4){
			System.out.print("  [WindCards>=4]");
		}
		if(countChalice>=4){
			System.out.print("  [ChaliceCards>=4]");
		}
		if(countFire>=4){
			System.out.print("  [FireCards>=4]");
		}
		if(countStore>=4){
			System.out.print("  [StoreCards>=4]");
		}
		System.out.println();
		for(TreasureCard i: cards){
			i.printCLI();
		}
	}
}
