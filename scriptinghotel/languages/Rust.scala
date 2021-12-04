package scriptinghotel.languages
import scriptinghotel._

object Rust extends Language("Ferris") {
  override def registerEvents(gameState: GameState): Unit = {

  }

  override def idleTextIn(area: Area): String = "scuttles in place"

  override def relationshipCutscenes: Vector[Event] = Vector[Event](
    new Cutscene(Vector[Moment](
      this.me("looks your way"),
      this.dialogue("Hi!! I'm Ferris! Some people also call me Rust, haha - And you're...?"),
      new Text("Rust is a clickety looking crab on the sand. They look ever-busy, like they always have something to do."),
      new Text("You tell Ferris your name. They listen enthusiastically."),
      this.dialogue("@playerName@! Okay, I won't forget! Nice to meet you!"),
      this.me("smiles very widely"),
      this.dialogue("I prefer being here, near my home. How do you feel about the sea?"),
      new RelationshipDialogueChoice(
        this,
        "How do you feel?",
        Map[String, String]("I love it" -> "Yes!!! I know right!! I think we understand each other", "It's not exactly... my favorite..." -> "Aw, I see... Well that's fine!", "I hate it" -> "O-oh..."),
        Set[String]("Yes!!! I know right!! I think we understand each other")
      ),
      this.dialogue("Well either way! I guess we have more to talk about!"),
      new Status("You and Rust keep chatting for a while."),
      new TimeSkip(1),
    ), new Exploration),
    new Cutscene(Vector[Moment](
      this.me("notices you're here"),
      this.dialogue("Hi again!! @playerName@, right? I told you I wouldn't forget!"),
      this.dialogue("I think we have a lot in common!"),
      this.dialogue("I mean, we both love the sea!! What more could you ask for!!!"),
      new Text("You tell Ferris to calm down a little, jokingly"),
      this.dialogue("Oh right yeah, I can get a little spastic sometimes"),
      this.dialogue("Sorry about that... It's not something I can always control"),
      new RelationshipDialogueChoice(
        this,
        "What do you say?",
        Map[String, String]("That's okay" -> "Aww, thank you!", "It's fine, it happens"-> "Thanks for being so kind to me @playerName@", "Don't do it again" -> "I.. I'll try not to, but. I said it's not something I can always control", "I hate you" -> "Um, like, jokingly? I can't tell..."),
        Set[String]("Aww, thank you!", "Thanks for being so kind to me @playerName@")
      ),
      this.dialogue("You know, in a way,"),
      this.dialogue("I guess you could say I'm concurrent, haha. I do so many things at once."),
      this.dialogue("(You could even say... *fearlessly* concurrent)"),
      new Text("You two talk for a while longer."),
      new TimeSkip(1)
    ), new Exploration),
    new Cutscene(Vector[Moment](
      this.me("excitedly waves towards you with their crab claws"),
      this.dialogue("Hi!!!"),
      this.dialogue("The gala is tonight! Do you have a partner yet?"),
      this.dialogue("I thought that maybe... you know..."),
      this.me("blushes somehow, even though that's kind of impossible with a bright red crab shell"),
      this.dialogue("@playerName@, I think, we've grown very close this weekend! Will.. will you be my gala partner?"),
      new RelationshipDialogueChoice(
        this,
        "What do you say?",
        Map[String, String]("Yes" -> "Omg! I'm so excited!", "No, I already have a partner, sorry" -> "Oh... Well, have fun with whoever it is!", "No" -> "Okay... That's fine!"),
        Set[String]("Omg! I'm so excited!")
      ),
      new TimeSkip(1)
    ), new Exploration),
  )
}
