package scriptinghotel

// Be warned, only like half of these are used

import scriptinghotel.languages.Language

/**
  * One thing that happens
  */
sealed abstract class Moment {
  def execute(gameState: GameState): Unit
}


class GenericText(val start: String, val end: String, val texts: String*) extends Moment {
  override def execute(gameState: GameState): Unit = {
    for (text <- texts) {
      gameState.show(gameState.replaceSpecialVariables(text), start, end)
    }
  }
}

class Text(texts: String*) extends GenericText("|", "", texts: _*) {}
class Status(texts: String*) extends GenericText("==", "==", texts: _*) {}
class Question(texts: String*) extends GenericText("*", "", texts: _*) {}
class Dialogue(language: Language, texts: String*) extends GenericText(s"$language:", "", texts: _*) {}

/**
  * Move to an area!
  * @param toArea where to go
  * @param verb how to go (e.g. "walk", "sneak", "carefully walk", ...)
  */
final class AreaTransition(val toArea: Area, verb: String) extends Status(s"You $verb to $toArea") {
  override def execute(gameState: GameState): Unit = {
    gameState.area = toArea
    super.execute(gameState)
  }
}

/**
  * Time passes...
  * @param hours How long (in hours)
  */
final class TimeSkip(val hours: Int) extends Status("Some time passes... (Press enter to progress)") {
  override def execute(gameState: GameState): Unit = {
    val newDay = gameState.addHours(hours)
    if (gameState.isGalaTime) {
      super.execute(gameState)
      gameState.interact()
      gameState.executeGalaEvent()
    }
    else if (newDay && gameState.area != YourRoom) {
      new Status("It's getting late. You'll go to your room to sleep the night.").execute(gameState)
      new AreaTransition(YourRoom, "make your way").execute(gameState)
      super.execute(gameState)
      gameState.interact()
    }
    else {
      super.execute(gameState)
      gameState.interact()
    }
  }
}

final class RelationshipIncrease(language: Language) extends Status(s"Your relationship with $language has increased") {
  override def execute(gameState: GameState): Unit = {
    language.relationshipLevel += 1
    super.execute(gameState)
  }
}

/**
  * You get a choice! ... But it doesn't matter
  * Use the Choice (Event) to influence story progression
  *
  * @param prompt the question
  * @param responses your options
  */
class MeaninglessChoice(val prompt: String, val responses: Map[String, String]) extends Moment {
  override def execute(gameState: GameState): Unit = {
    gameState.show(prepare(gameState))
  }
  def prepare(gameState: GameState): String = {
    gameState.readOption(prompt, responses.toVector)
  }
}

/**
  * You say one thing, they say another
  * @param language with whom?
  * @param prompt the question
  * @param responses your options
  * @param good set of responses that are good :>
  */
class RelationshipDialogueChoice(val language: Language, prompt: String, responses: Map[String, String], val good: Set[String]) extends MeaninglessChoice(prompt, responses) {
  override def execute(gameState: GameState): Unit = {
    val resp = prepare(gameState)
    language.dialogue(resp).execute(gameState)
    if (good.contains(resp)) {
      new RelationshipIncrease(language).execute(gameState)
    }
    else if (language.relationshipLevel != gameState.maxRelationshipBeforeGala) {
      new Status(s"You feel as if that answer didn't increase your relationship with $language.").execute(gameState)
    }
  }
}

final class StoreAnswer(val prompt: String, variable: String) extends Question(prompt) {
  override def execute(gameState: GameState): Unit = {
    super.execute(gameState)
    gameState.setSpecialVariable(variable, gameState.readInput())
  }
}