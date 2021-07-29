package module1

object App {

  def main(args: Array[String]): Unit = {

    val two = (x: Int) => { return x; 2 }

    println(two(6))


    def sumItUp: Int = {
          def one(x: Int): Int = {
            return x
            1
          }

          val two = (x: Int) => { return x; 2 }

          1 + one(2) + two(5)
   }

    println("Hello world")

    println(sumItUp)
  }
}
