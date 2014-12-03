object Functions	{
	import publicVariables._
	import Entities._
	/* ---- LEVELS ---- */
	def intro()	=	{
		println("You are a knight. Go kill stuff.")
		println("Press enter to continue.")
		readLine();
		linksTo = Array("south","east","west");
	}
	def generateLevel(Level:Int)	=	{
		if (Explored.contains(curLvl))	{	}	//Does nothing if already explored.
		else	{	//Otherwise enters combat and generates Enemies.
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
				setupEnemy();
			}
		}
	}
	def setupEnemy() =	{
	  	//Sets up our monster variables correctly based on the current opponent.
		if (queue.nonEmpty)	{
			if (queue(0) == "Slime")	{Slime;}
			else if (queue(0) == "Lizard")	{Lizard;}
			else if (queue(0) == "Drake")	{Drake;}
			else	{Matt;}
		}
	}
	def printLevelInfo() =	{
		if (queue.nonEmpty)	{
			println("Monsters in this room.\n------------------")
			for (i <- 0 to queue.length-1)	{
				if (i != queue.length-1)	{
					print(queue(i)+", ");
				}
				else	{
					print(queue(i)+ ".")
				}
				println()
			}
			println("You will be facing "+queue(0)+" first.");
		}
		else	{
			println("There is nothing in the room.");
		}
	}
	/* ---- /LEVELS ---- */
	
	/* ---- COMBAT ---- */
	def combat() =	{
		while (queue.nonEmpty && plyHP > 0)	{
			chooseWeapon();
			def chooseWeapon() =	{
				var wepDMG = 0;
				var retry = true;
				while (retry)	{
					println();
					println("What weapon would you like to use to attack?\nWeapons include: 'bow', 'longsword', 'shortsword'.");
					wep = readLine().toLowerCase;
					if (weapons.contains(wep))	{
						wepDMG = weapons.apply(wep)
						retry = false;
					}
					else { retry = true; println(wep+" is not a weapon.")}
				}
				dicerolls();
				def dicerolls() =	{
					var roll = Math.round(Math.random()*wepDMG)
					if (wep != "bow" && roll < 4)	{ println("Your attack missed!") }
					else if (wep == "bow" && roll < 2)	{ println("Your attack missed!") }
					else	{
						var newRoll = roll.toDouble
						def typeDamage() =	{
							//This is really inefficient, possibly look into a better way.
							if (queue(0) == "Slime")	{
								if (wep == "bow")	{ newRoll = newRoll * .85 }
								else if (wep == "longsword")	{ newRoll = newRoll * 1.25 }
								else	{ newRoll = newRoll * 1 }
							}
							else if (queue(0) == "Lizard")	{
								if (wep == "bow")	{  }
								else if (wep == "longsword")	{  }
								else	{  }
							}
							else if (queue(0) == "Drake")	{}
							else if (queue(0) == "Matt")	{}
						}
						var finalDAM = newRoll.toInt
						println("You attacked "+queue(0)+ s" with $wep for $finalDAM.")
						enemyHP -= finalDAM;
						if (enemyHP <= 0) { enemyHP = 0; }
						println(queue(0)+s"'s HP is now $enemyHP.")
					}
				}
				if (enemyHP <= 0) { 
					queue = queue.drop(1);
					if (queue.nonEmpty)	{
						setupEnemy();
						println("You will now be facing " +queue(0));
						println();
					}
				}
				else { enemyAttack(); }
				def enemyAttack() =	{
					var roll = Math.round(Math.random()*mDAM)
					var Missed = roll * mMiss	//Look at these values. They feel wrong.
					if (Missed <= roll*.25)	{
						println("Enemy missed!")
					}
					else {
						println(queue(0)+s" attacked you for $roll")
						plyHP -= roll.toInt;
						if (plyHP < 0)	{ plyHP = 0; }
						println("Your HP is now "+plyHP)
						println();
					}
				}
			}
		}
	}
	def treasure() =	{
		var x = Math.round(Math.random()*25);
		var potion = 0;
		if (x <= 7)	{potion = 15;}	//Add chance to get nothing.
		else if (x > 7 && x <= 15)	{potion = 25;}
		else if (x > 15 && x <= 23)	{potion = 35;}
		else	{potion = 50;}
		plyHP += potion;
		if (plyHP > 100)	{ plyHP = 100; }
		println(s"You find a $potion HP potion that restores your hp to $plyHP.");
	}
	def move() =	{
		Explored = Explored :+ curLvl
		var retry = true;
		while (retry)	{
			println("What direction would you like to travel in?")
			print("Your options include ")
			for (i <- 0 to linksTo.length-1)	{
				if (i != linksTo.length-1)	{
					print(linksTo(i) +", ");
				}
				else	{
					print(linksTo(i)+".")
				}
			}
			var direction = readLine().toLowerCase;
			println(direction)
			levelDirection();
			def levelDirection() =	{
				if (linksTo.contains(direction))	{
					if (direction == "north")	{ curLvl -= 3 }
					else if (direction == "south")	{ curLvl += 3 }
					else if (direction == "east")	{
						if (curLvl == 1 || curLvl == 4 || curLvl == 7)	{ curLvl += 1 }
						else	{ curLvl -= 2 }
					}
					else if (direction == "west")	{
						if (curLvl == 2 || curLvl == 5 || curLvl == 8)	{ curLvl -= 1 }
						else	{ curLvl += 2 }
					}
					retry = false;
					println("You enter the "+direction+"ern room.")
				}
				else	{ retry = true; println(direction+" is not an available option.")}
			}
			updateLink();
			def updateLink() =	{	//Checks what rooms are connected to our current room.
				linksTo = Array("").drop(1)
				if (curLvl != 9 && curLvl != 8)	{ linksTo = linksTo :+ "south" }
				if (curLvl != 2 && curLvl != 5 && curLvl != 8)	{ linksTo = linksTo :+ "east" }
				if (curLvl != 3 && curLvl != 6 && curLvl != 9)	{ linksTo = linksTo :+ "west" }
				if (curLvl != 3 && curLvl != 1 && curLvl != 2)	{ linksTo = linksTo :+ "north" }
			}
			println()
		}
	}
	
	
	def bossEntry() =	{
		
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
