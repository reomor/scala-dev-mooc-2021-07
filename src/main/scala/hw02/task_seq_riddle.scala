package hw02

object task_seq_riddle {

  /** Рассмотрим последовательность с числами:
    *
    * 1
    * 1 1
    * 2 1
    * 1 2 1 1
    * 1 1 1 2 2 1
    * 3 1 2 2 1 1
    * ...........
    *
    * 1. Реализуйте функцию генерирующую след последовательность из текущей
    */

  def nextLine(currentLine: List[Int]): List[Int] =
    currentLine.foldRight(List[Int]())((el, list) =>
      list match {
        case increment :: value :: tail if el == value =>
          (increment + 1) :: value :: tail
        case tail => 1 :: el :: tail
      }
    )
//    task"Реализуйте функцию генерирующую след последовательность из текущей"()

  /** 2. Реализуйте ленивый список, который генерирует данную последовательность
    * Hint: см. LazyList.cons
    *
    * lazy val funSeq: LazyList[List[Int]]  ...
    */

  val funSeq: LazyList[List[Int]] = List[Int](1) #:: funSeq.map(nextLine)
//    task"Реализуйте ленивый список, который генерирует данную последовательность"()

  def main(args: Array[String]): Unit = {
    println(nextLine(List(1)))
    println(nextLine(List(1, 1)))
    println(nextLine(List(2, 1)))
    println(nextLine(List(1, 2, 1, 1)))
  }
}
