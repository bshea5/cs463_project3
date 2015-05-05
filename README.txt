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
parameters passed to our functions, was the most helpful part.
	
