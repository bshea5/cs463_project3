// Each dancer will be its own thread

// The Runnable interface defines a single method, run, 
// meant to contain the code executed in the thread. 

public class Dancer
{
	protected boolean isFinished;
	protected int mNumber;
	protected int mDanceCard[] = {0,0,0,0,0,0,0,0}; // 8 dances

	public Dancer(int dancer_number)
	{
		this.isFinished = false;
		this.mNumber 	= dancer_number;
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