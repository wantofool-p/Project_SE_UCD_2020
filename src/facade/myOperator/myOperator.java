//this interface would handle the number from input
package facade.myOperator;

import java.util.List;
import java.util.Scanner;
//this is a kind of sub facade
public interface myOperator {//the interface for the "Facade" class
	// use card
	static void useFloodCard(){};//use a flood card // (has been override in the "Facade" class)
	static void useWaterRise(){};//use a WaterRise! card // (has been override in the "Facade" class)

}



