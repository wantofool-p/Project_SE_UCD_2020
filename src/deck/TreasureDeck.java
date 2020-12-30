package deck;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import deck.treasureCard.ChaliceCard;
import deck.treasureCard.FireCard;
import deck.treasureCard.HelicopterLift;
import deck.treasureCard.Sandbags;
import deck.treasureCard.StoneCard;
import deck.treasureCard.TreasureCard;
import deck.treasureCard.WaterRise;
import deck.treasureCard.WindCard;

public class TreasureDeck{
	protected Stack<TreasureCard> st = new Stack<TreasureCard>();
	public TreasureDeck(){
		;
	}
	public Stack<TreasureCard> getStack(){
		return st;
	}
	public void setStack(Stack<TreasureCard> st){
		this.st = st;
	}
	public void init(){//this method must be run once
		HelicopterLift[] helicopterLift={
			new HelicopterLift(),
			new HelicopterLift(),
			new HelicopterLift()
		};
		Sandbags[] sandbags={
			new Sandbags(),
			new Sandbags()
		};
		WaterRise[] waterRise={
			new WaterRise(),
			new WaterRise(),
			new WaterRise()
		};
		ChaliceCard[] chaliceCard={
			new ChaliceCard(),
			new ChaliceCard(),
			new ChaliceCard(),
			new ChaliceCard(),
			new ChaliceCard()
		};
		FireCard[] fireCard={
			new FireCard(),
			new FireCard(),
			new FireCard(),
			new FireCard(),
			new FireCard()
		};
		StoneCard[] stoneCard={
			new StoneCard(),
			new StoneCard(),
			new StoneCard(),
			new StoneCard(),
			new StoneCard()
		};
		WindCard[] windCard={
			new WindCard(),
			new WindCard(),
			new WindCard(),
			new WindCard(),
			new WindCard()
		};
		ArrayList<Integer> tempList5 = new ArrayList<Integer>();
		for(int i=0;i<3+2+3+(4*5);i++){
			tempList5.add(i);
		}
		ArrayList<TreasureCard> tempTreasureCardList = new ArrayList<TreasureCard>();
		for (int i=0;i<3;i++) {
			tempTreasureCardList.add(helicopterLift[i]);
		}
		for (int i=0;i<2;i++) {
			tempTreasureCardList.add(sandbags[i]);
		}
		for (int i=0;i<3;i++) {
			tempTreasureCardList.add(waterRise[i]);
		}
		for (int i=0;i<5;i++) {
			tempTreasureCardList.add(chaliceCard[i]);
			tempTreasureCardList.add(fireCard[i]);
			tempTreasureCardList.add(stoneCard[i]);
			tempTreasureCardList.add(windCard[i]);
		}
		for(int i=0;i<3+2+3+(4*5);i++){
			this.st.push(tempTreasureCardList.get(tempList5.remove(new Random().nextInt(tempList5.size()))));
		}
	}
	public boolean isStackEmpty(){
		return this.getStack().empty();
	}
	public TreasureCard popCard(){
		if(this.getStack().empty()){
			System.err.println("stack empty");
			return null;
		} else {
			return this.getStack().pop();
		}
	}
	public void pushCard(TreasureCard card){
		this.getStack().push(card);
	}
	public boolean shuffle(TreasureDeck UsedTDeck){//assume it is valid to use (when the TDeck is empty)
		if((!UsedTDeck.getStack().empty())&&(this.getStack().empty())){
			ArrayList<TreasureCard> beforeRandomizeTreasureCardList = new ArrayList<TreasureCard>();
			for (TreasureCard i: UsedTDeck.getStack()) {
				beforeRandomizeTreasureCardList.add(i);
			}
			//shuffle
			ArrayList<Integer> tempList2 = new ArrayList<Integer>();
			for(int i=0;i<beforeRandomizeTreasureCardList.size();i++){
				tempList2.add(i);
			}
			for(int i=0;i<beforeRandomizeTreasureCardList.size();i++){
				this.st.push(beforeRandomizeTreasureCardList.get(tempList2.remove(new Random().nextInt(tempList2.size()))));
			}
			while(!UsedTDeck.getStack().empty()){
				UsedTDeck.getStack().pop();
			}
			return true;
		} else {
			return false;
		}
	}
	public void printCLI(){//debug only
		System.out.println();
		for (TreasureCard i: this.st) {
			i.printCLI();
		}
	}
}