package scriptinghotel.languages

import scriptinghotel._

/**
  * A character in the dating sim :)
  *
  * Each language registers event trees on startup.
  */
abstract class Language(val name: String) {
  /**
    * Add the events associated with this language to the story progression.
    *
    * This operation can require that certain event path IDs exist.
    *
    * @param gameState The game object to attach the events to
    */
  def registerEvents(gameState: GameState): Unit = {}
  // NOTE: pre-registering events is actually deprecated. I was going to remove it but there are parts of code that would need to be refactored
  // and I'm going to otacruise so I have to finish this by Sunday

  var relationshipLevel: Int = 0

  /**
    * text -> moment
    * @param texts text to say
    * @return moment
    */
  def dialogue(texts: String*): Dialogue = new Dialogue(this, texts: _*)

  /**
    * Convenience method
    * @param action What am I doing
    * @return Text
    */
  def me(action: String): Text = new Text(s"$this $action.")

  override def toString: String = name

  /**
    * What should be displayed when checking the current game state?
    * @param area In area
    * @return Text
    */
  def idleTextIn(area: Area): String = "is standing idly"

  def relationshipCutscenes: Vector[Event] = Vector[Event]()

  /**
    * The next cutscene to be shown to the player with the current state of [things]
    * @return some Event
    */
  def nextCutscene(): Event = if (this.relationshipLevel < this.relationshipCutscenes.length) relationshipCutscenes(this.relationshipLevel) else this.defaultDialogue

  /**
    * When you've exhausted a language's dialogue, this is what happens (no time elapsed)
    * @return Cutscene
    */
  def defaultDialogue: Cutscene = new Cutscene(Vector[Moment](
    this.dialogue("Hi, @playerName@! How are you doing?"),
    new MeaninglessChoice("How are you?", Map[String, String]("I'm good" -> "That's good to hear", "I'm okay" -> "Me too", "Not that good" -> "I'm sorry about that")),
    this.dialogue("See you at the gala!"),
    this.me("smiles at you")
  ), new Exploration)
}
