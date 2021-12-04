package scriptinghotel.languages
import scriptinghotel._

/**
  * The "tutorial" language. This should always be registered first!
  */
object Scala extends Language("Scala") {

  /**
    * Add the events associated with this language to the story progression.
    *
    * This operation can require that certain event path IDs exist.
    *
    * @param gameState The game object to attach the events to
    */
  override def registerEvents(gameState: GameState): Unit = {
    gameState.registerEventAtPath("", "", introCutscene)
  }

  val introCutscene = new Cutscene(
    Vector[Moment](
      this.dialogue("Hey there."),
      new Text("A programming language confidently approaches you. They're wearing an elegant red uniform, reminding you of a twisty tower."),
      new Text("They look about as sturdy as a tower, too - but more approachable."),
      this.dialogue("You must be the winner of the raffle, eh? Welcome to Scripting Hotel!"),
      this.dialogue("Nice to meet you! My name is Scala."),
      this.me("flashes a wide grin"),
      this.dialogue("I'm your personal assistant for this trip. If you have any questions, I'm your guy! Now, err, what's your name again?"),
      new StoreAnswer("(What's your name?)", "playerName"),
      this.dialogue("@playerName@, eh? That's a great name. Now let me show you around a little! We're in the hotel lobby right now."),
      this.dialogue("This is the center of the whole gig, heh. Over there are the restaurant & shops, and over there are the rooms."),
      this.dialogue("You're situated at room 404."),
      this.me("gestures towards a hallway"),
      this.dialogue("You can also take a stroll outside in the garden, or maybe visit the beach. I recommend going at least once."),
      this.dialogue("Of course, you can check the map anytime."),
      this.me("hands you a small map of the area"),
      this.dialogue("Now, the important part."),
      this.dialogue("This hotel is hosting a gala on Sunday evening. (That's in 2 days' time.)"),
      this.dialogue("Now, you're an esteemed guest, and of course you can attend at will."),
      this.dialogue("That said, if you do choose to participate, we strongly encourage you to come with a partner."),
      this.dialogue("No stress of course, @playerName@. You can explore around this place. I'll carry your bag to your room."),
      this.me("bows intricately, and starts carrying your SSD"),
      new Text("You make your way to your room."),
      new Text("You now have the freedom to roam around the hotel. Maybe you'll find someone to take to the gala!"),
    ),
    new Exploration
  )

  override def nextCutscene(): Event = new Cutscene(
    Vector[Moment](
      this.dialogue("Hey there. Need help?"),
      this.dialogue("You can explore around this hotel, and talk to other characters."),
      this.dialogue("If I were you, I would be looking for someone to take to the gala!"),
      this.dialogue("Hope that helps!")
    ),
    new Exploration
  )
}
