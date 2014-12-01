object Functions extends App {
	import publicVariables._
	import Entities._
	/* ---- LEVELS ---- */
	def generateLevel(Level:Unit)	=	{
		//Checks what rooms are connected to our current room.
		if (curLvl == 1)	{linksTo = Array("east","west","south");}//add more
		generateEnemies();
		
		def generateEnemies() =	{
			var roomDifficulty = Math.round(Math.random()*3).toInt
			queueEnemies(roomDifficulty)
		}
		
		def queueEnemies(Difficulty:Int) =	{
			var maxEnemies = Math.round(Math.random()*Difficulty).toInt
			var monsterToQueue = "";
			for (i <- 1 to maxEnemies)	{
				var roll = Math.round(Math.random()*4);
				if (roll == 0)	{roll = 1}
				if (roll == 1)	{monsterToQueue = "Slime"}
				else if (roll == 2)	{monsterToQueue = "Lizard"}
				else if (roll == 3)	{monsterToQueue = "Drake"}
				else if (roll == 4)	{monsterToQueue = "Matt"}
				queue = queue :+ monsterToQueue
			}
			if (queue.isEmpty)	{ enemyHP = 0; }
		}
	}
	
	def printLevelInfo() =	{
		if (enemyHP != 0)	{
			for (i <- 0 to queue.length-1)	{
				println(s"There is a $queue(i) here.");
			}
			println(s"You will be facing $queue(0) first.");
		}
		else	{
			println("There is nothing in the room.");
		}
	}
	/* ---- /LEVELS ---- */
	
	/* ---- COMBAT ---- */
	def setupEnemy() =	{
	  	//Sets up our monster variables correctly based on the current opponent.
		if (queue(0) == "Slime")	{Slime;}
		else if (queue(0) == "Lizard")	{Lizard;}
		else if (queue(0) == "Drake")	{Drake;}
		else	{Matt;}
	}
	def combat() =	{
		chooseWeapon();
		def chooseWeapon() =	{
			var wepDMG = 0;
			var retry = true;
			while (retry)	{
				println("What weapon would you like to use to attack?");
				wep = readLine().toLowerCase;
				if (weapons.contains(wep))	{
					wepDMG = weapons.apply(wep)
					retry = false;
				}
				else { retry = true; }
			}
			dicerolls();
			def dicerolls() =	{
				var roll = Math.round(Math.random()*wepDMG)
				if (roll < 4)	{ println("Your attack missed!") }
				else	{
					//TO BE ADDED.
				}
				println(s"$queue(0) attacked with $wep for $wepDMG.")
				enemyHP -= wepDMG;
				println(s"$queue(0)'s HP is now $enemyHP.")
			}
			enemyAttack();
			def enemyAttack() =	{
				
			}
		}
	}
	def treasure() =	{
			
	}
	def move() =	{
			
	}
	
	
	
	def failureMessage() =	{
		println();
		println("You have failed to defeat the evil Kan Krusher Kelman and save the kingdom from his evil.");
		println("Inevitably, without your assistance, the kingdom fell to his influence and all was lost.");
		println("---- GAME OVER ----");
	}
	def gameSuccess() =	{
		println();
		println("You have defeated the evil Kan Krusher Kelman and saved the kingdom from his evil.");
		println("Upon your return you are granted command of the throne.\nNow it is your turn to lead the kingdom to glory.");
		println("---- GAME OVER ----");
	}
}
