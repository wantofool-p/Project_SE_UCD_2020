package facade;

import java.util.ArrayList;

import board.Board;
import board.tile.Cave;
import board.tile.Garden;
import board.tile.Palace;
import board.tile.Status;
import board.tile.Temple;
import board.tile.TreasureTile;
import deck.treasureCard.HelicopterLift;
import deck.treasureCard.Sandbags;
import role.DirectionType;
import role.Diver;
import role.Explorer;
import role.Messenger;
import role.Navigator;
import role.Pilot;
import role.StdRole;
import setting.Options;
import treasure.TreasureType;

public interface MyOutput {
	static void printCardListForUse(StdRole selectedPlayer){
		System.out.println("[Treasure cards in tot: " + selectedPlayer.getCards().size() + "]");
		for(int i=0; i<selectedPlayer.getCards().size(); i++){
			System.out.print((i+1) + ": " + selectedPlayer.getCards().get(i).getName());
			if((selectedPlayer.getCards().get(i).getClass()!=HelicopterLift.class)&&(selectedPlayer.getCards().get(i).getClass()!=Sandbags.class)){
				System.out.print("[Not Allowed]");
			}
			System.out.println();
		}
		System.out.println("0: cancel");
	}
	static void printCardListForDrop(StdRole selectedPlayer){
		System.out.println("[Treasure cards in tot: " + selectedPlayer.getCards().size() + "]");
		for(int i=0; i<selectedPlayer.getCards().size(); i++){
			System.out.println((i+1) + ": " + selectedPlayer.getCards().get(i).getName());
		}
		System.out.println("0: cancel");
	}
	static void printPlayerListForChoose(Board board){
		int j=1;
		for(StdRole i:board.getPlayerList()){
			System.out.println(j + ": " + i.getName());
			j++;
		}
		System.out.println("0: Cancel");
	}
	static void printPlayerListForChoose(ArrayList<StdRole> playerList){
		int j=1;
		for(StdRole i:playerList){
			System.out.println(j + ": " + i.getName());
			j++;
		}
		System.out.println("0: Cancel");
	}
	static void printMoveMenu(Board board, ArrayList<Boolean> moveFlag, StdRole selectedPlayer){
		DirectionType[] upDownLeftRight = {DirectionType.UP, DirectionType.DOWN, DirectionType.LEFT, DirectionType.RIGHT};
		DirectionType[] diagonal = {DirectionType.TOPLEFT, DirectionType.BOTTOMRIGHT, DirectionType.BOTTOMLEFT, DirectionType.TOPRIGHT};
		ArrayList<DirectionType> validDirection = new ArrayList<DirectionType>();
		int tempInt = 4;
		StringBuilder[] str = {
			new StringBuilder("8: ↑"),
			new StringBuilder("2: ↓"),
			new StringBuilder("4: ←"),
			new StringBuilder("6: →"),
			new StringBuilder("7: ↖"),
			new StringBuilder("3: ↘"),
			new StringBuilder("1: ↙"),
			new StringBuilder("9: ↗")
		};
		for(int i=0; i<4; i++){
			if(moveFlag.get(i)){
				validDirection.add(upDownLeftRight[i]);
			} else {
				str[i].append(" [Not Allowed]");
			}
		}
		if(selectedPlayer.getClass()==Explorer.class){
			tempInt = 8;
			for(int i=0; i<4; i++){
				if(moveFlag.get(i+4)){
					validDirection.add(diagonal[i]);
				} else {
					str[i+4].append(" [Not Allowed]");
				}
			}
		}
		board.printWithCoordB(validDirection, selectedPlayer);
		for(int i=0; i<tempInt; i++){
			System.out.println(str[i]);
		}
		System.out.println("0: cancel");// TODO "5 or 0: cancel"
	}
	static void printStoreUpMenu(Board board, ArrayList<Boolean> moveFlag, StdRole selectedPlayer){
		DirectionType[] upDownLeftRightMiddle = {DirectionType.UP, DirectionType.DOWN, DirectionType.LEFT, DirectionType.RIGHT, DirectionType.MIDDLE};
		DirectionType[] diagonal = {DirectionType.TOPLEFT, DirectionType.BOTTOMRIGHT, DirectionType.BOTTOMLEFT, DirectionType.TOPRIGHT};
		ArrayList<DirectionType> validDirection = new ArrayList<DirectionType>();
		int tempInt = 5;
		StringBuilder[] str = {
			new StringBuilder("8: ↑"),
			new StringBuilder("2: ↓"),
			new StringBuilder("4: ←"),
			new StringBuilder("6: →"),
			new StringBuilder("5: •"),
			new StringBuilder("7: ↖"),
			new StringBuilder("3: ↘"),
			new StringBuilder("1: ↙"),
			new StringBuilder("9: ↗")
		};
		for(int i=0; i<5; i++){
			if(moveFlag.get(i)){
				validDirection.add(upDownLeftRightMiddle[i]);
			} else {
				str[i].append(" [Not Allowed]");
			}
		}
		if(selectedPlayer.getClass()==Explorer.class){
			tempInt = 9;
			for(int i=0; i<4; i++){
				if(moveFlag.get(i+5)){
					validDirection.add(diagonal[i]);
				} else {
					str[i+5].append(" [Not Allowed]");
				}
			}
		}
		board.printWithCoordB(validDirection, selectedPlayer);
		for(int i=0; i<tempInt; i++){
			System.out.println(str[i]);
		}
		System.out.println("0: cancel");
	}
	/*
	ArrayList<ArrayList<Boolean>> == {
		row0: move: {if↑, if↓, if←, if→, if↖, if↘, if↙, if↗}
		row1: shoreUp: {if↑, if↓, if←, if→, if•, if↖, if↘, if↙, if↗}
		row2: giveATreasureCard: {if alone, if no card}
		row3: captureATreasure: {if no enough any same type treasure cards, if not at treasure tile, if no enough required treasure cards}
		row4: use functional card: {if no functional card}
		row5: ability: {if ability is limited to use for now}
		row6: end: {if the selectedPlayer is not the currPlayer}
	}
	*/
	static ArrayList<ArrayList<Boolean>> printMenu(Board board, StdRole selectedPlayer, StdRole currPlayer){
		ArrayList<ArrayList<Boolean>> result = new ArrayList<ArrayList<Boolean>>();
		System.out.println("[This is "+ currPlayer.getName() + "'s turn]");
		System.out.println("[The player has been selected: " + selectedPlayer.getName() + "  AP:" + selectedPlayer.getAP() + "]");
		boolean ifNoAP = selectedPlayer.getAP()<=0;
		//move
		result.add(printMove(board, selectedPlayer, ifNoAP));
		//shoreUp
		result.add(printShoreUp(board, selectedPlayer, ifNoAP));
		//giveATreasureCard
		result.add(printPassCard(board, selectedPlayer, ifNoAP));
		//captureATreasure
		result.add(printCaptureTreasure(board, selectedPlayer, ifNoAP));
		//use functional card
		result.add(printUseCard(board, selectedPlayer));
		//switch the selected player
		System.out.println("6: select another player");
		//ability
		result.add(printUseAbility(board, selectedPlayer, ifNoAP));
		//end
		result.add(printEnd(board, selectedPlayer, ifNoAP, currPlayer));
		return result;
	}
	static ArrayList<Boolean> printMove(Board board, StdRole selectedPlayer, boolean ifNoAP){
		ArrayList<Boolean> moveFlag = new ArrayList<Boolean>();//↑↓←→↖↘↙↗
		System.out.print("1: move");
		if(ifNoAP){
			System.out.print(" [No enough AP]");
		}
		DirectionType[] upDownLeftRight = {DirectionType.UP, DirectionType.DOWN, DirectionType.LEFT, DirectionType.RIGHT};
		for(DirectionType i: upDownLeftRight){
			if(selectedPlayer.getDestination(board, i)==null){
				moveFlag.add(false);
			} else {
				if((selectedPlayer.getDestination(board, i).getStatus()==Status.SUNK)&&(selectedPlayer.getClass()!=Diver.class)){
					moveFlag.add(false);
				} else {
					moveFlag.add(true);
				}
			}
		}
		if(selectedPlayer.getClass()==Explorer.class){
			DirectionType[] diagonal = {DirectionType.TOPLEFT, DirectionType.BOTTOMRIGHT, DirectionType.BOTTOMLEFT, DirectionType.TOPRIGHT};
			for(DirectionType i: diagonal){
				if(selectedPlayer.getDestination(board, i)==null){
					moveFlag.add(false);
				} else if(selectedPlayer.getDestination(board, i).getStatus()==Status.SUNK){
					moveFlag.add(false);
				} else {
					moveFlag.add(true);
				}
			}
		} else {
			for(int i=0; i<4; i++){
				moveFlag.add(false);
			}
		}
		boolean tempFlag = true;
		for(Boolean i: moveFlag){
			if(i==true){
				tempFlag=false;
				break;
			}
		}
		if(tempFlag){
			System.out.print(" [No valid tile nearby]");
		}
		System.out.println();
		return moveFlag;
	}
	static ArrayList<Boolean> printShoreUp(Board board, StdRole selectedPlayer, boolean ifNoAP){
		ArrayList<Boolean> shoreUpFlag = new ArrayList<Boolean>();//↑↓←→•↖↘↙↗
		System.out.print("2: shore up a flood tile");
		if(ifNoAP){
			System.out.print(" [No enough AP]");
		}
		DirectionType[] upDownLeftRightMiddle = {DirectionType.UP, DirectionType.DOWN, DirectionType.LEFT, DirectionType.RIGHT, DirectionType.MIDDLE};
		for(DirectionType i: upDownLeftRightMiddle){
			if(selectedPlayer.getDestination(board, i)==null){
				shoreUpFlag.add(false);
			} else {
				if(selectedPlayer.getDestination(board, i).getStatus()==Status.FLOODED){
					shoreUpFlag.add(true);
				} else {
					shoreUpFlag.add(false);
				}
			}
		}
		if(selectedPlayer.getClass()==Explorer.class){
			DirectionType[] diagonal = {DirectionType.TOPLEFT, DirectionType.BOTTOMRIGHT, DirectionType.BOTTOMLEFT, DirectionType.TOPRIGHT};
			for(DirectionType i: diagonal){
				if(selectedPlayer.getDestination(board, i)==null){
					shoreUpFlag.add(false);
				} else if(selectedPlayer.getDestination(board, i).getStatus()==Status.FLOODED){
					shoreUpFlag.add(true);
				} else {
					shoreUpFlag.add(false);
				}
			}
		} else {
			for(int i=0; i<4; i++){
				shoreUpFlag.add(false);
			}
		}
		boolean tempFlag = true;
		for(Boolean i: shoreUpFlag){
			if(i==true){
				tempFlag=false;
				break;
			}
		}
		if(tempFlag){
			System.out.print(" [No flooded tile nearby]");
		}
		System.out.println();
		return shoreUpFlag;
	}
	static ArrayList<Boolean> printPassCard(Board board, StdRole selectedPlayer, boolean ifNoAP){
		ArrayList<Boolean> passCardFlag = new ArrayList<Boolean>();//[0]=if alone, [1]=if no card
		System.out.print("3: pass a treasure card to another player");
		if(ifNoAP){
			System.out.print(" [No enough AP]");
		}
		if(selectedPlayer.getCurrStdTile().getPlayers().size()==1){
			passCardFlag.add(true);
			if(selectedPlayer.getClass()!=Messenger.class){
				System.out.print(" [No other player]");
			}
		} else {
			passCardFlag.add(false);
		}
		if(selectedPlayer.getCards().size()==0){
			passCardFlag.add(true);
			System.out.print(" [No cards]");
		} else {
			passCardFlag.add(false);
		}
		System.out.println();
		return passCardFlag;
	}
	static ArrayList<Boolean> printCaptureTreasure(Board board, StdRole selectedPlayer, boolean ifNoAP){
		ArrayList<Boolean> captureTreasureFlag = new ArrayList<Boolean>();//[0]=if no enough any same type treasure cards, [1]=if not at treasure tile, [2]=if no enough required treasure cards
		System.out.print("4: capture the treasure if you can");
		if(ifNoAP){
			System.out.print(" [No enough AP]");
		}
		int countFire=0, countWind=0, countStore=0, countChalice=0;
		TreasureType tempTreasureType;
		for(int i=0; i<selectedPlayer.getCards().size(); i++){
			tempTreasureType = selectedPlayer.getCards().get(i).getTreasureType();
			if (tempTreasureType==TreasureType.FIRE){
				countFire++;
			} else if (tempTreasureType==TreasureType.WIND){
				countWind++;
			} else if (tempTreasureType==TreasureType.STONE){
				countStore++;
			} else if (tempTreasureType==TreasureType.CHALICE){
				countChalice++;
			} else {
				;
			}
		}
		if((countFire>=4)||(countWind>=4)||(countStore>=4)||(countChalice>=4)){
			captureTreasureFlag.add(false);
		} else {
			captureTreasureFlag.add(true);
			System.out.print(" [No enough correlate treasure card]");
		}
		if(selectedPlayer.getCurrStdTile() instanceof TreasureTile){
			captureTreasureFlag.add(false);
			if((selectedPlayer.getCurrStdTile().getClass() == Cave.class)&&(countFire<4)){
				captureTreasureFlag.add(true);
				System.out.print(" [No enough Fire card]");
			} else if((selectedPlayer.getCurrStdTile().getClass() == Garden.class)&&(countWind<4)){
				captureTreasureFlag.add(true);
				System.out.print(" [No enough Wind card]");
			} else if((selectedPlayer.getCurrStdTile().getClass() == Palace.class)&&(countChalice<4)){
				captureTreasureFlag.add(true);
				System.out.print(" [No enough Chalice card]");
			} else if((selectedPlayer.getCurrStdTile().getClass() == Temple.class)&&(countStore<4)){
				captureTreasureFlag.add(true);
				System.out.print(" [No enough Store card]");
			} else {
				captureTreasureFlag.add(false);
			}
			if((selectedPlayer.getCurrStdTile().getClass() == Cave.class)&&(Cave.getIfGet())){
				System.out.print(" [The fire treasure has been taken]");
			} else if((selectedPlayer.getCurrStdTile().getClass() == Garden.class)&&(Garden.getIfGet())){
				System.out.print(" [The wind treasure has been taken]");
			} else if((selectedPlayer.getCurrStdTile().getClass() == Palace.class)&&(Palace.getIfGet())){
				System.out.print(" [The chalice treasure has been taken]");
			} else if((selectedPlayer.getCurrStdTile().getClass() == Temple.class)&&(Temple.getIfGet())){
				System.out.print(" [The store treasure has been taken]");
			} else {
				captureTreasureFlag.add(false);
			}
		} else {
			captureTreasureFlag.add(true);
			captureTreasureFlag.add(true);
			System.out.print(" [Not at treasure tile]");
		}
		System.out.println();
		return captureTreasureFlag;
	}
	static ArrayList<Boolean> printUseCard(Board board, StdRole selectedPlayer){
		ArrayList<Boolean> useCardFlag = new ArrayList<Boolean>();//[0]=if no functional card
		System.out.print("5: use a functional treasure card");
		boolean tempFlag = true;
		for(int i=0; i<selectedPlayer.getCards().size(); i++){
			if((selectedPlayer.getCards().get(i).getClass()==HelicopterLift.class)||(selectedPlayer.getCards().get(i).getClass()==Sandbags.class)){
				tempFlag=false;
				break;
			}
		}
		useCardFlag.add(tempFlag);
		if(useCardFlag.get(0)){
			System.out.print(" [No Functional Card]");
		}
		System.out.println();
		return useCardFlag;
	}
	static ArrayList<Boolean> printUseAbility(Board board, StdRole selectedPlayer, boolean ifNoAP){
		ArrayList<Boolean> useAbilityFlag = new ArrayList<Boolean>();//[0]=if ability is limited to use for now
		if(selectedPlayer.getClass()==Pilot.class){
			System.out.print("7: fly ability");
		} else if((selectedPlayer.getClass()==Navigator.class)&&(Options.ifNavigatorHasOwnAbility==true)){
			System.out.print("7: guide ability");
		} else {
			useAbilityFlag.add(false);
			return useAbilityFlag;
		}
		if(ifNoAP){
			System.out.print(" [No enough AP]");
		}
		if(selectedPlayer.getClass()==Pilot.class){
			if(((Pilot) selectedPlayer).getIfAbilityUsed()){
				useAbilityFlag.add(true);
				System.out.print(" [Ability used]");
			} else {
				useAbilityFlag.add(false);
			}
		} else {
			useAbilityFlag.add(false);
		}
		System.out.println();
		return useAbilityFlag;
	}
	static ArrayList<Boolean> printEnd(Board board, StdRole selectedPlayer, boolean ifNoAP, StdRole currPlayer){
		ArrayList<Boolean> endFlag = new ArrayList<Boolean>();//[0] = if the selectedPlayer is not the currPlayer
		if(ifNoAP){
			System.out.print("0: end this turn");//end
		} else {
			System.out.print("0: drop all remaining APs to end this turn");//end
		}
		if(currPlayer!=selectedPlayer){
			endFlag.add(true);
			System.out.print(" [Not your turn]");//end
		} else {
			endFlag.add(false);
		}
		System.out.println();
		return endFlag;
	}
}