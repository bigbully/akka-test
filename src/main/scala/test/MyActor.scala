package test

import akka.actor.{ActorLogging, Props, Actor}

/**
 * User: bigbully
 * Date: 14/11/14
 * Time: 下午3:56
 */
class MyActor extends Actor with ActorLogging{
  import context._

  override def receive: Actor.Receive = {
    case "123" => {
      log.info("context:{}", this.context)
      context.actorOf(Props[MyActor], "myActor1")
    }

    case "234" => {
      actorOf(Props[MyActor], "myActor2")
    }
  }
}
