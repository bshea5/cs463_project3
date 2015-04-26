class Buffer 
{
	private int[] msg;  // index 0 = dancer, index 1 = dance type
	private boolean empty;	// if msg is [0,0], than empty

	public Buffer()
	{
		msg = new int[] {0,0};
		empty = true;
	}

	public synchronized void put (int leaderID, int dance_number) 
	{ 
		while (empty == false) {	//wait till the buffer becomes empty
			try { wait(); }
			catch (InterruptedException e) {}	// throwing e caused problems
		}
		msg[0] = leaderID;
		msg[1] = dance_number;
		empty = false;
		System.out.println("Producer: put..." + msg);
	}

	public synchronized int[] get () 
	{
		while (empty == true)  {	//wait till something appears in the buffer
			try { wait(); }
			catch (InterruptedException e) {}
		}
		empty = true;

		// get information for dance card
		int[] contents = msg;

		// clear old msg
		msg[0] = 0;
		msg[1] = 0;

		System.out.println("Consumer: got..." + contents);
		return contents;
		// maybe, if msg returned is [0,0], that is a no
	}
}
