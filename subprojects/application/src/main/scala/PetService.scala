package application.pet

import cats.data._
import cats.effect._
import cats.implicits._
import domain.pet.Pet
import infrastructure.pet.{PetDontExist, PetExist, PetRepository}

class PetService(petRepository: PetRepository) {

  def create(pet: Pet): EitherT[IO, PetExist.type, Unit ] = {
    val exists: IO[Boolean] = petRepository.exist(pet.name)
    val create: IO[Unit] = petRepository.create(pet)

    val run: IO[Either[PetExist.type, Unit]] = exists.flatMap {
      case true => PetExist.asLeft[Unit].pure[IO]
      case false => create.map(_ => ().asRight[PetExist.type])
    }

    EitherT(run)
  }

  def list: IO[List[Pet]] = {
    petRepository.list()
  }

  def find(pet:Pet): OptionT[IO, Pet] = {
    val findByName: IO[Option[Pet]] = petRepository.findByName(pet.name)

    OptionT(findByName)
  }

  def update(newage: Int, pet: Pet): EitherT[IO, PetDontExist.type, Unit] = {
    val exist: IO[Boolean] = petRepository.exist(pet.name)
    val update: IO[Unit] = petRepository.updateAge(newage, pet)

    val run: IO[Either[PetDontExist.type, Unit]] = exist.flatMap{
      // I can do
      // update.map(_ => ().asRight[PetDontExist.type])
      // Right(Unit))
      case true => update.map(_ => Right(Unit))
      // I can do:
      // PetDontExist.asLeft[Unit].pure[IO]
      // IO{PetDontExist.asLeft[Unit]}
      // IO(PetDontExist.asLeft[Unit])
      // IO(Left(PetDontExist.asLeft[Unit])
      // IO(Left(PetDontExist))
      case false => IO(Left(PetDontExist))
    }

    EitherT(run)
  }
}