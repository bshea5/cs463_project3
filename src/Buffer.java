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
			try { wait(); }			// are the waits breaking this?
			catch (InterruptedException e) {}	// throwing e caused problems
		}
		this.msg = m;
		empty = false;
		notify();
		//System.out.println("Producer: put..." + msg.dancer + " & dance is: " + msg.dance_number);
	}

	public synchronized Message get() 
	{
		while (empty == true)  {	//wait till something appears in the buffer
			try { wait(); }
			catch (InterruptedException e) {}
		}
		empty = true;

		//notify();
		//System.out.println("Consumer: got..." + contents.dancer + " & dance is: " + contents.dance_number);
		return this.msg;
	}
}
