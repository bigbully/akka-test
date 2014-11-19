package test

import akka.actor.Actor
import akka.cluster.ClusterEvent.MemberUp


/**
 * User: bigbully
 * Date: 14-7-20
 * Time: 下午7:13
 */

class MyAct extends Actor{

  import context._

  override def receive: Actor.Receive = {
    case "START" => actorSelection("user/clusterListener")
    case MemberUp(member) =>
      println("Member is Up: " +  member.address)
  }
}
