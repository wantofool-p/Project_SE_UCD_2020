//this interface would handle the number from input
package facade.myNumManager;

import java.util.List;
import java.util.Scanner;
//this is a kind of sub facade
public interface myNumManager {//the interface for the "Facade" class
	// code reuse (input)
	static int inputNumberInRange(String hint, int minValidNum, int maxValidNum){
		int tempInt=0;
		while(true){
			System.out.print(hint + " (" + minValidNum + "~" + maxValidNum + "):"));
			if (scanner.hasNextInt()) {
				tempInt = scanner.nextInt();
				if ((1<=tempInt)||(tempInt<=4)){//check if this number is in-range
					return tempInt;
				} else {
					System.out.println("not in range of 1~4");
				}
			} else {
				System.out.println("not int");
			}
		}
		return -1; // ERROR
	};//looking for a in-range number
	static int inputNumberInList(String hint, List<Integer> validNumList){
		int tempInt=0;
		while(true){
			System.out.print(hint + " (" + minValidNum + "~" + maxValidNum + "):");
			if (scanner.hasNextInt()) {
				tempInt = scanner.nextInt();
				if ((1<=tempInt)||(tempInt<=4)){//check if this number is in-range
					return tempInt;
				} else {
					System.out.println("not in range of 1~4");
				}
			} else {
				System.out.println("not int");
			}
		}
		return -1; // ERROR
	};//looking for a in-list number
	private boolean checkNumberInList(List<Integer> validNumList){return false;};//check if this number is in-list

}
