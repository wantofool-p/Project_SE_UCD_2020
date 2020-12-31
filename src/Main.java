

import java.io.IOException;

import facade.MyGameManager;
import setting.Options;

public class Main{
	public static void main(String[] args){
		for(String i: args){
			if(i.equalsIgnoreCase("cheat")){
				System.out.println("cheat!!");
				Options.ifCheat=true;
			} else if(i.equalsIgnoreCase("mod")){
				System.out.println("mod!!");
				Options.mod();
			} else if(i.equalsIgnoreCase("real")){
				System.out.println("real!!");
				Options.real();
			} else if(i.equalsIgnoreCase("realdiv")){
				System.out.println("realdiv!!");
				Options.ifDiverAllowedStopAtSunkTile=false;
			} else if(i.equalsIgnoreCase("realnav")){
				System.out.println("realnav!!");
				Options.ifNavigatorHasOwnAbility=true;
			} else if(i.equalsIgnoreCase("realcopter")){
				System.out.println("realcopter!!");
				Options.ifHelicopterLiftPlayerShouldAtTheSameTile=true;
			} else if(i.equalsIgnoreCase("debug")){
				System.out.println("debug!!");
				Options.ifDebug=true;
			}
		}
		MyGameManager facade = new MyGameManager();
		facade.init();
		facade.printBoardWithAllCoordCLI();//DEBUG
		while(true){
			try {
				facade.begin();//turns begins
				facade.takeAction();//during turns
				facade.end();//turns ends
			} catch (IOException e) {
				e.printStackTrace();
			}//during this turn
		}
	}
}