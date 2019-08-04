package test.learning.cats.Monads

import cats.{Eval, Id, Monad}
import cats.instances.option._
import org.scalatest.FunSuite

class OptionMonadSpec extends FunSuite {

  //  trait Monad[F[_]] {
  //    def pure[A](value: A): F[A]
  //    def flatMap[A, B](value: F[A])(func: A => F[B]): F[B
  //    def map[A, B](value: F[A])(func: A => B): F[B]
  //  }

  test("pure()") {
    val result1 = Monad[Option].pure(3)  //monad option is instance of cats.instances.OptionInstances$$anon$1@376c94a8
    assert(Some(3) === result1)
  }

  test("map()") {
    val result1 = Monad[Option].map(Some(3))(a => 2*a)
    val result2 = Monad[Option].map(Some(3))(a => Some(a.toString))

    assert(Some(6) === result1)
    assert(Some(Some("3")) === result2)
  }

  test("flatMap()") {
    val result1 = Monad[Option].flatMap(Some(3))(a => Some(2*a))
    val result2 = Monad[Option].flatMap(Some(3))(a => Some(a.toString))

    assert(Some(6) === result1)
    assert(Some("3") === result2)
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