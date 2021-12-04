package scriptinghotel.languages
import scriptinghotel._

object Python extends Language("python") {
  override def idleTextIn(area: Area): String = "slithers around"

  override def relationshipCutscenes: Vector[Event] = Vector[Event](
    new Cutscene(
      Vector[Moment](
        this.dialogue("hey."),
        this.dialogue("what's up."),
        this.me("looks at you with her snake eyes (by the way I forgot to mention she is a snake)"),
        this.dialogue("how're you doing."),
        this.dialogue("or is it \"how'r\"? i don't know."),
        this.dialogue("i'm python. python the snake."),
        this.dialogue("let me guess: @playerName@?"),
        this.dialogue("how did i know that? it's a secret."),
        this.dialogue("... by the way, let me tell you a secret:"),
        this.dialogue("i'm good friends with Scala."),
        this.dialogue("that's how i knew your name, in case you didn't get the punchline."),
        this.dialogue("as you can tell, i'm very cool."),
        this.dialogue("if i were a human, i'd be a skater girl. those are cool, right?"),
        new RelationshipDialogueChoice(
          this,
          "(Are skater girls cool?)",
          Map[String, String]("Yes" -> "heck yeah.", "No" -> "incorrect.", "heck yeah." -> "heck yeah."),
          Set[String]("heck yeah.")
        ),
        this.dialogue("anyways."),
        this.dialogue("rad. you seem like a fun person."),
        this.dialogue("wanna do whatever it is that snakes and computers do?"),
        new Text("You do computer things. She does snake things. You both are happy."),
        new TimeSkip(1),
      ),
      new Exploration
    ),
    new Cutscene(
      Vector[Moment](
        this.dialogue("hey."),
        this.dialogue("i'm a bit tired. from being so cool."),
        this.dialogue("is it okay if we just sit here for now?"),
        this.dialogue("thanks in advance."),
        new RelationshipDialogueChoice(
          this,
          "(What do you say?)",
          Map[String, String]("Okay" -> "heck yeah.", "No" -> "that's a shame."),
          Set[String]("heck yeah.")
        ),
        this.dialogue("i'm just going to rest a bit."),
        this.dialogue("thanks for being kind."),
        new TimeSkip(1)
      ),
      new Exploration
    ),
    new Cutscene(
      Vector[Moment](
        this.dialogue("hey."),
        this.dialogue("you're rad."),
        this.dialogue("you know how rad?"),
        this.dialogue("so rad that i can't help but to think about you."),
        this.dialogue("sorry, that was a little direct."),
        this.dialogue("i'm a little concerned about myself. i'm supposed to be cool and not have people on my mind this way."),
        this.dialogue("oh well."),
        this.dialogue("i bet you're thinking \"I got her all flustered up!\" or something."),
        this.dialogue("well guess what?"),
        this.dialogue("..."),
        this.dialogue("you're correct."),
        this.dialogue("wanna go to the gala with me?"),
        new RelationshipDialogueChoice(
          this,
          "(Go with python?)",
          Map[String, String]("Yes" -> "heck yeah. you can't tell from the tone of my voice but i'm ecstatic right now.", "I'm going with someone else" -> "well then. i guess your resident cool girl snake was too late to ask.", "No" -> "alright. you can't tell from the tone of my voice but i'm distraught right now."),
          Set[String]("heck yeah. you can't tell from the tone of my voice but i'm ecstatic right now.")
        ),
        new TimeSkip(1)
      ),
      new Exploration
    )
  )
}
