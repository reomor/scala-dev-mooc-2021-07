package module1

import module3.{printEffectRunningTime, zioConcurrency}
import zio.{ExitCode, URIO, ZEnv, ZIO}

import scala.language.postfixOps

object App {
  def main(args: Array[String]): Unit = {
    zio.Runtime.default.unsafeRun(printEffectRunningTime(zioConcurrency.greeter2))
  }
}

object AppZIO extends zio.App{
  def run(args: List[String]): URIO[ZEnv, ExitCode] = ZIO.effect(println("Hello")).exitCode
}
