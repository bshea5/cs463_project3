import java.util.ArrayList;
import java.util.Random;

public class Leader extends Dancer implements Runnable
{
	Follower[] dancersToAsk;
	Random rand = new Random();			// randomly pick dancer
	Boolean[] asked;

	public Leader(int dancer_number, Follower[] followers)
	{
		super(dancer_number);
		this.dancersToAsk = followers;
		this.mName = "Leader " + Integer.toString(mNumber);

		this.asked = new Boolean[followers.length];
		for(int i = 0; i < this.asked.length; i++)
			this.asked[i] = false;
	}

	public void run()
	{
		int current_dance = 0;
		Boolean allAsked = false;
		System.out.println("Leader: " + this.mNumber + " is starting.");
		while(current_dance < (mDanceCard.length))
		{
			// ask a random dancer to dance current dance
			// the putter runs until the reciever is able to accept a 
			Message msgToSend 	= new Message(this, current_dance);
			int randomNumber 	= rand.nextInt(dancersToAsk.length);
			Follower target 	= dancersToAsk[randomNumber];

			if (asked[randomNumber] || target.getIsFinished())
			{
				asked[randomNumber] = true;

				allAsked = true;
				for(int i = 0; i < asked.length; i++)
				{
					if (!asked[i])
					{
						allAsked = false;
					}
				}
				if (allAsked)	// if asked everyone, move on to next dance 
				{
					System.out.println(mNumber + " No one to do dance " + current_dance + " with...");
					current_dance++;
					for(int i = 0; i < asked.length; i++)
						asked[i] = false;
					allAsked = false;
				}
			}
			else	// ask them to dance
			{		
				System.out.println("Send Message to follower: " + target.getDancerID());	
				this.put(msgToSend, target);

				// check buffer until we have a response
				// the getter in buffer runs until it gets a response
				Message response = this.mBuff.get();

				if (response.dance_number != (-1))  // She didn't say no!
				{
					int responseID 	= response.dancer.getDancerID();
					this.markCard(current_dance, responseID);
					current_dance++; // next groove

					// reset asked[]
					for(int i = 0; i < asked.length; i++)
						asked[i] = false;
				}
				else
				{
					asked[randomNumber] = true;
				}
			}
		}
		System.out.println("Leader " + mNumber + " is finished.");
		isFinished = true; 
	}
}