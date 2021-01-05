package test_cases;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Test;
import board.Board;
import board.tile.Cave;
import board.tile.Status;
import deck.TreasureDeck;
import deck.treasureCard.ChaliceCard;
import deck.treasureCard.FireCard;
import deck.treasureCard.Sandbags;
import role.DirectionType;
import role.Diver;
import role.Engineer;
import role.Explorer;
import role.Messenger;
import role.Navigator;
import treasure.Fire;
public class PlayerTest {
	private Board testB=new Board();
	/**For Diver at SUNK cliffsOfAbandon, when use ability, end() method should return 5 nearest Tiles
	*   {NullTile,               NullTile,                         twilightHollow,        tidalPalace,          NullTile,      NullTile},
		{NullTile,               breakersBridge(SUNK),             bronzeGate(nearest),   caveOfEmbers,         caveOfShadows, NullTile},
		{whisperingGarden(SUNK), cliffsOfAbandon(SUNK,Diver here), copperGate(SUNK),      coralPalace(nearest), crimsonForest, phantomRock},
		{watchtower(nearest),    dunesOfDeception(SUNK),           foolsLanding(nearest), goldGate,             howlingGarden, silverGate},
		{NullTile,               observatory(nearest),             mistyMarsh,            lostLagoon,           ironGate,      NullTile},
		{NullTile,               NullTile,                         templeOfTheSun,        templeOfTheMoon,      NullTile,      NullTile},
	*/
	@Test
	public void testDiverEnd() {
		Diver testPlayerD=new Diver();
		testB.getStdTile(1,1).setStatus(Status.SUNK);
		testB.getStdTile(2,0).setStatus(Status.SUNK);
		testB.getStdTile(2,1).setStatus(Status.SUNK);
		testB.getStdTile(2,2).setStatus(Status.SUNK);
		testB.getStdTile(3,1).setStatus(Status.SUNK);
		testPlayerD.setCurrStdTile(testB.getStdTile(2, 1));
		assertEquals("Should find 5 nearest tiles",5,testPlayerD.end(testB).size());
	}
	/**For Engineer at cliffsOfAbandon, when use ability, shoreup() method successfully makes dunesOfDeception, return NORMAL,
	* SUNK copperGate keep still, finally AP becomes 0 given 1
	*   {NullTile,         NullTile,                       twilightHollow,                   tidalPalace,     NullTile,      NullTile},
		{NullTile,         breakersBridge,                 bronzeGate,                       caveOfEmbers,    caveOfShadows, NullTile},
		{whisperingGarden, cliffsOfAbandon(Engineer here), copperGate(SUNK 1st,FLOODED 2rd), coralPalace,     crimsonForest, phantomRock},
		{watchtower,       dunesOfDeception(FLOODED),      foolsLanding,                     goldGate,        howlingGarden, silverGate},
		{NullTile,         observatory,                    mistyMarsh,                       lostLagoon,      ironGate,      NullTile},
		{NullTile,         NullTile,                       templeOfTheSun,                   templeOfTheMoon, NullTile,      NullTile},
	*/
	@Test
	public void testEngineerShoreUp() {
		Engineer testPlayerE=new Engineer();
		testPlayerE.setAP(1);
		testPlayerE.setCurrStdTile(testB.getStdTile(2, 1));
		testB.getStdTile(2,2).setStatus(Status.SUNK);//SUNK test 1st
		testB.getStdTile(3,1).setStatus(Status.FLOODED);
		assertEquals("Once the asked shore up is relevant to a SUNK tile, shoreUp should NOT work",false,testPlayerE.shoreUp(testB,DirectionType.DOWN,DirectionType.RIGHT));
		testB.getStdTile(2,2).setStatus(Status.FLOODED);//FLOODED TEST 2RD
		testPlayerE.shoreUp(testB,DirectionType.DOWN,DirectionType.RIGHT);//call again
		assertEquals("FLOODED copperGate should return NORMAL",Status.NORMAL,testB.getStdTile(2,2).getStatus());
		assertEquals("FLOODED dunesOfDeception should return NORMAL",Status.NORMAL,testB.getStdTile(3,1).getStatus());
		assertEquals("AP should run out",0,testPlayerE.getAP());
	}
	/**For Messenger at different tile with another player, can still give card directly by passCard() method
	*   {NullTile,         NullTile,                        twilightHollow,             tidalPalace,     NullTile,      NullTile},
		{NullTile,         breakersBridge,                  bronzeGate,                 caveOfEmbers,    caveOfShadows, NullTile},
		{whisperingGarden, cliffsOfAbandon(Messenger here), copperGate(Navigator here), coralPalace,     crimsonForest, phantomRock},
		{watchtower,       dunesOfDeception,                foolsLanding,               goldGate,        howlingGarden, silverGate},
		{NullTile,         observatory,                     mistyMarsh,                 lostLagoon,      ironGate,      NullTile},
		{NullTile,         NullTile,                        templeOfTheSun,             templeOfTheMoon, NullTile,      NullTile},
	*
	*/
	@Test
	public void testMessengerPassCard() {
		Messenger testPlayerM=new Messenger();
		Navigator testPlayerN=new Navigator();
		testPlayerM.setAP(1);
		testPlayerM.setCurrStdTile(testB.getStdTile(2,1));
		testPlayerN.setCurrStdTile(testB.getStdTile(2,2));
		//ArrayList<TreasureCard> testCards=new ArrayList<TreasureCard>();
		FireCard fireTest=new FireCard();
		ChaliceCard chaliceTest=new ChaliceCard();
		Sandbags sandbagsTest=new Sandbags();
		testPlayerM.addTreasureCard(fireTest);
		testPlayerM.addTreasureCard(chaliceTest);
		testPlayerM.addTreasureCard(sandbagsTest);
		testPlayerM.passCard(testPlayerN, 1);
		assertTrue("testPlayerN should get ChaliceCard",testPlayerN.getCards().get(0) instanceof ChaliceCard);
		assertEquals("The size of Messenger's cards should be 2",2,testPlayerM.getCards().size());
		assertEquals("AP should run out",0,testPlayerM.getAP());
	}
	//About Pilot and Explorer, their special ability of movement has been tested in PlayerMovementTest
	//About Navigator, since the ability is not required, here not plan to test
	/**Here another special point is, Explorer's nearest tile judgement is different from others since he can move diagonally
	*   {NullTile,               NullTile,                            twilightHollow,        tidalPalace,      NullTile,      NullTile},
		{NullTile,               breakersBridge(SUNK),                bronzeGate(nearest),   caveOfEmbers,     caveOfShadows, NullTile},
		{whisperingGarden(SUNK), cliffsOfAbandon(SUNK,Explorer here), copperGate(SUNK),      coralPalace,      crimsonForest, phantomRock},
		{watchtower(nearest),    dunesOfDeception(SUNK),              foolsLanding(nearest), goldGate,         howlingGarden, silverGate},
		{NullTile,               observatory,                         mistyMarsh,            lostLagoon,       ironGate,      NullTile},
		{NullTile,               NullTile,                            templeOfTheSun,         templeOfTheMoon, NullTile,      NullTile},
	*/
	@Test
	public void testExplorerSink() {
		Explorer testPlayerE=new Explorer();
		testB.getStdTile(1,1).setStatus(Status.SUNK);
		testB.getStdTile(2,0).setStatus(Status.SUNK);
		testB.getStdTile(2,1).setStatus(Status.SUNK);
		testB.getStdTile(2,2).setStatus(Status.SUNK);
		testB.getStdTile(3,1).setStatus(Status.SUNK);
		testPlayerE.setCurrStdTile(testB.getStdTile(2, 1));
		assertEquals("Should find 3 nearest tiles",3,testPlayerE.sink(testB).size());
	}
	@Test
	public void testCaptureTreasure(){
		//test 3 same treasure cards to capture treasure, should fail
		//test 5 same treasure cards to capture treasure, should success by costing 4
		Navigator testPlayerN1=new Navigator();
		Navigator testPlayerN2=new Navigator();
		Cave testCave=new Cave("testCave");
		testPlayerN1.setAP(1);
		testPlayerN2.setAP(1);
		for(int i=0;i<3;i++) {
			testPlayerN1.addTreasureCard(new FireCard());//assign 3 Firecards to testPlayer1
		}
		testPlayerN1.setCurrStdTile(testCave);
		testPlayerN2.setCurrStdTile(testCave);//Set testPlayers at caveOfShadows
		Fire COF=new Fire();
		Cave.setTreasure(COF);//Set Crystal of Fire treasure
		assertNotNull("should has treasure",Cave.getFire());
		TreasureDeck testTused=new TreasureDeck();
		assertFalse("3 cards to capture is not enough",testPlayerN1.captureTreasure(testTused));
		for(int i=0;i<5;i++) {
			testPlayerN2.addTreasureCard(new FireCard());//assign 5 Firecards to testPlayer2
		}
		assertNotNull("should has treasure",Cave.getFire());
		assertTrue("5 cards should be enough to capture",testPlayerN2.captureTreasure(testTused));
		assertEquals("After capture the player remain 1 treasure card",1,testPlayerN2.getCards().size());
		assertEquals("Should get treasure: Crystal of Fire",COF,testPlayerN2.getTreasures().get(0));
		assertEquals("AP should run out",0,testPlayerN2.getAP());
	}
	@After
	public void resetIfGet() throws Exception {
		Cave.setIfGet(false); //Reset Cave static ifGet
	}
}
