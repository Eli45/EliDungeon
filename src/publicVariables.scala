object publicVariables	{
	import publicClasses._
	var play = true;
	var linksTo = Array("").drop(1);	//Array for the 4 possible directions.
	var prevEnv = Array("","dark","","","","","","","","",""); //Array for previous lvlenv. //1st lvl always dark.
	//element 0 is used as a placeholder.
	var Class = "";
	var name = "";
	var ammo = 10;
	var mage = false;
	var mana = 100;
	//For max dmg.
	var weapons = Map(
		"longsword" -> 13,
		"shortsword" -> 8,
		"bow" -> 6,
		"1" -> 13,
		"2" -> 8,
		"3" -> 6,
		//Special mage powers. Requires mana.
		"fireball" -> 14,	//20 mana
		"lightning" -> 18, //30 mana
		"contortion" -> 23, //45 mana
		"4" -> 14,
		"5" -> 18,
		"6" -> 23
	);
	//Min dmg.
	var minVal = Map(
		"longsword" -> 7,
		"shortsword" -> 5,
		"bow" -> 4,
		"fireball" -> 7,	//Special mage powers. Requires mana.
		"lightning" -> 9,
		"contortion" -> 11
	);
	//Possibility to miss. 45 = 45%(not anymore).
	val missChances = Map(
		"longsword" -> 30,	//29%	This is because of our use of < instead of <=.
		"shortsword" -> 18,	//17%
		"bow" -> 7,			//6%
		"fireball" -> 0,
		"lightning" -> 0,
		"contortion" -> 0
	);
	//Specific monster damages. (To be used in the fashion of (roll * <output of map>))
	val LSdmgs = Map[String, Double](
		"Slime" -> 0.80,
		"Lizard" -> 0.90,
		"Lean Machine" -> 0.95,
		"Drake" -> 1,
		"Matt" -> 1.25,
		"King Kelman" -> 1.3
	);
	val SSdmgs = Map[String, Double](
		"Slime" -> 1.3,
		"Lizard" -> 1.15,
		"Lean Machine" -> 1.2,
		"Drake" -> 0.9,
		"Matt" -> 0.8,
		"King Kelman" -> 0.75
	);
	val BOWdmgs = Map[String, Double](
		"Slime" -> 0.80,
		"Lizard" -> 1.5,
		"Lean Machine" -> 1,
		"Drake" -> 1,
		"Matt" -> 0.45,
		"King Kelman" -> 1.25
	);
	val FBdmgs = Map[String, Double](
		"Slime" -> 0.60,
		"Lizard" -> 1,
		"Lean Machine" -> 1.75,
		"Drake" -> 0.75,
		"Matt" -> 2,
		"King Kelman" -> 1.5
	);
	val LTdmgs = Map[String, Double](
		"Slime" -> 1.80,
		"Lizard" -> 1.5,
		"Lean Machine" -> 0.5,
		"Drake" -> 1,
		"Matt" -> 0.60,
		"King Kelman" -> 1.30
	);
	val CTdmgs = Map[String, Double](
		"Slime" -> 0.50,
		"Lizard" -> 1,
		"Lean Machine" -> 1,
		"Drake" -> 1.5,
		"Matt" -> 2,
		"King Kelman" -> 1.7
	);
	//end Specific monster damages.
	var curLvl = 1;
	var placeholder = new character("placeholder",0,0,0,"");
	var queue:Array[character] = Array(placeholder);
	var player = new Player("",0,"");
	var wep = "";
	var Explored = Array(0).drop(1);	//Will keep track of all the levels we have explored.
	var boss = false;
	var wepDMG = 0;
	var missChance = 0;
	var minDMG = 0;
	
	
	def reset() =	{	//Resets variables and restarts from intro();
		println();
		println("Would you like to reset the game? Y/N.");
		var r = readLine();
		if (r.equalsIgnoreCase("Y") || r.equalsIgnoreCase("Yes"))	{
			play = true;
			linksTo = Array("").drop(1);	//Array for the 4 possible directions.
			prevEnv = Array("","dark","","","","","","","","",""); //Array for previous lvlenv. //1st lvl always dark.
			//element 0 is used as a placeholder.
			Class = "";
			name = "";
			ammo = 10;
			mage = false;
			mana = 100;
			weapons = Map(
				"longsword" -> 13,
				"shortsword" -> 8,
				"bow" -> 6,
				"1" -> 13,
				"2" -> 8,
				"3" -> 6,
				//Special mage powers. Requires mana.
				"fireball" -> 14,	//20 mana
				"lightning" -> 18, //30 mana
				"contortion" -> 23, //45 mana
				"4" -> 14,
				"5" -> 18,
				"6" -> 23
			);
			minVal = Map(
				"longsword" -> 7,
				"shortsword" -> 5,
				"bow" -> 4,
				"fireball" -> 7,	//Special mage powers. Requires mana.
				"lightning" -> 9,
				"contortion" -> 11
			);
			curLvl = 1;
			placeholder = new character("placeholder",0,0,0,"");
			queue = Array(placeholder);
			player = new Player("",0,"");
			wep = "";
			Explored = Array(0).drop(1);	//Will keep track of all the levels we have explored.
			boss = false;
			wepDMG = 0;
			missChance = 0;
			minDMG = 0;
		}
		else	{
			play = false;
			println("Game terminated.");
		}
	}
}
