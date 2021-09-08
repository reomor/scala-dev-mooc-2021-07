package module1
import type_system.Rectangle
import module2.implicits
import zio.ZIO
import zio.ExitCode
import zio.ZEnv
import zio.URIO
import module3.zioOperators

object App {

  def main(args: Array[String]): Unit = {
    zio.Runtime.default.unsafeRun(ZIO.effect(println("Hello")))
  }
}


object AppZIO extends zio.App{
  def run(args: List[String]): URIO[ZEnv, ExitCode] = ZIO.effect(println("Hello")).exitCode
}
