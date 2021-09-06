package hw00

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration.Inf
import scala.concurrent.{Await, Future}

object HwTraining {

  final case class Training(name: String)

  trait Show[A] {
    def show(a: A): String
  }

  object Show {

    def apply[A](implicit ev: Show[A]): Show[A] = ev

    implicit val trainingShow: Show[Training] = new Show[Training] {
      override def show(v: Training): String = s"[${v.name}]"
    }
  }

  trait NewbieFunctor[A] {
    def map[B](f: A => B): NewbieFunctor[B]
  }

  trait Functor[F[_]] {

    def map[A, B](fa: F[A])(f: A => B): F[B]

    /** Выбросить значение (очень часто используется в IO) */
    def as[A](fa: F[A]): F[Unit]

    def pair[A](fa: F[A]): F[(A, A)]

    def pairMap[A, B](fa: F[A])(f: A => B): F[(A, B)]

    def showF[A: Show](fa: F[A]): F[String]

    def nested[FF[_]: Functor, G[_]: Functor, A, B](
        fga: FF[G[A]]
    )(f: A => B): FF[G[B]] =
      Functor[FF].map(fga) { ga =>
        Functor[G].map(ga)(f)
      }
  }

  object Functor {

    def apply[F[_]](implicit ev: Functor[F]): Functor[F] = ev

    implicit val futureFunctor: Functor[Future] = new Functor[Future] {

      override def map[A, B](fa: Future[A])(f: A => B): Future[B] = fa.map(f)

      override def as[A](fa: Future[A]): Future[Unit] = Future.unit

      override def pair[A](fa: Future[A]): Future[(A, A)] = fa.map(v => (v, v))

      override def pairMap[A, B](fa: Future[A])(f: A => B): Future[(A, B)] =
        fa.map(v => (v, f(v)))

      override def showF[A: Show](fa: Future[A]): Future[String] =
        fa.map(v => Show[A].show(v))
    }
  }

  trait Cell {
  }

  object Cell {
    final case class ALiveCell(size: Int) extends Cell
    object EmptyCell extends Cell
  }

  trait Semigroup[A] {

    def combine(a: A, b: A): A
  }

  object Semigroup {

    def apply[A](implicit ev: Semigroup[A]): Semigroup[A] = ev

    implicit val cellSemigroup: Semigroup[Cell] = new Semigroup[Cell] {
      override def combine(a: Cell, b: Cell): Cell = a match {
        case Cell.ALiveCell(aSize) => b match {
          case Cell.ALiveCell(bSize) => Cell.ALiveCell(aSize + bSize)
          case Cell.EmptyCell => a
        }
        case Cell.EmptyCell => b
      }
    }
  }

  trait Monoid[A] extends Semigroup[A] {

    def empty: A

    /** Саггрегировать все  */
    def foldMap(list: List[A])(implicit ev: Semigroup[A]): A = list.foldLeft(empty)(ev.combine)
  }

  object Monoid {

    def apply[A](implicit ev: Monoid[A]): Monoid[A] = ev

    implicit val cellMonoid: Monoid[Cell] = new Monoid[Cell] {

      override def empty: Cell = Cell.EmptyCell

      override def combine(a: Cell, b: Cell): Cell = Semigroup.cellSemigroup.combine(a, b)
    }
  }

  def main(args: Array[String]): Unit = {

    val t = Training("scala")
    println(Show[Training].show(t))

    println(
      Await.result(
        Functor[Future].map(Future.successful(1))(v => v + 1),
        Inf
      )
    )

    println(
      Await.result(
        Functor[Future].nested(Future.successful(Future.successful(1)))(v => v + 1),
        Inf
      )
    )
  }
}
