object publicVariables	{
	var play = true;
	var mDAM = 0;
	var linksTo = Array("").drop(1);	//Array for the 4 possible directions.
	var prevEnv = Array("","dark","","","","","","","","",""); //Array for previous lvlenv. //1st lvl always dark.
					//	^This element is used as a placeholder.
	var Class = "";
	var name = "";
	var ammo = 10;
	//For max dmg.
	var weapons = Map(
		"longsword" -> 13,
		"shortsword" -> 8,
		"bow" -> 6,
		"1" -> 13,
		"2" -> 8,
		"3" -> 6
	);
	//Min dmg.
	var minVal = Map(
	    "longsword" -> 7,
	    "shortsword" -> 5,
	    "bow" -> 4
	);
	//Possibility to miss. 45 = 45%.
	val missChances = Map(
		"longsword" -> 45,
		"shortsword" -> 25,
		"bow" -> 7
	);
	//Specific monster damages. (To be used in the fashion of (roll * <output of map>))
	val LSdmgs = Map[String,Double](
		"Slime" -> 0.80,
		"Lizard" -> 0.90,
		"Drake" -> 1,
		"Matt" -> 1.25,
		"King Kelman" -> 1.3
	);
	val SSdmgs = Map[String,Double](
		"Slime" -> 1.3,
		"Lizard" -> 1.15,
		"Drake" -> 0.9,
		"Matt" -> 0.8,
		"King Kelman" -> 0.75
	);
	val BOWdmgs = Map[String,Double](
		"Slime" -> 0.80,
		"Lizard" -> 1.5,
		"Drake" -> 1,
		"Matt" -> 0.45,
		"King Kelman" -> 1.25
	);
	//end Specific monster damages.
	var curLvl = 1;
	var queue = Array("").drop(1);
	var enemyHP = 0;
	var plyHP = 100;
	var wep = "";
	var mMiss = 0.0;
	var Explored = Array(0).drop(1);	//Will keep track of all the levels we have explored.
	var boss = false;

	def reset() =	{	//Resets variables and restarts from intro();
		println();
		println("Would you like to reset the game? Y/N.");
		var r = readLine();
		if (r.equalsIgnoreCase("Y") || r.equalsIgnoreCase("Yes"))	{
			play = true;
			enemyHP = 0;
			mDAM = 0;
			linksTo = Array("").drop(1);
			curLvl = 1;
			queue = Array("").drop(1);
			enemyHP = 0;
			plyHP = 100;
			wep = "";
			boss = false;
		}
		else	{
			play = false;
			println("Game terminated.");
		}
	}
}
