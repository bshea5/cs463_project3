public class SocratesDance 
{
	Leader[] leaders;
	Follower[] followers;

	public SocratesDance(int n, int m)
	{
		// build m followers
		this.followers = new Follower[m];
		for(int i = 0; i < m; i++)
		{
			this.followers[i] = new Follower(i+1);;
			//System.out.println("i: " + i + " => " + followers[i].toString());
		}

		// build n leaders
		this.leaders = new Leader[n];
		for(int i = 0; i < n; i++)
		{
			leaders[i] = new Leader(i+1, this.followers);
			//System.out.println("i: " + i + " => " + leaders[i].toString());
		}

	}

	public static void main(String args[])
	{
		int n = 0, m = 0;

		// check if arguements are valid before we begin.
		if (args.length != 2)
		{
			System.out.println("Invalid number of arguements.");
			System.exit(1);
		}
		try {
			n = Integer.parseInt(args[0]);		// # of leaders
			m = Integer.parseInt(args[1]);		// # of followers
		} catch (NumberFormatException e) {
			System.err.println("Argument must be an integer.");
			System.exit(1);
		}

		SocratesDance sd = new SocratesDance(n, m);
		sd.startDance();
	}

	public void startDance()
	{
		Thread[] leader_threads = new Thread[leaders.length];
		Thread[] follower_threads = new Thread[followers.length];

		// start each dancer, followers first
		for(int i = 0; i < followers.length; i++)
		{
			Thread t = new Thread(followers[i]);
			follower_threads[i] = t;
			t.start();
		}

		for(int i = 0; i < leaders.length; i++)
		{
			Thread t = new Thread(leaders[i]);
			leader_threads[i] = t;
			t.start();
		}

		// wait for leader threads to finish
		try
		{
			for(int i = 0; i < leader_threads.length; i++)
				leader_threads[i].join();
		} catch (InterruptedException e) {}

		// halt the followers once the leader threads are finished
		for(int i = 0; i < follower_threads.length; i++)
			follower_threads[i].stop();

		// print the results
		// report, in order, each dancer's card
		System.out.println(leadersToString());
		//System.out.println(followersToString());

		System.out.println("The dance is over! ");
	}

	public void report()
	{
		

	}

	public String leadersToString()
	{
		String s = "Leaders:\n";
		for(int i = 0; i < leaders.length; i++)
		{
			s += this.leaders[i].toString() + "\n";
		}
		return s;
	}

	public String followersToString()
	{
		String s = "Followers:\n";
		for(int i = 0; i < this.followers.length; i++)
		{
			s += this.followers[i].toString() + "\n";
		}
		return s;
	}
}