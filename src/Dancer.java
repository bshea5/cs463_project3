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
		this.mNumber 	= dancer_number;
		this.mDanceCard = new int[] {0,0,0,0,0,0,0,0};	// 8 dances
		this.mBuff 		= new Buffer();
	}

	public boolean isDoneDancing() 
	{ 
		return this.isFinished; 
	}

	protected void markCard(int dance_number, int dancer_num)
	{
		mDanceCard[dance_number] = dancer_num;
	}

}