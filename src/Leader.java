import java.util.ArrayList;
import java.util.Random;

public class Leader extends Dancer implements Runnable
{
	ArrayList<Follower> dancersToAsk;	// list of dancers left to ask
	Random rand = new Random();			// randomly pick dancer

	public Leader(int dancer_number, Follower[] followers)
	{
		super(dancer_number);
		dancersToAsk = new ArrayList<Follower>();
		for(int i = 0; i < followers.length; i++)
		{
			this.dancersToAsk.add(followers[i]);
		}
		this.mName = "Leader " + Integer.toString(mNumber);
	}

	// NOTE: currently gets stuck if everyone keeps telling him 'no'

	public void run()
	{
		int current_dance = 0;
		Boolean[] asked = new Boolean[dancersToAsk.size()];
		for(int i = 0; i < asked.length; i++)
			asked[i] = false;
		System.out.println("Leader: " + this.mNumber + " is starting.");

		while(!dancersToAsk.isEmpty() && (current_dance < (mDanceCard.length)) )
		{
			System.out.println("Leader " + mNumber + "s " + "Current Dance: " + current_dance);
			// ask a random dancer to dance current dance
			// the putter runs until the reciever is able to accept a 
			Message msgToSend 	= new Message(this, current_dance);
			int randomNumber 	= rand.nextInt(dancersToAsk.size());
			Follower target 	= dancersToAsk.get(randomNumber);

			// check if target is finished first, if so, remove them
			if (target.getIsFinished())
			{
				// dancersToAsk.remove(target);
				// System.out.println("dancers left to ask: " + dancersToAsk.size());
				asked[randomNumber] = true;
			}
			else if (asked[randomNumber])	// already asked this person
			{
				System.out.println("already asked you.");
				// check if there are still ppl to ask
				Boolean allAsked = true;
				for(int i = 0; i < asked.length; i++)
				{
					if (!asked[i])
					{
						System.out.println("dancer: " + i + "found an guy we haven't asked");
						allAsked = false;
					}
				}
				if (allAsked)	// if asked everyone, move on to next dance
				{
					System.out.println("No one to do this dance with...");
					current_dance++;
					for(int i = 0; i < asked.length; i++)
						asked[i] = false;
				}
			}
			// else, ask them to dance
			else
			{			
				this.put(msgToSend, target);			// put does finish

				// check buffer until we have a response
				// the getter in buffer runs until it gets a response
				Message response = this.mBuff.get();

				int responseID 	= response.dancer.getDancerID();
				if (responseID != this.mNumber)  		// She said yes! & got her number
				{
					this.markCard(current_dance, responseID);
					current_dance++; // next groove

					// reset dancers to ask
					for(int i = 0; i < asked.length; i++)
						asked[i] = false;
				}
				else // if (response.dance_number == -1)	// she asked to not be asked again...
				{
					// dancersToAsk.remove(target);
					asked[randomNumber] = true;
				}
				// else
				// {
				// 	//System.out.println("bugger off");
				// 	asked[randomNumber] = true;
				// }
			}
		}
		System.out.println("Leader " + mNumber + " is finished.");
		isFinished = true; 
	}
}