import java.util.ArrayList;

public class Follower extends Dancer implements Runnable
{
	// circular dependency if followers need leaders in constructor
	public Follower(int dancer_number)
	{
		super(dancer_number);
		this.mName = "Follower " + Integer.toString(mNumber);
	}

	public void run()
	{
		System.out.println("Follower: " + this.mNumber + " is starting.");
		while (!this.isFinished)
		{
			Message request = this.mBuff.get(); // get request from own mailbox
			// why is the request have the wrong dance number?

			// how many times has the follower danced with the requester.
			// might be faster with an array of timesDanced values for each leader known
			int count = 0;
			for (int i=0; i<mDanceCard.length; i++)
			{
				if (mDanceCard[i] == request.dancer.getDancerID())
					count++;
			}
			
			System.out.println(mNumber + " Being asked to dance: " + request.dance_number + " by " 
								+ request.dancer.getDancerID());

			Leader leader = (Leader)request.dancer;
			if (mDanceCard[request.dance_number] != 0 || count >= 2) // nope.
			{
				request.dance_number = -1;
				this.put(request, leader); 	// send back request with -1 for dance_number
			}
			else // say yes! mark card and see if you're done
			{
				this.markCard(request.dance_number, request.dancer.getDancerID());
				request.dancer = this;
				this.put(request, leader);	// send back request, but with the follower's own number
			}
		}
		System.out.println("Follower " + mNumber + " is finished.");
		
		// still check requests, but refuse them all.
		while (this.isFinished)
		{
			Message request = this.mBuff.get();

			Leader leader = (Leader)request.dancer;
			request.dance_number = -1;
			this.put(request, leader);
		}
	}
}
