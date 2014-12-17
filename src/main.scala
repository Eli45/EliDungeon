object main extends App {
	import publicVariables._, publicClasses._, Functions._
	while (play)	{
		intro();
		while (player.hp > 0 && curLvl != 10)	{
			generateLevel();
			printLevelInfo();
			while (player.hp > 0 && queue(0).hp > 0)	{
				combat();
			}
			if (player.hp > 0)	{
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
			while (player.hp > 0 && queue(0).hp > 0)	{
				combat();
			}
			if (player.hp <= 0) {
				failureMessage();
			}
			else	{
				gameSuccess();
			}
		}
		reset();
	}
}
