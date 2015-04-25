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
			int count = 0;
			for (int i=0; i<mDanceCard.length; i++)
			{
				if (mDanceCard[i] == request[0])
					count++;
			}
			if (mDanceCard[request[1]] != 0 || count > 2)
				this.potentialLeaders.get(request[0]).put([0,0]);
			else
			{
				this.potentialLeaders.get(request[0]).put(request);
				this.mDanceCard[request[1]] = request[0];
			}

			isFinished = true;
			for (int i=0; i<potentialLeaders.size(); i++)
				if (!potentialLeaders.get(i).getIsFinished())
					isFinished = false;
		}
	}
}
