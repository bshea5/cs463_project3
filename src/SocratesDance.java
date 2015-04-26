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
		System.out.println(sd.leadersToString());
		System.out.println(sd.followersToString());
	}

	public void startDance()
	{
		// start each dancer, followers first
		for(int i = 0; i < followers.length; i++)
			followers[i].start();

		for(int i = 0; i < leaders.length; i++)
			leaders[i].start();

		// wait for leaders to finish


		// print the results
		// report, in order, each dancer's card
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