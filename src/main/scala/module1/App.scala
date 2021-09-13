package module1
import type_system.Rectangle
import module2.implicits
import zio.ZIO
import zio.ExitCode
import zio.ZEnv
import zio.URIO
import module3.zioOperators
import zio.Cause
import zio.console._
import zio.IO
import zio.duration._
import scala.language.postfixOps



object App {

  def main(args: Array[String]): Unit = {

    sealed trait NotificationError
    case object NotificationByEmailFailed extends NotificationError
    case object NotificationBySMSFailed extends NotificationError

    val z1 = ZIO.fail(NotificationByEmailFailed)
    val z2 = ZIO.fail(NotificationBySMSFailed)

    val z3 = z1.zipPar(z2)

    val z4 = z3.tapCause{
      case Cause.Both(left, right) => 
        putStrLn(left.failureOption.toString()) *> putStrLn(right.failureOption.toString())
    }.orElse(putStrLn("app failed"))
  

    zio.Runtime.default.unsafeRun(z4)
  }
}


object AppZIO extends zio.App{
  def run(args: List[String]): URIO[ZEnv, ExitCode] = ZIO.effect(println("Hello")).exitCode
}
