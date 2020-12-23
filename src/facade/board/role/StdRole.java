package facade.board.role;
import java.util.ArrayList;
import java.util.List;

import facade.board.tile.StdTile;
import facade.deck.treasureCard.TreasureCard;
import facade.treasure.Treasure;
public abstract class StdRole{
	protected boolean isAlive=true;
	protected int AP=0;//action points
	protected StdTile curr;//current position
	protected List<TreasureCard> cards = new ArrayList<TreasureCard>();//should up to 5
	protected List<Treasure> treasures = new ArrayList<Treasure>();//should up to 4
	protected String name;
	public StdRole(){
		;
	}
	public void begin(){
		this.AP=3;
	}
	public void end(){
		this.AP=0;
	}
	public int getAP(){
		return this.AP;
	}
	public String getName(){
		return this.name;
	}
	public void freeMove(StdTile destination){//assume a vaild operation (Coordinate, AP)
		destination.getPlayers().add(this);
		this.curr.getPlayers().remove(this);
		this.curr=destination;
	}
	public void move(StdTile destination){//assume a vaild operation (Coordinate, AP)
		destination.getPlayers().add(this);
		this.curr.getPlayers().remove(this);
		this.curr=destination;
		this.AP--;
	}
	public void shoreUp(StdTile destination){//assume a vaild operation //TODO add estimation in main
		destination.storeUp();
		this.AP--;
	}
//	public void giveATreasureCard(StdRole targetRole){
//		this.cards;
//	}
	// public void captureATreasure(){
	// 	;
	// }
	// public void useFunctionalCard(FunctionalCard card, StdTile destination){//assume a vaild operation //TODO add estimation in main
	// 	@SuppressWarnings("rawtypes")
	// 	Class temp = card.getClass();
	// 	if (temp == HelicopterLift.class){
	// 		card.use(this.getCurr(), destination);
	// 	}else if(temp==Sandbags.class){
	// 		card.use(this.getCurr(), destination);
	// 	}else if(temp==WaterRise.class){
	// 		System.out.println("printOneCharacterCLI ERROR temp==WaterRise.class");
	// 	}else{
	// 		System.out.println("printOneCharacterCLI ERROR");
	// 	}
	// }
	public StdTile getCurr(){
		return curr;
	}
	public void setCurr(StdTile curr){
		this.curr=curr;
	}
	public void addTreasureCard(TreasureCard card){//maybe assume a vaild operation
		if(cards.size()<5){
			this.cards.add(card);
		} else {
			;//refuse //TODO
		}
	}
	public void removeTreasureCard(TreasureCard card){//maybe assume a vaild operation
		if(cards.size()>0){
			this.cards.remove(card);
		} else {
			;//refuse //TODO
		}
	}
	public void removeTreasureCard(TreasureCard card, List<TreasureCard> usedCards){//maybe assume a vaild operation
		if(cards.size()>0){
			usedCards.add(card);
			this.cards.remove(card);
		} else {
			;//refuse //TODO
		}
	}
	public List<TreasureCard> getCards(){
		return cards;
	}
	public List<Treasure> getTreasures(){
		return treasures;
	}
	public void printCLI(){
		System.out.print(this.name + "  AP: " + this.AP);
		if(this.treasures.size()>0){
			for(Treasure i: this.treasures){
				System.out.print("  [Treasure ");
				System.out.print(i.getName());
				System.out.print("]");
			}
		}
		System.out.println();
		for(TreasureCard i: cards){
			i.printCLI();
		}
	}
}
