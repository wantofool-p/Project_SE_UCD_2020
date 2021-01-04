package test_cases;
import org.junit.Test;
import static org.junit.Assert.*;
import board.Board;
import board.tile.Cave;
import board.tile.FoolsLanding;
import board.tile.Garden;
import board.tile.Palace;
import board.tile.Temple;
import deck.FloodDeck;
import deck.TreasureDeck;
import deck.floodCard.FloodCard;
import deck.treasureCard.ChaliceCard;
import deck.treasureCard.FireCard;
import deck.treasureCard.HelicopterLift;
import deck.treasureCard.Sandbags;
import deck.treasureCard.StoneCard;
import deck.treasureCard.TreasureCard;
import deck.treasureCard.WaterRise;
import deck.treasureCard.WindCard;
public class DeckTest {
	@Test
   public void testLoad() {
	   FloodDeck testF=new FloodDeck();
	   TreasureDeck testT=new TreasureDeck();
	   Board testB=new Board();
	   testF.init(testB);
	   assertEquals("Flood deck must contain 24 cards after load",24,testF.getStack().size());
	   testT.init();
	   assertEquals("Treasure deck must contain 28 cards after load",28,testT.getStack().size());
	   for(int i=0;i<testF.getStack().size()-1;i++) {
	   assertTrue("Flood deck must contain Floodcards", testF.getStack().get(i) instanceof FloodCard); 
       }
	   for(int i=0;i<testT.getStack().size()-1;i++) {
	   assertTrue("Treasure deck must contain Treasurecards",testT.getStack().get(i) instanceof TreasureCard);
	   }
   }
	@Test
   public void testShuffle() {
	   FloodDeck testF=new FloodDeck();
	   TreasureDeck testT=new TreasureDeck();
	   FloodDeck testFused=new FloodDeck();
	   TreasureDeck testTused=new TreasureDeck();
	   
	   testF.shuffle(testFused);
	   testT.shuffle(testTused);
	   
	   assertTrue("Initialized testT should be empty given by initialized shuffled testTused!",testT.getStack().isEmpty());
	   assertTrue("Initialized testF should be empty given by initialized shuffled testFused!",testF.getStack().isEmpty());
	   
	   testFused.getStack().push(new FloodCard("1",new Temple("testTemple")));//assign 5 types of Floodcard for Fused
	   testFused.getStack().push(new FloodCard("2",new Palace("testPalace")));
	   testFused.getStack().push(new FloodCard("3",new Garden("testGarden")));
	   testFused.getStack().push(new FloodCard("4",new FoolsLanding()));
	   testFused.getStack().push(new FloodCard("5",new Cave("testCave")));
	   testTused.getStack().push(new WindCard()); //assign 8 types of Treasurecard for Tused
	   testTused.getStack().push(new StoneCard());
	   testTused.getStack().push(new FireCard());
	   testTused.getStack().push(new ChaliceCard());
	   testTused.getStack().push(new WindCard());
	   testTused.getStack().push(new Sandbags());
	   testTused.getStack().push(new WaterRise());
	   testTused.getStack().push(new HelicopterLift());
	   
	   testF.shuffle(testFused);
	   testT.shuffle(testTused);
	   
	   assertNotNull("testF should be added by the shuffled testFused and have cards",testF);
	   assertNotNull("testT should be added by the shuffled testTused and have cards",testT);
	   assertEquals("testF must contain 5 cards after shuffling",5,testF.getStack().size());
	   assertEquals("testT must contain 8 cards after shuffling",8,testT.getStack().size());

   }
   @Test
    public void testUseFloodCard() {
       FloodDeck testF=new FloodDeck();
 	   FloodDeck testFused=new FloodDeck();
 	   testF.getStack().push(new FloodCard("1",new Temple("testTemple")));
	   testF.getStack().push(new FloodCard("2",new Palace("testPalace")));
	   testF.getStack().push(new FloodCard("3",new Garden("testGarden")));
	   testF.getStack().push(new FloodCard("4",new FoolsLanding()));
	   testF.getStack().push(new FloodCard("5",new Cave("testCave")));
	   testF.useCard(testFused);
	   assertEquals("testF must contain 4 cards after using 1 card ",4,testF.getStack().size());
	   assertEquals("testFused must contain 1 card after using 1 card ",1,testFused.getStack().size());
	   for(int i=0;i<4;i++) {
		   testF.useCard(testFused);
	   }
	   assertEquals("testF must contain 5 cards again after running out the cards in it ",5,testF.getStack().size());
	   assertEquals("testFused must contain 0 card",0,testFused.getStack().size());
    }
}
