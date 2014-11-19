import akka.actor.{Props, ActorSystem}
import sample.cluster.simple.SimpleClusterListener
import test.MyAct

object HelloWorld {
  def main(args: Array[String]) {


    val system = ActorSystem("ClusterSystem")
    system.actorOf(Props[MyAct], name = "myAct")
  }
}

