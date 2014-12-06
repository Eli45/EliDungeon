object Functions	{
	import publicVariables._
	import Entities._
	/* ---- LEVELS ---- */
	def intro()	=	{
		var retry = true;
		println("What are you known as fair soldier?");
		name = readLine();
		while (retry)	{
			println("Were you known to be an archer, knight, or assassin before this unfortunate turn of events.");
			Class = readLine();
			if (Class.equalsIgnoreCase("archer"))	{ retry = false; ClassBonus("archer"); }
			else if (Class.equalsIgnoreCase("knight"))	{ retry = false; ClassBonus("knight"); }
			else if (Class.equalsIgnoreCase("assassin"))	{ retry = false; ClassBonus("assassin") }
			else	{ retry = true; println(Class+" is not a class."); }
			def ClassBonus(Class:String) =	{	//Sets damages for different classes.
				if (Class == "archer")	{
					weapons = Map(
						"longsword" -> 6,
						"shortsword" -> 8,
						"bow" -> 11,
						"1" -> 6,
						"2" -> 8,
						"3" -> 11
					);
					minVal = Map(
						"longsword" -> 1,
						"shortsword" -> 5,
						"bow" -> 6
					);
				}
				else if (Class == "knight")	{
					weapons = Map(
						"longsword" -> 17,
						"shortsword" -> 6,
						"bow" -> 4,
						"1" -> 17,
						"2" -> 6,
						"3" -> 4
					);
					minVal = Map(
						"longsword" -> 10,
						"shortsword" -> 3,
						"bow" -> 1
					);
				}
				else	{
					weapons = Map(
						"longsword" -> 10,
						"shortsword" -> 11,
						"bow" -> 6,
						"1" -> 10,
						"2" ->11,
						"3" -> 6
					);
					minVal = Map(
						"longsword" -> 5,
						"shortsword" -> 8,
						"bow" -> 4
					);
				}
			}
		}
		println("Then so it was my noble "+Class+". The evil Kan Krusher Kelman has taken power from the king of 4chan.\nYou must defeat him at all costs.");
		println("The only things you take with you into the dark are your longsword, shortsword, and bow.");
		println("Press enter to venture into the basement.");
		readLine();
		linksTo = Array("south","east","west");
	}
	def generateLevel()	=	{
		if (Explored.contains(curLvl))	{	}	//Does nothing if already explored.
		else	{	//Otherwise enters combat and generates Enemies.
			queueEnemies();
			def queueEnemies() =	{
				var maxEnemies = Math.round(Math.random()*5).toInt;
				var monsterToQueue = "";
				for (i <- 1 to maxEnemies)	{
					var roll = Math.round(Math.random()*100);
					if (roll <= 35)	{ monsterToQueue = "Slime"; }
					else if (roll <= 65 && roll > 35)	{ monsterToQueue = "Lizard"; }
					else if (roll <= 75 && roll > 65)	{ monsterToQueue = "Lean Machine"; }
					else if (roll <= 85 && roll > 75)	{ monsterToQueue = "Drake"; }
					else if (roll <= 100 && roll > 85)	{ monsterToQueue = "Matt"; }
					queue = queue :+ monsterToQueue;
				}
				setupEnemy();
			}
		}
	}
	def setupEnemy() =	{
	  	//Sets up our monster variables correctly based on the current opponent.
		if (queue.nonEmpty)	{
			if (queue(0) == "Slime")	{ Slime; }
			else if (queue(0) == "Lizard")	{ Lizard; }
			else if (queue(0) == "Drake")	{ Drake; }
			else if (queue(0) == "Lean Machine") { LeanMachine; }
			else	{ Matt; }
		}
	}
	def printLevelInfo() =	{
		if (queue.nonEmpty)	{
			println("Monsters in this room.\n------------------");
			for (i <- 0 to queue.length-1)	{
				if (i != queue.length-1)	{
					print((i+1)+". "+queue(i)+", ");
				}
				else	{
					print((i+1)+". "+queue(i)+ ".");
				}
				println();
			}
			println("You will be facing "+queue(0)+" first.");
		}
		else	{
			println("There are no monsters in the room.");
		}
	}
	/* ---- /LEVELS ---- */

	/* ---- COMBAT ---- */
	def combat() =	{
		while (queue.nonEmpty && plyHP > 0)	{
			if (plyHP < 50 && boss == true)	{
				var roll = Math.round(Math.random()*100)
				if (roll <= 20)	{
					println("In the midst of the combat you find a half drunken keg dropped by Kelman.");
					plyHP += 15;
					println("HP restored by 15 to "+plyHP);
				}
			}
			chooseWeapon();
			def chooseWeapon() =	{
				var wepDMG = 0;
				var missChance = 0;
				var minDMG = 0;
				var retry = true;
				if (ammo < 0)	{ ammo = 0; }
				while (retry)	{
					println();
					println("What weapon would you like to use to attack?\nWeapons include: 'longsword'(1), 'shortsword'(2), 'bow'(3)["+ammo+"].");
					wep = readLine().toLowerCase;
					if (weapons.contains(wep))	{
						wepDMG = weapons.apply(wep);
						if (wep == "1")	{ wep = "longsword"; }
						else if (wep == "2")	{ wep = "shortsword"; }
						else if (wep == "3")	{ wep = "bow"; }
						if (wep == "bow" && ammo <= 0)	{
							println(wep.capitalize+" has no ammo!");
							retry = true;
						}
						else	{
							missChance = missChances.apply(wep);
							minDMG = minVal.apply(wep);
							retry = false;
						}
					}
					else { retry = true; println(wep.capitalize+" is not a weapon."); }
				}
				dicerolls();
				def dicerolls() =	{
					var roll = Math.round(Math.random()*(wepDMG+6));
					if (roll < minDMG)	{ roll = minDMG; }
					else if (roll > wepDMG)	{ roll = wepDMG; }
					var missedOrNo = Math.round(Math.random()*100);
					if (missedOrNo <= missChance)	{ println("Your attack missed!"); }
					else	{
						var newRoll = roll.toDouble;
						def typeDamage() =	{
							//Specific type damages against certain monsters.
							var dmgs = 0.0;
							if (wep == "longsword")	{ dmgs = LSdmgs.apply(queue(0)) * newRoll; }
							else if (wep == "shortsword")	{ dmgs = SSdmgs.apply(queue(0)) * newRoll; }
							else	{ BOWdmgs.apply(queue(0)) * newRoll; }
						}
						var finalDAM = newRoll.toInt;
						println("You attacked "+queue(0)+ s" with $wep for $finalDAM.");
						if (wep == "bow")	{ ammo -= 1; }
						enemyHP -= finalDAM;
						if (enemyHP <= 0) { enemyHP = 0; }
						println(queue(0)+s"'s HP is now $enemyHP.");
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
					var mDMG = Math.round(Math.random()*mDAM);
					var roll = Math.round(Math.random()*100);
					if (roll <= mMiss)	{
						println("Enemy missed!");
					}
					else {
						println(queue(0)+s" attacked you for $mDMG");
						plyHP -= mDMG.toInt;
						if (plyHP < 0)	{ plyHP = 0; }
						println("Your HP is now "+plyHP);
						println();
					}
				}
			}
		}
	}
	def treasure() =	{
		if (Explored.contains(curLvl))	{
			println("You have already looted this room!")
		}
		else	{
			var x = Math.round(Math.random()*25);
			var potion = 0;
			if (x <= 9)	{potion = 0;}
			else if (x > 9 && x <= 19)	{potion = 15;}
			else if (x > 19 && x <= 23)	{potion = 25;}
			else	{potion = 35;}
			plyHP += potion;
			if (plyHP > 100)	{ plyHP = 100; }
			if (potion != 0)	{
				println(s"You find a $potion HP keg that restores your hp to $plyHP.");
			}
			else	{
				println("You found no HP kegs in the room.");
			}
			if (ammo < 5)	{
				var arrows = Math.round(Math.random()*4).toInt;
				if (arrows != 0)	{
					println("You found "+arrows+" arrows in the room.");
					ammo += arrows
				}
				else	{
					println("You found no bows in the room.")
				}
			}
		}
	}
	def move() =	{
		Explored = Explored :+ curLvl;
		var retry = true;
		while (retry)	{
			println("What direction would you like to travel in?");
			print("Your options include ");
			for (i <- 0 to linksTo.length-1)	{
				if (i != linksTo.length-1)	{
					print(linksTo(i) +", ");
				}
				else	{
					print(linksTo(i)+".");
				}
			}
			println();
			println("Or alternatively type 'map' to see a map of the currently explored areas or 'legend' to see map legend.");
			var direction = readLine().toLowerCase;
			levelDirection();
			def levelDirection() =	{
				if (direction.equalsIgnoreCase("map"))	{
					makeMap();
					retry = true;
				}
				else if (direction.equalsIgnoreCase("legend"))	{
					println("'X' = Explored. 'P' = Player");
				}
				else if (linksTo.contains(direction))	{
					if (direction == "north")	{ curLvl -= 3; }
					else if (direction == "south")	{ curLvl += 3; }
					else if (direction == "east")	{
						if (curLvl == 1 || curLvl == 4 || curLvl == 7)	{ curLvl += 1; }
						else	{ curLvl -= 2; }
					}
					else if (direction == "west")	{
						if (curLvl == 2 || curLvl == 5 || curLvl == 8)	{ curLvl -= 1; }
						else	{ curLvl += 2; }
					}
					retry = false;
					if (Explored.contains(curLvl))	{
						println("You enter the "+prevEnv(curLvl)+" "+direction+"ern room.");
					}
					else	{
						var LevelEnv = "";
						var roll = Math.round(Math.random()*6);
						if (roll == 0)	{ LevelEnv = "dark"; }
						else if (roll == 1)	{ LevelEnv = "dank"; }
						else if (roll == 2)	{ LevelEnv = "murky"; }
						else if (roll == 3)	{ LevelEnv = "damp"; }
						else if (roll == 4)	{ LevelEnv = "smokey"; }
						else if (roll == 5)	{ LevelEnv = "ruined"; }
						else	{ LevelEnv = "stench-ridden"; }
						prevEnv(curLvl) = LevelEnv;
						println("You enter the "+LevelEnv+" "+direction+"ern room.");
					}

				}
				else	{ retry = true; println(direction+" is not an available option."); }
			}
			updateLink();
			def updateLink() =	{	//Checks what rooms are connected to our current room.
				linksTo = Array("").drop(1)
				if (curLvl != 9 && curLvl != 8)	{ linksTo = linksTo :+ "south"; }
				if (curLvl != 2 && curLvl != 5 && curLvl != 8)	{ linksTo = linksTo :+ "east"; }
				if (curLvl != 3 && curLvl != 6 && curLvl != 9)	{ linksTo = linksTo :+ "west"; }
				if (curLvl != 3 && curLvl != 1 && curLvl != 2)	{ linksTo = linksTo :+ "north"; }
			}
			println();
		}
	}
	def makeMap() =	{
		def explore(x:Int) =	{
			if (x == curLvl)	{
				if (x == 3 || x  == 6  || x == 9)	{ print("|"); }
				print ("P");
				if (x == 2 || x == 5 || x == 8)	{ print("|"); }
			}
			else if (x != 10)	{
				if (Explored.contains(x))	{
				  if (x == 3 || x  == 6  || x == 9)	{ print("|"); }
				  print("X");
				  if (x == 2 || x == 5 || x == 8)	{ print("|"); }
				}
				else	{
					if (x == 3 || x  == 6  || x == 9)	{ print(" "); }
					if (x == 2 || x == 5 || x == 8)	{ print(" "); }
					print(" ");
				}
			}
			else	{
				if(Explored.contains(x))	{ print("  ");print("|X|");}
			}
			print(" ");
		}
		//Loops would be difficult to implement here due to patterns so I won't.

		explore(3); explore(1); explore(2);
		println();
		explore(6); explore(4); explore(5);
		println();
		explore(9); explore(7); explore(8);
		println();
		explore(10)
		/* if all levels explored and player is located at 4 then should result in an output of:
		 *  _____
		 * |X X X|
		 * |X P X|
		 * |X X X|
		 *   |X|
		 * otherwise, unexplored levels are simply displayed as blank spaces.
		 */
	}
	def bossEntry() =	{
		queue = Array("").drop(1); queue = queue :+ "King Kelman";
		KingKelman;
		println("You have entered the final room of Kelman's basement.");
		println("You can smell the gut-wrenching reek of doritos and mountain dew in the air.");
		if (plyHP > 90)	{
			plyHP -= 15;
			println("Unfortunately, you drink some mountain dew by accident. It reduces your hp by 15 to "+plyHP);
		}
		else	{
			plyHP += 20;
			println("You drink a keg from off the ground. It restores your hp by 20 to "+plyHP);
		}
		if (plyHP > 0)	{
			println("You are now facing the evil King Kelman. Defeat him at all costs.");
		}
	}
	def failureMessage() =	{
		println();
		println("You have failed to defeat the evil Kan Krusher Kelman and save the kingdom of 4chan from his evil.");
		println("Inevitably, without your assistance, the kingdom totally fell to his influence and all was lost.");
		println("All accounts of your daring adventure fade with the passage of time.\nSoon, even Kelman does not remember the battle against the "+Class+", "+name+".")
		println("----  DEFEAT  ----");
		println("---- GAME OVER ----");
	}
	def gameSuccess() =	{
		println();
		println("You have defeated the evil Kan Krusher Kelman and saved the kingdom from his evil.");
		println("Upon your return you are granted command of 4chan.\nNow it is your turn to lead the kingdom to glory.");
		println("The tales of "+name+", a noble "+Class+", will be sung throughout the lands for centuries to come.")
		println("----  DEFEAT  ----");
		println("---- GAME OVER ----");
	}
}
