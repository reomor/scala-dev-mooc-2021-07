package module3

import zio.{Has, Task, ULayer, ZIO, ZLayer}
import zio.clock.{Clock, sleep}
import zio.console.{putStrLn, _}
import zio.duration.durationInt
import zio.macros.accessible
import zio.random._

import java.io.IOException
import java.util.concurrent.TimeUnit
import scala.io.StdIn
import scala.language.postfixOps

package object zio_homework {

  /** 1.
    * Используя сервисы Random и Console, напишите консольную ZIO программу,
    * которая будет предлагать пользователю угадать число от 1 до 3 и печатать в консоль угадал или нет.
    * Подумайте, на какие наиболее простые эффекты ее можно декомпозировать
    */

  lazy val easyGuessProgram: ZIO[Console with Random, IOException, Unit] = for {
    random <- ZIO.environment[Random].map(_.get)
    expected <- random.nextInt.map(randomInt => Math.abs(randomInt) % 3 + 1)
    console <- ZIO.environment[Console].map(_.get)
    _ <- console.putStrLn("Enter numeric number between [1, 3]")
    actual <- console.getStrLn.map(_.toInt)
  } yield {
    if (actual == expected) println("Match") else println("Not match")
  }

  /** Improvement
    */
  val randomIntZ: ZIO[Random, Nothing, Int] = for {
    random <- ZIO.environment[Random].map(_.get)
    value <- random.nextInt.map(randomInt => Math.abs(randomInt) % 3 + 1)
  } yield value

  val consoleReadIntZ: ZIO[Console, IOException, Int] = for {
    console <- ZIO.environment[Console].map(_.get)
    _ <- console.putStrLn("Enter numeric number between [1, 3]")
    value <- console.getStrLn.map(_.toInt)
  } yield value

  lazy val guessProgram: ZIO[Console with Random, IOException, Unit] = for {
    expected <- randomIntZ
    actual <- consoleReadIntZ
    _ <-
      if (actual == expected) {
        putStrLn("Match")
      } else
        putStrLn("Not match").zipRight(guessProgram)
  } yield ()

  /** 2. реализовать функцию doWhile (общего назначения),
    * которая будет выполнять эффект до тех пор, пока его значение в условии не даст true
    */
//  def doWhile[R, E, A](predicate: A => Boolean, effect: ZIO[R, E, A]): ZIO[R, E, A] =
//    effect.flatMap {
//      result => if (predicate(result)) ZIO() else effect
//    }

//  def doWhileHZ[R, E, A](predicate: A => Boolean, effect: ZIO[R, E, A]): ZIO[Any, Nothing, Unit] = for {
//    result <- effect.run
//    _ <-
//      result.foldM(
//        cause => ZIO.fail(cause.),
//        res => if (predicate(res))
//          ZIO.succeed(())
//        else
//          doWhileHZ(predicate, effect)
//      )
//  } yield ()

  def doWhile[R, E, A](
      effect: ZIO[R, E, A],
      predicate: A => Boolean
  ): ZIO[R, E, Unit] =
    effect.foldM(
      fail => ZIO.fail(fail),
      success =>
        if (predicate(success))
          ZIO.succeed(())
        else
          doWhile(effect, predicate)
    )

//  def doWhileDebuggable[R, E, A](
//      effect: ZIO[R, E, A],
//      predicate: A => Boolean
//  ): ZIO[R, E, Unit] =
//    effect.foldM(
//      fail => ZIO.fail(fail),
//      success =>
//        if (predicate(success)) {
//          println(success)
//          ZIO.succeed(())
//        } else {
//          println(success)
//          doWhile(effect, predicate)
//        }
//    )

  /** 3. Реализовать метод, который безопасно прочитает конфиг из файла, а в случае ошибки вернет дефолтный конфиг
    * и выведет его в консоль
    * Используйте эффект "load" из пакета config
    */

  def loadConfigOrDefault = ???

  /** 4. Следуйте инструкциям ниже для написания 2-х ZIO программ,
    * обратите внимание на сигнатуры эффектов, которые будут у вас получаться,
    * на изменение этих сигнатур
    */

  /** 4.1 Создайте эффект, который будет возвращать случайным образом выбранное число от 0 до 10 спустя 1 секунду
    * Используйте сервис zio Random
    */
  lazy val eff = ???

  /** 4.2 Создайте коллекцию из 10 выше описанных эффектов (eff)
    */
  lazy val effects = ???

  /** 4.3 Напишите программу которая вычислит сумму элементов коллекции "effects",
    * напечатает ее в консоль и вернет результат, а также залогирует затраченное время на выполнение,
    * можно использовать функцию printEffectRunningTime, которую мы разработали на занятиях
    */

  lazy val app = ???

  /** 4.4 Усовершенствуйте программу 4.3 так, чтобы минимизировать время ее выполнения
    */

  lazy val appSpeedUp = ???

  /** 5. Оформите функцию printEffectRunningTime разработанную на занятиях в отдельный сервис, так чтобы ее
    * можно было использовать аналогично zio.console.putStrLn например
    */

  /** 6.
    * Воспользуйтесь написанным сервисом, чтобы создать эффект,
    * который будет логировать время выполнения программы из пункта 4.3
    */

  lazy val appWithTimeLog = ???

  /** Подготовьте его к запуску и затем запустите воспользовавшись ZioHomeWorkApp
    */

  lazy val runApp = ???

}
