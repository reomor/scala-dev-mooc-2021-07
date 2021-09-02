package hw03.futures

import scala.concurrent.{ExecutionContext, Future}

object task_futures_sequence {

  /** В данном задании Вам предлагается реализовать функцию fullSequence,
    * похожую на Future.sequence, но в отличии от нее,
    * возвращающую все успешные и не успешные результаты.
    * Возвращаемый тип функции - кортеж из двух списков,
    * в левом хранятся результаты успешных выполнений,
    * в правой результаты неуспешных выполнений.
    * Не допускается использование методов объекта Await и мутабельных переменных var
    */
  /** @param futures список асинхронных задач
    * @return асинхронную задачу с кортежем из двух списков
    */
  def fullSequence[A](futures: List[Future[A]])(implicit
      ex: ExecutionContext
  ): Future[(List[A], List[Throwable])] = {

    val handledFutures = futures map (f =>
      f map { v =>
        (List[A](v), List.empty[Throwable])
      } recover { ex =>
        (List.empty[A], List[Throwable](ex))
      }
    )

    Future.reduceLeft(handledFutures)((a, b) => (a._1 ++ b._1, a._2 ++ b._2))
  }
}
