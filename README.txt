Edward Lane and Brandon Shea -- Writeup of Experiences

	There were a couple interesting challenges we faced writing this
program. The biggest challenge, in the end, was writing it in Haskell. In both
our cases we are not experienced with functional programming, and starting out
in it can almost feel like one is learning to program all over again. However,
we think it overall feels rewarding when learned. As far as competition for
resources, we found that the competition could be managed without too much
trouble as long as the coder correctly synchronizes the mailbox. This is the
main form of competition in the program; multiple dancers may seek a mailbox
at the same time, and the ones who don't ask first must be sure to just wait
until their time comes. There was definitely a need for cooperation, but it
was simply in the form of making sure that the dancers made entries to and
read entries from the mailbox properly, as the mailboxes are the only things
depended on by multiple threads.
	The most straightforward part was the ability to define
object-oriented classes and instance variables for the objects of those
classes. Java's syntax is very friendly for objects, and we could easily make
a Dancer parent class with Leader and Follower children, along with a Message
and Buffer (Mailbox) class. The fact that we can hold as many variable values
as needed within class methods, rather than having to use nothing but the
parameters passed to our functions, was the most helpful part. As far as what
was laborious, we thought that having to pass references of all the follower
objects was difficult because we originally did not realize we would have to
do this and it caused a little refactoring for us.
	-- skipping third bullet for now
	When we first started Haskell, we were a little worried what
concurrency would be like in it, since Haskell is a challenging language for
us. However, we found that using MVar was actually surprisingly
straightforward. After running through some of the class examples posted
online, we understood the basic use of MVar and forkIO enough to get started
on the assignment. The laborious part, to us at least, was still getting used
to the statelessness of Haskell functions. However, we managed to make it a
bit more manageable by using data types to organizes some of the required
values and make function argument lists look a little more sane.
	-- skipping fifth bullet for now
	To be honest, we don't have any big pieces of advice we wish we could
have had at the start of the assignment. As usual, there were a fair amount of
examples in class, and they covered forking multiple threads, using MVar, and
some of the common issues that occur when dealing with concurrent programming.
Even more surprising was that the lectures gave not only examples of how to do
concurrency in Haskell, but also in Java as well. This was very helpful to us
as it cushioned the process of beginning the program in those languages.
Unfortunately, the assignment is still a challenge, as concurrent programming
is difficult and with the exception of a brief part of 367, there are no
required classes on concurrency at GMU. In fact, this was actually our first
programming assignment ever using concurrency. However, there is nothing that
can be done about that; and in the end we had an even bigger learning
experience because we got a brief start into concurrent programming. All in
all we felt the assignment was challenging and a good learning experience for
us.
