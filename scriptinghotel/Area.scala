package scriptinghotel

import scriptinghotel.languages._

sealed abstract class Area(val name: String) {
  override def toString: String = name

  def languages: Vector[Language]

  def description: Text

  def exits: Vector[Area]

  def actionVerb: String = "go"

  def specialEvents: Map[String, Event]

  /**
    * What actions can the player take here?
    * @param gameState What's the game state???
    * @return Action mapping
    */
  def actions(gameState: GameState): Vector[(String, Event)] = {
    val left = languages.map(lang => s"Talk to ${lang.name}" -> lang.nextCutscene())
    val middle = exits.map(exit => s"Go to ${exit.name}" -> new Cutscene(Vector[Moment](new AreaTransition(exit, exit.actionVerb)), new Exploration))
    val middleAlso = specialEvents.toVector
    val right = gameState.defaultEvents
    left ++ middle ++ middleAlso ++ right
  }
}

object Lobby extends Area("Lobby") {
  def languages = Vector[Language](Scala)

  override def description: Text = new Text("It's a beautifully decorated hotel lobby. You can see hallways leading to guest rooms.")

  override def exits: Vector[Area] = Vector[Area](YourRoom, Garden, Restaurant)

  override def specialEvents: Map[String, Event] = Map[String, Event]()
}

object YourRoom extends Area("Your room") {
  override def languages: Vector[Language] = Vector[Language]()

  override def description: Text = new Text("It's your room. It's very elegant and clean.")

  override def exits: Vector[Area] = Vector[Area](Lobby)

  override def specialEvents: Map[String, Event] = Map[String, Event](
    "Rest" -> new Cutscene(Vector[Moment](new TimeSkip(1)), new Exploration)
  )
}

object Garden extends Area("Garden") {
  override def languages: Vector[Language] = Vector[Language](Python, Haskell)

  override def description: Text = new Text("A wonderfully peaceful garden full of floral scents. You could sit here forever.")

  override def exits: Vector[Area] = Vector[Area](Lobby, Beach)

  override def specialEvents: Map[String, Event] = Map[String, Event](
    "Relax" -> new Cutscene(Vector[Moment](new Text("You sit down for a little while and look at the flowers.")), new Exploration)
  )
}

object Beach extends Area("Beach") {
  override def languages: Vector[Language] = Vector[Language](Rust)

  override def description: Text = new Text("A picturesque beach. You hear the sea breathing in and out, as the seawater licks the sand.")

  override def exits: Vector[Area] = Vector[Area](Garden)

  override def specialEvents: Map[String, Event] = Map[String, Event]()
}

object Restaurant extends Area("Restaurant") {
  override def languages: Vector[Language] = Vector[Language](C, Cpp)

  override def description: Text = new Text("A cozy hotel restaurant. There are guests lounging around at the bar.")

  override def exits: Vector[Area] = Vector[Area](Lobby)

  override def specialEvents: Map[String, Event] = Map[String, Event]()
}