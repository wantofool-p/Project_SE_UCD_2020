package role;
import java.util.HashSet;
import board.Board;
import board.tile.Status;
import board.tile.StdTile;
import setting.Options;
public class Navigator extends StdRole{
	public Navigator(){
		this.name="Navigator";
	}
	public HashSet<StdTile> getValidGuideDestination(Board board, StdRole targetRole){//many times per turn // not used //TODO fix
		if(Options.ifNavigatorHasOwnAbility){
			if(this.AP==0){
				return null;//no enough AP
			} else if(board==null){
				System.err.println("board == null");
				return null;//ERR
			} else if(targetRole==null){
				System.err.println("targetRole == null");
				return null;//ERR
			} else {//clockwise -- ↑→↓←↗↘↙↖
				HashSet<StdTile> tempValidTiles1 = new HashSet<StdTile>();
				HashSet<StdTile> tempValidTiles2 = new HashSet<StdTile>();
				int[] currCoord = targetRole.getCoord();
				int[] rowIncrement = {-1, 0, 1, 0, -1, 1, 1, -1};//clockwise -- ↑→↓←↗↘↙↖
				int[] colIncrement = {0, 1, 0, -1, 1, 1, -1, -1};//clockwise -- ↑→↓←↗↘↙↖
				StdTile tempTile;
				for(int i=0; i<4; i++){//↑→↓←
					tempTile=board.getStdTile(currCoord[0]+rowIncrement[i], currCoord[1]+colIncrement[i]);//↑→↓←
					if(tempTile!=null){
						if((tempTile.getStatus()!=Status.SUNK)||(targetRole.getClass()==Diver.class)){
							tempValidTiles1.add(tempTile);
						}
					}
				}
				if(targetRole.getClass()==Explorer.class){
					for(int i=4; i<8; i++){//↗↘↙↖
						tempTile=board.getStdTile(currCoord[0]+rowIncrement[i], currCoord[1]+colIncrement[i]);//↗↘↙↖
						if(tempTile!=null){
							if(tempTile.getStatus()!=Status.SUNK){
								tempValidTiles1.add(tempTile);
							}
						}
					}
				}
				HashSet<StdTile> tempValidTiles3 = new HashSet<StdTile>();
				for(StdTile i: tempValidTiles1){
					tempValidTiles3 = getValidGuideDestinationUP2(board, targetRole, i);
					if(tempValidTiles3!=null){
						if(tempValidTiles3.size()!=0){
							for(StdTile j: tempValidTiles3){
								tempValidTiles2.add(j);
							}
						}
					}
				}
				for(StdTile i: tempValidTiles2){
					tempValidTiles1.add(i);
				}
				if(tempValidTiles1.size()!=0){
					this.AP--;
				}
				return tempValidTiles1;
			}
		} else {
			return null;//disabled
		}
	}
	private HashSet<StdTile> getValidGuideDestinationUP2(Board board, StdRole targetRole, StdTile currTile){
		if(Options.ifNavigatorHasOwnAbility){
			if(this.AP==0){
				return null;//no enough AP
			} else if(board==null){
				System.err.println("board == null");
				return null;//ERR
			} else if(targetRole==null){
				System.err.println("targetRole == null");
				return null;//ERR
			} else if(currTile==null){
				System.err.println("currTile == null");
				return null;//ERR
			} else {//clockwise -- ↑→↓←↗↘↙↖
				HashSet<StdTile> tempValidTiles2 = new HashSet<StdTile>();
				int[] currCoord = currTile.getCoord();
				int[] rowIncrement = {-1, 0, 1, 0, -1, 1, 1, -1};//clockwise -- ↑→↓←↗↘↙↖
				int[] colIncrement = {0, 1, 0, -1, 1, 1, -1, -1};//clockwise -- ↑→↓←↗↘↙↖
				StdTile tempTile;
				for(int i=0; i<4; i++){//↑→↓←
					tempTile=board.getStdTile(currCoord[0]+rowIncrement[i], currCoord[1]+colIncrement[i]);//↑→↓←
					if(tempTile!=null){
						if((tempTile.getStatus()!=Status.SUNK)||(targetRole.getClass()==Diver.class)){
							tempValidTiles2.add(tempTile);
						}
					}
				}
				if(targetRole.getClass()==Explorer.class){
					for(int i=4; i<8; i++){//↗↘↙↖
						tempTile=board.getStdTile(currCoord[0]+rowIncrement[i], currCoord[1]+colIncrement[i]);//↗↘↙↖
						if(tempTile!=null){
							if(tempTile.getStatus()!=Status.SUNK){
								tempValidTiles2.add(tempTile);
							}
						}
					}
				}
				return tempValidTiles2;
			}
		} else {
			return null;//disabled
		}
	}
}
