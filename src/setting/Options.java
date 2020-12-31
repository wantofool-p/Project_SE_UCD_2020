package setting;

public final class Options {
	public static boolean ifDiverAllowedStopAtSunkTile=true;//default
	public static boolean ifNavigatorHasOwnAbility=false;//default
	public static boolean ifHelicopterLiftPlayerShouldAtTheSameTile=false;//default
	public static boolean ifCheat=false;//default
	public static boolean ifDebug=false;//default
	public Options(){
		;
	}
	public static void mod(){//default setting
		Options.ifDiverAllowedStopAtSunkTile=true;
		Options.ifNavigatorHasOwnAbility=false;
		Options.ifHelicopterLiftPlayerShouldAtTheSameTile=false;
	}
	public static void real(){//reality setting
		Options.ifDiverAllowedStopAtSunkTile=false;
		Options.ifNavigatorHasOwnAbility=true;
		Options.ifHelicopterLiftPlayerShouldAtTheSameTile=true;
	}
}
