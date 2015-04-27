import java.util.ArrayList;

public class Follower extends Dancer implements Runnable
{
	// circular dependency if followers need leaders in constructor
	public Follower(int dancer_number)
	{
		super(dancer_number);
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
			
			System.out.println("Being asked to dance: " + request.dance_number + " by " + request.dancer.getDancerID());

			Leader leader = (Leader)request.dancer;
			if (mDanceCard[request.dance_number] != 0) // sorry, already did this dance
			{
				this.put(request, leader); 	// send back request 
			}
			else if (count >= 2) // already danced with leader twice, how rude!
			{
				//System.out.println("Rude.");
				request.dance_number = -1; 	// request not to be asked again
				this.put(request, leader);
			}
			else // say yes! mark card
			{
				//System.out.println("Yes!");
				this.markCard(request.dance_number, request.dancer.getDancerID());
				request.dancer = this;
				this.put(request, leader);	// send back request, but with the follower's number
			}

			// markCard also checks if dancer is finished

		}
	}
}
