package facade.deck;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import facade.board.Board;
import facade.deck.floodCard.FloodCard;

public class FloodDeck{
	protected Stack<FloodCard> st = new Stack<FloodCard>();
	public FloodDeck(){
		;
	}
	public void load(Board board){//this method should be run once only
		//init FloodCard
		List<FloodCard> beforeRandomizeFloodCardList = new ArrayList<FloodCard>();
		//24 Tile in tot
		beforeRandomizeFloodCardList.add(new FloodCard(board.getAStdTileDebug(0).getName(), board.getAStdTileDebug(0)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getAStdTileDebug(1).getName(), board.getAStdTileDebug(1)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getAStdTileDebug(2).getName(), board.getAStdTileDebug(2)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getAStdTileDebug(3).getName(), board.getAStdTileDebug(3)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getAStdTileDebug(4).getName(), board.getAStdTileDebug(4)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getAStdTileDebug(5).getName(), board.getAStdTileDebug(5)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getAStdTileDebug(6).getName(), board.getAStdTileDebug(6)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getAStdTileDebug(7).getName(), board.getAStdTileDebug(7)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getAStdTileDebug(8).getName(), board.getAStdTileDebug(8)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getAStdTileDebug(9).getName(), board.getAStdTileDebug(9)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getAStdTileDebug(10).getName(), board.getAStdTileDebug(10)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getAStdTileDebug(11).getName(), board.getAStdTileDebug(11)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getAStdTileDebug(12).getName(), board.getAStdTileDebug(12)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getAStdTileDebug(13).getName(), board.getAStdTileDebug(13)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getAStdTileDebug(14).getName(), board.getAStdTileDebug(14)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getAStdTileDebug(15).getName(), board.getAStdTileDebug(15)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getAStdTileDebug(16).getName(), board.getAStdTileDebug(16)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getAStdTileDebug(17).getName(), board.getAStdTileDebug(17)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getAStdTileDebug(18).getName(), board.getAStdTileDebug(18)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getAStdTileDebug(19).getName(), board.getAStdTileDebug(19)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getAStdTileDebug(20).getName(), board.getAStdTileDebug(20)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getAStdTileDebug(21).getName(), board.getAStdTileDebug(21)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getAStdTileDebug(22).getName(), board.getAStdTileDebug(22)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getAStdTileDebug(23).getName(), board.getAStdTileDebug(23)));
		//resequencing tiles
		List<Integer> tempList2 = new ArrayList<Integer>();
		for(int i=0;i<10+8+6;i++){
			tempList2.add(i);
		}
		for(int i=0;i<10+8+6;i++){
			this.st.push(beforeRandomizeFloodCardList.get(tempList2.remove(new Random().nextInt(tempList2.size()))));
		}
	}
	public void printCLI(){//this method is debug only
		System.out.println();
		for (FloodCard i: this.st) {
			i.printCLI();
		}
	}
	public void useCard(FloodDeck UsedFDeck){
		if(this.st.empty()){//if empty
			shuffle(UsedFDeck);//shuffle the FloodCardDeck
		}
		UsedFDeck.getStack().push(this.st.peek());
		this.st.pop().useCard();
		if(this.st.empty()){//if empty
			shuffle(UsedFDeck);//shuffle the FloodCardDeck
		}
	}
	public void shuffle(FloodDeck UsedFDeck){//assume it is vaild to use (means the UsedFDeck is not empty)
		List<FloodCard> beforeRandomizeFloodCardList = new ArrayList<FloodCard>();
		for (FloodCard i: UsedFDeck.getStack()) {
			beforeRandomizeFloodCardList.add(i);
		}
		//shuffle
		List<Integer> tempList2 = new ArrayList<Integer>();
		for(int i=0;i<beforeRandomizeFloodCardList.size();i++){
			tempList2.add(i);
		}
		for(int i=0;i<beforeRandomizeFloodCardList.size();i++){
			this.st.push(beforeRandomizeFloodCardList.get(tempList2.remove(new Random().nextInt(tempList2.size()))));
		}
		while(!UsedFDeck.getStack().empty()){
			UsedFDeck.getStack().pop();
		}
	}
	public Stack<FloodCard> getStack(){
		return st;
	}
}