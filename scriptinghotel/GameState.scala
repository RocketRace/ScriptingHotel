package scriptinghotel

import scriptinghotel.languages._

import scala.collection.mutable
import scala.io.StdIn.readLine

class GameState(firstEvent: Event, startingArea: Area) {
  private var currentEvent = firstEvent
  /** Set this to change the internal location */
  var area = startingArea

  // note: you should use the addHours method
  var day = 0
  var hour = 0
  // (there are four hours in a day, don't question it)
  val hoursInDay = 4
  // pinky promise never to set the hour to a bad value
  def hourString = hour match {case 0 => "morning" case 1 => "noon" case 2 => "afternoon" case 3 => "evening"}

  /**
    * Progress forwards some time
    * @param hours how long
    * @return Whether a new day has elapsed
    */
  def addHours(hours: Int): Boolean = {
    this.hour += hours
    if (this.hour == hoursInDay) {
      this.day += 1
      this.hour = 0
      true
    }
    else false
  }

  /**
    * :O it's time
    */
  def isGalaTime: Boolean = day == 1 && hour == hoursInDay - 1
  /**
    * Relationship level achievable before going to the gala
    */
  val maxRelationshipBeforeGala = 3

  def show(text: String, prefix: String = "|", end: String = ""): Unit = {
    Thread.sleep(100)
    print(prefix + " ")
    for (char <- replaceSpecialVariables(text)) {
      print(char)
      Console.flush()
      Thread.sleep(10)
    }
    println(" " + end)
  }

  def showCurrentState(): Unit = {
    show(s"It is currently the $hourString of day ${day + 1}. The gala is ${if (day == 0) "tomorrow night" else "tonight"}. You're in $area.", "==", "==")
  }

  def showAreaInformation(area: Area): Unit = {
    area.description.execute(this)
    for (language <- area.languages) {
      language.me(language.idleTextIn(area)).execute(this)
    }
  }

  var specialVariables: mutable.Map[String, Any] = mutable.Map[String, Any]()
  def setSpecialVariable(variable: String, value: Any): Unit = {
    specialVariables(s"@$variable@") = value
  }
  /**
    * All text displayed on the screen replaces text of the format "@something@" with the variable "something" stringified
    * @param input string to apply replacements to
    * @return the resulting string
    */
  def replaceSpecialVariables(input: String): String =
    specialVariables.foldLeft(input)((temp, pair) => temp.replace(pair._1, pair._2.toString))

  val languages: Vector[Language] = Vector[Language](
    Rust, C, Cpp, Python, Haskell
  )

  def executeGalaEvent(): Unit = {
    new Status("It is the night of the gala!").execute(this)
    val asked = languages.filter(_.relationshipLevel >= this.maxRelationshipBeforeGala)
    if (asked.isEmpty) {
      new Text("You haven't gotten close enough with anyone in this hotel to take them to the gala.").execute(this)
      new Text("You feel a little disappointed. Maybe next time...").execute(this)
      Lose.run(this)
    }
    else if (asked.length == 1) {
      val the = asked.head
      new Text(s"You took $the with your partner to the gala. You two had a wonderful time, and made sure to get each other's numbers.").execute(this)
      Win.run(this)
    }
    else {
      val first = asked(0)
      val second = asked(1)
      new Text(s"You took both $first and $second to the gala as your partners. It was a little awkward at first, but you get the feeling that they didn't mind being a three.").execute(this)
      new Text("You got both of their numbers too, of course.").execute(this)
      Win.run(this)
    }
  }

  /**
    * Start the game. This is a blocking action.
    */
  def runUntilDone(): Unit = {
    while (!this.currentEvent.isDone) {
      this.run()
    }
    this.currentEvent match {
      case ending: Ending => ending.showEndingMessage(this)
    }
  }

  /**
    * Process one event. It may be story-related (output) or user-related (input)
    */
  def run(): Unit = {
    // move on to the next event
    currentEvent = currentEvent.run(this)
  }

  val helpStrings: Vector[String] = Vector[String](
    "Scripting Hotel is a dating simulator. You are a computer looking for a gala partner in the hotel, within 2 days.",
    "You can explore around the hotel, talk to characters, and build relationships. Be warned, time passes during cutscenes!",
    "You can also rest in your room to skip time.",
  )

  /**
    * Mapping from actions to optional events (for quitting, etc) that are none for certain actions (like help)
    * @return map
    */
  def defaultEvents: Map[String, Event] = Map[String, Event](
    "Get help" -> new Cutscene(Vector[Moment](new Text(this.helpStrings: _*)), new Exploration),
    "Quit game" -> new Cutscene(Vector[Moment](new Text("Quitting the game...")), Quit),
  )

  // vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
  // >>>>>>>>>>> BIG BIG WARNING! <<<<<<<<<<<<<<
  // ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
  // Event paths are deprecated.
  // They were an idea I had at the start to initialize the game story in advance. It actually isn't really used that much in the game...
  // It's still in here, though! Because the concept works in theory.
  // And it's annoying to refactor code that isn't bad (even if it uses a different API than you use right now).
  //
  //
  // So basically, the description below might be out of date.

  // === EVENT PATH SPECIFICATION ===
  //
  // An event path is a sequence of strings ("parts") separated by "#" characters.
  // If the first part begins with "%", it is treated as an ID and expanded into a constant string sequence.
  // The ID "%" is special and expands to an empty path.
  // Otherwise, paths are traversed as following:
  // - If the current event is a Cutscene, only the empty string is accepted. The next event in the sequence is picked.
  // - If the current event is a Choice, strings are treated as inputs to the String -> Event mapping.
  // - If the current input is a NoChoice, strings are parsed as ints and the corresponding index is passed to `eventAt`.
  // - If the current input is an Ending, ignore the rest of the path.
  //
  // (This is basically reinventing file paths, I know...)
  private var eventIds: mutable.Map[String, String] = mutable.Map[String, String]("%" -> "")

  /**
    * Starting from an event, fetch the path for the last path in a linear sequence
    * @param parent starting path
    * @return final path
    */
  def traverseThroughLinearPath(parent: String): String = {
    val base = traverseEventPath(parent)
    base match {
      case cutscene: Cutscene => ""
      case choice: Choice => {
        if (choice.choices.size == 1) {
          choice.choices.keys.head
        }
        else {
          throw new RuntimeException
        }
      }
      case _ => throw new RuntimeException
    }
  }

  /**
    * Expand an event ID, or return an empty path part.
    * @param id what to expand
    * @return the expanded
    */
  def tryExpandEventId(id: String): String = eventIds.getOrElse(id, "")

  /**
    * Register an ID
    * @param id ID to register
    * @param path path to register to it
    */
  def registerEventId(id: String, path: String): Unit = {
    eventIds(id) = path
  }

  /**
    * Split off the first part of a path
    * @param path to split
    * @return the head and tail, in a tuple
    */
  def pathHeadTail(path: String): (String, String) = {
    val parts = path.split("#", 1)
    (parts(0), parts(1))
  }

  /**
    * Join two paths with "#". If the left path is empty, return the right path instead
    * @param left left path
    * @param right right path
    * @return left and right joined by "#"
    */
  def joinEventPaths(left: String, right: String): String = if (left == "") right else s"$left#$right"

  /**
    * Traverse an absolute event path as specified above
    * @param path path to traverse
    * @return Event at the given location
    */
  def traverseAbsoluteEventPath(path: String): Event = {
    var currentNode = currentEvent
    for (part <- path.split("#")) {
      currentNode match {
        case choice: Choice => {
          currentNode = choice.choices(part)
        }
        case cutscene: Cutscene => {
          assert(part == "")
          currentNode = cutscene.next
        }
        case noChoice: NoChoice => {
          currentNode = noChoice.checks(part.toInt)._1
        }
        case _ => {}
      }
    }
    currentNode
  }

  /**
    * Traverse a path, including ID expansions
    * @param path path
    * @return event
    */
  def traverseEventPath(path: String): Event = {
    if (path.nonEmpty) {
      val (head, tail) = pathHeadTail(path)
      traverseAbsoluteEventPath(joinEventPaths(tryExpandEventId(head), tail))
    }
    else {
      currentEvent
    }
  }

  /**
    * Extend the event given by the input path
    * @param basePath The input path to traverse through
    * @param event What to append
    * @param registerId An optional path ID to register to the new event.
    * @param check An optional check to pass, if the event is a NoChoice
    */
  def registerEventAtPath(basePath: String, pathSuffix: String, event: Event, registerId: Option[String] = None, check: Option[GameState => Boolean] = None): Unit = {
    val baseEvent = traverseEventPath(basePath)
    baseEvent match {
      case choice: NoChoice => {
        val idx = pathSuffix.toInt
        if (choice.checks.length == idx) {
          choice.checks.append((event, check.get))  // this shouldn't be None if the story is created properly
        }
        else {
          choice.checks(pathSuffix.toInt) = (event, check.get)
        }
      }
      case other => other.updateChild(pathSuffix, event)
    }
    registerId match {
      case Some(id) => registerEventId(id, joinEventPaths(basePath, pathSuffix))
      case None => {}
    }
  }

  /**
    * Follows up an existing event with a new one
    * @param path Path to event
    * @param event Event
    * @param registerId Optional new ID
    * @param check optional check (for nochoice)
    */
  def registerEventFollowingPath(path: String, event: Event, registerId: Option[String] = None, check: Option[GameState => Boolean] = None): Unit = {
    registerEventAtPath(path, traverseThroughLinearPath(path), event, registerId, check)
  }

  def interact(): Unit = readLine() // input ignored
  def readInput(): String = readLine()

  /**
    * Choose between [things] using player input.
    *
    * The player inputs a number, which is used as the index for the map keys.
    *
    * Note that the order of choices is arbitrary and may not be the same across play sessions.
    * Let's say it's a feature and not a bug :>
    *
    * @param prompt What to show
    * @param options [label -> thing] mapping
    * @tparam T the kind of [things] to pick from
    * @return the chosen [thing]
    */
  def readOption[T](prompt: String, options: Vector[(String, T)]): T = {
    var choice: Option[Int] = None
    while (!choice.exists(x => x >= 1 && x <= options.size)) {
      show(prompt)
      for (((key, opt), i) <- options.zipWithIndex) {
        println(s"[${i + 1}] ${key}")
      }
      choice = readInput().toIntOption
    }
    options(choice.get - 1)._2
  }
}
