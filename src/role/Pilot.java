package role;

import java.util.ArrayList;

import board.Board;
import board.tile.Status;
import board.tile.StdTile;

public class Pilot extends StdRole{
	protected boolean ifAbilityUsed = false;
	public Pilot(){
		this.name="Pilot";
	}
	@Override
	public void begin(){
		this.AP=3;
		this.ifAbilityUsed=false;
	}
	public boolean getIfAbilityUsed(){
		return ifAbilityUsed;
	}
	@Override
	public ArrayList<StdTile> sink(Board board){//return valid Tile List
		ArrayList<StdTile> tempList = new ArrayList<StdTile>();
		StdTile temp;
		for(int id=0; id<24; id++){
			temp=board.getStdTile(id);
			if(temp.getStatus()!=Status.SUNK){
				tempList.add(temp);
			} else {
				;
			}
		}
		if (tempList.size()==0){
			this.isAlive=false;
		}
		return tempList;
	}
	public boolean flyAbility(Board board, int[] coord){//ONCE per turn
		int row=coord[0], col=coord[1];
		return this.flyAbility(board, row, col);
	}
	public boolean flyAbility(Board board, int row, int col){//ONCE per turn
		if(this.ifAbilityUsed){
			return false;
		} else if(this.AP==0){
			return false;//no enough AP
		} else {
			StdTile destination;
			destination = board.getStdTile(row, col);
			if(destination==null){
				return false;
			} else if(destination.getStatus()==Status.SUNK){
				return false;//failure
			} else {
				this.freeMove(destination);
				this.AP--;
				this.ifAbilityUsed=true;
				return true;
			}
		}
	}
}
