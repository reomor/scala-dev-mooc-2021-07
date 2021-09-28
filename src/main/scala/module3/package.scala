import module3.zioConcurrency.currentTime
import zio.ZIO
import zio.clock.Clock
import zio.console.{Console, putStrLn}

package object module3 {
  /**
   * Напишите эффект, который будет считать время выполнения любого эффекта
   */
  def printEffectRunningTime[R, E, A](zio: ZIO[R, E, A]): ZIO[Clock with Console with R, E, A] = for{
    start <- currentTime
    z <- zio
    end <- currentTime
    _ <- putStrLn(s"Running time: ${end - start}")
  } yield z
}
