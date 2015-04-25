public class Follower extends Dancer implements Runnable
{
	ArrayList<Leader> potentialLeaders;

	public Follower(int dancer_number, Leader[] leaders)
	{
		super(dancer_number);
		for(int i = 0; i < leaders.length; i++)
			this.potentialLeaders.add(leaders[i]);
	}

	public void run()
	{
		while (!this.isFinished)
		{
			int[] request = mBuff.get();

		}
	}
}
