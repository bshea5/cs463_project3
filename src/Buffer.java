class Buffer
{
	private Message msg;  	// Message object to hold msg contents
	private boolean empty;	

	public Buffer()
	{
		empty = true;
	}

	public synchronized void put(Message m) 
	{ 
		while (empty == false) {	// wait till the buffer becomes empty
			try { wait(); }	
			catch (InterruptedException e) {}	// throwing e caused problems
		}
		this.msg = m;
		empty = false;
		notifyAll();				// Needed to be notifyAll instead of notify
		//System.out.println("Producer: put..." + msg.dancer + " & dance is: " + msg.dance_number);
	}

	public synchronized Message get() 
	{
		while (empty == true)  {	//wait till something appears in the buffer
			try { wait(); }
			catch (InterruptedException e) {}
		}
		empty = true;

		notifyAll();
		//System.out.println("Consumer: got..." + contents.dancer + " & dance is: " + contents.dance_number);
		return this.msg;
	}
}
