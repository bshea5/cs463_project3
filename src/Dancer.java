// Each dancer will be its own thread

// The Runnable interface defines a single method, run, 
// meant to contain the code executed in the thread. 

public class Dancer
{
	protected boolean isFinished;
	protected int mNumber;
	protected int[] mDanceCard;
	protected Buffer mBuff; 		// each dancer has their own buffer for msgs

	public Dancer(int dancer_number)
	{
		this.isFinished = false;
		this.mNumber    = dancer_number;
		this.mDanceCard = new int[] {0,0,0,0,0,0,0,0};	// 8 dances
		this.mBuff      = new Buffer();
	}

	public int getDancerID()
	{
		return this.mNumber;
	}

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
	}

	// get msg from buffer
	private Message get()
	{
		return this.mBuff.get(); 
		// catch (InterruptedException e) {throw e;}
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
		String s = "";
		s += "My dance number is: " + Integer.toString(mNumber);
		return s;
	}

}
