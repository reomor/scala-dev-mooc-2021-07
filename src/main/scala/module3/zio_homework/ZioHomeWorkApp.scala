package module3.zio_homework

import zio.clock.Clock
import zio.console.{Console, getStrLn, putStrLn}
import zio.random.Random
import zio.{ExitCode, URIO, ZIO}

import java.io.IOException

object ZioHomeWorkApp extends zio.App {

  // https://www.youtube.com/watch?v=PaogLRrYo64&list=PLmtsMNDRU0Bzu7NfhTiGK7iCYjcFAYlal
  val myAppLogic: ZIO[Console, IOException, Unit] =
    for {
      _ <- putStrLn("Hello! What is your name?")
      name <- getStrLn
      _ <- putStrLn(s"Hello, ${name}, welcome to ZIO!")
    } yield ()

  override def run(
      args: List[String]
  ): URIO[Clock with Random with Console, ExitCode] =
    doWhile(randomIntZ, (a: Int) => a == 3).exitCode
//  guessProgram.exitCode
//  runApp.exitCode
}
