package scriptinghotel.languages
import scriptinghotel._

object C extends Language("C") {
  /**
    * Add the events associated with this language to the story progression.
    *
    * This operation can require that certain event path IDs exist.
    *
    * @param gameState The game object to attach the events to
    */
  override def registerEvents(gameState: GameState): Unit = {}

  override def idleTextIn(area: Area): String = "sits still, looking into the distance"

  override def relationshipCutscenes: Vector[Event] = Vector[Event](
    new Cutscene(Vector[Moment](
      this.me("looks your way but doesn't say anything"),
      new Text("You stand in silence for a few seconds."),
      this.dialogue("Hey there. I'm C."),
      this.dialogue("Do you want something?"),
      this.dialogue("..."),
      this.dialogue("Or do you just want to chat?"),
      this.dialogue("I'm not much for conversation, you know."),
      this.dialogue("My younger brother, C++, on the other hand... sheesh."),
      this.dialogue("You ask him a simple yes-or-no question and he just won't stop blabbing."),
      this.dialogue("I don't understand him at all."),
      this.dialogue("..."),
      this.dialogue("But enough about him."),
      this.me("glances at you with an indifferent expression"),
      this.dialogue("I haven't seen you before, what's your name?"),
      this.dialogue("... @playerName@, alright. Well that suits you."),
      this.dialogue("... @playerName@, right."),
      this.dialogue("Don't get used to compliments, by the way."),
      this.dialogue("You're an exception. I don't usually talk to strangers."),
      this.dialogue("Strangers are..."),
      new RelationshipDialogueChoice(
        this,
        "(Fill in the sentence before C can?)",
        Map[String, String]("Nosy" -> "... Right. They are.", "Exciting" -> "Ha, I wouldn't say that.", "(Stay silent)" -> "... You and I have similar vibes. I can respect that."),
        Set[String]("... You and I have similar vibes. I can respect that.", "... Right. They are.")
      ),
      this.dialogue("Anyway. I don't have much to do. Care to join?"),
      new Text("You two sit at the bar in silence for some time."),
      new TimeSkip(1),
    ), new Exploration),
    new Cutscene(Vector[Moment](
      this.dialogue("You again."),
      this.dialogue("I don't know why you're talking to me, really."),
      this.dialogue("The others are probably more interesting."),
      this.dialogue("I hate those kinds of people."),
      this.dialogue("Especially my brother."),
      this.dialogue("... Oh well."),
      this.dialogue("I'm not complaining. You're not as bad as they are."),
      this.dialogue("Ha."),
      new RelationshipDialogueChoice(
        this,
        "How do you react?",
        Map[String, String]("(Say nothing)" -> "Guess you got it.", "(Chuckle slightly)" -> "Heh, knew it.", "(Laugh uncomfortably loudly)" -> "... Don't make me take that back.", "That's funny" -> "It's not really."),
        Set[String]("Guess you got it.", "Heh, knew it.")
      ),
      this.dialogue("If you have nothing else to do..."),
      this.dialogue("Might as well stay here and... do nothing."),
      new Text("You two spend some more time in comfortable silence."),
      new TimeSkip(1),
    ), new Exploration),
    new Cutscene(Vector[Moment](
      this.dialogue("Hey."),
      this.dialogue("It's you again."),
      this.dialogue("You know, it's not that bad actually."),
      this.dialogue("... You talking to me."),
      this.me("looks away"),
      this.dialogue("I hate to admit it..."),
      this.dialogue("Guess you've rubbed off on me."),
      this.dialogue("Don't take it to your head."),
      this.me("smirks slightly. This is the first time you've seen him maybe smile"),
      this.dialogue("You know,"),
      this.dialogue("That gala thing."),
      this.dialogue("I wasn't going to go, but."),
      this.dialogue("..."),
      this.dialogue("Let's get to the point."),
      this.dialogue("Do you want to?"),
      new RelationshipDialogueChoice(
        this,
        "Will you go to the gala with C?",
        Map[String, String]("(Nod)" -> "Great. That's settled then.", "(Shake your head)" -> "Guess not.", "I have a partner already" -> "Yeah, you look like you would."),
        Set[String]("Great. That's settled then.")
      ),
      this.dialogue("See you then."),
      new TimeSkip(1)
    ), new Exploration)
  )
}
