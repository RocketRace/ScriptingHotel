package scriptinghotel.languages
import scriptinghotel._

object Cpp extends Language("C++") {
  override def idleTextIn(area: Area): String = "stands flamboyantly"

  override def relationshipCutscenes: Vector[Event] = Vector[Event](
    new Cutscene(
      Vector[Moment](
        this.me("looks your way enthusiastically"),
        this.dialogue("Well hello there!"),
        this.me("grins with extreme confidence"),
        this.dialogue("What's your name?"),
        this.dialogue("@playerName@, huh? That's fabulous!"),
        this.dialogue("I'm C++, the objectively better one of the C brothers."),
        this.dialogue("Now that I think about it, why do they call us both C?"),
        this.dialogue("We're nothing alike."),
        this.dialogue("I mean look at my brother!"),
        this.dialogue("Total weirdo."),
        this.dialogue("I love him, though."),
        this.dialogue("(Even though he's really weird.)"),
        this.dialogue("If you haven't met him yet, then, well, you're not missing out! Haha."),
        this.dialogue("He doesn't really talk much."),
        this.dialogue("So you wouldn't have much to chat about, honestly speaking."),
        this.dialogue("I kind of wish he would talk more. You know, there's a world outside that head of yours, too!"),
        this.dialogue("But whatever. I can't force him to change his personality."),
        this.dialogue("As much as I would love to! Imagine that."),
        this.dialogue("Either way!"),
        this.dialogue("I have a very important question for you. I ask this question to everyone I meet, if you're wondering."),
        this.dialogue("Which do you prefer, minimalism or maximalism?"),
        new RelationshipDialogueChoice(
          this,
          "(Maximalism or minimalism?)",
          Map[String, String]("Maximalism all the way!" -> "Heh, perfect.", "Minimalism" -> "You're not a lost cause yet!", "(Stay silent)" -> "... That was a question, by the way.", "I have no idea and I don't really care" -> "Wow, alright then buzzkill"),
          Set[String]("Heh, perfect.", "You're not a lost cause yet!")
        ),
        this.dialogue("I'm glad to have someone to chat to!"),
        new Text("You two sit at the bar and chat very loudly, annoying the other guests."),
        new TimeSkip(1),
      ),
      new Exploration
    ),
    new Cutscene(
      Vector[Moment](
        this.dialogue("It's @playerName@! My favorite person to chat to at this bar right now!"),
        this.dialogue("How are you doing! I hope you're having a great time at the hotel."),
        this.dialogue("It's a great place! Lots of wonderful people. I especially love the restaurant/bar. You see so many different kinds of languages here."),
        this.dialogue("Compiled languages, interpreted languages, functional languages, imperative languages, ... I met a nice esoteric language the other day, too!"),
        this.dialogue("I can't help but to notice, though: you're not a programming language!"),
        this.dialogue("I suppose it doesn't make a difference, though."),
        this.dialogue("I don't see a sign that says \"PROGRAMMING LANGUAGES ONLY HOTEL\", haha. That would be weird. And probably illegal. I don't really know law so I can't say!"),
        this.dialogue("Anyways, the more kinds of people, the better! I love to do people-watching."),
        this.dialogue("What do you think about people-watching?"),
        new RelationshipDialogueChoice(
          this,
          "What do you think?",
          Map[String, String]("I do that too!" -> "We're on the same brainwave! I love that!", "I don't really care about it" -> "Mmm, I was hoping you wouldn't say that. It's actually really fascinating!", "I hate that, it's so creepy and why would you do it" -> "I have to disagree with the creepy part - you might even do it subconsciously, you know!"),
          Set[String]("We're on the same brainwave! I love that!")
        ),
        this.dialogue("It's always great to chat with you. You're really wonderful, you know that?"),
        this.me("smiles playfully"),
        new Text("You two sit at the bar and chat very loudly, annoying the other guests even more."),
        new TimeSkip(1),
      ),
      new Exploration
    ),
    new Cutscene(
      Vector[Moment](
        this.dialogue("Hey there, @playername@! Anything happened since last time?"),
        this.me("gives a wide smile"),
        this.dialogue("I've got a few stories to tell you."),
        this.dialogue("Try not to get bored!"),
        this.dialogue("So, about that one -"),
        this.me("suddenly interrupts himself"),
        this.dialogue("Oh! I almost got carried away!"),
        this.dialogue("I actually have something important to ask you."),
        this.dialogue("You obviously know about the gala, right."),
        this.dialogue("I mean, everyone does!"),
        this.dialogue("(Hopefully. I mean it's a pretty big thing if you ask me)"),
        this.dialogue("Would you care to be my partner for the gala?"),
        this.dialogue("I know we've only known for so long, but I can't describe with words how many compliments I would shower you with. (I can't even describe it with dance!)"),
        this.dialogue("So, will you? I would be overjoyed if you did."),
        new RelationshipDialogueChoice(
          this,
          "Will you?",
          Map[String, String]("Yes!" -> "Yes!!! I suppose you feel similarly too then.", "I have someone else" -> "Darn it, I was too late! I guess I can still tell you these stories!", "No" -> "I guess not, then. No fear! I still have those stories to tell you!"),
          Set[String]("Yes!!! I suppose you feel similarly too then.")
        ),
        new TimeSkip(1)
      ),
      new Exploration
    )
  )
}
