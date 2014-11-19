import scala.util.{Try, Success, Failure}

/**
 * User: bigbully
 * Date: 14-6-28
 * Time: 下午7:18
 */

def divide: Try[Int] = {
  val dividend = Try(Console.readLine("Enter an Int that you'd like to divide:\n").toInt)
  val divisor = Try(Console.readLine("Enter an Int that you'd like to divide by:\n").toInt)
  val problem = dividend.flatMap(x => divisor.map(y => x/y))
  problem match {
    case Success(v) =>
      println("Result of " + dividend.get + "/"+ divisor.get +" is: " + v)
      Success(v)
    case Failure(e) =>
      println("You must've divided by zero or entered something that's not an Int. Try again!")
      println("Info from the exception: " + e.getMessage)
      divide
  }
}


val s:Try[String] = Success("123")
s.map (_.toInt)
val o = Try()
val oo = o.recoverWith{
  case ex:NumberFormatException => Try(123)
  case _ => Try(28282828)
}
