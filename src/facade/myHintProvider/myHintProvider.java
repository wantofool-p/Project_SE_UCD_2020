//this interface would handle the number from input
package facade.myHintProvider;

import java.util.List;
import java.util.Scanner;
//this is a kind of sub facade
public interface myHintProvider {//the interface for the "Facade" class
	// menu
	static void menu(){};
	// move
	static void move(){};
	//shoreUp
	static void shoreUp(){};
	//give
	static void give(){};
	//capture
	static void capture(){};
	//use
	static void use(){};
	//switch
	static void switch(){};
	//end
	static void end(){};
	//use a flood card // (has been override in the "Facade" class)
	static void useWaterRise(){};//use a WaterRise! card // (has been override in the "Facade" class)

}