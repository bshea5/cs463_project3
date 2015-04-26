public class SocratesDance 
{
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

		// initialize each thread(dancer), n leaders and m followers

		// wait for leaders to finish

		// print the results
		// report, in order, each dancer's card

	}
}