package test_cases;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import board.Board;
import board.tile.Status;
import role.DirectionType;
import role.Explorer;
import role.Pilot;
public class PlayerMovementTest {
	private Board testB;
	private Pilot testPlayerP;   /**use pilot and explorer to test is because: 
	                              *explorer can move to any directions and
	                              *pilot can fly freely*/
	private Explorer testPlayerE;
	
	 @Before
	    public void setUp() throws Exception {
	        testB = new Board();
	        testPlayerP=new Pilot();
	        testPlayerP.setAP(100);
	        testPlayerE=new Explorer();
	        testPlayerE.setAP(100);
	 }
	/**
	 * The standard board as below:
	 *  {NullTile,         NullTile,         twilightHollow, tidalPalace,     NullTile,      NullTile},
		{NullTile,         breakersBridge,   bronzeGate,     caveOfEmbers,    caveOfShadows, NullTile},
		{whisperingGarden, cliffsOfAbandon,  copperGate,     coralPalace,     crimsonForest, phantomRock},
		{watchtower,       dunesOfDeception, foolsLanding,   goldGate,        howlingGarden, silverGate},
		{NullTile,         observatory,      mistyMarsh,     lostLagoon,      ironGate,      NullTile},
		{NullTile,         NullTile,         templeOfTheSun, templeOfTheMoon, NullTile,      NullTile},
	 */
	 @Test
	 /**
	  * Firstly check explorer's eight directions to NullTile will be a fail:
	  * {NullTile(↖),         NullTile(↑),                             twilightHollow, tidalPalace,     NullTile,                             NullTile(↗)},
		{NullTile(←),         breakersBridge(testPlayerE 1st place),   bronzeGate,     caveOfEmbers,    caveOfShadows(testPlayerE 2nd place), NullTile},
		{whisperingGarden,    cliffsOfAbandon,                         copperGate,     coralPalace,     crimsonForest,                        phantomRock},
		{watchtower,          dunesOfDeception,                        foolsLanding,   goldGate,        howlingGarden,                        silverGate},
		{NullTile,            observatory(testPlayerE 3rd place),      mistyMarsh,     lostLagoon,      ironGate(testPlayerE 4th place),      NullTile(→)},
		{NullTile(↙),         NullTile,                                templeOfTheSun, templeOfTheMoon, NullTile(↓),                          NullTile(↘)},
	  * Secondly set pilot to any random standard tiles and then fly to NullTile, 
	  * this shouldn't work as well
	  */
	 public void testNullTileNotAllowed() { //If special example explorer and pilot is OK, all other types can be OK
		 testPlayerE.setCurrStdTile(testB.getStdTile(1, 1));
		 assertEquals("TOPLEFT to Nulltile shouldn't be allowed",false,testPlayerE.move(testB,DirectionType.TOPLEFT));//↖ to null
		 assertEquals("LEFT to Nulltile shouldn't be allowed",false,testPlayerE.move(testB,DirectionType.LEFT));//← to null
		 assertEquals("UP to Nulltile shouldn't be allowed",false,testPlayerE.move(testB,DirectionType.UP));//↑ to null
		 testPlayerE.setCurrStdTile(testB.getStdTile(1, 4));
		 assertEquals("TOPRIGHT to Nulltile shouldn't be allowed",false,testPlayerE.move(testB,DirectionType.TOPRIGHT));//↗ to null
		 testPlayerE.setCurrStdTile(testB.getStdTile(4, 1));
		 assertEquals("BOTTOMLEFT to Nulltile shouldn't be allowed",false,testPlayerE.move(testB,DirectionType.BOTTOMLEFT));//↙ to null
		 testPlayerE.setCurrStdTile(testB.getStdTile(4, 4));
		 assertEquals("RIGHT to Nulltile shouldn't be allowed",false,testPlayerE.move(testB,DirectionType.RIGHT));//→ to null
		 assertEquals("BOTTOMRIGHT to Nulltile shouldn't be allowed",false,testPlayerE.move(testB,DirectionType.BOTTOMRIGHT));//↘ to null
		 assertEquals("DOWN to Nulltile shouldn't be allowed",false,testPlayerE.move(testB,DirectionType.DOWN));//↓ to null
		 for(int x=0;x<6;x++) {
			 for(int y=0;y<6;y++) {
				 testPlayerP.setCurrStdTile(testB.getStdTile(x, y));
				 if(testB.getStdTile(x, y)!=null) {
					 assertEquals("Pilot to any Nulltile shouldn't be allowed,in this case [0,0] has problem",false,testPlayerP.flyAbility(testB,0,0));
					 assertEquals("Pilot to any Nulltile shouldn't be allowed,in this case [0,1] has problem",false,testPlayerP.flyAbility(testB,0,1));
					 assertEquals("Pilot to any Nulltile shouldn't be allowed,in this case [0,4] has problem",false,testPlayerP.flyAbility(testB,0,4));
					 assertEquals("Pilot to any Nulltile shouldn't be allowed,in this case [0,5] has problem",false,testPlayerP.flyAbility(testB,0,5));
					 assertEquals("Pilot to any Nulltile shouldn't be allowed,in this case [1,0] has problem",false,testPlayerP.flyAbility(testB,1,0));
					 assertEquals("Pilot to any Nulltile shouldn't be allowed,in this case [1,5] has problem",false,testPlayerP.flyAbility(testB,1,5));
					 assertEquals("Pilot to any Nulltile shouldn't be allowed,in this case [4,0] has problem",false,testPlayerP.flyAbility(testB,4,0));
					 assertEquals("Pilot to any Nulltile shouldn't be allowed,in this case [4,5] has problem",false,testPlayerP.flyAbility(testB,4,5));
					 assertEquals("Pilot to any Nulltile shouldn't be allowed,in this case [5,0] has problem",false,testPlayerP.flyAbility(testB,5,0));
					 assertEquals("Pilot to any Nulltile shouldn't be allowed,in this case [5,1] has problem",false,testPlayerP.flyAbility(testB,5,1));
					 assertEquals("Pilot to any Nulltile shouldn't be allowed,in this case [5,4] has problem",false,testPlayerP.flyAbility(testB,5,4));
					 assertEquals("Pilot to any Nulltile shouldn't be allowed,in this case [5,5] has problem",false,testPlayerP.flyAbility(testB,5,5));
				 }
			 }
		 }
	 }
	 /**Set explorer at 'foolsLanding', test move by eight directions:
	  *  {NullTile,         NullTile,           twilightHollow,          tidalPalace,        NullTile,      NullTile},
		{NullTile,         breakersBridge,      bronzeGate,              caveOfEmbers,       caveOfShadows, NullTile},
		{whisperingGarden, cliffsOfAbandon(←),  copperGate(↑),           coralPalace(↗),     crimsonForest, phantomRock},
		{watchtower,       dunesOfDeception(↓), foolsLanding(→)(↖),      goldGate,           howlingGarden(↘), silverGate},
		{NullTile,         observatory,         mistyMarsh,              lostLagoon(↙),      ironGate,      NullTile},
		{NullTile,         NullTile,            templeOfTheSun,          templeOfTheMoon,    NullTile,      NullTile},
	  */
	 public void testNormalMovement() {
		 testPlayerE.setCurrStdTile(testB.getStdTile(3, 2));
		 testPlayerE.move(testB,DirectionType.UP);
		 assertEquals("After move UP, should be at copperGate",testB.getStdTile(2,2),testPlayerE.getCurrStdTile());
		 testPlayerE.move(testB,DirectionType.LEFT);
		 assertEquals("After move LEFT, should be at cliffsOfAbandon",testB.getStdTile(2,1),testPlayerE.getCurrStdTile());
		 testPlayerE.move(testB,DirectionType.DOWN);
		 assertEquals("After move DOWN, should be at dunesOfDeception",testB.getStdTile(3,1),testPlayerE.getCurrStdTile());
		 testPlayerE.move(testB,DirectionType.RIGHT);
		 assertEquals("After move RIGHT, should be at foolsLanding",testB.getStdTile(3,2),testPlayerE.getCurrStdTile());
		 testPlayerE.move(testB,DirectionType.TOPRIGHT);
		 assertEquals("After move TOPRIGHT, should be at coralPalace",testB.getStdTile(2,3),testPlayerE.getCurrStdTile());
		 testPlayerE.move(testB,DirectionType.BOTTOMRIGHT);
		 assertEquals("After move BOTTOMRIGHT, should be at howlingGarden",testB.getStdTile(3,4),testPlayerE.getCurrStdTile());
		 testPlayerE.move(testB,DirectionType.BOTTOMLEFT);
		 assertEquals("After move BOTTOMLEFT, should be at lostLagoon",testB.getStdTile(4,3),testPlayerE.getCurrStdTile());
		 testPlayerE.move(testB,DirectionType.TOPLEFT);
		 assertEquals("After move TOPLEFT, should be at foolsLanding",testB.getStdTile(3,2),testPlayerE.getCurrStdTile());	 
	 }
	 /**Set pilot and explorer at foolsLanding and surrounded by sunk tiles, eight directions and fly to one of them 
	  * are both not allowed
	  * {NullTile,         NullTile,               twilightHollow,       		   tidalPalace,           NullTile,      NullTile},
		{NullTile,         breakersBridge,         bronzeGate,           		   caveOfEmbers,          caveOfShadows, NullTile},
		{whisperingGarden, cliffsOfAbandon(SUNK),  copperGate(SUNK),     		   coralPalace(SUNK),     crimsonForest, phantomRock},
		{watchtower,       dunesOfDeception(SUNK), foolsLanding(pilot/Explorer),   goldGate(SUNK),        howlingGarden, silverGate},
		{NullTile,         observatory(SUNK),      mistyMarsh(SUNK),               lostLagoon(SUNK),      ironGate,      NullTile},
		{NullTile,         NullTile,               templeOfTheSun,                 templeOfTheMoon,       NullTile,      NullTile},
	  */
	 public void testSunkTileNotAllowed() {
		 testB.getStdTile(2,1).setStatus(Status.SUNK);
		 testB.getStdTile(2,2).setStatus(Status.SUNK);
		 testB.getStdTile(2,3).setStatus(Status.SUNK);
		 testB.getStdTile(3,1).setStatus(Status.SUNK);
		 testB.getStdTile(3,3).setStatus(Status.SUNK);
		 testB.getStdTile(4,1).setStatus(Status.SUNK);
		 testB.getStdTile(4,2).setStatus(Status.SUNK);
		 testB.getStdTile(4,3).setStatus(Status.SUNK);
		 testPlayerE.setCurrStdTile(testB.getStdTile(3, 2));
		 testPlayerP.setCurrStdTile(testB.getStdTile(3, 2));
		 assertEquals("TOPLEFT to SUNK cliffsOfAbandon shouldn't be allowed",false,testPlayerE.move(testB,DirectionType.TOPLEFT));//↖ 
		 assertEquals("LEFT to SUNK dunesOfDeception shouldn't be allowed",false,testPlayerE.move(testB,DirectionType.LEFT));//←
		 assertEquals("UP to SUNK copperGate shouldn't be allowed",false,testPlayerE.move(testB,DirectionType.UP));//↑
		 assertEquals("TOPRIGHT to SUNK coralPalace shouldn't be allowed",false,testPlayerE.move(testB,DirectionType.TOPRIGHT));//↗ 
		 assertEquals("BOTTOMLEFT to SUNK observatory shouldn't be allowed",false,testPlayerE.move(testB,DirectionType.BOTTOMLEFT));//↙ 
		 assertEquals("RIGHT to SUNK goldGate shouldn't be allowed",false,testPlayerE.move(testB,DirectionType.RIGHT));//→ 
		 assertEquals("BOTTOMRIGHT to SUNK lostLagoon shouldn't be allowed",false,testPlayerE.move(testB,DirectionType.BOTTOMRIGHT));//↘ 
		 assertEquals("DOWN to SUNK mistyMarsh shouldn't be allowed",false,testPlayerE.move(testB,DirectionType.DOWN));//↓ 
		 assertEquals("Pilot fly to SUNK tiles shouldn't be allowed,in this case cliffsOfAbandon has problem",false,testPlayerP.flyAbility(testB,2,1));
	 }
}
