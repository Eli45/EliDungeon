object publicVariables	{
	var mDAM = 0;
	var linksTo = Array("").drop(1)	//Array for the 4 possible directions.
	val weapons = Map(
		"shortsword" -> 8,
		"longsword" -> 13,
		"bow" -> 6
	)
	var curLvl = 1;
	var queue = Array("").drop(1)
	var enemyHP = 0;
	var plyHP = 100;
	var wep = "";
	var mMiss = 0.0;
	
	def reset() =	{
		println();
		println("Would you like to reset the game? Y/N.");
		var r = readLine();
		if (r.equalsIgnoreCase("Y") || r.equalsIgnoreCase("Yes"))	{
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
			println("Game terminated.");
		}
	}
}