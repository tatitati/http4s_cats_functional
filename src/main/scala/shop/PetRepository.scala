package shop

import cats._
import cats.Monad
import cats.effect.IO
import cats.implicits._
import cats.syntax.either._

import scala.util.Random

class PetRepository {
  var cache: Map[Long, Pet] = Map()
  private val random = new Random

  def create(pet: Pet): Either[String, Pet] = { // it can returns List[Pet], Option[Pet], ....
    val randomId = random.nextLong()
    val petToSave = pet.copy(id = Some(randomId))

    cache += (randomId -> petToSave)

    Right(petToSave)
  }

  def findByName(name: String): List[Pet] =
    cache.values
      .filter(p => p.name == name)
      .toList

  def doesNotExist(pet: Pet): Either[String, Unit] = {
    val found = this.findByName(pet.name)
    println(found)
    if (found.size === 0) {
      Right()
    } else {
      Left("the animal exist")
    }
  }
}
