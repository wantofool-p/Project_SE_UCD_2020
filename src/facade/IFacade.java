//this is a overview for all methods that exist in the "Facade" class
package facade;

import java.util.Scanner;

//this is a overview for all methods that related to the "Facade" class
//this interface lists all methods inside the "Facade" class, just for a better overview/introduction, similar as the header file in C++
public interface IFacade {//the interface for the "Facade" class
	// print
	public void printBoardCLI();//print the Board
	public void printFDeckCLI();//print the Flood Deck
	public void printUsedFDeckCLI();//print the Used Flood Deck
	public void printTDeckCLI();//print the Treasure Deck
	public void printUsedTDeckCLI();//print the Used Treasure Deck
	public void printWaterMeterCLI();//print the WaterMeter
	public void printPlayersCLI();//print the Player List
	// init
	public void init();//initialization the game (must be called before call the func "begin")
	// Lose or Win
	public boolean ifLose();//check if the team lose the game
	public boolean ifWin();//check if the team win the game
	// turns
	public void begin();//turn begin
	public void action();//during the turn (player should spend all APs at here)
	public void end();//turn end

	// code reuse (input)
	private int inputNumberInRange(int minNum, int maxNum){return -1;};//looking for a in-range number
	private boolean checkNumberInRange(int minNum, int maxNum){return false;};//check if this number is in-range
	private void useFunctionalCard(){};//a player use a functional Treasure Card

}
