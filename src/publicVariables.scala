object publicVariables	{
	var play = true;
	var mDAM = 0;
	var linksTo = Array("").drop(1)	//Array for the 4 possible directions.
	val weapons = Map(
		"shortsword" -> 8,
		"longsword" -> 13,
		"bow" -> 6,
		"1" -> 6,
		"2" -> 13,
		"3" -> 8
	)
	var curLvl = 1;
	var queue = Array("").drop(1)
	var enemyHP = 0;
	var plyHP = 100;
	var wep = "";
	var mMiss = 0.0;
	var Explored = Array(0).drop(1)	//Will keep track of all the levels we have explored.

	def reset() =	{	//Needs to actually restart the game. As of current it only resets variables. Does not restart.
		println();
		println("Would you like to reset the game? Y/N.");
		var r = readLine();
		if (r.equalsIgnoreCase("Y") || r.equalsIgnoreCase("Yes"))	{
			play = true;
			enemyHP = 0;
			mDAM = 0;
			linksTo = Array("").drop(1)	//Array for the 4 possible directions.
			curLvl = 1;
			queue = Array("").drop(1)
			enemyHP = 0;
			plyHP = 100;
			wep = "";
		}
		else	{
			play = false;
			println("Game terminated.");
		}
	}
}
