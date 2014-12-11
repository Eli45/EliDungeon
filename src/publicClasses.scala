object publicClasses	{
	import publicVariables._
	class character(Name:String, HP:Int, Damage:Int, missChance:Int, Ability:String)	{
		var name = Name;
		var maxhp = HP;
		var hp = HP;
		var dmg = Damage;
		var ability = Ability.toLowerCase;
		var abilityChance = 0;
		var abilityDesc = "";
		var mChance = missChance;
		var lastdmg = 0;
		private var canClutch = false;
		private var canAbsorb = false;
		private var canFly = false;
		private var canChug = false;
		private var canDodge = false;
		private var canTurtle = false;
		if (ability == "clutch")	{ canClutch = true; abilityChance = 50;abilityDesc = "came back to life with 15 hp."; }
		else if (ability == "absorb")	{ canAbsorb = true; abilityChance = 30;abilityDesc = "absorbed your damage!"; }
		else if (ability == "dodge")	{ canDodge = true; abilityChance = 50;abilityDesc = "dodged your attack!"; }
		else if (ability == "turtle")	{ canTurtle = true; abilityChance = 10;abilityDesc = "started turtling! All damage taken reduced by 90% for the next turn."; }
		else if (ability == "fly")	{ canFly = true; abilityChance = 5;abilityDesc = "flew away from your attack and smashed back into you for 10 damage!"; }
		else if (ability == "chug")	{ canChug = true; abilityChance = 15;abilityDesc = "chugged a keg to regain 15 HP!"; }
		def attack(Enemy:Player) =	{
			var rand:Float = 0;
			rand = Math.round(Math.random()*dmg+7);
			var missed = Math.round(Math.random()*100);
			if (missed <= mChance)	{ rand = 0; }
			lastdmg = rand.toInt;
			if (lastdmg > Enemy.hp)	{
				lastdmg = Enemy.hp;
			}
			Enemy.hp -= lastdmg;
		}
		def useAbility() =	{
			if (canClutch && hp <= 0)	{
				var chance = Math.round(Math.random());
				if ( chance == 0 )
					hp += 15;
					canClutch = false;
			}
			else if (canAbsorb)	{	//See abilitydesc for what it does.
				hp += player.lastdmg;
			}
			else if (canFly)	{
				hp += player.lastdmg;
				player.hp -= 10;
			}
			else if (canChug)	{
				hp += 15;
			}
			else if (canDodge)	{
				hp += player.lastdmg;
			}
			else if (canTurtle)	{
				canTurtle = false;
			}
		}
	}
	
	class Player(Name:String, HP:Int, Class:String)	{
		var name = Name;
		var maxhp = HP;
		var hp = HP;
		var classStr = Class;
		var abChance = 0;
		var lastdmg = 0;
		if (classStr == "knight")	{
			
		}
		else if (classStr == "archer")	{
		  
		}
		else if (classStr == "assassin")	{
		  
		}
		def attack(Enemy:character, modifier:Double) =	{
		  var roll = Math.round(Math.random()*((wepDMG+6)*modifier));
		  if (roll < minDMG)	{ roll = minDMG; }
		  else if (roll > wepDMG)	{ roll = wepDMG; }
		  var missedOrNo = Math.round(Math.random()*100);
		  if (missedOrNo < missChance)	{ lastdmg = 0; }
		  else	{
			  var newRoll = roll.toDouble;
			  var almostfinalDAM = 0.0;
			  if (wep == "longsword")	{ almostfinalDAM = LSdmgs.apply(Enemy.name) * newRoll; }
			  else if (wep == "shortsword")	{ almostfinalDAM = SSdmgs.apply(Enemy.name) * newRoll; }
			  else if (wep == "bow")	{ almostfinalDAM = BOWdmgs.apply(Enemy.name) * newRoll; }
			  else if (wep == "fireball")	{ almostfinalDAM = FBdmgs.apply(Enemy.name) * newRoll; }
			  else if (wep == "lightning")	{ almostfinalDAM = LTdmgs.apply(Enemy.name) * newRoll; }
			  else	{ almostfinalDAM = CTdmgs.apply(Enemy.name) * newRoll; }
			  lastdmg = almostfinalDAM.toInt;
		  }
		  if (lastdmg > Enemy.hp)	{
			  lastdmg = Enemy.hp;
		  }
		  Enemy.hp -= lastdmg;
		}
		def useAbility() =	{
		  
		}
			
	}
}