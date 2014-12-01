object Game extends App {
	import publicVariables._
	import Entities._
	import Functions._
	while (plyHP > 0 && curLvl != 10)	{
		generateLevel();
		printLevelInfo();
		setupEnemy();
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
