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
			Message request = mBuff.get(); 		// stuck here?

			// how many times has the follower danced with the requester.
			// might be faster with an array of timesDanced values for each leader known
			int count = 0;
			for (int i=0; i<mDanceCard.length; i++)
			{
				if (mDanceCard[i] == request.dancer.getDancerID())
					count++;
			}

			Leader leader = (Leader)request.dancer;
			// already did this dance or already danced with leader twice
			if (mDanceCard[request.dance_number] != 0 || count > 2)
			{
				this.put(request, leader); 	// send back request 
			}
			else // say yes! mark card
			{
				this.markCard(request.dance_number, request.dancer.getDancerID());
				request.dancer = this;
				this.put(request, leader);	// send back request, but with the follower's number
			}

			// markCard also checks if dancer is finished

		}
	}
}
