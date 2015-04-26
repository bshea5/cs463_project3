import java.util.ArrayList;
import java.util.Random;

public class Leader extends Dancer implements Runnable
{
	ArrayList<Follower> dancersToAsk;
	Random rand = new Random();
	int current_dance;

	public Leader(int dancer_number, Follower[] followers)
	{
		super(dancer_number);
		dancersToAsk = new ArrayList<Follower>();
		for(int i = 0; i < followers.length; i++)
		{
			this.dancersToAsk.add(followers[i]);
		}
		current_dance = 0;
	}

	public void run()
	{
		// keep asking dancers to dance current song
		//for(int dance = 0; dance < mDanceCard.length; dance++)
		while(!isFinished && !dancersToAsk.isEmpty())
		{
			if (isFinished || dancersToAsk.isEmpty())
				return;	// exit run

			// ask a random dancer to dance current dance
			// the putter runs until the reciever is able to accept a msg
			Message msgToSend = new Message(this, current_dance);
			int randomNumber = rand.nextInt(dancersToAsk.size()-1);
			Follower target = dancersToAsk.get(randomNumber);
			this.put(msgToSend, target);

			// check buffer until we have a response
			// the getter in buffer runs until it gets a response
			Message response = this.mBuff.get();
			int responseID = response.dancer.getDancerID();
			if (responseID != this.mNumber)  // She said yes! & got her number
			{
				this.markCard(current_dance, responseID);
				if (current_dance == mDanceCard.length-1)
					isFinished = true; 	// dance all dances, time to finish
				else 
					current_dance++; 	// next groove
			}
			// else she said no ;;  ask someone else for same dance
		} 
	}
}