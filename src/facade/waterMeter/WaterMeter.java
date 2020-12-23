package facade.waterMeter;

public class WaterMeter{
	protected int level=-1;
	public WaterMeter(){
		;
	}
	public void setWaterLevel(int level){
		this.level = level;
		//waterMeter(1~10)
		//waterLevel(2~5)
		//waterMeter  waterLevel  difficulty
		//     1           2         Novice
		//     2           2         Normal
		//     3           3         Elite
		//     4           3         Legendary
		//     5           3
		//     6           4
		//     7           4
		//     8           5
		//     9           5
		//     10          Game Over :X
	}
	public int getLevel(){
		return level;
	}
	public int getMeter(){
		switch (this.level) {
			case 1:
			case 2:
				return 2;
			case 3:
			case 4:
			case 5:
				return 3;
			case 6:
			case 7:
				return 4;
			case 8:
			case 9:
				return 5;
			case 10:
			case 11:
				return 6;//Game Over
			default:
				return -1;//ERROR
		}
	}
	public void printCLI(){
		System.out.println("Current Water Level = " + this.level);
		System.out.println("Current Water Meter = " + this.getMeter());
	}
	public void addLevel(){
		this.level++;
	}
}