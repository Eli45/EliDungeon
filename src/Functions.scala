object Functions	{
	import publicVariables._, publicClasses._
	/* ---- LEVELS ---- */
	def intro()	=	{
		var retry = true;
		println("What are you known as fair soldier?");
		name = readLine();
		while (retry)	{
			println("Were you known to be an archer, knight, mage, or assassin before this unfortunate series of events?");
			Class = readLine();
			var HP = 0;
			if (Class.equalsIgnoreCase("archer"))	{ retry = false; ClassBonus("archer"); }
			else if (Class.equalsIgnoreCase("knight"))	{ retry = false; ClassBonus("knight"); }
			else if (Class.equalsIgnoreCase("assassin"))	{ retry = false; ClassBonus("assassin"); }
			else if (Class.equalsIgnoreCase("mage"))	{ retry = false; ClassBonus("mage"); }
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
					HP = 90;
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
					HP = 115;
				}
				else if (Class == "assassin")	{
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
					HP = 95;
				}
				else	{ //Mage
					weapons = Map(
						"longsword" -> 5,
						"shortsword" -> 7,
						"bow" -> 5,
						"1" -> 5,
						"2" -> 7,
						"3" -> 5,
						"fireball" -> 14,	//Special mage powers. Requires mana.
						"lightning" -> 18,
						"contortion" -> 23,
						"4" -> 14,
						"5" -> 18,
						"6" -> 23
					);
					minVal = Map(
						"longsword" -> 2,
						"shortsword" -> 4,
						"bow" -> 4,
						"fireball" -> 10,
						"lightning" -> 12,
						"contortion" -> 14
					);
					mage = true;
					HP = 80;
				}
			}
			player = new Player( name, HP, Class );
		}
		println("Then so it was my noble "+Class.toLowerCase+".\nThe evil Kan Krusher Kelman has taken power from the king of 4chan.\nYou must defeat him at all costs.");
		if (mage)	{
			println("The only things you take with you into the dark are your longsword, shortsword, bow, and knowledge of spells.");
		}
		else	{
			println("The only things you take with you into the dark are your longsword, shortsword, and bow.");
		}
		println("Press enter to venture into the basement.");
		readLine();
		println("You enter the first room.");
		linksTo = Array("south","east","west");
	}
	def generateLevel()	=	{
		if (Explored.contains(curLvl))	{	}	//Does nothing if already explored.
		else	{	//Otherwise enters combat and generates Enemies.
			queueEnemies();
			def queueEnemies() =	{
				var maxEnemies = Math.round(Math.random()*5).toInt;
				var monsterToQueue = new character("",0,0,0,"");
				if (maxEnemies != 0)	{
					monsterToQueue = new character("",0,0,0,"");
					for (i <- 1 to maxEnemies)	{
						var roll = Math.round(Math.random()*100);
						var char = "";
						var mhp = 0;
						var md = 0;
						var mc = 0;
						var ab = "";
						if (roll <= 35)	{ char = "Slime";mhp = 13;md = 6;mc = 20;ab = "absorb"; }
						else if (roll <= 65 && roll > 35)	{ char = "Lizard";mhp = 6;md = 7;mc = 10;ab = "dodge"; }
						else if (roll <= 75 && roll > 65)	{ char = "Lean Machine";md = 23;mhp = 15;mc = 20;ab = "clutch"; }
						else if (roll <= 85 && roll > 75)	{ char = "Drake";mhp = 35;md = 17;mc = 35;ab = "fly"; }
						else if (roll <= 100 && roll > 85)	{ char = "Matt";mhp = 50;md = 13;mc = 40;ab = "turtle"; }
						monsterToQueue = new character( char, mhp, md, mc, ab );
						if (queue.nonEmpty)	{	//Checks if placeholder is present.
							if (queue(0).name == "placeholder")	{	//Fixes it if it is.
								queue(0) = monsterToQueue;
							}
							else	{
								queue = queue :+ monsterToQueue;	//If it isn't operations continue as normal.
							}
						}
						else	{
							queue = queue :+ monsterToQueue;	//Operations continue as normal if it isn't.
						}
					}
				}
				if (queue.nonEmpty)	{			//I lied.
					queue.drop(queue.length);	//Theoretically fixed the placeholder bug.
				}
			}
		}
	}
	def printLevelInfo() =	{
		//Prints the monsters in the room to the user.
		if (queue.nonEmpty && queue(0).name != "placeholder")	{	//placeholder bug fixed with ducttape and bubblegum.
			println("Monsters in this room.\n------------------");
			for (i <- 0 to queue.length-1)	{
				if (i != queue.length-1)	{
					print((i+1)+". "+queue(i).name+", ");
				}
				else	{
					print((i+1)+". "+queue(i).name+ ".");
				}
				println();
			}
			println("You will be facing "+queue(0).name+" first.");
		}
		else	{
			println("There are no monsters in the room.");
		}
	}
	/* ---- /LEVELS ---- */

	/* ---- COMBAT ---- */
	def combat() =	{
		while (queue.nonEmpty && player.hp > 0 && queue(0).name != "placeholder")	{
			//Only used in the boss battle. (Used for supply drops.)
			if (player.hp < 50 && boss == true)	{
				var roll = Math.round(Math.random()*100)
				if (roll <= 20)	{
					println("In the midst of the combat you find a half drunken keg dropped by Kelman.");
					player.hp += 15;
					println("HP restored by 15 to "+player.hp);
				}
			}
			//Prompts player to choose one of the three weapons.
			chooseWeapon();
			def chooseWeapon() =	{
				wepDMG = 0;
				missChance = 0;
				minDMG = 0;
				var retry = true;
				if (ammo < 0)	{ ammo = 0; }
				while (retry)	{
					println();
					println("What weapon would you like to use to attack?\nWeapons include: 'longsword'(1), 'shortsword'(2), 'bow'(3)["+ammo+"].");
					if (mage)	{
						println("You can also use the spells: 'fireball'(4){15}, 'lightning'(5){20}, 'contortion'(6){25}. Mana["+mana+"]");
					}
					wep = readLine().toLowerCase;
					if (weapons.contains(wep))	{
						wepDMG = weapons.apply(wep);
						if (wep == "1")	{ wep = "longsword"; }
						else if (wep == "2")	{ wep = "shortsword"; }
						else if (wep == "3")	{ wep = "bow"; }
						else if (wep == "4")	{ wep = "fireball"; }
						else if (wep == "5")	{ wep = "lightning"; }
						else if (wep == "6")	{ wep = "contortion"; }
						if (wep == "bow" && ammo <= 0)	{
							println(wep.capitalize+" has no ammo!");
							retry = true;
						}
						else if (wep == "fireball" && mana < 15)	{
							println("You have no mana to use "+wep.capitalize);
						}
						else if (wep == "lightning" && mana < 20)	{
							println("You have no mana to use "+wep.capitalize);
						}
						else if (wep == "contortion" && mana < 25)	{
							println("You have no mana to use "+wep.capitalize);
						}
						else	{
							missChance = missChances.apply(wep);
							minDMG = minVal.apply(wep);
							retry = false;
						}
					}
					else { retry = true; println(wep.capitalize+" is not a weapon."); }
				}
				//Rolls damages dealt.
				var usedMana = false;
				var expMana = 0;
				var usedAbility = false;
				var turtling = false;
				var clutched = false;
				var absorbedOrDodged = false;
				var flew = false;
				var chugged = false;
				var dead = false;
				dicerolls();
				def dicerolls() =	{
					if (!turtling)	{
						player.attack(queue(0), 1);
					}
					else	{
						player.attack(queue(0), 0.1);
					}
					
					if (wep == "bow")	{ ammo -= 1; }
					if (mage)	{
						if (wep == "fireball")	{ mana -= 15; expMana = 15; usedMana = true; }
						else if (wep == "lightning")	{ mana -= 20; expMana = 20; usedMana = true; }
						else if (wep == "contortion")	{mana -= 25; expMana = 25; usedMana = true; }	
					}
					if (queue(0).hp <= 0) { queue(0).hp = 0; dead = true; }
				}
				//If monster is dead then we drop the current one and face the next.
				if (queue(0).hp <= 0) {
					dead = true;
					if (queue(0).name == "Lean Machine")	{
						queue(0).useAbility();
						if (queue(0).hp > 0)	{
							dead = false;
							clutched = true;
							usedAbility = true;
							enemyAttack();
						}
						else	{
							dead = true;
						}
					}
					if (dead)	{	
						printDamage();
						queue = queue.drop(1);
						if (queue.nonEmpty)	{
							println("You will now be facing " +queue(0).name);
							println();
						}
						else	{	//Checks if queue is empty, if it is we assign a placeholder so we don't get an array error.
							queue = queue :+ new character("placeholder",-1,-1,-1,"placeholder");
						}
					}
				}
				else { enemyAttack(); }
				//Serves the same purpose as dicerolls(); Rolls monster damage.
				def enemyAttack() =	{
					if (!dead)	{
						var abOrNo = Math.round(Math.random()*100);
						if (abOrNo <= queue(0).abilityChance && queue(0).name != "Lean Machine")	{
							queue(0).useAbility();
							if (queue(0).ability == "turtle")	{
								turtling = true;
							}
							else if (queue(0).ability == "dodge" || queue(0).ability == "absorb")	{
								absorbedOrDodged = true;
							}
							else if (queue(0).ability == "fly")	{
								flew = true;
							}
							else if (queue(0).ability == "chug")	{
								chugged = true;
							}
							usedAbility = true;
						}
						else	{
							queue(0).attack(player);
						}
						
						if (queue(0).lastdmg != 0)	{
							if (player.hp < 0)	{ player.hp = 0; }
						}
					}
					printDamage();
				}
				def printDamage()	{
					var missed = false;
					if (usedMana)	{
						println("By using "+wep+" you expended "+expMana+" mana. This leaves you at "+mana+" mana.");
					}
					if (flew)	{
						if (player.lastdmg != 0)	{	//SHOULD FIX DRAKE PRINTINFO THEORETICALLY
							println("You tried attacking "+queue(0).name+ s" with $wep but it flew away!");	
							println(queue(0).name+" flew into the sky and smashed into your for 10 damage!");
							println(queue(0).name+s"'s HP is still" + queue(0).hp + "/"+queue(0).maxhp);
						}
						else	{
							println("You missed! "+queue(0).name+s"'s HP is still" + queue(0).hp + "/"+queue(0).maxhp);
							println(queue(0).name+" flew into the sky and smashed into your for 10 damage!");
						}
					}
					else if (absorbedOrDodged)	{  }
					else if (player.lastdmg > 0 && !chugged)	{
						println("You attacked "+queue(0).name+ s" with $wep for "+player.lastdmg+".");
						if (!clutched)	{
							println(queue(0).name+s"'s HP is now " + queue(0).hp + "/"+queue(0).maxhp);
						}
						else	{
							println(queue(0).name+s"'s HP is now 0" + "/"+queue(0).maxhp);
						}
					}
					else if (!absorbedOrDodged && !chugged)	{
						println("You missed! " + queue(0).name+"'s HP is still " + queue(0).hp + "/"+queue(0).maxhp+"!");
						missed = true;
					}	
					else if (chugged)	{
						if (!missed)	{
							println("You attacked "+queue(0).name+ s" with $wep for "+player.lastdmg+".");
							println("However "+queue(0).name+" used "+queue(0).ability+" and "+queue(0).abilityDesc);
						}
						else	{
							println(s"Your attack with $wep missed!");
							println(queue(0).name+" used "+queue(0).ability+" and "+queue(0).abilityDesc);
						}
						println(queue(0).name+s"'s HP is now " + queue(0).hp + "/"+queue(0).maxhp);
					}
					else	{ }
					
					if (!usedAbility && !dead && queue(0).lastdmg != 0)	{
						println(queue(0).name+s" attacked you for " + queue(0).lastdmg);
					}
					else if (queue(0).lastdmg == 0 && !dead && !usedAbility)	{
						println(queue(0).name+" missed!");
					}
					else if (!dead && !chugged)	{
						println(queue(0).name+" used "+queue(0).ability+"!");
						if (!missed)	{
							println(queue(0).name +" "+ queue(0).abilityDesc);
							println(queue(0).name+"'s HP is still "+queue(0).hp+"/"+queue(0).maxhp);
						}
						else	{
							println("However it could not "+queue(0).ability+" your attack because you missed!");
						}
					}
					println("Your HP is "+player.hp+"/"+player.maxhp);
				}
			}
		}
	}
	def treasure() =	{
		//Decides whether player can obtain potions from this room or not.
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
			player.hp += potion;
			if (player.hp > player.maxhp)	{ player.hp = player.maxhp; }
			println();
			if (potion != 0)	{
				println(s"You find a $potion HP keg that restores your hp to "+player.hp+".");
			}
			else	{
				println("You found no HP kegs in the room.");
			}
			//Gives bows to player if their bow count is less than 5 and they get a little lucky.
			if (ammo < 5)	{
				var arrows = Math.round(Math.random()*4).toInt;
				if (arrows != 0)	{
					println("You found "+arrows+" arrows in the room.");
					ammo += arrows
				}
				else	{
					println("You found no arrows in the room.")
				}
			}
			if (mana != 100)	{
				var manaToGain = 25;
				if (mana > 75)	{ manaToGain = 100 - mana; }
				mana += manaToGain;
				if (mana > 100)	{ mana = 100; }
				println("You regenerated "+manaToGain+" mana. Your total mana is now "+mana);
			}
		}
	}
	def move() =	{
		//Adds cur level to explored array.
		Explored = Explored :+ curLvl;
		var retry = true;
		while (retry)	{
			//Prints options to move.
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
						//Rolls dice for random level environment.
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
		//Map making function.
		def explore(x:Int) =	{
			if (x == curLvl)	{
				if (x == 3 || x  == 6 || x == 9)	{ print("|"); }
				print ("P");
				if (x == 2 || x == 5 || x == 8)	{ print("|"); }
			}
			else if (x != 10)	{
				if (Explored.contains(x))	{
				  if (x == 3 || x  == 6 || x == 9)	{ print("|"); }
				  print("X");
				  if (x == 2 || x == 5 || x == 8)	{ print("|"); }
				}
				else	{
					if (x == 3 || x  == 6 || x == 9)	{ print(" "); }
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
		//Boss room, level 10.
		var monsterToQueue = new character( "King Kelman", 100, 7, 15, "chug" );
		queue = Array(placeholder).drop(1); 
		queue = queue :+ monsterToQueue;
		//KingKelman;
		println("You have entered the final room of Kelman's basement.");
		println("You can smell the gut-wrenching reek of doritos and mountain dew in the air.");
		//If player too powerful, nerf.
		if (player.hp > 90)	{
			player.hp -= 15;
			println("Unfortunately, you drink some mountain dew by accident. It reduces your hp by 15 to "+player.hp);
		}
		//Otherwise if player too weak, buff.
		else	{
			player.hp += 20;
			println("You drink a keg from off the ground. It restores your hp by 20 to "+player.hp+".");
		}
		//Checks that the player isn't dead after the nerf. Is not longer needed due only nerfing if above 90 hp but I'm leaving it in.
		if (player.hp > 0)	{
			println("You are now facing the evil King Kelman. Defeat him at all costs.");
		}
	}
	def failureMessage() =	{
		println();
		println("You have failed to defeat the evil Kan Krusher Kelman and save the kingdom of 4chan from his evil.");
		println("Inevitably, without your assistance, the kingdom totally fell to his influence and all was lost.");
		if (boss)	{
			println("All accounts of your daring adventure fade with the passage of time.\nSoon, even Kelman does not remember the battle against the "+Class+", "+name+".")
		}
		println("----  DEFEAT  ----");
		println("---- GAME OVER ----");
	}
	def gameSuccess() =	{
		println();
		println("You have defeated the evil Kan Krusher Kelman and saved the kingdom from his evil.");
		println("Upon your return you are granted command of 4chan.\nNow it is your turn to lead the kingdom to glory.");
		println("The tales of "+name+", a noble "+Class.toLowerCase+", will be sung throughout the lands for centuries to come.")
		println("----  VICTORY  ----");
		println("---- GAME OVER ----");
	}
}
