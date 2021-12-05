package scriptinghotel.languages
import scriptinghotel._

object Haskell extends Language("Haskell") {
  override def idleTextIn(area: Area): String = "sits among the flowers. She seems tranquil."

  override def relationshipCutscenes: Vector[Event] = Vector[Event](
    new Cutscene(
      Vector[Moment](
        new Text("You approach Haskell. She looks a little startled."),
        this.dialogue("Hi..."),
        this.me("looks away from you"),
        this.dialogue("I'm Haskell, and, um, and what's your name?"),
        new Text("\"@playerName@\", you tell her."),
        this.dialogue("Hi, @playerName@, that's a nice name."),
        this.dialogue("Is that a weird thing to say? Sorry"),
        this.dialogue("I was just relaxing by the flowers."),
        this.dialogue("They're really pretty, aren't they?"),
        new Text("You agree with her."),
        this.dialogue("I've been trying to take care of my own garden, too."),
        this.dialogue("It's, um, a work in progress."),
        this.dialogue("Oh, I'm rambling. What do you think about plants?"),
        new RelationshipDialogueChoice(
          this,
          "(Opinion about plants?)",
          Map[String, String]("I love them" -> "Yeah, me too - I just want to take care of them!", "I don't really like plants" -> "... That's okay", "Sorry - plants gross me out" -> "It's, fine"),
          Set[String]("Yeah, me too - I just want to take care of them!")
        ),
        this.dialogue("Flowers are just so pure. I want to do everything to help them."),
        this.dialogue("Although, well, nature does its thing -- but I want to have a positive impact, too."),
        new Text("You two sit by the flowers for some time."),
        new TimeSkip(1)
      ),
      new Exploration
    ),
    new Cutscene(
      Vector[Moment](
        this.dialogue("Hey, @playerName@..."),
        this.me("smiles softly"),
        new Text("She looks to be a bit more comfortable around your presence."),
        this.dialogue("The flowers look healthy."),
        this.dialogue("I'm glad they're doing alright."),
        this.dialogue("Maybe I can do something positive in this world, haha."),
        this.dialogue("Sorry, maybe that's too depressing."),
        this.dialogue("I just think, um, that everyone deserves the best."),
        this.dialogue("And I want to help others reach that."),
        this.dialogue("Even if it's just some flowers."),
        this.dialogue("Is that really so weird?"),
        new RelationshipDialogueChoice(
          this,
          "(Is that weird?)",
          Map[String, String]("It is pretty weird" -> "Um. I didn't expect you to say that. But, I guess you're being honest...", "It's not weird at all" -> "Thank you, @playerName@ - I feel like we, understand each other, you know?", "What" -> "Sorry, sorry... I guess I was being weird again"),
          Set[String]("Thank you, @playerName@ - I feel like we, understand each other, you know?")
        ),
        new Text("You two keep chatting for a little longer."),
        new TimeSkip(1)
      ),
      new Exploration
    ),
    new Cutscene(
      Vector[Moment](
        this.dialogue("Hi, @playerName@"),
        this.dialogue("I've missed you!"),
        this.dialogue("I guess it's only been a little while, but, um, I really enjoy being with you."),
        this.dialogue("You've been really great to me in a way that nobody else has."),
        this.dialogue("And, I, well, uh,"),
        this.me("looks away and blushes"),
        this.dialogue("This is going to sound dumb..."),
        this.me("makes eye contact"),
        this.dialogue("Do you want to go to the gala with me?"),
        this.me("quickly looks away again"),
        this.dialogue("It's okay if you say no! I just,"),
        this.dialogue("Wanted to ask you."),
        new RelationshipDialogueChoice(
          this,
          "(Go to the gala with her?)",
          Map[String, String]("Yes" -> "Really? I, I would like that a lot.", "No" -> "Oh... That's, okay... I guess.", "I have somebody else, sorry" -> "Who is it? - Sorry, I shouldn't have said that. It's, okay, I understand."),
          Set[String]("Really? I, I would like that a lot.")
        ),
        new TimeSkip(1)
      ),
      new Exploration
    )
  )
}
