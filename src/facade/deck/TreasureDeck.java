package facade.deck;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import facade.deck.treasureCard.ChaliceCard;
import facade.deck.treasureCard.FireCard;
import facade.deck.treasureCard.HelicopterLift;
import facade.deck.treasureCard.Sandbags;
import facade.deck.treasureCard.StoneCard;
import facade.deck.treasureCard.TreasureCard;
import facade.deck.treasureCard.WaterRise;
import facade.deck.treasureCard.WindCard;

public class TreasureDeck{
	protected Stack<TreasureCard> st = new Stack<TreasureCard>();
	public TreasureDeck(){
		;
	}
	public void load(){//this method should be run once only
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
		List<Integer> tempList5 = new ArrayList<Integer>();
		for(int i=0;i<3+2+3+(4*5);i++){
			tempList5.add(i);
		}
		List<TreasureCard> tempTreasureCardList = new ArrayList<TreasureCard>();
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
	public void printCLI(){//this method is debug only
		System.out.println();
		for (TreasureCard i: this.st) {
			i.printCLI();
		}
	}
	public Stack<TreasureCard> getStack(){
		return st;
	}
	public void shuffle(TreasureDeck UsedTDeck){//assume it is vaild to use (means the TDeck is empty)
		List<TreasureCard> beforeRandomizeTreasureCardList = new ArrayList<TreasureCard>();
		for (TreasureCard i: UsedTDeck.getStack()) {
			beforeRandomizeTreasureCardList.add(i);
		}
		//shuffle
		List<Integer> tempList2 = new ArrayList<Integer>();
		for(int i=0;i<beforeRandomizeTreasureCardList.size();i++){
			tempList2.add(i);
		}
		for(int i=0;i<beforeRandomizeTreasureCardList.size();i++){
			this.st.push(beforeRandomizeTreasureCardList.get(tempList2.remove(new Random().nextInt(tempList2.size()))));
		}
		while(!UsedTDeck.getStack().empty()){
			UsedTDeck.getStack().pop();
		}
	}
}