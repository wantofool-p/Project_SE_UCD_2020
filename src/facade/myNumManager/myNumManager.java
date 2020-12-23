//this interface would handle the number from input
package facade.myNumManager;

import java.util.List;
import java.util.Scanner;
//this is a kind of sub facade
public interface myNumManager {//the interface for the "Facade" class
	// code reuse (input)
	static int inputNumberInRange(int minNum, int maxNum){return -1;};//looking for a in-range number
	private boolean checkNumberInRange(int minNum, int maxNum){return false;};//check if this number is in-range
	static int inputNumberInList(List<Integer> numList){return -1;};//looking for a in-list number
	private boolean checkNumberInList(List<Integer> numList){return false;};//check if this number is in-list

}
