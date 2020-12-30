package deck;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import board.Board;
import deck.floodCard.FloodCard;

public class FloodDeck{
	protected Stack<FloodCard> st = new Stack<FloodCard>();
	public FloodDeck(){
		;
	}
	public Stack<FloodCard> getStack(){
		return st;
	}
	public void setStack(Stack<FloodCard> st){
		this.st = st;
	}
	public void init(Board board){//this method must be run once
		//init FloodCard
		ArrayList<FloodCard> beforeRandomizeFloodCardList = new ArrayList<FloodCard>();
		//24 Tile in tot
		beforeRandomizeFloodCardList.add(new FloodCard(board.getStdTile(0).getName(), board.getStdTile(0)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getStdTile(1).getName(), board.getStdTile(1)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getStdTile(2).getName(), board.getStdTile(2)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getStdTile(3).getName(), board.getStdTile(3)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getStdTile(4).getName(), board.getStdTile(4)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getStdTile(5).getName(), board.getStdTile(5)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getStdTile(6).getName(), board.getStdTile(6)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getStdTile(7).getName(), board.getStdTile(7)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getStdTile(8).getName(), board.getStdTile(8)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getStdTile(9).getName(), board.getStdTile(9)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getStdTile(10).getName(), board.getStdTile(10)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getStdTile(11).getName(), board.getStdTile(11)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getStdTile(12).getName(), board.getStdTile(12)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getStdTile(13).getName(), board.getStdTile(13)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getStdTile(14).getName(), board.getStdTile(14)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getStdTile(15).getName(), board.getStdTile(15)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getStdTile(16).getName(), board.getStdTile(16)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getStdTile(17).getName(), board.getStdTile(17)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getStdTile(18).getName(), board.getStdTile(18)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getStdTile(19).getName(), board.getStdTile(19)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getStdTile(20).getName(), board.getStdTile(20)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getStdTile(21).getName(), board.getStdTile(21)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getStdTile(22).getName(), board.getStdTile(22)));
		beforeRandomizeFloodCardList.add(new FloodCard(board.getStdTile(23).getName(), board.getStdTile(23)));
		//rearrange tiles
		ArrayList<Integer> tempList2 = new ArrayList<Integer>();
		for(int i=0;i<10+8+6;i++){
			tempList2.add(i);
		}
		for(int i=0;i<10+8+6;i++){
			this.st.push(beforeRandomizeFloodCardList.get(tempList2.remove(new Random().nextInt(tempList2.size()))));
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
	public void shuffle(FloodDeck UsedFDeck){//assume it is valid to use
		ArrayList<FloodCard> beforeRandomizeFloodCardList = new ArrayList<FloodCard>();
		for (FloodCard i: UsedFDeck.getStack()) {
			beforeRandomizeFloodCardList.add(i);
		}
		//shuffle
		ArrayList<Integer> tempList2 = new ArrayList<Integer>();
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
	public void printCLI(){//debug only
		System.out.println();
		for (FloodCard i: this.st) {
			i.printCLI();
		}
	}
}