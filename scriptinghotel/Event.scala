package scriptinghotel

import java.lang
import scala.collection.mutable
import scala.sys.exit

// This module gives a higher level overview of the story progression.
// Individual event details are specified as Moments.

// Warning, I wrote lots of these initially and then realized I don't need them for my game later on

/**
  * An class for representing event flow. All story progression can be respresented as an event.
  */
abstract class Event {
  /**
    * Run the event with the specified inputs.
    *
    * This is assumed to be non-reentrant, i.e. run should only ever be called once.
    *
    * @return The next event to run.
    */
  def run(gameState: GameState): Event

  /**
    * Extend this event in some way.
    * @param parthPart The direct child to extend
    * @param event The child event to add
    */
  def updateChild(parthPart: String, event: Event): Unit

  /**
    * Is the game over while this event is active?
    * @return Is the game over while this event is active?
    */
  def isDone: Boolean = false
}
/**
  * Cutscene with a linear sequence of moments
  * @param moments The moments to execute
  * @param next The event following this (mutable)
  */
final class Cutscene(private val moments: Vector[Moment], var next: Event = Unfinished) extends Event {
  override def run(gameState: GameState): Event = {
    moments.foreach(_.execute(gameState))
    next
  }

  override def updateChild(parthPart: String, event: Event): Unit = {
    assert(parthPart == "")
    this.next = event
  }
}

/**
  * You, the player, get a choice!
  * @param prompt What is the choice
  * @param choices What are your choices (mutable)
  */
final class Choice(private val prompt: String, var choices: mutable.Map[String, Event]) extends Event {
  override def run(gameState: GameState): Event = {
    gameState.readOption(prompt, choices.toVector)
  }

  override def updateChild(parthPart: String, event: Event): Unit = {
    choices(parthPart) = event
  }
}

/**
  * Perform a story branch, but without the player's control
  *
  * For example, go to a different cutscene based on relationship levels.
  *
  * The first condition in `checks` that returns a Some(Event)
  * @param checks A sequence of Events and callables that return `true` when a condition is met.
  * @param default The event that happens when no checks succeed. Defaults to `Unfinished`, which ends the game in an incomplete state.
  */
final class NoChoice(private val default: Event = Unfinished, var checks: mutable.Buffer[(Event, GameState => Boolean)]) extends Event {
  override def run(gameState: GameState): Event = {
    for ((event, check) <- checks) {
      if (check(gameState)) {
        return event
      }
    }
    default
  }

  // Must use `addCheck`
  override def updateChild(parthPart: String, event: Event): Unit = {
    throw new RuntimeException
  }
}

final class Exploration extends Event {
  // No direct event additions allowed
  // Update the return value of `character.nextCutscene` (via changing relationship level, etc.)
  override def updateChild(parthPart: String, event: Event): Unit = {
    throw new RuntimeException
  }

  override def run(gameState: GameState): Event = {
    var nextEvent: Option[Event] = None
    gameState.showCurrentState()
    gameState.showAreaInformation(gameState.area)
    gameState.readOption("What do you do?", gameState.area.actions(gameState))
  }
}

/**
  * We're done! In one way or another...
  */
sealed abstract class Ending extends Event {
  override def run(gameState: GameState): Event = {
    showEndingMessage(gameState)
    gameState.show("Thank you for playing Scripting Hotel!")
    exit()
  }
  override def isDone: Boolean = true
  def showEndingMessage(gameState: GameState): Unit = {}

  override def updateChild(parthPart: String, event: Event): Unit = throw new lang.RuntimeException("Ending state has no children")
}

/**
  * Uh oh something went wrong :<
  */
object Unfinished extends Ending {
  override def showEndingMessage(gameState: GameState): Unit = {
    gameState.show("... Suddenly, the game is over.", "")
    gameState.show("(Something went wrong with the story! This should never happen...)", "")
  }
}

/**
  * Quit the game (why would yoU!!)
  */
object Quit extends Ending {}

/**
  * Contrat
  */
object Win extends Ending {
  override def showEndingMessage(gameState: GameState): Unit = {
    gameState.show("Congratulations, @playerName@! You beat the game!")
  }
}

/**
  * 0.0 what's this
  */
object SecretWin extends Ending {
  override def showEndingMessage(gameState: GameState): Unit = {
    gameState.show("Congratulations, @playerName@! You found the secret ending and won the game!")
  }
}

/**
  * Dang it
  */
object Lose extends Ending {
  override def showEndingMessage(gameState: GameState): Unit = {
    gameState.show("Sorry, @playerName@! You have lost the game.")
    gameState.show("Better luck next time!")
  }
}
