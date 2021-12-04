package scriptinghotel

import scriptinghotel.languages._

object ScriptingHotelApp extends App {
  val introduction = new Cutscene(
    Vector[Moment](
      new Status("Welcome to Scripting Hotel!"),
      new Text("You're a computer. You've recently entered into a raffle, and the prize is a weekend at a 5 star hotel, the Scripting Hotel!"),
      new Text("Of course, you didn't expect to win."),
      new Text(""),
      new Text("..."),
      new Text(""),
      new Text("But you were wrong! As you check your inbox one day, you open an email titled \"Congratulations\" - You won the raffle! That's amazing!"),
      new Text("You quickly begin to pack your belongings. The hotel trip is quite soon! Better be prepared."),
      new Text("However, you've heard a lot of rumors about this particular hotel. They are odd rumors, but you never know with places you've never been before..."),
      new TimeSkip(0),
      new Status("It is the day of the trip!"),
      new Text("You are very excited. You've brought a SSD with all the things you'll need on the way."),
      new Text("You're looking forward to meeting new people. In truth, you feel a little lonely and would love a new friend."),
      new Text("Actually, would love a bit more than a friend... But you won't admit that to yourself quite yet! And besides, you're not even at the hotel yet."),
      new Text("You ride on the bus."),
      new TimeSkip(0),
      new Status("You've arrived at the hotel."),
    ),
    Unfinished
  )
  val gameState = new GameState(introduction, YourRoom)
  Scala.registerEvents(gameState)
  gameState.runUntilDone()
}
