// Each dancer will be its own thread

// The Runnable interface defines a single method, run, 
// meant to contain the code executed in the thread. 

public class Dancer
{
	protected boolean isFinished;
	public String mName;
	protected int mNumber;
	protected int[] mDanceCard;
	protected Buffer mBuff; 		// each dancer has their own buffer for msgs

	public Dancer(int dancer_number)
	{
		this.isFinished = false;
		this.mNumber    = dancer_number;
		this.mDanceCard = new int[] {0,0,0,0,0,0,0,0};	// 8 dances
		this.mBuff      = new Buffer();
		this.mName 		= "Dancer " + Integer.toString(mNumber);
	}

	public int getDancerID()
	{
		return this.mNumber;
	}

	// record the dance you performed and with whom
	// also check to see if you've finished your card.
	protected void markCard(int dance_number, int dancer_num)
	{
		// mark card
		mDanceCard[dance_number] = dancer_num;

		// check if card is complete
		for(int i = 0; i < mDanceCard.length; i++)
		{
			if (mDanceCard[i] == 0)
				return;
		}
		isFinished = true;
		//System.out.println("I finished my card! said a follower.");
	}

	// get msg from buffer
	private Message get()
	{
		return this.mBuff.get(); 
	}

	// put a msg in the target's buffer
	public void put(Message m, Dancer target)
	{
		target.mBuff.put(m);
	}

	public boolean getIsFinished()
	{
		return isFinished;
	}

	public String toString()
	{
		String[] dance_names = {
			"Waltz	  ",
			"Tango	  ",
			"Foxtrot  ",
			"Quickstep",
			"Rumba    ",
			"Samba	  ",
			"Cha Cha  ",
			"Jive	  "
		};

		String s = "";
		s += mName + "\n";
		for(int i = 0; i < mDanceCard.length; i++)
		{
			s += dance_names[i] + "\twith " + Integer.toString(mDanceCard[i]) + "\n";
		}
		return s;
	}

}
