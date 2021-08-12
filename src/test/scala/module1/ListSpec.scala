package module1

import module1.list.List.{incList, shoutString}
import org.scalatest.flatspec.AnyFlatSpec

class ListSpec extends AnyFlatSpec {

  import module1.list._

  "mkString" should "return in right order" in {
    val value: List.Cons[Int] = List.Cons(1, List.Cons(2, List.Cons(3)))
    assert("[1,2,3]" == value.mkString())
  }

  "reverse" should "return in reverse order" in {
    val value: List.Cons[Int] = List.Cons(1, List.Cons(2, List.Cons(3)))
    assert("[3,2,1]" == value.reverse().mkString())
  }

  "map" should "return apply f" in {
    val value: List.Cons[Int] = List.Cons(1, List.Cons(2, List.Cons(3)))
    assert("[2,3,4]" == value.map(x => x + 1).mkString())
  }

  "filter" should "return filter odd" in {
    val value: List.Cons[Int] = List.Cons(1, List.Cons(2, List.Cons(3, List.Cons(4))))
    assert("[2,4]" == value.filter(x => x % 2 == 0).mkString())
  }

  "incList" should "return incremented list" in {
    val value: List.Cons[Int] = List.Cons(1, List.Cons(2, List.Cons(3, List.Cons(4))))
    assert("[2,3,4,5]" == incList(value).mkString())
  }

  "shoutString" should "return prefixed list" in {
    val value: List.Cons[String] = List.Cons("1", List.Cons("2", List.Cons("3", List.Cons("4"))))
    assert("[!1,!2,!3,!4]" == shoutString(value).mkString())
  }
}
