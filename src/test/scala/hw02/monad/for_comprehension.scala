package hw02.monad

import org.scalatest.flatspec.AnyFlatSpec

class for_comprehension extends AnyFlatSpec {

  "check correct map" should "ok" in {
    assert(NonEmptyWrap(10).map(_ * 10) === NonEmptyWrap(100))
    assert(NonEmptyWrap(10).map(el => NonEmptyWrap(el)) === NonEmptyWrap(NonEmptyWrap(10)))
    assert(Wrap.empty[Int].map(el => el * 10) === EmptyWrap)
  }

  "check correct flatMap" should "ok" in  {
    assert(NonEmptyWrap(10).flatMap(el => NonEmptyWrap(el * 10)) === NonEmptyWrap(10 * 10))
    assert(Wrap.empty[Int].flatMap(_ => NonEmptyWrap(10)) === EmptyWrap)
    assert(Wrap.empty[Int].flatMap(_ => EmptyWrap) === EmptyWrap)
  }

  "check correct pure" should "ok" in  {
    assert(EmptyWrap === Wrap.empty)
  }
}
