1. Create a graphical interface that represents the table of philosophers. The interface must
be able to properly represent a philosopher thinking, eating, or being hungry.
- The GUI must clearly show a philosopher’s name, current state, as well as any
possible forks in use.
- Your GUI must include a log, that displays textual information concerning each
philosopher’s name and change in state (eating, thinking, hungry).
- You GUI must include a button to start and a button to pause the simulation.
- You GUI must include a means for the user to define how many ticks per second
will be executed when the simulation is running. (See item 2 for a definition of a
tick.)
- Your GUI must include a way to choose between SemaphoreDinner and
MonitorDinner. (See item 2 for more information.)
- Don’t worry about imposing constraints and validation to the problem at this
point. For example, it is okay for the interface to allow two philosophers next to
each other to eat at the same time.
- Don’t worry about concurrency at this point.

2. Create a SemaphoneDinner and a MonitorDinner class. The former will be a solution to
the problem using Semaphores only, the latter will be a solution using Monitors only
(async. methods). Both solutions should be based on Fig. 2-47 from the book.
- Any of the two solutions should have a method tick(). Every time tick is called all
philosophers should be able to think or to attempt to eat.
- A philosopher has a name, and thinks and eats for a random number of ticks
between 1 and 15. For example, a philosopher Cardi B may start thinking for 6
ticks, then attempt to eat. Unfortunately for Cardi B, one of her neighbors is
eating, so she is not able to eat for the next 10 ticks. Eventually, Cardi B’s
neighbor finishes eating, so she starts to eat, finishing in 3 more ticks. Once done
eating, Cardi B goes back to think for a random number of ticks, and so on.
- A simulation that is running will continue to call the method tick() from the current
solution until its paused. The method will be called according to the number of
ticks per second defined by the user.
- Whenever a philosopher is hungry (attempting to eat, but not allowed to), eating,
or thinking, the GUI must be updated accordingly (including the log).