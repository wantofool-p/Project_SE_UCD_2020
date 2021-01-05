package test_cases;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import board.Board;
import board.tile.Cave;
import board.tile.Garden;
import board.tile.Palace;
import board.tile.Status;
import board.tile.Temple;
import deck.treasureCard.HelicopterLift;
import deck.treasureCard.Sandbags;
import deck.treasureCard.StoneCard;
import deck.treasureCard.WaterRise;
import facade.MyGameManager;
import role.Diver;
import role.Engineer;
import role.Explorer;
import role.Messenger;
import role.Navigator;
import role.Pilot;
import role.StdRole;
import treasure.Fire;
public class MyGameManagerTest {
	private MyGameManager testMGM;
	@Before
	public void setUp() throws Exception {//The card deck should be loaded once GameManager set up, the used card deck should be empty
		testMGM = new MyGameManager();
		assertFalse("New board set up should NOT be initialized",testMGM.getBoard().ifInit);
		assertEquals("FloodDeck should be prepared",24,testMGM.getFloodDeck().getStack().size());
		assertEquals("TreasureDeck should be prepared",28,testMGM.getTreasureDeck().getStack().size());
		assertEquals("usedFloodDeck should have no thing",0,testMGM.getUsedFloodDeck().getStack().size());
		assertEquals("usedTreasureDeck should have no thing",0,testMGM.getUsedTreasureDeck().getStack().size());
	}
	@Test
	public void testInitForTest() {
		testMGM.initForTest(2,4);//the input of initForTest() are the num of players and water meters respectively
		//The board should be initialized at this time, or say the board will be rearranged
		assertTrue("New board set up should be initialized",testMGM.getBoard().ifInit);
		assertEquals("Should assign 2 Players",2,testMGM.getBoard().getPlayerList().size());
		assertEquals("Water Meter should be set as 4",4,testMGM.getWaterMeter().getMeter());
	}
	@Test
	public void testFirstTurnAssignCards() {
		//No matter what role is assigned, everyone will get 2 treasure cards
		//Also both of them shouldn't be WaterRise Card
		ArrayList<StdRole> testPlayerList = new ArrayList<StdRole>();
		testPlayerList.add(new Diver());
		testPlayerList.add(new Engineer());
		testPlayerList.add(new Explorer());
		testPlayerList.add(new Messenger());
		testPlayerList.add(new Navigator());
		testPlayerList.add(new Pilot());
		testMGM.getBoard().setPlayerList(testPlayerList);
		testMGM.initAssignCards();
		for(int i=0;i<6;i++) {
			assertFalse("No one can own WaterRise Card by the 1st card in the 1st turn",testMGM.getBoard().getPlayerList().get(i).getCards().get(0) instanceof WaterRise );
			assertFalse("No one can own WaterRise Card by the 2nd card in the 1st turn",testMGM.getBoard().getPlayerList().get(i).getCards().get(1) instanceof WaterRise );
		}
	}
	@Test
	public void testInitPlayerByRoles() {
		//this method should randomly assign roles to the set number of players
		//In this case assign 4 players, should be always different by two calls
		testMGM.initRoles(4);
		ArrayList<StdRole> testPlayerList1 =testMGM.getBoard().getPlayerList();
		testMGM.initRoles(4);
		ArrayList<StdRole> testPlayerList2 =testMGM.getBoard().getPlayerList();
		assertFalse("Both player lists should be different",testPlayerList1.equals(testPlayerList2));
	}
	/**Test 4 situations for lose:
	* 1. Before Fire treasure is taken, caveOfEmbers and caveOfShadows has been SUNK
	* Use standard board as below:
	*   {NullTile,         NullTile,          twilightHollow,     tidalPalace,           NullTile,             NullTile},
		{NullTile,         breakersBridge,    bronzeGate,         caveOfEmbers(SUNK),    caveOfShadows(SUNK),  NullTile},
		{whisperingGarden, cliffsOfAbandon,   copperGate,         coralPalace,           crimsonForest,        phantomRock},
		{watchtower,       dunesOfDeception,  foolsLanding,       goldGate,              howlingGarden,        silverGate},
		{NullTile,         observatory,       mistyMarsh,         lostLagoon,            ironGate,             NullTile},
		{NullTile,         NullTile,          templeOfTheSun,     templeOfTheMoon,       NullTile,             NullTile},
	*2. foolsLanding has been SUNK
	*3. Navigator at observatory and it's SUNK, the surrounded tiles(NOT treasureTile) are also SUNK
	*   {NullTile,         NullTile,                             twilightHollow,     tidalPalace,           NullTile,             NullTile},
		{NullTile,         breakersBridge(SUNK),                 bronzeGate,         caveOfEmbers,          caveOfShadows,        NullTile},
		{whisperingGarden, cliffsOfAbandon(SUNK),                copperGate,         coralPalace,           crimsonForest,        phantomRock},
		{watchtower,       dunesOfDeception(SUNK),               foolsLanding,       goldGate,              howlingGarden,        silverGate},
		{NullTile,         observatory(Navigator here,SUNK),     mistyMarsh(SUNK),   lostLagoon(SUNK),      ironGate(SUNK),       NullTile},
		{NullTile,         NullTile,                             templeOfTheSun,     templeOfTheMoon,       NullTile,             NullTile},
	*4. Water Level reaches 5 by set 3 Water Meter after using 5 WaterRise card, the relationship shows below
	*   //waterMeter(1~10)
		//waterLevel(2~5)
		//waterMeter  waterLevel  difficulty
		//     1           2         Novice
		//     2           2         Normal
		//     3           3         Elite
		//     4           3         Legendary
		//     5           3
		//     6           4
		//     7           4
		//     8           5
		//     9           5
		//     10          Game Over :X
	*/
	@Test
	public void testIfLose1() {
		//1
		Fire COF=new Fire();
		Cave.setTreasure(COF);
		testMGM.getBoard().getStdTile(1,3).setStatus(Status.SUNK);
		testMGM.getBoard().getStdTile(1,4).setStatus(Status.SUNK);
		assertTrue("Treasure hasn't been captured but both Caves has been SUNK, should be a true loss",testMGM.ifLose1());
	}
	@After
	public void resetStatus() {
		testMGM.getBoard().getStdTile(1,3).setStatus(Status.NORMAL);
		testMGM.getBoard().getStdTile(1,4).setStatus(Status.NORMAL);
	}
	@Test
	public void testIfLose2() {
		//2
		testMGM.getBoard().getStdTile(3,2).setStatus(Status.SUNK);
		assertTrue("Fool's Landing has been SUNK, should be a true loss",testMGM.ifLose2());
	}
	@Test
	public void testIfLose3() {
		//3
		Navigator testPlayerN=new Navigator();
		ArrayList<StdRole> testPlayerList = new ArrayList<StdRole>();
		testPlayerList.add(testPlayerN);
		testMGM.getBoard().setPlayerList(testPlayerList);
		testPlayerN.setCurrStdTile(testMGM.getBoard().getStdTile(4, 1));
		testMGM.getBoard().getStdTile(1,1).setStatus(Status.SUNK);//breakersBridge(SUNK)
		testMGM.getBoard().getStdTile(2,1).setStatus(Status.SUNK);//cliffsOfAbandon(SUNK)
		testMGM.getBoard().getStdTile(3,1).setStatus(Status.SUNK);//dunesOfDeception(SUNK)
		testMGM.getBoard().getStdTile(4,1).setStatus(Status.SUNK);//observatory(Navigator here,SUNK)
		testMGM.getBoard().getStdTile(4,2).setStatus(Status.SUNK);//mistyMarsh(SUNK)
		testMGM.getBoard().getStdTile(4,3).setStatus(Status.SUNK);//lostLagoon(SUNK)
		testMGM.getBoard().getStdTile(4,4).setStatus(Status.SUNK);//ironGate(SUNK)
		testPlayerN.sink(testMGM.getBoard());//works similar with playerSwim() method
		assertTrue("Navigator is not alive, should be a true loss",testMGM.ifLose3());
	}
	@Test
	public void testIfLose4() {
		//4
		testMGM.getWaterMeter().setWaterMeter(9);
		assertFalse("Water Level haven't reached the skull and crossbones, should NOT be a loss",testMGM.ifLose4());
		testMGM.useWaterRiseTest(new WaterRise());//Water Meter should be 8, reach Water Level 5, the method useWaterRise() works well
		assertTrue("Water Level reach the skull and crossbones, should be a true loss",testMGM.ifLose4());
	}
	@Test
	/**Here test the method useSandbags
	* Assume a Navigator, he has two Sandbags Card
	* If he wants to shore up NORMAL Tile, it should be a fail
	* If he wants to shore up FLOODED Tile, it should return Normal, and cost one of the Sandbags
	*   {NullTile,                     NullTile,          twilightHollow,     tidalPalace,           NullTile,             NullTile},
		{NullTile,                     breakersBridge,    bronzeGate,         caveOfEmbers(SUNK),    caveOfShadows(SUNK),  NullTile},
		{whisperingGarden,             cliffsOfAbandon,   copperGate,         coralPalace,           crimsonForest,        phantomRock},
		{watchtower(3,0),              dunesOfDeception,  foolsLanding,       goldGate,              howlingGarden,        silverGate},
		{NullTile,                     observatory,       mistyMarsh,         lostLagoon,            ironGate,             NullTile},
		{NullTile,                     NullTile,          templeOfTheSun,     templeOfTheMoon,       NullTile,             NullTile},
	* We take watchtower for example
	*/
	public void testUseSandbagsForTest() {
		Navigator testPlayerN=new Navigator();
		ArrayList<StdRole> testPlayerList = new ArrayList<StdRole>();
		testPlayerList.add(testPlayerN);
		testMGM.getBoard().setPlayerList(testPlayerList);
		testPlayerN.addTreasureCard(new Sandbags());
		testPlayerN.addTreasureCard(new Sandbags());
		int[] tempInt=testMGM.getBoard().getStdTile(3,0).getCoord();//get the position of NORMAL watchtower
		assertFalse("No need to use Sandbags for NORMAL watchtower",testMGM.useSandbagsForTest(new Sandbags(),0,testPlayerN,tempInt));
		assertEquals("The two Sandbags Cards won't be cost",2,testPlayerN.getCards().size());
		testMGM.getBoard().getStdTile(3,0).setStatus(Status.FLOODED);//Set watchtower
		assertTrue("Need to use Sandbags for FLOODED watchtower",testMGM.useSandbagsForTest(new Sandbags(),0,testPlayerN,tempInt));
		assertEquals("FLOODED watchtower turns NORMAL",Status.NORMAL,testMGM.getBoard().getStdTile(3,1).getStatus());
		assertEquals("Use 1 Sandbags Card, should has 1 remaining",1,testPlayerN.getCards().size());
	}
	/**foolslanding shouldn't be SUNK, and all other Treasures are collected,
	*the static variable ifGet has ensure once collect the treasure
	*The standard board:
	*   {NullTile,                 NullTile,          twilightHollow,             tidalPalace,           NullTile,               NullTile},
		{NullTile,                 breakersBridge,    bronzeGate,                 caveOfEmbers,          caveOfShadows,          NullTile},
		{whisperingGarde,          cliffsOfAbandon,   copperGate,                 coralPalace ,          crimsonForest,          phantomRock},
		{watchtower,               dunesOfDeception,  foolsLanding,               goldGate,              howlingGarden,          silverGate},
		{NullTile,                 observatory,       mistyMarsh,                 lostLagoon,            ironGate,               NullTile},
		{NullTile,                 NullTile,          templeOfTheSun,             templeOfTheMoon,       NullTile,               NullTile},
	*
	*/
	@Test
	public void testifWin() {
		//all tiles chosen are default NORMAL
		Palace.setIfGet(true);//all treasures taken
		Cave.setIfGet(true);
		Temple.setIfGet(true);
		Garden.setIfGet(true);
		testMGM.setBoard(new Board());
		//Assume 4 players assigned with different roles
		Navigator testPlayerN=new Navigator();
		Pilot testPlayerP=new Pilot();
		Engineer testPlayerE=new Engineer();
		Messenger testPlayerM=new Messenger();
		ArrayList<StdRole> testPlayerList = new ArrayList<StdRole>();
		testPlayerList.add(testPlayerN);
		testPlayerList.add(testPlayerP);
		testPlayerList.add(testPlayerE);
		testPlayerList.add(testPlayerM);
		testMGM.getBoard().setPlayerList(testPlayerList);
		testMGM.getBoard().getStdTile(3, 2).setPlayers(testMGM.getBoard().getPlayerList());//Set them in foolslanding
		assertTrue("Should satisfy the win condition",testMGM.ifWin());
	}
	@After
	public void resetIfGet() {
		Cave.setIfGet(false);
		Palace.setIfGet(false);
		Garden.setIfGet(false);
		Temple.setIfGet(false);
	}
	/**Test use Helicopterlift to bring multiple players to an expected destination
	* The standard board with NORMAL status:
	*   {NullTile,                 NullTile,          twilightHollow,             tidalPalace,           NullTile,               NullTile},
		{NullTile,                 breakersBridge,    bronzeGate,                 caveOfEmbers,          caveOfShadows,          NullTile},
		{whisperingGarde,          cliffsOfAbandon,   copperGate,                 coralPalace ,          crimsonForest,          phantomRock},
		{watchtower(3,0),          dunesOfDeception,  foolsLanding(3,2),          goldGate,              howlingGarden,          silverGate},
		{NullTile,                 observatory,       mistyMarsh,                 lostLagoon(4,3),       ironGate,               NullTile},
		{NullTile,                 NullTile,          templeOfTheSun,             templeOfTheMoon,       NullTile,               NullTile},
	 * In this case, testPlayerE and testPlayerN will at watchtower
	 * testPlayerE use Helicopterlift to bring both to foolslanding
	 * Then assume ifWin() is true, the function should just return ture(win the game), won't move them to any place(try lostLagoon)
	 */
	@Test
	public void  testUseHelicopterLiftByMultipleForTest() {
		Engineer testPlayerE=new Engineer();
		Navigator testPlayerN=new Navigator();
		ArrayList<StdRole> testPlayerList = new ArrayList<StdRole>();
		testPlayerList.add(testPlayerE);
		testPlayerList.add(testPlayerN);
		testMGM.getBoard().setPlayerList(testPlayerList);

		testPlayerE.addTreasureCard(new HelicopterLift());
		testPlayerE.setCurrStdTile(testMGM.getBoard().getStdTile(3,0));//Set them in watchtower
		testPlayerN.setCurrStdTile(testMGM.getBoard().getStdTile(3,0));
		int[] tempInt=testMGM.getBoard().getStdTile(3,2).getCoord();//go to foolsLanding(3,2)
		HashSet<Integer> tempIntHashSet=new HashSet<Integer>();
		tempIntHashSet.add(1);
		tempIntHashSet.add(2);//take 2 members, indicates testPlayerE and testPlayerN

		testMGM.useHelicopterLiftByMultipleForTest(new HelicopterLift(),0,testPlayerE,tempIntHashSet,tempInt);
		assertEquals("testPlayerE should have no cards",0,testPlayerE.getCards().size());
		assertEquals("watchtower should have no people",0,testMGM.getBoard().getStdTile(3,0).getPlayers().size());
		assertEquals("foolsLanding should get two people",2,testMGM.getBoard().getStdTile(3,2).getPlayers().size());

		Palace.setIfGet(true);//all treasures taken
		Cave.setIfGet(true);
		Temple.setIfGet(true);
		Garden.setIfGet(true);
		int[] tempInt2=testMGM.getBoard().getStdTile(4,3).getCoord();//go to lostlagoon
		testPlayerE.addTreasureCard(new HelicopterLift());
		testMGM.useHelicopterLiftByMultipleForTest(new HelicopterLift(),0,testPlayerE,tempIntHashSet,tempInt2);
		assertEquals("lostlagoon should have no people, because the condition of win the game is satisfied",0,testMGM.getBoard().getStdTile(4,3).getPlayers().size());
	}
	@After
	public void resetIfGet2() {
		Cave.setIfGet(false);
		Palace.setIfGet(false);
		Garden.setIfGet(false);
		Temple.setIfGet(false);
	}
	@Test
	public void  testUseCardWhileDrop() throws IOException {//only test drop card here
		Engineer testPlayerE=new Engineer();
		ArrayList<StdRole> testPlayerList = new ArrayList<StdRole>();
		testPlayerList.add(testPlayerE);
		testMGM.getBoard().setPlayerList(testPlayerList);
		testPlayerE.addTreasureCard(new Sandbags());
		testPlayerE.addTreasureCard(new StoneCard());
		testMGM.UseCardWhileDrop(testPlayerE,1,0);
		assertEquals("Drop card with index 1, one card should be remained",1,testPlayerE.getCards().size());
		assertTrue("The remaining card should be Stone card with original index 2",testPlayerE.getCards().get(0) instanceof StoneCard);
	}
}