//this interface would handle the number from input
package facade;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;

import board.Board;
//this is a kind of sub facade
public interface MyInput {//the interface for the "Facade" class
	static void inputAnything() throws IOException {//looking for a confirm // may never use
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		br.readLine();
	};
	static ArrayList<Integer> inputMultipleOneDigitNumber(String hint, ArrayList<Integer> validNumList) throws IOException{//looking for an in-list number
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str;
		int temp = 0;
		boolean flag = true;
		ArrayList<Integer> result = new ArrayList<Integer>();
		do {
			System.out.println(hint);
			str = br.readLine();
			System.out.println(str);//debug
			for(int i=0; i<str.length(); i++){
				temp = str.charAt(i)-48;
				if(validNumList.contains(temp)){
					result.add(temp);
					flag = false;
				}
			}
			if(flag){
				System.out.println("not in list");
			}
		} while (flag);
		return result;
	};
	static int inputOneDigitNumber(String hint, int minValidNum, int maxValidNum) throws IOException{//looking for an in-range number
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str;
		int temp = 0;
		boolean flag = true;
		do {
			System.out.println(hint);
			str = br.readLine();
			System.out.println(str);//debug
			temp = str.charAt(0)-48;
			flag = (temp<minValidNum)||(maxValidNum<temp);
			if(flag){
				System.out.println("not in range");
			}
		} while (flag);
		return temp;
	};
	static int inputOneDigitNumber(String hint, ArrayList<Integer> validNumList) throws IOException{//looking for an in-list number
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str;
		int temp = 0;
		boolean flag = true;
		do {
			System.out.println(hint);
			str = br.readLine();
			System.out.println(str);//debug
			temp = str.charAt(0)-48;
			flag = !validNumList.contains(temp);
			if(flag){
				System.out.println("not in list");
			}
		} while (flag);
		return temp;
	};
	static int inputOneNumber(String hint, int minValidNum, int maxValidNum) throws IOException{//looking for an in-list number
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str;
		boolean loopFlag = true;
		boolean tempFlag = true;
		int tempSum=0;
		char tempChar;
		while(loopFlag){
			System.out.println(hint);
			str = br.readLine();
			tempSum=0;
			for(int i=0; i<str.length(); i++){
				tempChar = str.charAt(i);
				if((48<=tempChar)&&(tempChar<=57)){
					tempFlag = true;
					tempSum*=10;
					tempSum+=tempChar-48;
				} else if(tempFlag){
					break;
				}
			}
			if(tempFlag==false){
				System.out.println("not int");
			} else if((tempSum<minValidNum)||(maxValidNum<tempSum)){
				System.out.println("not in range");
			} else {
				loopFlag=false;
			}
		}
		return tempSum;
	};
	static HashSet<Integer> chooseMultiPlayer(String hint, Board board) throws IOException{//looking for an in-range number
		System.out.println("choose multiple players, separate by comma (eg: 1,2)(0 to cancel)");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str;
		HashSet<Integer> tempIntHashSet;
		boolean flag = true;
		do {
			str = br.readLine();
			System.out.println(str);//debug
			if(str=="0"){
				return null;//cancel
			}
			tempIntHashSet = str2intHashSet(str);
			if(tempIntHashSet==null){
				System.out.println("not valid input");
			} else {
				for(int i:tempIntHashSet){
					if(i>board.getPlayerList().size()){
						System.out.println("not valid player");
						tempIntHashSet=null;
						break;
					}
				}
			}
			if(tempIntHashSet!=null){
				flag=false;
			}
		} while (flag);
		return tempIntHashSet;
	};
	static HashSet<Integer> str2intHashSet(String str){
		boolean flag=false;
		char tempChar;
		int sum=0;
		int i=0;
		HashSet<Integer> tempIntHashSet = new HashSet<Integer>();
		while(flag==false){
			for(; i<str.length(); i++){
				tempChar = str.charAt(i);
				if((48<=tempChar)&&(tempChar<=57)){
					flag = true;
					sum*=10;
					sum+=tempChar-48;
				} else if(flag){
					break;
				}
			}
			if(i>=str.length()){
				if(flag){
					tempIntHashSet.add(sum);
				}
				return tempIntHashSet;//success
			} else {//find next int
				flag=false;
				tempIntHashSet.add(sum);
				sum=0;
			}
		}
		return null;//ERR
	}
	// static int chooseOnePlayer(String hint, Board board) throws IOException{//looking for an in-range number
	// 	return -1;//TODO
	// };
	static int[] inputStartCoord(Board board) throws IOException{//looking for an valid Start Coord
		return inputCoord("input Start Coord (row,col eg: 1,1)(0 to cancel):", board);
	};
	static int[] inputDestinationCoord(Board board) throws IOException{//looking for an valid Destination Coord
		return inputCoord("input Destination Coord (row,col eg, 1,1)(0 to cancel):", board);
	};
	static int[] inputCoord(String hint, ArrayList<int[]> validCoord) throws IOException{//looking for an valid Coord
		System.out.println(hint);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str;
		int[] tempCoord;
		boolean flag = true;
		do {
			str = br.readLine();
			System.out.println(str);//debug
			if(str.charAt(0)=='0'){
				return null;//cancel
			}
			tempCoord = str2coord(str);
			if(tempCoord==null){
				System.out.println("not valid input");
				continue;
			}
			for(int[] i: validCoord){
				if((i[0]==tempCoord[0])&&(i[1]==tempCoord[1])){
					flag=false;
					break;
				}
			}
			if(flag){
				System.out.println("not valid Coord");
			}
		} while (flag);
		return tempCoord;
	};
	static int[] inputCoord(String hint, Board board) throws IOException{//looking for an valid Coord
		board.printWithAllMeaningfulCoord();
		System.out.println(hint);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str;
		int[] tempCoord;
		boolean flag = true;
		do {
			str = br.readLine();
			System.out.println(str);//debug
			if(str=="0"){
				return null;//cancel
			}
			tempCoord = str2coord(str);
			if(tempCoord==null){
				System.out.println("not valid input");
			} else if(board.getStdTile(tempCoord)==null) {
				System.out.println("not valid Coord");
			} else {
				flag=false;
			}
		} while (flag);
		return tempCoord;
	};
	static int[] str2coord(String str){
		int end=0;
		boolean flag=false;
		char tempChar;
		int tempSum=0;
		int[] result={-1, -1};//row, col
		for(int i=0; i<str.length(); i++){
			tempChar = str.charAt(i);
			if((48<=tempChar)&&(tempChar<=57)){
				flag = true;
				tempSum*=10;
				tempSum+=tempChar-48;
			} else if(flag){
				end = i;
				break;
			}
		}
		if(flag==false){
			return null;
		}
		result[0] = tempSum;
		tempSum = 0;
		flag=false;
		for(int i=++end; i<str.length(); i++){
			tempChar = str.charAt(i);
			if((48<=tempChar)&&(tempChar<=57)){
				flag = true;
				tempSum*=10;
				tempSum+=tempChar-48;
			} else if(flag){
				break;
			}
		}
		if(flag==false){
			return null;
		}
		result[1] = tempSum;
		return result;
	}
}