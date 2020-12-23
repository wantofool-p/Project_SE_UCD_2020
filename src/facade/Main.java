package facade;

import java.util.Scanner;

public class Main{
	public static void main(String[] args){
		Scanner scanner = new Scanner(System.in);//The Scanner should the unique. Only one Scanner exist. It should be exist until the program has been terminated.
		Facade facade = new Facade();

		facade.printBoardCLI(); // DEBUG // could be remove
		facade.printFDeckCLI(); // DEBUG // could be remove
		facade.printUsedFDeckCLI(); // DEBUG // could be remove
		facade.printTDeckCLI(); // DEBUG // could be remove
		facade.printUsedTDeckCLI(); // DEBUG // could be remove
		facade.printWaterMeterCLI(); // DEBUG // could be remove
		facade.printPlayersCLI(); // DEBUG // could be remove

		facade.init(scanner);

		facade.printBoardCLI(); // DEBUG // could be remove
		facade.printFDeckCLI(); // DEBUG // could be remove
		facade.printUsedFDeckCLI(); // DEBUG // could be remove
		facade.printTDeckCLI(); // DEBUG // could be remove
		facade.printUsedTDeckCLI(); // DEBUG // could be remove
		facade.printWaterMeterCLI(); // DEBUG // could be remove
		facade.printPlayersCLI(); // DEBUG // could be remove

		while(true){
			facade.begin();//turns begins
			facade.action(scanner);//during this turn
			facade.end();//turns ends
			if(facade.ifLose()){
				System.out.println("Your team has been defeated.");
				System.exit(0);
			}
			if(facade.ifWin()){
				System.out.println("Your team has captured all the treasures. You win.");
				System.exit(0);
			}
		}
	}
}