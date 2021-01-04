package test_cases;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import board.Board;
import treasure.Chalice;
import treasure.Fire;
import treasure.Stone;
import treasure.Wind;
public class BoardTest {
	private Board testB;
	 @Before
	    public void setUp() throws Exception {//new board set up should be initialized
	        testB = new Board();
	        testB.init();
	        assertTrue("New board set up should be initialized",testB.ifInit);
	 }
	@Test
	public void testPlaceGetTreasure() {
        assertTrue("TidalPalace should manage Ocean's Chalice", testB.getStdTile(14).getTreasure() instanceof Chalice); 
        assertTrue("CaveOfShadows should manage the Crystal of the Fire", testB.getStdTile(10).getTreasure() instanceof Fire);
        assertTrue("TempleOftheMoon should manage the Earth Stone ", testB.getStdTile(16).getTreasure() instanceof Stone);
        assertTrue("Whispering Garden should manage the Statue of the Wind", testB.getStdTile(12).getTreasure() instanceof Wind);
	}
	@Test
	public void testRearrange() {//24 tiles in total, theoreticlly after shuffling the tile may be at the original place
		                         //looking for a coordinate difference after shuffling
		int[] cr = new int[24];//rows
		int[] cc = new int[24];//cols
		for(int i=0; i<24; i++) {
			cr[i] = (testB.getStdTile(i).getCoord())[0];
			cc[i] = (testB.getStdTile(i).getCoord())[1];
		}
//		int c00r=(testB.getStdTile(0).getCoord())[0];
//		int c00c=(testB.getStdTile(0).getCoord())[1];
//		
//		int c01r=(testB.getStdTile(1).getCoord())[0];
//		int c01c=(testB.getStdTile(1).getCoord())[1];
//		
//		int c02r=(testB.getStdTile(2).getCoord())[0];
//		int c02c=(testB.getStdTile(2).getCoord())[1];
//		
//		int c03r=(testB.getStdTile(3).getCoord())[0];
//		int c03c=(testB.getStdTile(3).getCoord())[1];
//		
//		int c04r=(testB.getStdTile(4).getCoord())[0];
//		int c04c=(testB.getStdTile(4).getCoord())[1];
//		
//		int c05r=(testB.getStdTile(5).getCoord())[0];
//		int c05c=(testB.getStdTile(5).getCoord())[1];
//		
//		int c06r=(testB.getStdTile(6).getCoord())[0];
//		int c06c=(testB.getStdTile(6).getCoord())[1];
//		
//		int c07r=(testB.getStdTile(7).getCoord())[0];
//		int c07c=(testB.getStdTile(7).getCoord())[1];
//		
//		int c08r=(testB.getStdTile(8).getCoord())[0];
//		int c08c=(testB.getStdTile(8).getCoord())[1];
//		
//		int c09r=(testB.getStdTile(9).getCoord())[0];
//		int c09c=(testB.getStdTile(9).getCoord())[1];
//	
//		int c10r=(testB.getStdTile(10).getCoord())[0];
//		int c10c=(testB.getStdTile(10).getCoord())[1];
//		
//		int c11r=(testB.getStdTile(11).getCoord())[0];
//		int c11c=(testB.getStdTile(11).getCoord())[1];
//		
//		int c12r=(testB.getStdTile(12).getCoord())[0];
//		int c12c=(testB.getStdTile(12).getCoord())[1];
//		
//		int c13r=(testB.getStdTile(13).getCoord())[0];
//		int c13c=(testB.getStdTile(13).getCoord())[1];
//		
//		int c14r=(testB.getStdTile(14).getCoord())[0];
//		int c14c=(testB.getStdTile(14).getCoord())[1];
//		
//		int c15r=(testB.getStdTile(15).getCoord())[0];
//		int c15c=(testB.getStdTile(15).getCoord())[1];
//		
//		int c16r=(testB.getStdTile(16).getCoord())[0];
//		int c16c=(testB.getStdTile(16).getCoord())[1];
//		
//		int c17r=(testB.getStdTile(17).getCoord())[0];
//		int c17c=(testB.getStdTile(17).getCoord())[1];
//		
//		int c18r=(testB.getStdTile(18).getCoord())[0];
//		int c18c=(testB.getStdTile(18).getCoord())[1];
//		
//		int c19r=(testB.getStdTile(19).getCoord())[0];
//		int c19c=(testB.getStdTile(19).getCoord())[1];
//		
//		int c20r=(testB.getStdTile(20).getCoord())[0];
//		int c20c=(testB.getStdTile(20).getCoord())[1];
//		
//		int c21r=(testB.getStdTile(21).getCoord())[0];
//		int c21c=(testB.getStdTile(21).getCoord())[1];
//		
//		int c22r=(testB.getStdTile(22).getCoord())[0];
//		int c22c=(testB.getStdTile(22).getCoord())[1];
//		
//		int c23r=(testB.getStdTile(23).getCoord())[0];
//		int c23c=(testB.getStdTile(23).getCoord())[1];
		testB.rearrange();
		double similarity = 0;
		for(int i=0; i<24; i++) {
			if(((testB.getStdTile(i).getCoord())[0]==cr[i])&&((testB.getStdTile(i).getCoord())[1]==cc[i])){
				similarity += 1.0/24.0;
			}
		}
		//should be true at most of time
		assertTrue("After shuffling, the similarity for both board may <= 0.33", (similarity<=0.33));
		//assertTrue("After shuffling, the new position of twilightHollow should be changed",(c00r!=(testB.getStdTile(0).getCoord())[0])||(c00c!=(testB.getStdTile(0).getCoord())[1]));
		//assertTrue("After shuffling, the new position of breakersBridge should be changed",(c01r!=(testB.getStdTile(1).getCoord())[0])||(c01c!=(testB.getStdTile(1).getCoord())[1]));
		//assertTrue("After shuffling, the new position of lostLagoon should be changed",(c02r!=(testB.getStdTile(2).getCoord())[0])||(c02c!=(testB.getStdTile(2).getCoord())[1]));
		//assertTrue("After shuffling, the new position of watchtower should be changed",(c03r!=(testB.getStdTile(3).getCoord())[0])||(c03c!=(testB.getStdTile(3).getCoord())[1]));
		//assertTrue("After shuffling, the new position of crimsonForest should be changed",(c04r!=(testB.getStdTile(4).getCoord())[0])||(c04c!=(testB.getStdTile(4).getCoord())[1]));
		//assertTrue("After shuffling, the new position of cliffsOfAbandon should be changed",(c05r!=(testB.getStdTile(5).getCoord())[0])||(c05c!=(testB.getStdTile(5).getCoord())[1]));
		//assertTrue("After shuffling, the new position of mistyMarsh should be changed",(c06r!=(testB.getStdTile(6).getCoord())[0])||(c06c!=(testB.getStdTile(6).getCoord())[1]));
		//assertTrue("After shuffling, the new position of phantomRock should be changed",(c07r!=(testB.getStdTile(7).getCoord())[0])||(c07c!=(testB.getStdTile(7).getCoord())[1]));
		//assertTrue("After shuffling, the new position of dunesOfDeception should be changed",(c08r!=(testB.getStdTile(8).getCoord())[0])||(c08c!=(testB.getStdTile(8).getCoord())[1]));
		//assertTrue("After shuffling, the new position of observatory should be changed",(c09r!=(testB.getStdTile(9).getCoord())[0])||(c09c!=(testB.getStdTile(9).getCoord())[1]));
		//assertTrue("After shuffling, the new position of caveOfShadows should be changed",(c10r!=(testB.getStdTile(10).getCoord())[0])||(c10c!=(testB.getStdTile(10).getCoord())[1]));
		//assertTrue("After shuffling, the new position of caveOfEmbers should be changed",(c11r!=(testB.getStdTile(11).getCoord())[0])||(c11c!=(testB.getStdTile(11).getCoord())[1]));
		//assertTrue("After shuffling, the new position of whisperingGarden should be changed",(c12r!=(testB.getStdTile(12).getCoord())[0])||(c12c!=(testB.getStdTile(12).getCoord())[1]));
		//assertTrue("After shuffling, the new position of howlingGarden should be changed",(c13r!=(testB.getStdTile(13).getCoord())[0])||(c13c!=(testB.getStdTile(13).getCoord())[1]));
		//assertTrue("After shuffling, the new position of tidalPalace should be changed",(c14r!=(testB.getStdTile(14).getCoord())[0])||(c14c!=(testB.getStdTile(14).getCoord())[1]));
		//assertTrue("After shuffling, the new position of coralPalace should be changed",(c15r!=(testB.getStdTile(15).getCoord())[0])||(c15c!=(testB.getStdTile(15).getCoord())[1]));
		//assertTrue("After shuffling, the new position of templeOfTheMoon should be changed",(c16r!=(testB.getStdTile(16).getCoord())[0])||(c16c!=(testB.getStdTile(16).getCoord())[1]));
		//assertTrue("After shuffling, the new position of templeOfTheSun should be changed",(c17r!=(testB.getStdTile(17).getCoord())[0])||(c17c!=(testB.getStdTile(17).getCoord())[1]));
		//assertTrue("After shuffling, the new position of foolsLanding should be changed",(c18r!=(testB.getStdTile(18).getCoord())[0])||(c18c!=(testB.getStdTile(18).getCoord())[1]));
		//assertTrue("After shuffling, the new position of goldGate should be changed",(c19r!=(testB.getStdTile(19).getCoord())[0])||(c19c!=(testB.getStdTile(19).getCoord())[1]));
		//assertTrue("After shuffling, the new position of ironGate should be changed",(c20r!=(testB.getStdTile(20).getCoord())[0])||(c20c!=(testB.getStdTile(20).getCoord())[1]));
		//assertTrue("After shuffling, the new position of silverGate should be changed",(c21r!=(testB.getStdTile(21).getCoord())[0])||(c21c!=(testB.getStdTile(21).getCoord())[1]));
		//assertTrue("After shuffling, the new position of bronzeGate should be changed",(c22r!=(testB.getStdTile(22).getCoord())[0])||(c22c!=(testB.getStdTile(22).getCoord())[1]));
		//assertTrue("After shuffling, the new position of copperGate should be changed",(c23r!=(testB.getStdTile(23).getCoord())[0])||(c23c!=(testB.getStdTile(23).getCoord())[1]));
	}
}
