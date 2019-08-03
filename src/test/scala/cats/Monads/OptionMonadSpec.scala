package test.cats.Monads

import cats.Monad
import org.scalatest.FunSuite

class OptionMonadSpec extends FunSuite {

  //  trait Monad[F[_]] {
  //    def pure[A](value: A): F[A]
  //    def flatMap[A, B](value: F[A])(func: A => F[B]): F[B
  //    def map[A, B](value: F[A])(func: A => B): F[B]
  //  }

  test("pure()") {
    val monadOption = Monad[Option]  //monad option is instance of cats.instances.OptionInstances$$anon$1@376c94a8
    val result1 = monadOption.pure(3)

    assert(Some(3) === result1)
  }

  test("flatMap()") {
    val result1 = Monad[Option]
      .flatMap(Some(3))(a => Some(a + 2))

    assert(Some(5) === result1)
  }

  test("flatMap() changing resulting type") {
    val result1 = Monad[Option]
      .flatMap(Some(3))(a => Some(a.toString))

    assert(Some("3") === result1, "Input can be Some[int], but after flapMap I have Some[String]")
  }

  test("map()") {
    var result1 = Monad[Option]
      .map(Some(3))(a => 2*a)

    assert(Some(6) === result1)
  }

  test("Monads chaing nicely together") {
    val a = Some(3)
    val b = Some(2)

    val result1 =
      a.flatMap{ aVal =>
        b.map { bVal =>
          bVal + aVal + 2
        }
      }

    val result2 = for {
      a <-  Monad[Option].pure(3)
      b <-  Monad[Option].pure(2)
    } yield (a+b+2)

    val result3 = for {
      a <-  Some(3)
      b <-  Some(2)
    } yield (a+b+2)

    val result4 = Monad[Option].flatMap(Some(3))(a => Some(a + 4))

    assert(Some(7) === result1)
    assert(Some(7) === result2)
    assert(Some(7) === result3)
    assert(Some(7) === result4)
  }

  test("use case") {
    import cats.syntax.flatMap._
    import cats.syntax.functor._


    // bound context here:
    // we are saying that for F[_] we have an implicit Monad[F[_]]
    def sumSquare[F[_] : Monad](a: F[Int], b: F[Int]): F[Int] = {
      for {
        x <- a
        y <- b
      } yield x*x + y*y
    }

    val result1 = sumSquare(Option(3), Option(4))
    assert(Some(25) === result1)
  }
}
