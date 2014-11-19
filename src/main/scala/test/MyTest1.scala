package test

import scala.concurrent.blocking
import scala.concurrent.duration._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import java.util.concurrent.Executor
import akka.actor.{ChildStats, ChildNameReserved, Props, ActorSystem}
import scala.collection.immutable

/**
 * User: bigbully
 * Date: 14-9-29
 * Time: 下午2:02
 */
object MyTest1 extends App{

//  val pool = new MyExecutor
//  pool.execute(new Runnable {
//    override def run(): Unit = println("123")
//  })

  val system = ActorSystem("abc")
  val act = system.actorOf(Props[MyActor], "myActor")
  act ! "123"
  act ! "234"

  Thread.sleep(1000)

  println(system.actorSelection("akka://abc/user/myActor"))

//  val emptyStats = immutable.TreeMap.empty[String, String]
//  emptyStats.updated("123", "1")
//  emptyStats.updated("234", "2")
//
//  println(emptyStats)
}
