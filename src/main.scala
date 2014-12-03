object main extends App {
	import publicVariables._, Entities._, Functions._
	intro();
	while (plyHP > 0 && curLvl != 10)	{
		generateLevel(curLvl);
		printLevelInfo();
		while (plyHP > 0 && enemyHP > 0)	{
			combat();
		}
		if (plyHP > 0)	{
			treasure();
			move();
		}
		//if not > 0 then forces while loop end and fails player if not in boss room.
	}
	if (curLvl != 10)	{
		failureMessage();
	}
	else	{
		bossEntry();
		while (plyHP > 0 && enemyHP > 0)	{
			combat();
		}
		if (plyHP <= 0) {
			failureMessage();
		}
		else	{
			gameSuccess();
		}
	}
	reset();
}